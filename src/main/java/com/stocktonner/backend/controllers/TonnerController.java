package com.stocktonner.backend.controllers;

import com.stocktonner.backend.dtos.StockAllTonnerDTO;
import com.stocktonner.backend.dtos.RequestTonnerDTO;
import com.stocktonner.backend.dtos.TonnerDTO;
import com.stocktonner.backend.services.TonnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tonners")
public class TonnerController {

    @Autowired
    private TonnerService tonnerService;

    @GetMapping
    public List<TonnerDTO> findAll(
            @RequestParam(name = "model", defaultValue = "") String model,
            @RequestParam(name = "status", defaultValue = "") String status){
        return tonnerService.findAll(model, status);
    }


    @GetMapping(value = "/estoquetotal")
    public StockAllTonnerDTO findAllStockCurrentAll(){
        return tonnerService.findAllStockCurrentAll();
    }


    @GetMapping(value = "/solicitartotal")
    public RequestTonnerDTO findAllRequestAll(){
        return tonnerService.findAllRequestAll();
    }

    @PostMapping
    public TonnerDTO insert(@RequestBody TonnerDTO dto){
        return tonnerService.insert(dto);
    }
}


