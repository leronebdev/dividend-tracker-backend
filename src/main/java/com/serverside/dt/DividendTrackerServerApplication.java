package com.serverside.dt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.serverside.dt.config.AlphaVantageProperties;
import com.serverside.dt.config.BusinessQuantProperties;
import com.serverside.dt.config.EodHdProperties;
import com.serverside.dt.config.FinnhubProperties;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({
	BusinessQuantProperties.class, FinnhubProperties.class, AlphaVantageProperties.class, EodHdProperties.class
})
@EnableCaching
public class DividendTrackerServerApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(DividendTrackerServerApplication.class, args);
	}

}
