package com.finpoints.bss.fund.port.adapter.restapi.internal;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/fund/internal/event")
public class EventsController {

    private final IncompleteEventPublications incompleteEventPublications;

    public EventsController(IncompleteEventPublications incompleteEventPublications) {
        this.incompleteEventPublications = incompleteEventPublications;
    }

    @Operation(summary = "Resubmit incomplete event publications")
    @PutMapping("/publications")
    public void resubmitEventPublication(@RequestParam(defaultValue = "10") Integer durationSeconds) {
        incompleteEventPublications.resubmitIncompletePublicationsOlderThan(Duration.ofSeconds(durationSeconds));
    }


}
