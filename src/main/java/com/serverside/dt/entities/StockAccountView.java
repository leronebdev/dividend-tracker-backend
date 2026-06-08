package com.serverside.dt.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vw_stock_request")
public class StockAccountView {

    @Id
    private UUID stockId; // must exist in the view

    // No fields needed — projection handles everything
}
