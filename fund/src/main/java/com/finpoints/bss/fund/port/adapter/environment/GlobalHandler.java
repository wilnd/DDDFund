package com.finpoints.bss.fund.port.adapter.environment;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finpoints.bss.common.ExceptionCode;
import com.finpoints.bss.fund.application.ApplicationException;
import com.finpoints.bss.fund.application.ApplicationExceptionCode;
import com.finpoints.bss.fund.port.adapter.restapi.NoBodyWarp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.finpoints.bss.fund.application.ApplicationExceptionCode.BadRequest;
import static com.finpoints.bss.fund.application.ApplicationExceptionCode.InternalServerError;

/**
 * 响应数据全局处理
 *
 * @author flynn shu
 * @date 2020/3/24
 */
@Slf4j
@ControllerAdvice
public class GlobalHandler implements ResponseBodyAdvice<Object> {

    private static final String ACCEPT_LANGUAGES = "Accept-Language";
    private static final String LANGUAGE_ZH_CN = "zh-CN";
    private static final String LANGUAGE_ZH_HK = "zh-HK";
    private static final String LANGUAGE_EN_US = "en-US";
    private static final Object NULL_OBJECT = new Object();

    private final List<String> responseNoWrapPaths = List.of(
            "/actuator/**"
    );
    private final PathMatcher pathMatcher = new AntPathMatcher();

    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;

    public GlobalHandler(ObjectMapper objectMapper, MessageSource messageSource) {
        this.objectMapper = objectMapper;
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorMessage defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {

        if (e instanceof ApplicationException ae) {
            // 平台异常
            return new ErrorMessage(ae.getExceptionCode(),
                    new CodeAndMessage(ae.getCode(), ae.getMessage()), ae.getArgs());
        } else if (e instanceof BindException ex) {
            // 方法参数校验失败
            if (ex.getBindingResult().hasErrors()) {
                Optional<ObjectError> firstError = ex.getBindingResult().getAllErrors().stream().findFirst();
                if (firstError.isPresent()) {
                    return new ErrorMessage(BadRequest,
                            new CodeAndMessage("IllegalArgument", firstError.get().getDefaultMessage()));
                }
            }
        } else if (e instanceof IllegalArgumentException) {
            // 参数错误
            return new ErrorMessage(BadRequest, new CodeAndMessage("IllegalArgument", e.getMessage()));
        } else if (e instanceof IllegalStateException) {
            // 不适当的状态
            return new ErrorMessage(BadRequest, new CodeAndMessage("IllegalState", e.getMessage()));
        } else if (e instanceof ConstraintViolationException) {
            // 违反约束条件
            StringBuilder builder = new StringBuilder("[");
            ((ConstraintViolationException) e).getConstraintViolations().forEach(violation -> {
                builder.append(violation.getMessage()).append(",");
            });
            String message = builder.substring(0, builder.length() - 1) + "]";
            return new ErrorMessage(BadRequest, new CodeAndMessage("IllegalArgument", message));
        } else if (e instanceof ValidationException) {
            // 参数校验异常
            return new ErrorMessage(BadRequest, new CodeAndMessage("IllegalArgument", e.getMessage()));
        } else if (e instanceof HttpMessageNotReadableException) {
            // 参数校验异常（枚举字段）
            return new ErrorMessage(BadRequest, new CodeAndMessage("IllegalArgument", e.getMessage()));
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            return new ErrorMessage(BadRequest, new CodeAndMessage("IllegalArgument", e.getMessage()));
        }
        log.error("GlobalHandler-未知异常", e);
        return new ErrorMessage(InternalServerError, new CodeAndMessage("UnknownError", e.getMessage()));
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 不拦截的路径
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if (responseNoWrapPaths.stream().anyMatch(pattern -> pathMatcher.match(pattern, request.getRequestURI()))) {
                return false;
            }
        }

        //获取当前处理请求的controller的方法
        Method method = methodParameter.getMethod();
        if (method == null) {
            return false;
        }
        NoBodyWarp warp = method.getAnnotation(NoBodyWarp.class);
        if (warp != null && warp.nowarp()) {
            return false;
        }

        // 不拦截不需要处理返回值的方法
        String name = method.getName();
        return !name.equals("uiConfiguration") && !name.equals("swaggerResources") && !name.equals("getDocumentation");
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (o == null) {
            return ResponseBase.ok(NULL_OBJECT);
        }
        if (o instanceof ErrorMessage errorMessage) {
            response.setStatusCode(errorMessage.getExceptionCode().status());
            String msg = errorMessage.getDefaultMsg() != null ? errorMessage.getDefaultMsg()
                    : i18nErrorMessage(errorMessage.getExceptionCode(), errorMessage.getArgs());
            return ResponseBase.of(errorMessage.getExceptionCode().code(), msg, errorMessage.getContent());
        } else if (o instanceof JsonNode) {
            return ResponseBase.ok(o.toString());
        } else if (o instanceof String) {
            try {
                return objectMapper.writeValueAsString(ResponseBase.ok(o));
            } catch (JsonProcessingException e) {
                return ResponseBase.of(InternalServerError.code(), i18nErrorMessage(InternalServerError), null);
            }
        }
        return ResponseBase.ok(o);
    }

    private String i18nErrorMessage(ExceptionCode exceptionCode, Object... args) {
        ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (attributes == null) {
            return exceptionCode.code();
        }
//        String acceptLanguages = StringUtils.isNotEmpty(attributes.getRequest().getHeader(ACCEPT_LANGUAGES)) ?
//                attributes.getRequest().getHeader(ACCEPT_LANGUAGES) : LANGUAGE_ZH_CN;
//        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(attributes.getRequest());
//        if (localeResolver != null) {
//            switch (acceptLanguages) {
//                case LANGUAGE_ZH_HK:
//                    localeResolver.setLocale(attributes.getRequest(), attributes.getResponse(), Locale.TRADITIONAL_CHINESE);
//                    break;
//                case LANGUAGE_EN_US:
//                    localeResolver.setLocale(attributes.getRequest(), attributes.getResponse(), Locale.US);
//                    break;
//                default:
//                    localeResolver.setLocale(attributes.getRequest(), attributes.getResponse(), Locale.SIMPLIFIED_CHINESE);
//            }
//        }
        try {
            return messageSource.getMessage(exceptionCode.code(), args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            log.warn(">>>>>> Exception code '{}' i18n message undefined.", exceptionCode.code());
            return exceptionCode.code();
        }
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new CustomDateEditor(objectMapper.getDateFormat(), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(objectMapper.getDateFormat(), true));
    }

    @Getter
    static class CodeAndMessage {
        private final String code;
        private final String msg;

        public CodeAndMessage(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Getter
    static class ErrorMessage {

        private final ExceptionCode exceptionCode;
        private final Object content;

        private Object[] args;
        private String defaultMsg;

        public ErrorMessage(ExceptionCode exceptionCode, Object[] args) {
            this.exceptionCode = exceptionCode;
            this.content = NULL_OBJECT;
            this.args = args;
        }

        public ErrorMessage(ExceptionCode exceptionCode, Object content, Object[] args) {
            this.exceptionCode = exceptionCode;
            this.content = content;
            this.args = args;
        }

        public ErrorMessage(ExceptionCode exceptionCode, Object content) {
            this.exceptionCode = exceptionCode;
            this.content = content;
        }
    }

    @Data
    static class ResponseBase<T> {

        private String code;
        private String msg;
        private T data;

        public ResponseBase(String code, String msg, T data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        public ResponseBase() {
        }

        public static <T> ResponseBase<T> of(String code, String msg, T data) {
            return new ResponseBase<T>(code, msg, data);
        }

        public static <T> ResponseBase<T> ok(T data) {
            return new ResponseBase<T>(ApplicationExceptionCode.OK.code(), ApplicationExceptionCode.OK.name(), data);
        }
    }
}
