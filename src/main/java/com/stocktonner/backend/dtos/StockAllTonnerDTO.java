package com.stocktonner.backend.dtos;

import com.stocktonner.backend.entities.Tonner;

public class StockAllTonnerDTO {

    private Integer stockCurrent;

    public StockAllTonnerDTO(){

    }

    public StockAllTonnerDTO(Integer stockCurrent) {
        this.stockCurrent = stockCurrent;
    }

    public StockAllTonnerDTO(Tonner entity) {
        stockCurrent = entity.getStockCurrent();
    }

    public Integer getStockCurrent() {
        return stockCurrent;
    }

    public void setStockCurrent(Integer stockCurrent) {
        this.stockCurrent = stockCurrent;
    }
}
