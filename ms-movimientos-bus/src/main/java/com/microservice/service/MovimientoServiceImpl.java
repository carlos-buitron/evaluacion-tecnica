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
import java.util.Optional;

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
        return movimientoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ApiException(String.format("No existe el movimiento con ID %s", String.valueOf(id)));
                });
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
        Optional<Cuenta> cuentaOpt = cuentaRepository.findById(movimientoDto.getCuentaId());
        Cuenta cuenta = null;
        ResponseMovimiento responseMovimiento = null;
        if(cuentaOpt.isPresent()) {
            cuenta = cuentaOpt.get();
            List<Movimiento> movimientos = movimientoRepository.findByCuenta(cuenta);
            int cantidadMovimientos = movimientos.size();
            Movimiento movimiento = movimientos.get(cantidadMovimientos -1);
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

            cuentaOpt.get().setSaldoInicial(movimiento.getSaldoActual());
            cuentaRepository.save(cuentaOpt.get());
            responseMovimiento = ResponseMovimiento.builder()
                    .saldoActual(cuenta.getSaldoInicial())
                    .tipoMovimiento(movimiento.getTipoMovimiento())
                    .fecha(movimiento.getFecha())
                    .cuentaId(cuenta.getId())
                    .build();
        }
        return ResponseApi.builder()
                .mensaje("Operación Exitosa")
                .resultado(responseMovimiento)
                .build();
    }

    @Override
    public ResponseMovimiento delete(Long id) {
        eliminadaMovimiento(id);
        return ResponseMovimiento.builder().build();
    }

    private void eliminadaMovimiento(Long id){
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ApiException(String.format("No existe un movimiento registrado con %s", id));
                });
        movimientoRepository.delete(movimiento);
    }

    private Movimiento getMovimiento(Long id){
        return movimientoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ApiException(String.format("No existe la cuenta con ID %s", String.valueOf(id)));
                });
    }

}
