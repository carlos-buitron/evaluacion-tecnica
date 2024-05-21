package com.microservice.mapper;

import com.microservice.model.Cuenta;
import com.microservice.model.response.ResponseCuenta;

public class ResponseCuentaMapper {
    public static ResponseCuenta buildResponseCuenta(Cuenta cuenta) {
        return ResponseCuenta.builder()
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipoCuenta(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldoInicial())
                .estado(cuenta.isEstado())
                .clienteId(cuenta.getClienteId())
                .build();
    }
}
