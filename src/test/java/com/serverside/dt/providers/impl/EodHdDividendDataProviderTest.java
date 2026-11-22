package com.serverside.dt.providers.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.serverside.dt.clients.EodHdClient;
import com.serverside.dt.mappers.EodHdDividendMapper;
import com.serverside.dt.providers.EodHdDividendDataProvider;
import com.serverside.dt.records.DividendRecord;
import com.serverside.dt.records.EodHdDividendResponse;

@ExtendWith(MockitoExtension.class)
class EodHdDividendDataProviderTest {

    @Mock
    private EodHdClient eodHdClient;

    @Mock
    private EodHdDividendMapper eodHdDividendMapper;

    @InjectMocks
    private EodHdDividendDataProvider provider;

    @Test
    void shouldReturnMappedDividends() {

        String ticker = "CASH.TO";

        List<EodHdDividendResponse> response = List.of(
                new EodHdDividendResponse(
                        LocalDate.of(2026, 11, 26),
                        LocalDate.of(2026, 11, 17),
                        LocalDate.of(2026, 11, 26),
                        LocalDate.of(2026, 12, 2),
                        "Monthly",
                        new BigDecimal("0.0815"),
                        new BigDecimal("0.0815"),
                        "CAD"));

        List<DividendRecord> expected = List.of(
                new DividendRecord(
                        ticker,
                        LocalDate.of(2026, 11, 26),
                        LocalDate.of(2026, 12, 2),
                        new BigDecimal("0.0815")));

        when(eodHdClient.getDividends(ticker))
                .thenReturn(response);

        when(eodHdDividendMapper.toDividendRecords(
                response,
                ticker))
                .thenReturn(expected);

        List<DividendRecord> actual =
                provider.getDividends(ticker);

        assertEquals(expected, actual);

        verify(eodHdClient)
                .getDividends(ticker);

        verify(eodHdDividendMapper)
                .toDividendRecords(response, ticker);
    }

    @Test
    void shouldReturnEmptyListWhenClientReturnsNothing() {

        String ticker = "CASH.TO";

        when(eodHdClient.getDividends(ticker))
                .thenReturn(List.of());

        when(eodHdDividendMapper.toDividendRecords(
                List.of(),
                ticker))
                .thenReturn(List.of());

        List<DividendRecord> result =
                provider.getDividends(ticker);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldSupportTorontoTickers() {

        assertTrue(
                provider.supports("CASH.TO"));

        assertTrue(
                provider.supports("BND.TO"));

        assertTrue(
                provider.supports("CDZ.TO"));
    }

    @Test
    void shouldNotSupportNonTorontoTickers() {

        assertFalse(
                provider.supports("AAPL"));

        assertFalse(
                provider.supports("MSFT"));

        assertFalse(
                provider.supports("VOO"));
    }
}