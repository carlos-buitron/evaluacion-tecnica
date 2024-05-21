package com.microservice.service;

import com.microservice.enumeration.Genero;

import com.microservice.exception.ApiException;

import com.microservice.mapper.ClienteMapper;
import com.microservice.mapper.ResponseClienteMapper;

import com.microservice.model.Cliente;
import com.microservice.model.request.RequestApi;
import com.microservice.model.response.ResponseCliente;

import com.microservice.repository.ClienteRepository;
import com.microservice.repository.PersonaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {

    private static final Logger LOG = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final PersonaRepository personaRepository;

    private final ClienteRepository clienteRepository;

    // Crear un nuevo cliente
    @Transactional
    @Override
    public ResponseCliente create(RequestApi request) {
        Cliente cliente = clienteRepository.save(ClienteMapper.buildCliente(request));
        return ResponseClienteMapper.buildResponseCliente(cliente);
    }

    // Actualizar informacion del cliente
    @Transactional
    @Override
    public ResponseCliente update(RequestApi request) {
        Cliente cliente = actualizaCliente(request);
        clienteRepository.save(cliente);
        return ResponseClienteMapper.buildResponseCliente(cliente);
    }

    // Actualizar contraseña del cliente
    @Transactional
    @Override
    public ResponseCliente update(Long id, RequestApi request) {
        Cliente cliente = getCliente(id);
        cliente.setContrasena(request.getContrasena());
        clienteRepository.save(cliente);
        return ResponseClienteMapper.buildResponseCliente(cliente);
    }

    // Borrado logico cliente
    @Transactional
    @Override
    public ResponseCliente update(String identificacion) {
        Cliente cliente = getCliente(identificacion);
        // Borrado logico
        cliente.setEstado(Boolean.FALSE);
        clienteRepository.save(cliente);
        return ResponseClienteMapper.buildResponseCliente(cliente);
    }

    // Borrar cliente
    @Transactional
    @Override
    public void eliminar(Long id) {
        Cliente cliente = getCliente(id);
        clienteRepository.delete(cliente);
    }

    // Obtener informacion del cliente
    @Transactional(readOnly = true)
    @Override
    public ResponseCliente get(String identificacion) {
        Cliente cliente = getCliente(identificacion);
        return ResponseClienteMapper.buildResponseCliente(cliente);
    }

    @Override
    public ResponseCliente get(Long id) {
        Cliente cliente = getCliente(id);
        return ResponseClienteMapper.buildResponseCliente(cliente);
    }

    private Cliente getCliente(String identificacion) {
        // Si el cliente no existe, lanza una excepcion
        return clienteRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> new ApiException(String.format("No existe un cliente registrado con documento %s.", identificacion)));
    }

    private Cliente getCliente(Long id) {
        // Si el cliente no existe, lanza una excepcion
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ApiException(String.format("No existe un cliente registrado con identificador %s.", id)));
    }

    private Cliente actualizaCliente(RequestApi request) {
        Cliente cliente = getCliente(request.getIdentificacion());
        // DNI, EDAD por lo general son inmutables
        cliente.setGenero(Genero.valueOf(request.getGenero()));
        cliente.setNombre(request.getNombres());
        cliente.setDireccion(request.getDireccion());
        cliente.setTelefono(request.getTelefono());
        cliente.setContrasena(request.getContrasena());
        cliente.setEstado(request.getEstado());
        return cliente;
    }
}
