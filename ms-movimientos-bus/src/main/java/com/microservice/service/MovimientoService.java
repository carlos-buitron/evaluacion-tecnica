package com.microservice.service;

import com.microservice.dto.MovimientoDto;
import com.microservice.model.Movimiento;
import com.microservice.model.response.ResponseApi;

import java.util.List;

public interface MovimientoService {

    List<Movimiento> obtenerTodosLosMovimientos();
    Movimiento obtenerMovimientoPorId(Long id);
    Movimiento crearMovimiento(Movimiento movimiento);
    Movimiento actualizarMovimiento(Long id, Movimiento movimiento);
    boolean eliminarMovimiento(Long id);
    ResponseApi realizarMovimiento(MovimientoDto movimiento);
}
