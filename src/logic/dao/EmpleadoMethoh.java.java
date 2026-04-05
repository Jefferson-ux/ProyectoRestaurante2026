
package logic.dao;
import connection.ConnectionDB;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class EmpleadoMethod {
    private final Connection conn;
    
 
    
    public EmpleadoMethod() {
         //===========================================//   
        //Crear la conexión al iniciar el formulario //
       //===========================================//   
           ConnectionDB connection = new ConnectionDB();
           
           
         //=====================================//  
        //Verificar que la conexion sea exitosa//
       //=====================================//
            this.conn = connection.getConnection();
       
            if (connection.getConnection() == null) {
            JOptionPane.showConfirmDialog(null, "No se puede conectar a la base de datos", "Error de conexión", 1);
        }
    }
    public ResultSet combobox_ListarGeneros() throws SQLException {
        String sql = "select `Género` from vista_genero";
        Statement st =conn.createStatement();       // Creamos el statements
        ResultSet rs=st.executeQuery(sql);          // Ejecutamos la consulta
        return rs;                                  // Devolvemos los resultados 
    }
    
    /* Mantenimiento a la tabla Empleado */
    /** Listar todos los empleados desde la vista Vista_Empleado ...6 lines*/
        public ResultSet listarEmpleados() throws SQLException{
        String sql = "SELECT * FROM vista_empleado ";   /*SQL Query*/
        Statement st = conn.createStatement(); /*Creamos la sentencia*/
        return st.executeQuery(sql);  /*Ejecutamos el query y obtenemos el resultado */
    }

    /** Buscar empleados usando un procedimiento almacenado seguro */
    public ResultSet buscarEmpleados(String nombre) throws SQLException{
        String sql = "CALL buscar_empleado(?)";             /*Llamada al procedimiento*/
        CallableStatement cs = conn.prepareCall(sql);/*Usamos CallableStatement*/
        cs.setString(1, nombre);    /*Asignamos parámetros*/
        return cs.executeQuery(); 
    }

    /** Insertar un nuevo empleado utilizando el procedimiento almacenado*/

    public void insertarEmpleado(String dni, String nombres, String apellidos, 
                                 String fechaNac, String fechaReg, String residencia, 
                                 String correo1, String correo2, String tel1, String tel2, 
                                 String observacion, int idGenero) throws SQLException {
        
        String sql = "{CALL insertar_empleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, dni);
            cs.setString(2, nombres);
            cs.setString(3, apellidos);
            cs.setString(4, fechaNac); 
            cs.setString(5, fechaReg);
            cs.setString(6, residencia);
            cs.setString(7, correo1);
            cs.setString(8, correo2);
            cs.setString(9, tel1);
            cs.setString(10, tel2);
            cs.setString(11, observacion);
            cs.setInt(12, idGenero);
            
            cs.execute();
            System.out.println("Inserción exitosa desde Java");
        }
    }    
        
    /** Modifica un empleado existente usando el procedure almacenado */
    public void modificarEmpleado(int id, String dni, String nombres, String apellidos, 
                                  String fNac, String fReg, String dir, String c1, String c2, 
                                  String t1, String t2, String obs, int idGen, int estado) throws SQLException {
        
        String sql = "{CALL Update_Empleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setInt(1, id);
            ps.setString(2, dni);
            ps.setString(3, nombres);
            ps.setString(4, apellidos);
            ps.setString(5, fNac);
            ps.setString(6, fReg);
            ps.setString(7, dir);
            ps.setString(8, c1);
            ps.setString(9, c2);
            ps.setString(10, t1);
            ps.setString(11, t2);
            ps.setString(12, obs);
            ps.setInt(13, idGen);
            ps.setInt(14, estado);
            ps.executeUpdate();
        }
    }
     
    /** Desactiva (da de baja lógica) un empleado usando su DNI */
     public void desactivarEmpleado(String dni) throws SQLException{
        String sql = "CALL Desactivar_Empleado(?)";         /*Llamada al procedimiento*/
        try (PreparedStatement ps = conn.prepareCall(sql)) {
            ps.setString(1, dni); // Enviamos String para evitar el error de tipos
            ps.executeUpdate();
            System.out.println("Empleado desactivado");
        }                   

    }      
      

}