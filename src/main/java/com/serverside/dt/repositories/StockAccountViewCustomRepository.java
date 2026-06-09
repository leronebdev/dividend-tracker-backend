package com.serverside.dt.repositories;

import java.util.List;

import com.serverside.dt.dtos.StockAccountProjectionDTO;

public interface StockAccountViewCustomRepository {
    List<StockAccountProjectionDTO> fetchAllFromView();
}
