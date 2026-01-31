package com.stocktonner.backend.services;

import com.stocktonner.backend.dtos.StockAllTonnerDTO;
import com.stocktonner.backend.dtos.RequestTonnerDTO;
import com.stocktonner.backend.dtos.TonnerDTO;
import com.stocktonner.backend.entities.Tonner;
import com.stocktonner.backend.repositories.TonnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TonnerService {

    @Autowired
    private TonnerRepository tonnerRepository;

    @Transactional(readOnly = true)
    public List<TonnerDTO> findAll(String model, String status){
        List<Tonner> list = tonnerRepository.searchByModel(model, status);
        return list.stream().map(x -> new TonnerDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public StockAllTonnerDTO findAllStockCurrentAll(){
        Integer tonner = tonnerRepository.searchSumCurrentAll();
        return new StockAllTonnerDTO(tonner);

        /* Declaramos um valor inteiro para receber a soma do repositorio. E não a entidade "Tonner" */
    }

    @Transactional(readOnly = true)
    public RequestTonnerDTO findAllRequestAll(){
        Integer requestTonner = tonnerRepository.searchSumRequest();
        return new RequestTonnerDTO(requestTonner);

        /* Declaramos um valor inteiro para receber a soma do repositorio. E não a entidade "Tonner" */
    }

    @Transactional
    public TonnerDTO insert(TonnerDTO dto){
        Tonner entity = new Tonner();

        entity.setModel(dto.getModel());
        entity.setStockMinimum(dto.getStockMinimum());
        entity.setStockCurrent(dto.getStockCurrent());

        entity.setStatus(dto.getStatus());
        entity.setRequest(dto.getRequest());

        entity = tonnerRepository.save(entity);

        return new TonnerDTO(entity);
    }
}
