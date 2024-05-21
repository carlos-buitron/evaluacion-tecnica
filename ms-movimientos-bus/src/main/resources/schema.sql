CREATE DATABASE IF NOT EXISTS `sistema`;
USE `sistema`;
DROP TABLE IF EXISTS movimientos;
DROP TABLE IF EXISTS cuentas;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS personas;

CREATE TABLE personas
(
    persona_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(100)    NOT NULL,
    genero         ENUM ('M', 'F') NOT NULL,
    edad 		   INT 			   NOT NULL CHECK (edad>=18),
    identificacion VARCHAR(50)     NOT NULL UNIQUE,
    direccion      VARCHAR(255)    NOT NULL,
    telefono       VARCHAR(20)     NOT NULL
);


CREATE TABLE clientes
(
    cliente_id BIGINT       PRIMARY KEY,
    contrasena VARCHAR(255) NOT NULL,
    estado     BOOLEAN      NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES personas (persona_id)
);


CREATE TABLE cuentas
(
    cuenta_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_cuenta VARCHAR(50)        NOT NULL,
    tipo_cuenta   VARCHAR(50)        NOT NULL,
    saldo_inicial DECIMAL(15, 2)     DEFAULT 0.00,
    estado        BOOLEAN            NOT NULL,
    cliente_id    BIGINT             NOT NULL,
    CONSTRAINT UQ_NUMERO_CUENTA UNIQUE (numero_cuenta),
    FOREIGN KEY (cliente_id) REFERENCES clientes (cliente_id)
);

CREATE TABLE movimientos
(
    movimiento_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha           DATETIME       DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(50)    NOT NULL,
    valor           DECIMAL(15, 2) DEFAULT 0.00,
    saldo_actual    DECIMAL(15, 2) DEFAULT 0.00,
    cuenta_id       BIGINT         NOT NULL,
    FOREIGN KEY (cuenta_id) REFERENCES cuentas (cuenta_id)
);