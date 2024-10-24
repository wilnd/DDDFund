package com.finpoints.bss.fund.port.adapter.mock;

import com.finpoints.bss.common.domain.model.Operator;
import com.finpoints.bss.common.requester.CurrentRequesterService;
import com.finpoints.bss.common.requester.Requester;
import org.springframework.stereotype.Component;

@Component
public class MockRequesterServiceImpl implements CurrentRequesterService {

    @Override
    public Requester currentRequester() {
        return new Requester(
                "SYSTEM",
                "system",
                "System",
                "0.0.0.0"
        );
    }
}
