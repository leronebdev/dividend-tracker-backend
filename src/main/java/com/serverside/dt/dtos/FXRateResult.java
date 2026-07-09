package com.serverside.dt.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FXRateResult {
    private String date;
    private String from;
    private String to;
    private BigDecimal rate;

}
