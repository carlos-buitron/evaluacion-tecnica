package com.microservice.service;

import com.microservice.model.Cuenta;
import com.microservice.model.Movimiento;
import com.microservice.model.request.RequestApi;
import com.microservice.model.response.ResponseCuenta;

import java.util.List;

public interface CuentaService {

    List<Cuenta> obtenerTodasLasCuentas();
    Cuenta obtenerCuentaPorId(Long id);
    Cuenta actualizarCuenta(Long id, Cuenta cuenta);
    boolean eliminarCuenta(Long id);
    ResponseCuenta create(RequestApi request);
    ResponseCuenta update(RequestApi request);
    ResponseCuenta update(Long id, RequestApi requestApi);
    ResponseCuenta delete(String numeroCuenta);
    ResponseCuenta delete(Long id);
    ResponseCuenta get(String numeroCuenta);

    List<Cuenta> obtenerCuentasPorCliente(Long clienteId);
    Movimiento registrarMovimiento(String numeroCuenta, Movimiento movimiento);


}
