package com.microservice.mapper;

import com.microservice.model.Cliente;
import com.microservice.model.Persona;
import com.microservice.model.request.RequestApi;

public class ClienteMapper {
    public static Cliente buildCliente(RequestApi request) {
        return Cliente.builderConstructorCliente()
                .persona(PersonaMapper.buildPersona(request))
                .contrasena(request.getContrasena())
                .estado(request.getEstado())
                .build();
    }

    public static Cliente buildActualizaCliente(Cliente cliente, Persona persona) {
        return Cliente.builderConstructorCliente()
                .persona(persona)
                .contrasena(cliente.getContrasena())
                .estado(cliente.getEstado())
                .build();
    }
}
