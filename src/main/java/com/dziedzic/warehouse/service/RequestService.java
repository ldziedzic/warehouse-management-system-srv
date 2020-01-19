package com.dziedzic.warehouse.service;

import com.dziedzic.warehouse.model.Request;
import com.dziedzic.warehouse.repository.RequestRepository;
import com.dziedzic.warehouse.security.TokenProvider;

import org.springframework.stereotype.Service;


@Service
public class RequestService {

    private final RequestRepository requestRepository;


    public RequestService(RequestRepository requestRepository, TokenProvider tokenProvider) {
        this.requestRepository = requestRepository;
    }

    public boolean checkIfRequestPerformed(String guid) {
        return requestRepository.findByGuid(guid).isPresent();
    }

    public void markRequestAsPerformed(String guid) {
        Request request = new Request(guid);
        requestRepository.save(request);
    }
}
