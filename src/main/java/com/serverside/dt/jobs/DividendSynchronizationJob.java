package com.serverside.dt.jobs;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.serverside.dt.jobs.services.DividendSynchronizationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DividendSynchronizationJob {

    private final DividendSynchronizationService synchronizationService;

    @Scheduled(cron = "${dividend-tracker.jobs.dividend-sync.cron}")
    public void execute() {

        log.info("Starting dividend synchronization");

        synchronizationService.synchronize();

        log.info("Dividend synchronization complete");
    }
}