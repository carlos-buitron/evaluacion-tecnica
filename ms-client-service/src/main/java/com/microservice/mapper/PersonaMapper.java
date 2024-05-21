package com.microservice.mapper;

import com.microservice.enumeration.Genero;
import com.microservice.model.Persona;
import com.microservice.model.request.RequestApi;

public class PersonaMapper {
    public static Persona buildPersona(RequestApi request) {
        return Persona.builder()
                .nombre(request.getNombres())
                .genero(Genero.valueOf(request.getGenero()))
                .edad(request.getEdad())
                .identificacion(request.getIdentificacion())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .build();
    }

    public static Persona buildActualizaPersona(RequestApi request) {
        return Persona.builder()
                .identificacion(request.getIdentificacion())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .build();
    }
}
