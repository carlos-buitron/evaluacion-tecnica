package com.microservice.mapper;

import com.microservice.dto.ClienteDto;
import com.microservice.model.Cuenta;
import com.microservice.model.request.RequestApi;

public class CuentaMapper {
    public static Cuenta buildCuenta(RequestApi requestApi) {
        return Cuenta.builder()
                .numeroCuenta(requestApi.getNumeroCuenta())
                .tipoCuenta(requestApi.getTipoCuenta())
                .saldoInicial(requestApi.getSaldoInicial())
                .estado(requestApi.getEstado())
                .clienteId(requestApi.getClienteId())
                .build();
    }

    public static Cuenta buildCuenta(RequestApi requestApi, ClienteDto cliente) {
        return Cuenta.builder()
                .numeroCuenta(requestApi.getNumeroCuenta())
                .tipoCuenta(requestApi.getTipoCuenta())
                .saldoInicial(requestApi.getSaldoInicial())
                .estado(requestApi.getEstado())
                .clienteId(cliente.getId())
                .build();
    }

}
