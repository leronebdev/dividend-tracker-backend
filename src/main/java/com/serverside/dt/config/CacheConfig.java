package com.serverside.dt.config;

import java.time.Duration;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	public CacheManager cacheManager() {

		SimpleCacheManager cacheManager = new SimpleCacheManager();

		cacheManager.setCaches(List.of(

				new CaffeineCache("dividendHistory",
						Caffeine.newBuilder().expireAfterWrite(Duration.ofHours(24)).maximumSize(1000).build()),

				new CaffeineCache("stockPrices",
						Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(15)).maximumSize(1000).build()),

				new CaffeineCache(
						"exchangeRates",
						Caffeine.newBuilder().expireAfterWrite(Duration.ofHours(24)).maximumSize(100).build())));

		return cacheManager;
	}
}