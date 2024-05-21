package com.microservice.component.impl;

import com.microservice.component.ClienteComponent;
import com.microservice.configuration.EndpointsConfiguration;
import com.microservice.dto.ClienteDto;

import com.microservice.exception.ApiException;
import com.microservice.libraries.WebClientUtil;
import com.microservice.model.response.ResponseApi;
import com.microservice.util.Converters;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@AllArgsConstructor
public class ClienteComponentImp implements ClienteComponent {
    private final WebClient webClient;
    private final EndpointsConfiguration endpoints;

    @Autowired
    public ClienteComponentImp(WebClient.Builder webClientBuilder, EndpointsConfiguration config) {
        this.webClient = webClientBuilder.build();
        this.endpoints = config;
    }


    @Override
    public ClienteDto getCliente(String identificacion) {
       log.info("{}", String.format("Obteniendo Json por medio de una identficacion del cliente %s.", identificacion));
        try {
            ClienteDto clienteDto = WebClientUtil.executeCall(
                    webClient,
                    HttpMethod.GET,
                    pathBaseUrlClientee(identificacion).replace("{{identificacion}}", identificacion),
                    ClienteDto.class,
                    String.class,
                    identificacion
            );
            log.info("{}", "Json Obtenido OK ...");
            return clienteDto;
        } catch (Exception ex) {
            log.error("{}", String.format("Error al obtener el cliente con identificacion %s", identificacion));
            throw new ApiException(String.format("Error al obtener el cliente con identificacion %s", identificacion));
        }
    }

    @Override
    public ClienteDto getCliente(Long id) {
        log.info("{}", String.format("Obteniendo Json por medio de una ID del cliente %s.", id));
        try {
            ResponseApi respuesta = WebClientUtil.executeCall(
                    webClient,
                    HttpMethod.GET,
                    pathBaseUrlCliente(id).replace("{{id}}", String.valueOf(id)),
                    ResponseApi.class,
                    Long.class,
                    id
            );
            log.info("{}", "Json Obtenido OK ...");
            return Converters.convertToGeneric(
                    respuesta.getResultado(),
                    ClienteDto.class);
        } catch (Exception ex) {
            log.error("{}", String.format("Error al obtener el cliente con una ID %s", id));
            throw new ApiException(String.format("Error al obtener el cliente con una ID %s", id));
        }
    }

    private String pathBaseUrlClientee(String identificacion) {
        if (identificacion == null || identificacion.isEmpty()) {
            throw new ApiException("Identificacion del cliente inválido");
        }
        return endpoints.getObtenerClienteIdentificacion();
    }

    private String pathBaseUrlCliente(Long id) {
        if (id == null) {
            throw new ApiException("Id del cliente inválido");
        }
        return endpoints.getObtenerClienteId();
    }

}