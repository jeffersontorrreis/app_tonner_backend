package com.stocktonner.backend.dtos;

import com.stocktonner.backend.entities.Tonner;

public class RequestTonnerDTO {

    private Integer request;

    public RequestTonnerDTO(){

    }

    public RequestTonnerDTO(Integer request) {
        this.request = request;
    }

    public RequestTonnerDTO(Tonner entitiy) {
        request = entitiy.getRequest();
    }

    public Integer getRequest() {
        return request;
    }

    public void setRequest(Integer request) {
        this.request = request;
    }
}
