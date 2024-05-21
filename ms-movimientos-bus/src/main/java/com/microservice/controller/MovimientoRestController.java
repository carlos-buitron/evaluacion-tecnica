package com.microservice.controller;

import com.microservice.constants.Constantes;
import com.microservice.dto.MovimientoDto;
import com.microservice.model.Movimiento;
import com.microservice.model.response.ResponseApi;
import com.microservice.service.MovimientoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/movimientos")
public class MovimientoRestController {

    private final MovimientoService movimientoService;

    @GetMapping
    public List<Movimiento> obtenerTodosLosMovimientos() {
        return movimientoService.obtenerTodosLosMovimientos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> obtenerMovimientoPorId(@PathVariable Long id) {
        Movimiento movimiento = movimientoService.obtenerMovimientoPorId(id);
        if (movimiento != null) {
            return ResponseEntity.ok(movimiento);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear")
    public ResponseEntity<Movimiento> crearMovimiento(@RequestBody Movimiento movimiento) {
        Movimiento nuevoMovimiento = movimientoService.crearMovimiento(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMovimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimiento> actualizarMovimiento(@PathVariable Long id, @RequestBody Movimiento movimiento) {
        Movimiento movimientoActualizado = movimientoService.actualizarMovimiento(id, movimiento);
        if (movimientoActualizado != null) {
            return ResponseEntity.ok(movimientoActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        if (movimientoService.eliminarMovimiento(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/realizar")
    public ResponseEntity<ResponseApi> realizarMovimiento(@RequestBody MovimientoDto movimiento) {
        return new ResponseEntity<>(
                ResponseApi.builder()
                        .mensaje(Constantes.SUCCESS_OPERATION)
                        .resultado(movimientoService.realizarMovimiento(movimiento))
                        .build(),
                HttpStatus.OK
        );
    }
}
