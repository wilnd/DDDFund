package com.finpoints.bss.fund.mybatis.event;

import org.springframework.modulith.events.core.EventPublicationRepository;
import org.springframework.modulith.events.core.PublicationTargetIdentifier;
import org.springframework.modulith.events.core.TargetEventPublication;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MybatisEventPublicationRepository implements EventPublicationRepository {

    @Override
    public TargetEventPublication create(TargetEventPublication publication) {
        return null;
    }

    @Override
    public void markCompleted(Object event, PublicationTargetIdentifier identifier, Instant completionDate) {

    }

    @Override
    public List<TargetEventPublication> findIncompletePublications() {
        return List.of();
    }

    @Override
    public List<TargetEventPublication> findIncompletePublicationsPublishedBefore(Instant instant) {
        return List.of();
    }

    @Override
    public Optional<TargetEventPublication> findIncompletePublicationsByEventAndTargetIdentifier(Object event, PublicationTargetIdentifier targetIdentifier) {
        return Optional.empty();
    }

    @Override
    public void deletePublications(List<UUID> identifiers) {

    }

    @Override
    public void deleteCompletedPublications() {

    }

    @Override
    public void deleteCompletedPublicationsBefore(Instant instant) {

    }
}
