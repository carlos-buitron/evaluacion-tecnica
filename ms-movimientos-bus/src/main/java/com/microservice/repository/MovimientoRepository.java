package com.microservice.repository;

import com.microservice.model.Cuenta;
import com.microservice.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.id = :cuentaId AND m.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.id = :id")
    Movimiento findByCuentaId(Long id);

    List<Movimiento> findByCuenta(Cuenta cuenta);
}
