/**************************************
1.- CARGO
**************************************/

-- 1. Primero definimos el delimitador
DELIMITER //

DROP PROCEDURE IF EXISTS Update_Cargo //

-- 2. Creamos el procedimiento
CREATE PROCEDURE Update_Cargo (
    IN p_id_cargo      INT,
    IN p_nombre_cargo  VARCHAR(255),
    IN p_estado        INT
)
BEGIN
    DECLARE v_existencia INT;
    DECLARE v_id_existe  INT;

    -- Validar que el ID exista
    SELECT COUNT(*) INTO v_id_existe FROM cargo WHERE id_cargo = p_id_cargo;

    IF v_id_existe = 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: El ID de cargo no existe.', 
            MYSQL_ERRNO = 20071;
    END IF;

    -- Validar nombre no nulo o vacío
    IF p_nombre_cargo IS NULL OR TRIM(p_nombre_cargo) = '' THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: El nombre del cargo es obligatorio.', 
            MYSQL_ERRNO = 20072;
    END IF;

    -- Validar estado
    IF p_estado IS NULL OR p_estado NOT IN (0,1) THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: El estado debe ser 0 o 1.', 
            MYSQL_ERRNO = 20073;
    END IF;

    -- Validar duplicados
    SELECT COUNT(*) INTO v_existencia FROM cargo 
    WHERE UPPER(nombre_cargo) = UPPER(TRIM(p_nombre_cargo)) 
      AND id_cargo <> p_id_cargo;

    IF v_existencia > 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: Ya existe un cargo con ese nombre.', 
            MYSQL_ERRNO = 20074;
    END IF;

    -- Ejecutar el UPDATE
    UPDATE cargo
    SET nombre_cargo = TRIM(p_nombre_cargo),
        estado       = p_estado
    WHERE id_cargo = p_id_cargo;

END // 

-- 3. Volvemos al delimitador estándar
DELIMITER ;

--4. Ejemplo de uso
CALL Update_Cargo(5,"Ayudante de Cocina II",1);

/**************************************
2.- CATEGORIA 
**************************************/

DELIMITER //

DROP PROCEDURE IF EXISTS Update_Categoria //

CREATE PROCEDURE Update_Categoria (
    IN p_id_categoria     INT,
    IN p_nombre_categoria VARCHAR(255),
    IN p_estado            INT
)
BEGIN
    DECLARE v_existencia INT;
    DECLARE v_id_existe  INT;

    -- 1. Validar que el ID exista
    SELECT COUNT(*) INTO v_id_existe FROM categoria WHERE id_categoria = p_id_categoria;

    IF v_id_existe = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: No se encontró la categoría con ese ID.',
            MYSQL_ERRNO = 20077;
    END IF;

    -- 2. Validar nombre no nulo o vacío
    IF p_nombre_categoria IS NULL OR TRIM(p_nombre_categoria) = '' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: El nombre de la categoría no puede estar vacío.',
            MYSQL_ERRNO = 20075;
    END IF;

    -- 3. Validar estado (debe ser 0 o 1)
    IF p_estado IS NULL OR p_estado NOT IN (0,1) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: El estado debe ser 0 o 1.',
            MYSQL_ERRNO = 20078; 
    END IF;

    -- 4. Verificar duplicados (mismo nombre en otro ID)
    SELECT COUNT(*) INTO v_existencia 
    FROM categoria 
    WHERE UPPER(TRIM(nombre_categoria)) = UPPER(TRIM(p_nombre_categoria)) 
      AND id_categoria <> p_id_categoria;

    IF v_existencia > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: Ya existe una categoría con ese nombre.',
            MYSQL_ERRNO = 20076;
    END IF;

    -- 5. Ejecutar el UPDATE
    UPDATE categoria
    SET nombre_categoria = TRIM(p_nombre_categoria),
        estado           = p_estado
    WHERE id_categoria = p_id_categoria;

END //

DELIMITER ;

CALL Update_Categoria(10,"Jugos Naturales",1);

/**************************************
3.- CLIENTE
**************************************/
DELIMITER //

DROP PROCEDURE IF EXISTS Update_Cliente //

CREATE PROCEDURE Update_Cliente (
    IN p_id_cliente      INT,
    IN p_dni             VARCHAR(20),
    IN p_nombre          VARCHAR(100),
    IN p_apellido        VARCHAR(100),
    IN p_correo          VARCHAR(100),
    IN p_telefono        VARCHAR(20),
    IN p_observacion     VARCHAR(200),
    IN p_estado          INT
)
BEGIN
    -- Declaración de variables locales
    DECLARE v_dni_duplicado INT;
    DECLARE v_id_existe      INT;
    
    -- Variables para limpieza de datos
    DECLARE v_dni         VARCHAR(20)  DEFAULT TRIM(p_dni);
    DECLARE v_correo      VARCHAR(100) DEFAULT LOWER(TRIM(p_correo));
    DECLARE v_nombre      VARCHAR(100) DEFAULT TRIM(p_nombre);
    DECLARE v_apellido    VARCHAR(100) DEFAULT TRIM(p_apellido);
    DECLARE v_telefono    VARCHAR(20)  DEFAULT TRIM(p_telefono);
    DECLARE v_observacion VARCHAR(200) DEFAULT TRIM(p_observacion);

    -- Manejo de excepciones para ROLLBACK
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL; -- Re-lanza el error para que Java lo capture
    END;

    START TRANSACTION;

    -- 1. Validar que el ID exista
    SELECT COUNT(*) INTO v_id_existe FROM cliente WHERE id_cliente = p_id_cliente;
    IF v_id_existe = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: No existe el cliente con ese ID.', MYSQL_ERRNO = 20085;
    END IF;

    -- 2. Validar DNI (8 dígitos)
    -- En MySQL se usa REGEXP
    IF v_dni IS NULL OR NOT v_dni REGEXP '^[0-9]{8}$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El DNI debe tener exactamente 8 dígitos.', MYSQL_ERRNO = 20078;
    END IF;

    -- 3. Validar correo
    IF v_correo IS NULL OR NOT v_correo REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El correo electrónico es inválido.', MYSQL_ERRNO = 20079;
    END IF;

    -- 4. Validar nombre y apellido
    IF v_nombre IS NULL OR v_nombre = '' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El nombre no puede estar vacío.', MYSQL_ERRNO = 20080;
    END IF;

    IF v_apellido IS NULL OR v_apellido = '' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El apellido no puede estar vacío.', MYSQL_ERRNO = 20081;
    END IF;

    -- 5. Validar teléfono (9 dígitos, empieza con 9)
    IF v_telefono IS NULL OR NOT v_telefono REGEXP '^9[0-9]{8}$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El teléfono debe tener 9 dígitos y empezar con 9.', MYSQL_ERRNO = 20082;
    END IF;

    -- 6. Validar estado
    IF p_estado IS NULL OR p_estado NOT IN (0,1) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El estado debe ser 0 o 1.', MYSQL_ERRNO = 20083;
    END IF;

    -- 7. Validar DNI duplicado
    SELECT COUNT(*) INTO v_dni_duplicado FROM cliente 
    WHERE dni_cliente = v_dni AND id_cliente <> p_id_cliente;

    IF v_dni_duplicado > 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Error: Ya existe otro cliente con ese DNI.', MYSQL_ERRNO = 20084;
    END IF;

    -- 8. UPDATE
    UPDATE cliente
    SET dni_cliente      = v_dni,
        nombre_cliente    = v_nombre,
        apellido_cliente  = v_apellido,
        correo_cliente    = v_correo,
        telefono_cliente  = v_telefono,
        observacion_cliente = v_observacion,
        estado            = p_estado
    WHERE id_cliente = p_id_cliente;

    COMMIT;
    
    SELECT 'Cliente actualizado correctamente.' AS Resultado;

END //

DELIMITER ;
--9. Ejemplo de uso
CALL Update_Cliente(
    31, 
    '32955556', 
    'David', 
    'Gonzales Aguilar', 
    'davidaguilar@gmail.com', 
    '987654321', 
    'Cliente muy frecuente', 
    1
);

/**************************************
4.- CONTRATO
**************************************/

DELIMITER //

DROP PROCEDURE IF EXISTS Update_Contrato //

CREATE PROCEDURE Update_Contrato (
    IN p_id_contrato      INT,
    IN p_descripcion      VARCHAR(255),
    IN p_fecha            DATE,
    IN p_id_turno         INT,
    IN p_id_empleado      INT,
    IN p_id_tipo_contrato INT,
    IN p_estado           INT
)
BEGIN
    DECLARE v_existe INT;

    -- Manejo de excepciones para ROLLBACK
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    -- 1. Validar existencia del contrato
    SELECT COUNT(*) INTO v_existe FROM contrato WHERE id_contrato = p_id_contrato;
    IF v_existe = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El contrato no existe.', MYSQL_ERRNO = 20086;
    END IF;

    -- 2. Validar descripción
    IF p_descripcion IS NULL OR TRIM(p_descripcion) = '' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: La descripción no puede estar vacía.', MYSQL_ERRNO = 20087;
    END IF;

    -- 3. Validar fecha (NULL y futura)
    -- En MySQL se usa CURRENT_DATE o NOW() en lugar de SYSDATE
    IF p_fecha IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: La fecha no puede ser NULL.', MYSQL_ERRNO = 20088;
    END IF;

    IF p_fecha > CURRENT_DATE THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: La fecha no puede ser futura.', MYSQL_ERRNO = 20089;
    END IF;

    -- 4. Validar existencia de Turno (FK)
    SELECT COUNT(*) INTO v_existe FROM turno WHERE id_turno = p_id_turno;
    IF v_existe = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El turno no existe.', MYSQL_ERRNO = 20090;
    END IF;

    -- 5. Validar existencia de Empleado (FK)
    SELECT COUNT(*) INTO v_existe FROM empleado WHERE id_empleado = p_id_empleado;
    IF v_existe = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El empleado no existe.', MYSQL_ERRNO = 20091;
    END IF;

    -- 6. Validar existencia de Tipo Contrato (FK)
    SELECT COUNT(*) INTO v_existe FROM tipo_contrato WHERE id_tipo_contrato = p_id_tipo_contrato;
    IF v_existe = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El tipo de contrato no existe.', MYSQL_ERRNO = 20092;
    END IF;

    -- 7. Validar estado (0 o 1)
    IF p_estado IS NULL OR p_estado NOT IN (0,1) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El estado debe ser 0 o 1.', MYSQL_ERRNO = 20093;
    END IF;

    -- 8. UPDATE
    UPDATE contrato
    SET descripcion_contrato = TRIM(p_descripcion),
        fecha_contrato       = p_fecha,
        id_turno             = p_id_turno,
        id_empleado          = p_id_empleado,
        id_tipo_contrato     = p_id_tipo_contrato,
        estado               = p_estado
    WHERE id_contrato = p_id_contrato;

    COMMIT;

END //

DELIMITER ;

--8. Ejemplo de uso
CALL Update_Contrato(
    11,                      -- p_id_contrato
    'Contrato actualizado',  -- p_descripcion
    '2024-07-01',            -- p_fecha (En MySQL se pasa como String YYYY-MM-DD)
    2,                       -- p_id_turno
    5,                       -- p_id_empleado
    1,                       -- p_id_tipo_contrato
    1                        -- p_estado
);

/**************************************
5.- DETALLE PEDIDO
**************************************/

DELIMITER //

DROP PROCEDURE IF EXISTS Update_DetallePedido //

CREATE PROCEDURE Update_DetallePedido (
    IN p_id_detalle   INT,
    IN p_cantidad     DECIMAL(10,2), -- Usamos decimal por si hay cantidades no enteras
    IN p_precio       DECIMAL(10,2),
    IN p_observacion  VARCHAR(200)
)
BEGIN
    DECLARE v_existe INT;
    -- Equivalente a NVL: Si el TRIM resulta vacío o NULL, ponemos 'SIN OBSERVACIONES'
    DECLARE v_obs_limpia VARCHAR(200);

    -- Manejo de errores
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    -- Lógica de limpieza para la observación
    SET v_obs_limpia = TRIM(p_observacion);
    IF v_obs_limpia IS NULL OR v_obs_limpia = '' THEN
        SET v_obs_limpia = 'SIN OBSERVACIONES';
    END IF;

    START TRANSACTION;

    -- 1. Validar existencia del detalle
    SELECT COUNT(*) INTO v_existe FROM detalle_pedido WHERE id_detalle = p_id_detalle;
    IF v_existe = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El detalle de pedido no existe.', MYSQL_ERRNO = 20095;
    END IF;

    -- 2. Validar cantidad
    IF p_cantidad IS NULL OR p_cantidad <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: La cantidad debe ser mayor a 0.', MYSQL_ERRNO = 20096;
    END IF;

    -- 3. Validar precio
    IF p_precio IS NULL OR p_precio <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El precio debe ser mayor a 0.', MYSQL_ERRNO = 20097;
    END IF;

    -- 4. Validar que el pedido asociado exista (Integridad)
    SELECT COUNT(*) INTO v_existe 
    FROM pedido p
    INNER JOIN detalle_pedido d ON p.id_pedido = d.id_pedido
    WHERE d.id_detalle = p_id_detalle;

    IF v_existe = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El pedido asociado no existe.', MYSQL_ERRNO = 20098;
    END IF;

    -- 5. UPDATE
    UPDATE detalle_pedido
    SET cantidad_detalle        = p_cantidad,
        precio_unitario_detalle = p_precio,
        observacion_detalle     = v_obs_limpia
    WHERE id_detalle = p_id_detalle;

    COMMIT;

END //

DELIMITER ;
--6.Ejemplo de uso
CALL Update_DetallePedido(
    61,                     -- p_id_detalle
    2,                      -- p_cantidad
    25.00,                  -- p_precio
    'Sin cebolla ni ajo'    -- p_observacion
);

/**************************************
6.- EMPLEADO
**************************************/

DELIMITER //

DROP PROCEDURE IF EXISTS Update_Empleado //

CREATE PROCEDURE Update_Empleado (
    IN p_id_empleado         INT,
    IN p_dni                 VARCHAR(20),
    IN p_nombres             VARCHAR(100),
    IN p_apellidos           VARCHAR(100),
    IN p_fecha_nacimiento    DATE,
    IN p_lugar_residencia    VARCHAR(200),
    IN p_correo1             VARCHAR(100),
    IN p_correo2             VARCHAR(100),
    IN p_telefono1           VARCHAR(20),
    IN p_telefono2           VARCHAR(20),
    IN p_observacion         VARCHAR(200),
    IN p_id_genero           INT,
    IN p_id_cargo            INT,
    IN p_estado              INT
)
BEGIN
    DECLARE v_existe INT;
    
    -- Variables para limpieza de datos
    DECLARE v_dni         VARCHAR(20)  DEFAULT TRIM(p_dni);
    DECLARE v_nombre      VARCHAR(100) DEFAULT TRIM(p_nombres);
    DECLARE v_apellido    VARCHAR(100) DEFAULT TRIM(p_apellidos);
    DECLARE v_correo1     VARCHAR(100) DEFAULT LOWER(TRIM(p_correo1));
    DECLARE v_telefono1   VARCHAR(20)  DEFAULT TRIM(p_telefono1));
    DECLARE v_obs_limpia  VARCHAR(200);

    -- Manejo de excepciones
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    -- Lógica de limpieza para observación (NVL)
    SET v_obs_limpia = TRIM(p_observacion);
    IF v_obs_limpia IS NULL OR v_obs_limpia = '' THEN
        SET v_obs_limpia = 'SIN OBSERVACIONES';
    END IF;

    START TRANSACTION;

    -- 1. Validar existencia del empleado
    SELECT COUNT(*) INTO v_existe FROM empleado WHERE id_empleado = p_id_empleado;
    IF v_existe = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El empleado no existe.', MYSQL_ERRNO = 20100;
    END IF;

    -- 2. Validar DNI (8 dígitos)
    IF v_dni IS NULL OR NOT v_dni REGEXP '^[0-9]{8}$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: El DNI debe tener 8 dígitos.', MYSQL_ERRNO = 20101;
    END IF;

    -- 3. Validar nombres y apellidos
    IF v_nombre IS NULL OR v_nombre = '' OR v_apellido IS NULL OR v_apellido = '' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Nombre y apellido son obligatorios.', MYSQL_ERRNO = 20102;
    END IF;

    -- 4. Validar fecha nacimiento (no nula y no futura)
    IF p_fecha_nacimiento IS NULL OR p_fecha_nacimiento >= CURRENT_DATE THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Fecha de nacimiento inválida.', MYSQL_ERRNO = 20103;
    END IF;

    -- 5. Validar correo principal
    IF v_correo1 IS NULL OR NOT v_correo1 REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Correo principal inválido.', MYSQL_ERRNO = 20104;
    END IF;

    -- 6. Validar teléfono principal
    IF v_telefono1 IS NULL OR NOT v_telefono1 REGEXP '^9[0-9]{8}$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Teléfono inválido.', MYSQL_ERRNO = 20105;
    END IF;

    -- 7. Validar género
    SELECT COUNT(*) INTO v_existe FROM genero WHERE id_genero = p_id_genero;
    IF v_existe = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Género no existe.', MYSQL_ERRNO = 20106;
    END IF;

    -- 8. Validar cargo
    SELECT COUNT(*) INTO v_existe FROM cargo WHERE id_cargo = p_id_cargo;
    IF v_existe = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Cargo no existe.', MYSQL_ERRNO = 20107;
    END IF;

    -- 9. Validar estado
    IF p_estado IS NULL OR p_estado NOT IN (0,1) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: Estado debe ser 0 o 1.', MYSQL_ERRNO = 20108;
    END IF;

    -- 10. UPDATE
    UPDATE empleado
    SET dni_empleado               = v_dni,
        nombre_empleado            = v_nombre,
        apellido_empleado          = v_apellido,
        fecha_nacimiento_empleado  = p_fecha_nacimiento,
        lugar_residencia_empleado  = TRIM(p_lugar_residencia),
        correo1_empleado           = v_correo1,
        correo2_empleado           = LOWER(TRIM(p_correo2)),
        telefono1_empleado         = v_telefono1,
        telefono2_empleado         = TRIM(p_telefono2),
        observacion_empleado       = v_obs_limpia,
        id_genero                  = p_id_genero,
        id_cargo                   = p_id_cargo,
        estado                     = p_estado
    WHERE id_empleado = p_id_empleado;

    COMMIT;

END //

DELIMITER ;
CALL Update_Empleado(
    11, 
    '12325678', 
    'Carlos', 
    'Lopez', 
    '1995-05-10', 
    'Av Lima 123', 
    'david1@gmail.com', 
    'david2@gmail.com', 
    '987656321', 
    '912348678', 
    NULL, 
    1, 
    2, 
    1
);