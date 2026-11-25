package com.serverside.dt.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

//@Bean Only need this for other implementations of cachine (Redis, Caffeine etc). Not for Ehcache
//	public CacheManager cacheManager() {
//
//		SimpleCacheManager cacheManager = new SimpleCacheManager();
//
//		cacheManager.setCaches(List.of(
//
//				new CaffeineCache("dividendHistoryBQ",
//						Caffeine.newBuilder().expireAfterWrite(Duration.ofHours(24)).maximumSize(1000).build()),
//				new CaffeineCache("dividendHistoryEOD",
//					Caffeine.newBuilder().expireAfterWrite(Duration.ofHours(24)).maximumSize(1000).build()),
//
//				new CaffeineCache("stockPricesFH",
//						Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(15)).maximumSize(1000).build()),
//				new CaffeineCache("stockPricesAV",
//					Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(15)).maximumSize(1000).build()),
//				
//				new CaffeineCache(
//						"exchangeRates",
//				
//						Caffeine.newBuilder().expireAfterWrite(Duration.ofHours(24)).maximumSize(100).build())));
//		
//		return cacheManager;
//	}	
	
}