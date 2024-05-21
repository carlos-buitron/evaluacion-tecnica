package com.microservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ReporteEstadoCuenta {
    private List<Cuenta> cuentas;
    private Map<Cuenta, List<Movimiento>> detalleMovimientos;

}
