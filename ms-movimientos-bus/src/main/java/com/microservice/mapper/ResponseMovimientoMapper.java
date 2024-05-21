package com.microservice.mapper;


import com.microservice.model.Movimiento;
import com.microservice.model.response.ResponseMovimiento;

public class ResponseMovimientoMapper {
    public static ResponseMovimiento buildResponseCuenta(Movimiento movimiento) {
        return ResponseMovimiento.builder()
                .fecha(movimiento.getFecha())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .valor(movimiento.getValor())
                .saldoActual(movimiento.getSaldoActual())
                .build();
    }
}
