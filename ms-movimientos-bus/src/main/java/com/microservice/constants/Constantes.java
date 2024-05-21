package com.microservice.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Constantes {
    //SUCCESS_OPERATION
    public static final String SUCCESS_OPERATION = "Operación Exitosa.";

    //BAD_REQUEST
    public static final String BAD_REQUEST = "Petición no válida, favor de validar su información";

    //UNAUTHORIZED
    public static final String UNAUTHORIZED = "No estas autorizado, favor de validar";

    //NOT_FOUND
    public static final String NOT_FOUND = "No se encontró información";

    //FAILED_OPERATION
    public static final String FAILED_OPERATION = "Operacion Fallida";

    //INTERNAL_ERROR
    public static final String INTERNAL_ERROR = "Problemas al procesar su solicitud favor de contactar a su administrador";

    //CONFLICT
    public static final String CONFLICT = "Conflicto en la operación";

    // UNAVAILABLE
    public static final String UNAVAILABLE = "Servicio No Disponible";

    // CARACTERES VALIDOS PARA GENERAR LA CONTRASEÑA
    public static final String CARACTERES_VALIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

}