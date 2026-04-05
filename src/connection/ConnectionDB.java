
package connection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {
        
     //====================================//
    /* --> Variables de configuración <-- */
   //====================================//
    private Connection conn;//1.--> Conexión a MySQL
    private Statement st;  //2.--> Ejecución del SQL
    private ResultSet rs; //3.--> Resultados del sql
    
    
     //=====================================//
    /* --> Config |> Datos de conexión <-- */
   //=====================================//
    
    private static final String URL = "jdbc:mysql://localhost:3306/db_restaurant";
            //=> jdbc (protocolo JDBC) localhost (mi PC) 3306 (port) universidad_db (database)
    private static final String USER = "root";  
            //=> USUARIO 
    private static final String PASSWORD = "";
            //=> PASSWORD 
    
    
     //================================================//
    /* --> Contrsuctor que inicializa la conexión <-- */
   //================================================//
    public ConnectionDB() {
        try {
            // Cargar el driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa con la BD 'universidad'.");
        ////////////////////////////////////////////////////////////////////////
            
        } catch (ClassNotFoundException e) { //=> Por si no existe el Driver
            System.err.println("Error, no se encontró el driver JBDC");
        } catch (SQLException e) { //Por si falla la conexión
            System.err.println("Error al conectar con la base de datos: "+e.getMessage());
        }
    }
    
   

    /* Mantenimiento a la tabla Empleado */
    /** Listar todos los empleados desde la vista Vista_Empleado ...6 lines*/
    public ResultSet listarEmpleados() throws SQLException{
        String sql = "SELECT * FROM vista_empleado ";   /*SQL Query*/
        st = conn.createStatement();                    /*Creamos la sentencia*/
        rs = st.executeQuery(sql);                      /*Ejecutamos el query */
        return rs;                                      /*Retorna el resultado*/
    }

    /** Buscar empleados usando un procedimiento almacenado seguro */
    public ResultSet buscarEmpleados(String p_param) throws SQLException{
        String sql = "CALL buscar_empleado(?)";             /*Llamada al procedimiento*/
        CallableStatement cs = conn.prepareCall(sql);       /*Usamos CallableStatement*/
        cs.setString(1, p_param);                           /*Asignamos parámetros*/
        rs = cs.executeQuery();                             /*Ejecutamos y obtenemos los resultados*/
        return rs;                 
    }

    /** Insertar un nuevo empleado utilizando el procedimiento almacenado */
    public void insertEmpleados(String p_param) throws SQLException{
        String sql = "CALL insertar_empleado(?)";           //Llamada al procedimiento
        try 
            (PreparedStatement ps =conn.prepareCall(sql)){
            ps.setString(1, p_param);
            ps.execute();
            System.out.println("Empleado insertado insertada");
        }
    }     
        
    /** Modifica un empleado existente usando el procedure almacenado */
     public void modificarEmpleados (int id, String dni, String nombres, String apellidos, 
                              String fechaNac, String fechaReg, String direccion, 
                              String correo1, String correo2, String tel1, String tel2, 
                              String observacion, int idGenero, int estado) throws SQLException{
        String sql = "CALL update_empleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";                  /*Llamada al procedimiento*/
        try (PreparedStatement ps = conn.prepareCall(sql)){
            ps.setInt(1,id);
            ps.setInt(1, id);
            ps.setString(2, dni);
            ps.setString(3, nombres);
            ps.setString(4, apellidos);
            ps.setString(5, fechaNac);
            ps.setString(6, fechaReg);
            ps.setString(7, direccion);
            ps.setString(8, correo1);
            ps.setString(9, correo2);
            ps.setString(10, tel1);
            ps.setString(11, tel2);
            ps.setString(12, observacion);
            ps.setInt(13, idGenero);
            ps.setInt(14, estado);   
            ps.executeUpdate();
            System.out.println("Empleado modificado");
        }                   
    }
     
    /** Desactiva (da de baja lógica) un empleado usando su DNI */
     public void desactivarEmpleado(String dni_empleado) throws SQLException{
        String sql = "CALL Desactivar_Empleado(?)";         /*Llamada al procedimiento*/
        try 
            (PreparedStatement ps =conn.prepareCall(sql)){
            ps.setInt(1,dni_empleado);
            ps.executeUpdate();
            System.out.println("Empleado desactivado");
        }                   

    }
    
    

     //==============================================//
    /* --> Método de cierre de la base de datos <-- */
   //==============================================//
    public void closeConnection(){
        try{
            
            if (rs!=null) rs.close();     // Cerrar resultados
            if (st!=null) st.close();     // Cerrar sentencia 
            if (conn!=null) conn.close(); // Cerrar conexión
            System.out.println("Conexión cerrada correctamente!");
        } catch (SQLException e){
            System.err.println("Error al cerrar la conexión: "+e.getMessage());
        }
        
        
        
        
    }
     //================================================================//
    /* --> Método getConexión |> Usar la Conexión en otras clases <-- */
   //================================================================//
    public Connection getConnection(){
            return conn;
        }
}
