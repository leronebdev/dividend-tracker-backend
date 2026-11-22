package com.serverside.dt.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.serverside.dt.records.DividendRecord;
import com.serverside.dt.records.EodHdDividendResponse;

@Mapper(componentModel = "spring")
public interface EodHdDividendMapper {

    @Mapping(target = "ticker", expression = "java(ticker)")
    @Mapping(target = "exDate", source = "response.date")
    @Mapping(target = "paymentDate", source = "response.paymentDate")
    @Mapping(target = "dividendPerShare", source = "response.value")
    DividendRecord toDividendRecord(
            EodHdDividendResponse response,
            String ticker);

    default List<DividendRecord> toDividendRecords(
            List<EodHdDividendResponse> responses,
            String ticker) {

        return responses.stream()
                .map(response ->
                        toDividendRecord(response, ticker))
                .toList();
    }
}