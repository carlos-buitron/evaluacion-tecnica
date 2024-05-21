package com.microservice.component;

import com.microservice.dto.ClienteDto;

import java.math.BigDecimal;

public interface ClienteComponent {
    ClienteDto getCliente(String identificacion);
    ClienteDto getCliente(Long id);
}
