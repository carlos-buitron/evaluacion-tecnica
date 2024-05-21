package com.microservice.service;

import com.microservice.model.ReporteEstadoCuenta;

import java.time.LocalDateTime;

public interface ReporteService {
    ReporteEstadoCuenta generarReporteEstadoCuenta(LocalDateTime fechaInicio, LocalDateTime fechaFin, Long clienteId);

}
