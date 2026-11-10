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
public class BusinessQuantWireMockServer implements CommandLineRunner {

    private final WireMockServer wireMockServer =
            new WireMockServer(
                    WireMockConfiguration.options()
                            .port(8089));

    @Override
    public void run(String... args) throws Exception {

        wireMockServer.start();

        registerStubs();

        log.info("BusinessQuant WireMock started on port 8089");
        log.info("View mappings at: http://localhost:8089/__admin/mappings");
    }

    private void registerStubs() throws IOException {

        wireMockServer.stubFor(
                get(urlPathEqualTo("/dividends"))
                        .withQueryParam(
                                "ticker",
                                matching(".*"))
                        .withQueryParam(
                                "mode",
                                equalTo("dps"))
                        .withQueryParam(
                                "api_key",
                                matching(".*"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader(
                                                "Content-Type",
                                                "application/json")
                                        .withBody(loadResponse())));
    }

    private String loadResponse() throws IOException {

        ClassPathResource resource =
                new ClassPathResource(
                        "wiremock/aapl-dividend-response.json");

        return new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8);
    }

    @PreDestroy
    public void shutdown() {

        if (wireMockServer.isRunning()) {

            log.info("Stopping BusinessQuant WireMock");

            wireMockServer.stop();
        }
    }
}