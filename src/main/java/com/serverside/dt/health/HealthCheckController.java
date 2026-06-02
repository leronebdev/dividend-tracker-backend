package com.serverside.dt.health;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController	
@RequestMapping("/health")
public class HealthCheckController {
	
	 @GetMapping("/app")
	    public Map<String, String> health() {
	        return Map.of("status", "UP", "message", "Dividend Tracker backend is healthy");
	    }

}
