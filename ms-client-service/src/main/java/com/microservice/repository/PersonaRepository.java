package com.microservice.repository;

import com.microservice.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByIdentificacion(String identificacion);
}
