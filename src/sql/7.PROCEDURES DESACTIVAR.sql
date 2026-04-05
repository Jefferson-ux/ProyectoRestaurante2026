


--5. Procedure para dar de baja un Empleado
DROP PROCEDURE IF EXISTS Desactivar_Empleado;
DELIMITER //
CREATE PROCEDURE Desactivar_Empleado(IN dni_empleado INT)
BEGIN
    DECLARE existe INT DEFAULT 0;
    DECLARE yaDesactivado INT DEFAULT 0;
    /* Verificar si el Empleado existe */
    SELECT COUNT(*) INTO existe
    FROM Empleado WHERE dni_empleado = dni_empleado;
    IF existe = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: El código de Empleado no existe. ';
    ELSE
        /* Verificar si ya está desactivada */
        SELECT COUNT(*) INTO yaDesactivado
        FROM Empleado
        WHERE dni_empleado = dni_empleado AND estado = 0;
        IF yaDesactivado > 0 THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El Empleado ya está desactivado.';
        ELSE
            /* Proceder con la desactivación */
            UPDATE Empleado
            SET estado = 0
            WHERE dni_empleado = dni_empleado;
        END IF;
    END IF;
END //






    
    /* Mantenimiento a la tabla Empleado */
    /** Listar todos los empleados desde la vista Vista_Empleado */
    public ResultSet listFacultades() throws SQLException{
        String sql = "Select * from v_mostrarfacultades";/*SQL Query*/
        st = conn.createStatement(); /*Creamos la sentencia*/
        rs = st.executeQuery(sql);  /*Ejecutamos el query */
        return rs;                 /*Retorna el resultado*/
    }

    //=> BUSCAR
    public ResultSet buscarFacultades(String nombre) throws SQLException{
        String sql = "CALL vera_BuscarFacultades(?)";/*Llamada al procedimiento*/
        CallableStatement cs = conn.prepareCall(sql);/*Usamos CallableStatement*/
        cs.setString(1, nombre);    /*Asignamos parámetros*/
        rs = cs.executeQuery();  /*Ejecutamos y obtenemos los resultados*/
        return rs;                 
    }
    //=> INSERTAR
    public void insertFacultades(String nombre) throws SQLException{
        String sql = "CALL vera_InsertarFacultad(?)";//Llamada al procedimiento
        try 
            (PreparedStatement ps =conn.prepareCall(sql)){
            ps.setString(1, nombre);
            ps.execute();
            System.out.println("Facultad insertada");
        }
    }     
        
     //=> MODIFICAR
     public void modifFacultades(int id, String nuevoNombre) throws SQLException{
        String sql = "CALL vera_ModificarFacultad(?,?)";/*Llamada al procedimiento*/
        try (PreparedStatement ps = conn.prepareCall(sql)){
            ps.setInt(1,id);
            ps.setString(2, nuevoNombre);
            ps.executeUpdate();
            System.out.println("Facultad modificada");
        }                   

    }
     
     //=> DELETE
     public void downFacultades(int id) throws SQLException{
        String sql = "CALL vera_DesactivarFacultad(?)";/*Llamada al procedimiento*/
        try 
            (PreparedStatement ps =conn.prepareCall(sql)){
            ps.setInt(1,id);
            ps.executeUpdate();
            System.out.println("Facultad desactivada");
        }                   

    }

