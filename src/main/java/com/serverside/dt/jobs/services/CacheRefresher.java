package com.serverside.dt.jobs.services;

import com.serverside.dt.enums.Cache;

public interface CacheRefresher
{
    public boolean refreshCache();
    public Cache getCache();
}
