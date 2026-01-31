package com.stocktonner.backend.dtos;

import com.stocktonner.backend.entities.Tonner;
import com.stocktonner.backend.entities.TonnerStatus;

public class TonnerDTO {
    private Long id;
    private String model;
    private Integer stockMinimum;
    private Integer stockCurrent;
    private TonnerStatus status;
    private Integer request;

    public TonnerDTO(){

    }

    public TonnerDTO(Long id, String model, Integer stockMinimum, Integer stockCurrent, TonnerStatus status, Integer request) {
        this.id = id;
        this.model = model;
        this.stockMinimum = stockMinimum;
        this.stockCurrent = stockCurrent;
        this.status = status;
        this.request = request;
    }

    public TonnerDTO(Tonner entity){
        id = entity.getId();
        model = entity.getModel();
        stockMinimum = entity.getStockMinimum();
        stockCurrent = entity.getStockCurrent();
        status = entity.getStatus();
        request = entity.getRequest();;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getStockMinimum() {
        return stockMinimum;
    }

    public void setStockMinimum(Integer stockMinimum) {
        this.stockMinimum = stockMinimum;
    }

    public Integer getStockCurrent() {
        return stockCurrent;
    }

    public void setStockCurrent(Integer stockCurrent) {
        this.stockCurrent = stockCurrent;
    }

    public TonnerStatus getStatus() {
        return status;
    }

    public void setStatus(TonnerStatus status) {
        this.status = status;
    }

    public Integer getRequest() {
        return request;
    }

    public void setRequest(Integer request) {
        this.request = request;
    }
}
