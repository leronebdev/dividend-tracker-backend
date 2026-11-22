package com.serverside.dt.mock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Component
@Profile("wiremock")
@Slf4j
public class EodHdWireMockServer implements CommandLineRunner {

	private final WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8090));

	@Override
	public void run(String... args) throws Exception {

		wireMockServer.start();

		registerDividendStubs();

		log.info("EODHD WireMock started on port 8090");
	}

	private void registerDividendStubs() {

		wireMockServer.stubFor(get(urlPathEqualTo("/api/div/CASH.TO")).withQueryParam("api_token", matching(".*"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
						.withBody(loadResponse("wiremock/cash-dividend-response.json"))));

		wireMockServer.stubFor(get(urlPathEqualTo("/api/div/BND.TO")).withQueryParam("api_token", matching(".*"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
						.withBody(loadResponse("wiremock/bnd-dividend-response.json"))));

		wireMockServer.stubFor(get(urlPathEqualTo("/api/div/CDZ.TO")).withQueryParam("api_token", matching(".*"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
						.withBody(loadResponse("wiremock/cdz-dividend-response.json"))));
	}

	private String loadResponse() throws IOException {

		ClassPathResource resource = new ClassPathResource("wiremock/eodhd-dividend-response.json");

		return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
	}

	private String loadResponse(String path) {

		try {

			ClassPathResource resource = new ClassPathResource(path);

			return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

		} catch (Exception ex) {

			throw new RuntimeException("Failed to load mock response: " + path, ex);
		}
	}

	@PreDestroy
	public void shutdown() {

		if (wireMockServer.isRunning()) {

			log.info("Stopping EODHD WireMock");

			wireMockServer.stop();
		}
	}
}