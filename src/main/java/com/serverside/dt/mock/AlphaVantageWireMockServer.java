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
public class AlphaVantageWireMockServer implements CommandLineRunner {

	private final WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8091));

	@Override
	public void run(String... args) throws Exception {

		wireMockServer.start();

		registerGlobalQuoteStub("CASH.TO", "wiremock/alphavantage/cash-global-quote.json");

		registerGlobalQuoteStub("BND.TO", "wiremock/alphavantage/bnd-global-quote.json");

		registerGlobalQuoteStub("CDZ.TO", "wiremock/alphavantage/cdz-global-quote.json");

		log.info("AlphaVantage WireMock started on port 8091");
	}

	private void registerGlobalQuoteStub(String ticker, String responseFile) throws IOException {

		wireMockServer.stubFor(get(urlPathEqualTo("/query")).withQueryParam("function", equalTo("GLOBAL_QUOTE"))
				.withQueryParam("symbol", equalTo(ticker)).withQueryParam("apikey", matching(".*"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
						.withBody(loadResponse(responseFile))));
	}

	private String loadResponse(String path) throws IOException {

		ClassPathResource resource = new ClassPathResource(path);

		return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
	}

	@PreDestroy
	public void shutdown() {

		if (wireMockServer.isRunning()) {
			wireMockServer.stop();
		}
	}
}