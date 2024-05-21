package com.microservice.service;

import com.microservice.model.Cuenta;
import com.microservice.model.Movimiento;
import com.microservice.model.ReporteEstadoCuenta;
import com.microservice.repository.MovimientoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class ReporteServiceImpl implements ReporteService{

    MovimientoRepository movimientoRepository;

    CuentaService cuentaService;

    public ReporteEstadoCuenta generarReporteEstadoCuenta(LocalDateTime fechaInicio, LocalDateTime fechaFin, Long clienteId) {
        // Obtener la lista de cuentas asociadas al cliente
        List<Cuenta> cuentas = cuentaService.obtenerCuentasPorCliente(clienteId);

        // Obtener el detalle de movimientos de las cuentas dentro del rango de fechas
        Map<Cuenta, List<Movimiento>> detalleMovimientos = new HashMap<>();
        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientos = movimientoRepository.findByCuentaIdAndFechaBetween(cuenta.getId(), fechaInicio, fechaFin);
            detalleMovimientos.put(cuenta, movimientos);
        }

        ReporteEstadoCuenta reporte = new ReporteEstadoCuenta();
        reporte.setCuentas(cuentas);
        reporte.setDetalleMovimientos(detalleMovimientos);
        return reporte;
    }
}
