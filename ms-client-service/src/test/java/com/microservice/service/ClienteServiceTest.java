package com.microservice.service;

import com.microservice.enumeration.Genero;
import com.microservice.exception.ApiException;
import com.microservice.model.Cliente;
import com.microservice.model.Persona;
import com.microservice.model.request.RequestApi;
import com.microservice.model.response.ResponseCliente;
import com.microservice.repository.ClienteRepository;
import com.microservice.repository.PersonaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ActiveProfiles(value = "test")
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    // Simular los repositorios de cliente y persona
    @Mock
    PersonaRepository personaRepository;
    @Mock
    ClienteRepository clienteRepository;

    // Simular de servicio cliente con los repositorios simulados
    @InjectMocks
    ClienteServiceImpl clienteService;

    Cliente cliente;

    Persona persona;

    RequestApi requestApi;

    @BeforeEach
    void setUp() {
        // Construir data simulado cliente
        persona = Persona.builder()
                .personaId(1l)
                .nombre("Eduardo Martinez")
                .genero(Genero.valueOf("M"))
                .edad(18)
                .identificacion("44448888")
                .direccion("Perú, Cusco, Ciudad de Calca")
                .telefono("965685338")
                .build();
        cliente = Cliente.builderConstructorCliente()
                .persona(persona)
                .estado(Boolean.TRUE)
                .contrasena("12345")
                .build();

        requestApi = RequestApi.builder()
                .nombres("Eduardo Martinez")
                .genero("M")
                .edad(18)
                .identificacion("44448888")
                .direccion("Perú, Cusco, Ciudad de Calca")
                .telefono("965685338")
                .contrasena("12345")
                .estado(Boolean.TRUE)
                .build();
    }

    @Test
    @DisplayName("Deberia registrar un cliente en base a su información")
    void create() {
        // Verifica que la simulacion registre un cliente
        when(clienteRepository.save(cliente))
                .thenReturn(cliente);
        ResponseCliente resultado = clienteService.create(requestApi);
        // Verifica que el cliente se ejecuta correctamente en el repositorio
        verify(clienteRepository, times(1)).save(eq(cliente));
        // Verifica que el cliente se ejecuta correctamente en el repositorio
        verify(clienteRepository).save(any(Cliente.class));
        // Verifica que la identificacion de respuesta sea el esperado
        assertEquals(cliente.getIdentificacion(), resultado.getIdentificacion());
        // Valida el resultado
        validaRespuesta(resultado);
    }

    @Test
    @DisplayName("Deberia actualizar los datos del cliente en base a su nueva información")
    void updateInformacion() {
        String identificacion = "44448888";
        requestApi = RequestApi.builder()
                .nombres("Eduardo Martinez")
                .genero("M")
                .edad(18)
                .identificacion("44448888")
                .direccion("Nueva direccion")
                .telefono("55555555")
                .contrasena("12345")
                .estado(Boolean.TRUE)
                .build();
        doReturn(Optional.of(cliente)).when(clienteRepository).findByIdentificacion(eq(identificacion));
        ResponseCliente resultado = clienteService.update(requestApi);
        // Verifica que el método save del repositorio sea llamado una vez
        verify(clienteRepository, times(1)).save(eq(cliente));
        // Valida el resultado
        validaRespuestaActualizada(resultado);
    }

    @Test
    @DisplayName("Debería lanzar una excepción al intentar actualizar informacion del cliente inexistente")
    void updateInformacionException() {
        String identificacion = "44448888";
        requestApi = RequestApi.builder()
                .nombres("Eduardo Martinez")
                .genero("M")
                .edad(18)
                .identificacion("44448888")
                .direccion("Nueva direccion")
                .telefono("55555555")
                .contrasena("12345")
                .estado(Boolean.TRUE)
                .build();
        // Verifica que la simulacion lanza una excepción cuando se busca un cliente por su identificacion que no existe
        doThrow(new ApiException(String.format("No existe un cliente registrado con documento %s.", identificacion)))
                .when(clienteRepository).findByIdentificacion(identificacion);
        Executable executable = () -> clienteService.update(requestApi);
        ApiException exception = assertThrows(ApiException.class, executable);
        // Verifica que el método save del repositorio sea llamado una vez
        verify(clienteRepository, times(1)).findByIdentificacion(identificacion);
        // Valida el resultado
        assertEquals(String.format("No existe un cliente registrado con documento %s.", identificacion), exception.getMessage());
    }


    @Test
    @DisplayName("Deberia actualizar la contraseña del cliente en base a su ID y nueva clave")
    void updateClave() {
        Long id = 1l;
        requestApi = RequestApi.builder()
                .contrasena("1111")
                .build();
        doReturn(Optional.of(cliente)).when(clienteRepository).findById(eq(id));
        ResponseCliente resultado = clienteService.update(id, requestApi);
        // Verifica que el método save del repositorio sea llamado una vez
        verify(clienteRepository, times(1)).save(eq(cliente));
        verify(clienteRepository, times(1)).findById(eq(id));
        // Valida el resultado
        validaRespuestaClaveActualizada(resultado);
    }

    @Test
    @DisplayName("Deberia actualizar el estado del cliente en base a su identificacion")
    void updateEstado() {
        String identificacion = "44448888";
        requestApi = RequestApi.builder()
                .estado(Boolean.FALSE)
                .build();
        doReturn(Optional.of(cliente)).when(clienteRepository).findByIdentificacion(eq(identificacion));
        ResponseCliente resultado = clienteService.update(identificacion);
        // Verifica que el método save del repositorio sea llamado una vez
        verify(clienteRepository, times(1)).save(cliente);
        verify(clienteRepository, times(1)).findByIdentificacion(identificacion);
        // Valida el resultado
        validaRespuestaEstadoActualizada(resultado);
    }

    @Test
    @DisplayName("Debería eliminar un cliente correctamente")
    void eliminar() {
        // Verificar que la simulación devuelva el cliente cuando se busca por ID
        doReturn(Optional.of(cliente)).when(clienteRepository).findById(anyLong());
        // Verifica que la simulacion para no hacer nada cuando se llama a delete
        doNothing().when(clienteRepository).delete(cliente);
        // Llama al método del servicio para eliminar el cliente
        clienteService.eliminar(1l);
        // Verifica que el método delete del repositorio sea llamado con el cliente correcto
        verify(clienteRepository, times(1)).delete(any(Cliente.class));
        // Verifica que el método findById del repositorio sea llamado con el ID correcto
        verify(clienteRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Debería lanzar una excepción al intentar eliminar un cliente inexistente")
    void eliminarException() {
        Long clienteId = 1l;
        // Verifica que la simulacion lanza una excepción cuando se busca un cliente por ID que no existe
        doThrow(new ApiException(String.format("No existe un cliente registrado con identificador %s.", clienteId)))
                .when(clienteRepository).findById(clienteId);
        // Verifica que se lanza la excepción correcta al intentar eliminar el cliente
        ApiException exception = assertThrows(ApiException.class, () -> clienteService.eliminar(clienteId));
        Executable executable = () -> clienteService.eliminar(clienteId);
        assertThrows(ApiException.class, executable);
        // Verifica que el mensaje de la excepción es el esperado
        assertEquals(String.format("No existe un cliente registrado con identificador %s.", clienteId), exception.getMessage());
    }

    @Test
    @DisplayName("Deberia traer la informacion del cliente en base a su identificacion")
    void getCliente() {
        when(clienteRepository.findByIdentificacion(eq("44448888"))) // anyString() any(String.class) eq("44448888")
                .thenReturn(Optional.of(cliente));
        ResponseCliente resultado = clienteService.get("44448888");
        verify(clienteRepository).findByIdentificacion(any(String.class));
        validaRespuesta(resultado);
    }

    @Test
    @DisplayName("Debería lanzar una excepción al intentar obtener un cliente inexistente por identificación")
    void getClienteException() {
        String identificacion = "44422278";
        // Verifica que la simulacion lanza una excepción cuando se busca un cliente por ID que no existe
        doThrow(new ApiException(String.format("No existe un cliente registrado con documento %s.", identificacion)))
                .when(clienteRepository).findByIdentificacion(identificacion);
        // Verifica que se lanza la excepción correcta al intentar eliminar el cliente
        ApiException exception = assertThrows(ApiException.class, () -> clienteService.get(identificacion));
        Executable executable = () -> clienteService.get(identificacion);
        assertThrows(ApiException.class, executable);
        // Verifica que el mensaje de la excepción es el esperado
        assertEquals(String.format("No existe un cliente registrado con documento %s.", identificacion), exception.getMessage());
    }

    private void validaRespuesta(ResponseCliente resultado) {
        // Evaluando sus respuestas
        assertNotNull(resultado);
        assertEquals("Eduardo Martinez", resultado.getNombres());
        assertEquals("44448888", resultado.getIdentificacion());
        assertEquals("965685338", resultado.getTelefono());
        assertTrue(resultado.getEstado());
        assertEquals("12345", resultado.getConstrasena());
        assertEquals("Perú, Cusco, Ciudad de Calca", resultado.getDireccion());
    }

    private void validaRespuestaActualizada(ResponseCliente resultado) {
        // Evaluando sus respuestas
        assertNotNull(resultado);
        assertEquals("Eduardo Martinez", resultado.getNombres());
        assertEquals("44448888", resultado.getIdentificacion());
        assertEquals("55555555", resultado.getTelefono());
        assertTrue(resultado.getEstado());
        assertEquals("12345", resultado.getConstrasena());
        assertEquals("Nueva direccion", resultado.getDireccion());
    }

    private void validaRespuestaEstadoActualizada(ResponseCliente resultado) {
        // Evaluando sus respuestas
        assertNotNull(resultado);
        assertEquals("Eduardo Martinez", resultado.getNombres());
        assertEquals("44448888", resultado.getIdentificacion());
        assertEquals("965685338", resultado.getTelefono());
        assertFalse(resultado.getEstado());
        assertEquals("12345", resultado.getConstrasena());
        assertEquals("Perú, Cusco, Ciudad de Calca", resultado.getDireccion());
    }

    private void validaRespuestaClaveActualizada(ResponseCliente resultado) {
        // Evaluando sus respuestas
        assertNotNull(resultado);
        assertEquals("Eduardo Martinez", resultado.getNombres());
        assertEquals("44448888", resultado.getIdentificacion());
        assertEquals("965685338", resultado.getTelefono());
        assertTrue(resultado.getEstado());
        assertEquals("1111", resultado.getConstrasena());
        assertEquals("Perú, Cusco, Ciudad de Calca", resultado.getDireccion());
    }
}