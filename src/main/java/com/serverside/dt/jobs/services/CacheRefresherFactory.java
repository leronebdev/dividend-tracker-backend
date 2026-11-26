package com.serverside.dt.jobs.services;

import java.util.Set;
import org.springframework.stereotype.Component;
import com.github.tomakehurst.wiremock.admin.NotFoundException;
import com.serverside.dt.enums.Cache;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CacheRefresherFactory
{
    private final Set<CacheRefresher>cacheRefreshers;
    
    public CacheRefresher getInstance(Cache cache)
    {
	return cacheRefreshers.stream().filter(cacheRefresher->cacheRefresher.getCache().equals(cache))
		.findFirst().orElseThrow(()->new NotFoundException("No Cache refresher was found for "  + cache.toString()));
    }
}
