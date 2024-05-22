package com.microservice.service;

import com.microservice.component.ClienteComponent;
import com.microservice.dto.ClienteDto;
import com.microservice.exception.ApiException;
import com.microservice.mapper.CuentaMapper;
import com.microservice.mapper.ResponseCuentaMapper;
import com.microservice.model.Cuenta;
import com.microservice.model.Movimiento;
import com.microservice.model.request.RequestApi;
import com.microservice.model.response.ResponseCuenta;
import com.microservice.repository.CuentaRepository;
import com.microservice.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
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
public class CuentaServiceImpl implements CuentaService {

    CuentaRepository cuentaRepository;
    MovimientoRepository movimientoRepository;
    ClienteComponent clienteComponent;

    public Cuenta obtenerCuentaPorId(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public ResponseCuenta create(RequestApi request) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findByNumeroCuenta(request.getNumeroCuenta());
        Movimiento movimiento = null;
        Cuenta cuenta = null;
        ClienteDto cliente = clienteComponent.getCliente(request.getClienteId());
        if(!cuentaOpt.isPresent()) {
            cuenta = cuentaRepository.save(CuentaMapper.buildCuenta(request, cliente));
            if(request.getSaldoInicial().compareTo(BigDecimal.ZERO) > 0) {
                movimiento = new Movimiento();
                movimiento.setCuenta(cuenta);
                movimiento.setSaldoActual(request.getSaldoInicial());
                movimiento.setValor(BigDecimal.ZERO);
                movimiento.setTipoMovimiento("Aporte");
                movimiento.setFecha(LocalDateTime.now());
                movimientoRepository.save(movimiento);
            }
        } else {
            throw new ApiException("Cuenta ya existe");
        }
        return ResponseCuentaMapper.buildResponseCuenta(cuenta);
    }

    @Override
    public ResponseCuenta update(RequestApi request) {
        Cuenta cuenta = actualizaCuenta(request);
        cuentaRepository.save(cuenta);
        return ResponseCuentaMapper.buildResponseCuenta(cuenta);
    }

    @Override
    public ResponseCuenta update(Long id, RequestApi requestApi) {
        Cuenta cuenta = getCuenta(id);
        if(requestApi.getNumeroCuenta() != null){
            cuenta.setNumeroCuenta(requestApi.getNumeroCuenta());
        }
        if (requestApi.getTipoCuenta() != null){
            cuenta.setTipoCuenta(requestApi.getTipoCuenta());
        }
        if (requestApi.getSaldoInicial() != null){
            cuenta.setSaldoInicial(requestApi.getSaldoInicial());
        }
        if (requestApi.getEstado() != null){
            cuenta.setEstado(requestApi.getEstado());
        }
        cuentaRepository.save(cuenta);
        return ResponseCuentaMapper.buildResponseCuenta(cuenta);
    }

    @Override
    public ResponseCuenta delete(Long id) {
        eliminadaCuenta(id);
        return ResponseCuenta.builder().build();
    }

    @Override
    public ResponseCuenta get(String numeroCuenta) {
        Cuenta cuenta =getCuenta(numeroCuenta);
        return ResponseCuentaMapper.buildResponseCuenta(cuenta);
    }

    private Cuenta getCuenta(String numeroCuenta){
        return cuentaRepository.findOptionalByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> {
            throw new ApiException(String.format("No existe la cuenta %s", numeroCuenta));
        });
    }

    private Cuenta getCuenta(Long id){
        return cuentaRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ApiException(String.format("No existe la cuenta con ID %s", String.valueOf(id)));
                });
    }

    private void eliminadaCuenta(Long id){
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> {
                throw new ApiException(String.format("No existe una cuenta registrado con %s", id));
                });
        cuentaRepository.delete(cuenta);
    }

    private Cuenta actualizaCuenta(RequestApi request){
        Cuenta cuenta = getCuenta(request.getNumeroCuenta());
        cuenta.setTipoCuenta(request.getTipoCuenta());
        cuenta.setSaldoInicial(request.getSaldoInicial());
        cuenta.setEstado(request.getEstado());
        return cuenta;
    }

    public List<Cuenta> obtenerCuentasPorCliente(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }

    @Transactional
    public Movimiento registrarMovimiento(String numeroCuenta, Movimiento movimiento) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> {
                    throw new ApiException("Cuenta no encontrada");
                });
        //double nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();
        BigDecimal nuevoSaldo = cuenta.getSaldoInicial().add(movimiento.getValor());
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimiento.setFecha(LocalDateTime.now());
        movimiento.setSaldoActual(nuevoSaldo);
        movimiento.setCuenta(cuenta);

        return movimientoRepository.save(movimiento);
    }
}
