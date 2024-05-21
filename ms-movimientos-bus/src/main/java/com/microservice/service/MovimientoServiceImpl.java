package com.microservice.service;

import com.microservice.dto.MovimientoDto;
import com.microservice.exception.ApiException;
import com.microservice.model.Cuenta;
import com.microservice.model.Movimiento;
import com.microservice.model.response.ResponseApi;
import com.microservice.model.response.ResponseMovimiento;
import com.microservice.repository.CuentaRepository;
import com.microservice.repository.MovimientoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class MovimientoServiceImpl implements MovimientoService {

    MovimientoRepository movimientoRepository;
    CuentaRepository cuentaRepository;
    CuentaService cuentaService;

    public List<Movimiento> obtenerTodosLosMovimientos() {
        return movimientoRepository.findAll();
    }

    public Movimiento obtenerMovimientoPorId(Long id) {
        return movimientoRepository.findById(id).orElse(null);
    }

    public Movimiento crearMovimiento(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }

    public Movimiento actualizarMovimiento(Long id, Movimiento movimiento) {
        if (movimientoRepository.existsById(id)) {
            movimiento.setId(id);
            return movimientoRepository.save(movimiento);
        }
        return null;
    }

    public boolean eliminarMovimiento(Long id) {
        if (movimientoRepository.existsById(id)) {
            movimientoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Historico de Movimientos
    public ResponseApi realizarMovimiento(MovimientoDto movimientoDto) {
        BigDecimal operacion = null;
        Cuenta cuenta = cuentaService.obtenerCuentaPorId(movimientoDto.getCuentaId());
        Movimiento movimiento = movimientoRepository.findByCuenta(cuenta);
        if (movimiento.getSaldoActual().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException("Saldo no disponible");
        }
        if (movimientoDto.getValor().compareTo(BigDecimal.ZERO) < 0) {
            System.out.println(movimientoDto.getValor().abs());
            if(movimiento.getSaldoActual().compareTo(movimientoDto.getValor().abs()) < 0) {
                throw new ApiException("Saldo no disponible");
            }
        }
        operacion = movimiento.getSaldoActual().add(movimientoDto.getValor());
        // Actualizar movimientos

        movimiento.setTipoMovimiento(movimientoDto.getTipoMovimiento());
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setCuenta(cuenta);
        movimiento.setValor(movimientoDto.getValor());
        movimiento.setSaldoActual(operacion);
        movimientoRepository.save(movimiento);

        cuenta.setSaldoInicial(movimiento.getSaldoActual());
        cuentaRepository.save(cuenta);

        ResponseMovimiento responseMovimiento = ResponseMovimiento.builder()
                .saldoActual(cuenta.getSaldoInicial())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .fecha(movimiento.getFecha())
                .cuentaId(cuenta.getId())
                .build();
        return ResponseApi.builder()
                .mensaje("Operación Exitosa")
                .resultado(responseMovimiento)
                .build();
    }
}
