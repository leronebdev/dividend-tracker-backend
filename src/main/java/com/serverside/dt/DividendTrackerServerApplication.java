package com.serverside.dt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.serverside.dt.config.BusinessQuantProperties;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(BusinessQuantProperties.class)
@EnableCaching
public class DividendTrackerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DividendTrackerServerApplication.class, args);
	}

}
