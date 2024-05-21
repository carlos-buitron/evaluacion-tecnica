package com.microservice.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseCuenta {
    @Valid
    @NotNull
    private String numeroCuenta;

    @Valid
    @NotNull
    private String tipoCuenta;

    @Valid
    @NotNull
    private BigDecimal saldoInicial;

    @Valid
    @NotNull
    private Boolean estado;

    @Valid
    @NotNull
    private Long clienteId;
}
