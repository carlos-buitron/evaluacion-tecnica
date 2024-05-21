package com.microservice.model;

import com.microservice.enumeration.Genero;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "personas")
public class Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "persona_id")
    private Long personaId;

    @NotEmpty(message = "Nombre de la persona es requerido")
    private String nombre;

    @NotNull(message = "Genero de la persona es requerido")
    @Enumerated(EnumType.STRING) // Mapeo entre ENUM y String
    private Genero genero;

    @NotNull(message = "Edad de la persona es requerido")
    @Min(value = 18, message = "La edad debe ser mayor o igual a 18")
    private int edad;

    @NotEmpty(message = "Identificacion de la persona es requerido")
    @Column(name = "identificacion", unique = true)
    private String identificacion;

    @NotEmpty(message = "Direccion de la persona es requerido")
    private String direccion;

    @NotEmpty(message = "Telefono de la persona es requerido")
    private String telefono;

}
