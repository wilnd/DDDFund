package com.finpoints.bss.fund.jpa.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.finpoints.bss.common.domain.model.IdentityGenerator;
import com.finpoints.bss.common.event.ComponentPathSerializer;
import com.finpoints.bss.fund.domain.model.mt.*;
import com.finpoints.bss.fund.domain.model.mt.command.MtWithdrawalCommand;
import com.finpoints.bss.fund.jpa.CrudRepositoryImpl;
import com.finpoints.bss.fund.jpa.JpaEntityConverter;
import com.finpoints.bss.fund.jpa.mt.JpaMtRequest;
import com.finpoints.bss.fund.jpa.mt.JpaMtRequestRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class MtRequestRepositoryImpl extends CrudRepositoryImpl<MtRequest, MtRequestId, JpaMtRequest>
        implements MtRequestRepository {

    private final JpaMtRequestRepository mtRequestRepository;

    public MtRequestRepositoryImpl(JpaMtRequestRepository mtRequestRepository,
                                   ComponentPathSerializer serializer) {
        super(new MtRequestConverter(serializer), mtRequestRepository);
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

        private final ComponentPathSerializer serializer;

        public MtRequestConverter(ComponentPathSerializer serializer) {
            this.serializer = serializer;
        }

        @Override
        public MtRequest toDomainEntity(JpaMtRequest persistenceEntity) {

            MtRequestCommand command = null;
            JsonNode responseContent = null;
            if (StringUtils.isNotBlank(persistenceEntity.getCommand())) {
                Class<? extends MtRequestCommand> requestType = resolveCommandType(persistenceEntity.getType());
                command = serializer.deserialize(persistenceEntity.getCommand(), requestType);
            }
            if (StringUtils.isNotBlank(persistenceEntity.getResponseContent())) {
                responseContent = serializer.deserialize(persistenceEntity.getResponseContent(), JsonNode.class);
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
                command = (String) serializer.serialize(domainEntity.getCommand());
            }
            if (domainEntity.getResponseContent() != null) {
                responseContent = (String) serializer.serialize(domainEntity.getResponseContent());
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
