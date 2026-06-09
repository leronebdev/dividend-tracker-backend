package com.serverside.dt.repositories;

import java.sql.Array;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.serverside.dt.dtos.StockAccountProjectionDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockAccountViewCustomRepositoryImpl implements StockAccountViewCustomRepository {

    private final JdbcTemplate jdbc;

    @Override
    public List<StockAccountProjectionDTO> fetchAllFromView() {

        String sql = "SELECT * FROM vw_stock_request";

        return jdbc.query(sql, (rs, rowNum) -> {

            // Extract DATE[] array from Postgres
            Array array = rs.getArray("payout_dates");

            List<String> payoutDates = List.of();

            if (array != null) {
                // Postgres returns java.sql.Date[]
                Date[] dateArray = (Date[]) array.getArray();
                if (dateArray != null) {
                payoutDates = Arrays.stream(dateArray)
                		.filter(Objects::nonNull)
                        .map(Date::toLocalDate)
                        .map(Object::toString)
                        .toList();
                }
            }

            return StockAccountProjectionDTO.builder()
                    .id(rs.getString("stock_id"))
                    .ticker(rs.getString("ticker"))
                    .company(rs.getString("company"))
                    .account(rs.getString("account_number"))
                    .currency(rs.getString("currency_code"))

                    .shares(rs.getBigDecimal("shares"))
                    .avgCost(rs.getBigDecimal("average_price"))

                    // Not in view
                    .purchaseDate(null)

                    .soldDate(rs.getDate("sold_date") != null
                            ? rs.getDate("sold_date").toString()
                            : null)

                    .dividendPerShare(rs.getBigDecimal("dividend_per_share"))

                    // Not in view
                    .dividendYield(null)

                    .payoutFrequency(
                            rs.getObject("payout_frequency") != null
                                    ? rs.getObject("payout_frequency").toString()
                                    : null
                    )

                    .exDividendDate(rs.getDate("ex_date") != null
                            ? rs.getDate("ex_date").toString()
                            : null)

                    .payoutDates(payoutDates)

                    .lastPayoutDate(rs.getDate("last_payout_date") != null
                            ? rs.getDate("last_payout_date").toString()
                            : null)

                    .build();
        });
    }
}
