package com.orbital3d.web.application.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.orbital3d.web.application.service.ClientCodeService;
import com.orbital3d.web.application.service.type.ClientCode;

/**
 * Implementation of {@link ClientCodeService}.
 * 
 * This implementation uses REST API to resolve client code. The endpoint can be configured via {@code client.code.endpoint} property.
 * By default, it is set to {@code http://localhost:5100/clientcode}.
 */
@Service
public class ClientCodeServiceImpl implements ClientCodeService {

    private final RestClient restClient = RestClient.create();

    @Value("${client.code.endpoint:http://localhost:5100/clientcode}")
    private String clientCodeEndpoint;

    public ClientCodeServiceImpl() {
        // Never mind
    }

    @Override
    public ClientCode resolveClientCode() {
        try {
            return restClient.get().uri(clientCodeEndpoint).retrieve().body(ClientCode.class);
        } catch (Exception e) {
            return new ClientCode(UUID.randomUUID().toString());
        }
    }

}
