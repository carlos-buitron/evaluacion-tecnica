package com.microservice.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microservice.model.Cuenta;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseMovimiento {

    @Valid
    @NotNull
    private LocalDateTime fecha;

    @Valid
    @NotNull
    private String tipoMovimiento;

    @Valid
    @NotNull
    private BigDecimal valor;

    @Valid
    @NotNull
    private BigDecimal saldoActual;

    @Valid
    @NotNull
    private Long cuentaId;
}
