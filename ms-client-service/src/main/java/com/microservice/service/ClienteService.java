package com.microservice.service;

import com.microservice.model.request.RequestApi;
import com.microservice.model.response.ResponseCliente;

public interface ClienteService {
    ResponseCliente create(RequestApi request);
    ResponseCliente update(RequestApi request);
    ResponseCliente update(Long id, RequestApi requestApi);
    ResponseCliente update(String identificacion);
    void eliminar(Long id);
    ResponseCliente get(String identificacion);
    ResponseCliente get(Long id);
}
