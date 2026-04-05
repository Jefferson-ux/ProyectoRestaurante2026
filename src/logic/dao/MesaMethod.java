package logic.dao;
import connection.ConnectionDB;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MesaMethod {

    private final Connection conn;
    
    public MesaMethod() {
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
    
    
    
    
             //======================================//  
        // Métodos del Formulario de Mesa - CRUD//
       //======================================//
            
       /* VIEWS --> MOSTRAR DATOS */
           public ResultSet listarMesas() throws SQLException{
        String sql = "Select * from vista_mesa";/*SQL Query*/
        Statement st = conn.createStatement(); /*Creamos la sentencia*/
        return st.executeQuery(sql);  /*Ejecutamos el query y obtenemos el resultado */
    }
       
       
       /* SEARCH --> BUSCAR DATOS */
    public ResultSet buscarMesas(String nombre) throws SQLException{
        String sql = "CALL buscar_mesa(?)";/*Llamada al procedimiento*/
        CallableStatement cs = conn.prepareCall(sql);/*Usamos CallableStatement*/
        cs.setString(1, nombre);    /*Asignamos parámetros*/
        return cs.executeQuery(); 
    }       



       /* INSERT--> AGREGAR DATOS */
    public void insertarMesas(String nombre, int capacidad) throws SQLException{
        if (existeMesaConNombre(nombre, 0)){
            throw new IllegalArgumentException("Ya existe una categoría registrada con ese nombre");
        }
        String sql = "{CALL insertar_mesa(?,?)}";//Llamada al procedimiento
        try 
            (PreparedStatement ps =conn.prepareCall(sql)){
            ps.setString(1, nombre);
            ps.setInt(2, capacidad);
            ps.execute();
            System.out.println("Mesa insertada con éxito");
        }
    } 


       /* UPDATE --> ACTUALIZAR DATOS */
     public void modificarMesas(int id, String nuevoNombre, int nuevaCapacidad) throws SQLException{
         if (existeMesaConNombre(nuevoNombre, id)){
            throw new IllegalArgumentException("Ya existe una mesa registrada con ese número");
        }
        String sql = "CALL vera_ModificarFacultad(?,?,?)";/*Llamada al procedimiento*/
        try (PreparedStatement ps = conn.prepareCall(sql)){
            ps.setInt(1,id);
            ps.setString(2, nuevoNombre);
            ps.setInt(3,nuevaCapacidad);
            ps.executeUpdate();
            System.out.println("Mesa modificada");
        }                   

    }




       /* DESACTIVATE --> DESACTIVAR DATOS */
     public void downFacultades(int id) throws SQLException{
        String sql = "CALL vera_DesactivarFacultad(?)";/*Llamada al procedimiento*/
        try 
            (PreparedStatement ps =conn.prepareCall(sql)){
            ps.setInt(1,id);
            ps.executeUpdate();
            System.out.println("Facultad desactivada");
        }   
    }                     
  
       
     
    /** Valida si ya existe una escuela profesional con ese nombre */
    public boolean existeMesaConNombre(String nombre, int codigo) throws SQLException {
        String sql = "SELECT 1 FROM mesa "
                   + "WHERE LOWER(numero_mesa) = LOWER(?) "
                   + "AND id_mesa <> ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nombre.trim());
        ps.setInt(2, codigo); // 0 si es insertar

        ResultSet rs = ps.executeQuery();
        return rs.next(); // si devuelve algo, ya existe
    }
       
         //======================================//  
        // Métodos de los COMBOBOX - VIEW de FK //
       //======================================//            
      


}