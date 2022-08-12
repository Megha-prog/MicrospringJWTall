package com.example.catalogue.service;

import com.example.catalogue.common.*;
import com.example.catalogue.model.Catalogue;
import com.example.catalogue.repository.CatalogueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatalogueService {
    @Autowired
    CatalogueRepository catalogueRepository;

    @Autowired
    private RestTemplate restTemplate;

    public CatalogueResponse createPurchase(CatalogueRequest catalogueRequest){
        String response = "";
        Catalogue catalogue=catalogueRequest.getCatalogue();
        catalogueRepository.save(catalogue);

        Order order=catalogueRequest.getOrder();
        order.setCatalogueCategory(catalogue.getCategory());
        order.setCatalogueItemName(catalogue.getItemName());
        order.setId(catalogue.getId());

        TransactionRequest transactionRequest=new TransactionRequest();
        transactionRequest.setOrder(order);
        transactionRequest.setPayment(new Payment());
        TransactionResponse transactionResponse=new TransactionResponse();

        transactionResponse = restTemplate.
                postForObject("http://ORDER-SERVICE/order/bookOrder",transactionRequest,
                        TransactionResponse.class);

        return new CatalogueResponse(catalogue, transactionResponse.getAmount(),
                transactionResponse.getOrder().getQty(), "Order has been  placed successfully");

    }



}
