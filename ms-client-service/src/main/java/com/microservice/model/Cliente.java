package com.microservice.model;


import com.microservice.enumeration.Genero;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "cliente_id")
@Entity
@Table(name = "clientes")
public class Cliente extends Persona implements Serializable {

    @NotEmpty(message = "Contraseña de cliente es requerida")
    private String contrasena;

    @NotNull(message = "Estado de cliente es requerido")
    private Boolean estado;

    // Constructor que llama al constructor de Persona y luego inicializa los campos específicos de Cliente
    @Builder(builderMethodName = "builderConstructorCliente")
    public Cliente(Persona persona, String contrasena, Boolean estado) {
        super(
                persona.getPersonaId(), persona.getNombre(), persona.getGenero(),
                persona.getEdad(), persona.getIdentificacion(), persona.getDireccion(),
                persona.getTelefono());
        this.contrasena = contrasena;
        this.estado = estado;
    }
}

