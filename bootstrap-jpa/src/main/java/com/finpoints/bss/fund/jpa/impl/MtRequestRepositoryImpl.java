package com.finpoints.bss.fund.jpa.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.fund.domain.model.mt.*;
import com.finpoints.bss.fund.domain.model.mt.command.MtWithdrawalCommand;
import com.finpoints.bss.fund.jpa.CrudRepositoryImpl;
import com.finpoints.bss.fund.jpa.JpaEntityConverter;
import com.finpoints.bss.fund.jpa.mt.JpaMtRequest;
import com.finpoints.bss.fund.jpa.mt.JpaMtRequestRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MtRequestRepositoryImpl extends CrudRepositoryImpl<MtRequest, MtRequestId, JpaMtRequest>
        implements MtRequestRepository {

    private final JpaMtRequestRepository mtRequestRepository;

    public MtRequestRepositoryImpl(JpaMtRequestRepository mtRequestRepository,
                                   ObjectMapper objectMapper) {
        super(new MtRequestConverter(objectMapper), mtRequestRepository);
        this.mtRequestRepository = mtRequestRepository;
    }

    @Override
    public MtRequestId nextId() {
        return new MtRequestId(IdentityGenerator.nextIdentity());
    }

    @Override
    public MtRequest findById(MtRequestId requestId) {
        JpaMtRequest mtRequest = mtRequestRepository.findByRequestId(requestId.rawId());
        return convertToDomain(mtRequest);
    }

    @Override
    public boolean existsById(MtRequestId requestId) {
        return mtRequestRepository.existsByRequestId(requestId.rawId());
    }

    public static class MtRequestConverter implements JpaEntityConverter<MtRequest, JpaMtRequest> {

        private final ObjectMapper objectMapper;

        public MtRequestConverter(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public MtRequest toDomainEntity(JpaMtRequest persistenceEntity) {

            MtWithdrawalCommand command = null;
            JsonNode responseContent = null;
            if (persistenceEntity.getCommand() != null) {
                try {
                    Class<? extends MtRequestCommand> requestType = resolveCommandType(persistenceEntity.getType());
                    command = (MtWithdrawalCommand) objectMapper.readValue(persistenceEntity.getCommand(), requestType);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (persistenceEntity.getResponseContent() != null) {
                try {
                    responseContent = objectMapper.readTree(persistenceEntity.getResponseContent());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            return new MtRequest(
                    new MtRequestId(persistenceEntity.getRequestId()),
                    persistenceEntity.getServerType(),
                    new MtServerId(persistenceEntity.getServerId()),
                    persistenceEntity.getAccount(),
                    persistenceEntity.getType(),
                    command,
                    persistenceEntity.getStatus(),
                    persistenceEntity.getResponseMessage(),
                    responseContent
            );
        }

        @Override
        public JpaMtRequest toPersistenceEntity(MtRequest domainEntity) {

            String command = null;
            String responseContent = null;

            if (domainEntity.getCommand() != null) {
                try {
                    command = objectMapper.writeValueAsString(domainEntity.getCommand());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (domainEntity.getResponseContent() != null) {
                try {
                    responseContent = objectMapper.writeValueAsString(domainEntity.getResponseContent());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            return new JpaMtRequest(
                    domainEntity.getRequestId().rawId(),
                    domainEntity.getServerType(),
                    domainEntity.getServerId().rawId(),
                    domainEntity.getAccount(),
                    domainEntity.getType(),
                    command,
                    domainEntity.getStatus(),
                    domainEntity.getResponseMessage(),
                    responseContent
            );
        }

        private Class<? extends MtRequestCommand> resolveCommandType(MtRequestType requestType) {
            if (requestType == MtRequestType.Withdrawal) {
                return MtWithdrawalCommand.class;
            }
            return null;
        }
    }
}
