package com.microservice.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestApi {
    private String nombres;
    private String genero;
    private int edad;
    /*@Valid
    @NotEmpty(message = "Identificacion de la persona es requerido")*/
    private String identificacion;
    private String direccion;
    private String telefono;
    private String contrasena;
    private Boolean estado;
}
