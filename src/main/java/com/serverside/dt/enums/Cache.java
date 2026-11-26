package com.serverside.dt.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Cache {
    STOCK_PRICES_FH("stockPricesFH"),
    STOCK_PRICES_AV("stockPricesAV"),
    DIVIDEND_HISTORY_EOD("dividendHistoryEOD"),
    EXCHANGE_RATES("exchangeRates"),
    DIVIDEND_HISTORY_BQ("dividendHistoryBQ");

    private final String cacheName;

    Cache(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public String toString() {	
	
	return cacheName;
    }

    /**
     * Gets an enum by its cache name string.
     * @param cacheName the cache name to look up
     * @return the matching CacheEnum, or null if not found
     */
    public static Cache fromCacheName(String cacheName) {
        for (Cache entry : values()) {
            if (entry.cacheName.equals(cacheName)) {
                return entry;
            }
        }
        return null;
    }
    public static List<String> getAllCacheNames()
    {
	List<Cache> allCaches = Arrays.asList(Cache.values());
	List<String> names = allCaches.stream()
	    .map(Cache::getCacheName)
	    .collect(Collectors.toList());
	return names;

    }
    /**
     * Gets an enum by its cache name string.
     * @param cacheName the cache name to look up
     * @return the matching CacheEnum, or throws IllegalArgumentException if not found
     */
    public static Cache fromCacheNameOrThrow(String cacheName) {
        Cache result = fromCacheName(cacheName);
        if (result == null) {
            throw new IllegalArgumentException("No CacheEnum with cache name '" + cacheName + "' exists. Valid names are: " + joinNames(values()));
        }
        return result;
    }

    private static String joinNames(Cache[] enums) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < enums.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append('\'').append(enums[i].cacheName).append('\'');
        }
        return sb.toString();
    }

    public String getCacheName() {
        return cacheName;
    }

}