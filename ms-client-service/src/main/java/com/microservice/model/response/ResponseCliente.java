package com.microservice.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseCliente {

    @Valid
    @NotNull
    private Long id;

    @Valid
    @NotNull
    private String nombres;

    @Valid
    @NotNull
    private String identificacion;

    @Valid
    @NotNull
    private String direccion;

    @Valid
    @NotNull
    private String telefono;

    @Valid
    @NotNull
    private String constrasena;

    @Valid
    @NotNull
    private Boolean estado;
}