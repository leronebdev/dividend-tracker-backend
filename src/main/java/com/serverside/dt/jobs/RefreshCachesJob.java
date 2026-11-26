package com.serverside.dt.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.serverside.dt.enums.Cache;
import com.serverside.dt.jobs.services.CacheRefresherFactory;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RefreshCachesJob
{
    private CacheRefresherFactory cacheRefresherFactory;
    
    

    @Scheduled(cron = "${dividend-tracker.jobs.cache-refresh.cron}", zone = "${dividend-tracker.jobs.cache-refresh.timezone}")
    public void refreshAllCaches() {

        log.info("Starting refresh for caches");
        Cache[] caches =Cache.values();
        for(int i_eachCache = 0; i_eachCache<caches.length;i_eachCache++)
        {
            cacheRefresherFactory.getInstance(caches[i_eachCache]).refreshCache();
        }
        log.info("Cache refresh complete");
    }
}
