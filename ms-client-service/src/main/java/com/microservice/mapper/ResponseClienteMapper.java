package com.microservice.mapper;

import com.microservice.model.Cliente;
import com.microservice.model.response.ResponseCliente;

public class ResponseClienteMapper {
    public static ResponseCliente buildResponseCliente(Cliente cliente) {
        return ResponseCliente.builder()
                .id(cliente.getPersonaId())
                .nombres(cliente.getNombre())
                .identificacion(cliente.getIdentificacion())
                .direccion(cliente.getDireccion())
                .telefono(cliente.getTelefono())
                .constrasena(cliente.getContrasena())
                .estado(cliente.getEstado())
                .build();
    }
}
