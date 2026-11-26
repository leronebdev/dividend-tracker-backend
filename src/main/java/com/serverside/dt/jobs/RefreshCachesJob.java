package com.serverside.dt.jobs;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.serverside.dt.jobs.services.DividendSynchronizationOrchestrator;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RefreshCachesJob
{
    
    //private final DividendSynchronizationService synchronizationService;
    private final DividendSynchronizationOrchestrator synchronizationService;

    @Scheduled(cron = "${dividend-tracker.jobs.dividend-sync.cron}", zone = "${dividend-tracker.jobs.dividend-sync.timezone}")
    public void refreshAllCaches() {

        log.info("Starting dividend synchronization");

        synchronizationService.synchronize();

        log.info("Dividend synchronization complete");
    }
}
