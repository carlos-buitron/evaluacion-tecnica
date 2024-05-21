package com.microservice.dto;

import com.microservice.model.Cuenta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDto {
    private BigDecimal saldoActual;
    private String tipoMovimiento;
    private LocalDateTime fecha;
    private BigDecimal valor;
    private Long cuentaId;
}