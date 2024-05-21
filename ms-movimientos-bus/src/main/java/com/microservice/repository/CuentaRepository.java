package com.microservice.repository;

import com.microservice.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findOptionalByNumeroCuenta(String numeroCuenta);
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
    @Query("SELECT c FROM Cuenta c WHERE c.clienteId = :clienteId")
    List<Cuenta> findByClienteId(Long clienteId);
}
