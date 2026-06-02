package com.serverside.dt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.serverside.dt.entities.Stock;
import com.serverside.dt.repositories.StockRepository;

@Service
public class StockService {

	@Autowired
    private StockRepository repo;
  
    public Stock createStock(Stock stock) {
        return repo.save(stock);
    }

    public List<Stock> getAllStocks() {
        return repo.findAll();
    }

    public Optional<Stock> getStockById(Long id) {
        return repo.findById(id);
    }

    public Optional<Stock> updateStock(Long id, Stock updated) {
        return repo.findById(id).map(existing -> {
            existing.setTicker(updated.getTicker());
            existing.setCompanyName(updated.getCompanyName());
            existing.setAverageCost(updated.getAverageCost());
            existing.setShares(updated.getShares());
            existing.setDividendYield(updated.getDividendYield());
            existing.setPurchaseDate(updated.getPurchaseDate());
            existing.setAccount(updated.getAccount());
            return repo.save(existing);
        });
    }

    public boolean deleteStock(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
