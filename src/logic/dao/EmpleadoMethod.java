
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
        ConnectionDB connection = new ConnectionDB();
        this.conn = connection.getConnection();
        
        if (this.conn == null) {
            // Cambiado a MessageDialog para que no pida confirmación Si/No, solo informe el error
            JOptionPane.showMessageDialog(null, "No se puede conectar a la base de datos", "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
     public boolean existeEmpleadoGenero (String nombre, int id_empleado) throws SQLException {
        String sql = "SELECT 1 FROM Empleado "
            + "WHERE LOWER(nombre_empleado) = LOWER(?) "
            + "AND id_empleado <> ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nombre.trim());
        ps.setInt(2, id_empleado); // 0 si es insertar
        ResultSet rs = ps.executeQuery();
        return rs.next(); // si devuelve algo, ya existe
    }
     
     
     
    /** * Carga la lista de géneros desde la vista */
    public ResultSet combobox_ListarGeneros() throws SQLException {
        String sql = "SELECT `Genero` FROM vista_genero";
        Statement st = conn.createStatement();
        return st.executeQuery(sql);
    }

    /** * Muestra todos los empleados de la vista_empleado */
    public ResultSet listarEmpleados() throws SQLException {
        String sql = "SELECT * FROM vista_empleado";
        Statement st = conn.createStatement();
        return st.executeQuery(sql);
    }

    /** * Busca por nombre usando el procedure */
    public ResultSet buscarEmpleados(String nombre) throws SQLException {
        String sql = "{CALL buscar_empleado(?)}";
        CallableStatement cs = conn.prepareCall(sql);
        cs.setString(1, nombre);
        return cs.executeQuery();
    }

    /** * Inserta un empleado (12 parámetros según tu SQL) */
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
            System.out.println("Empleado insertado con éxito");
        }
    }

    /** * Actualiza un empleado (14 parámetros según tu SQL) */
    public void modificarEmpleado(int id, String newNombre, String newApellido, String newFechanac, String newFechareg, String newDireccion, String newcorreo1, String newcorreo2, String newtelefono1, String newtelefono2, String newObservacion, int genero, int estado) throws SQLException{
        if (existeProductoUnidadMedida(nuevo_nombre, id)){
            
            
            
            {
        
        
        
        
        String sql = "{CALL Update_Empleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, id);
            cs.setString(2, dni);
            cs.setString(3, nombres);
            cs.setString(4, apellidos);
            cs.setString(5, fNac);
            cs.setString(6, fReg);
            cs.setString(7, dir);
            cs.setString(8, c1);
            cs.setString(9, c2);
            cs.setString(10, t1);
            cs.setString(11, t2);
            cs.setString(12, obs);
            cs.setInt(13, idGen);
            cs.setInt(14, estado);
            cs.executeUpdate();
            System.out.println("Empleado modificado correctamente");
        }
    }

    /** * Baja lógica por DNI */
    public void desactivarEmpleado(String dni) throws SQLException {
        String sql = "{CALL Desactivar_Empleado(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, dni); 
            cs.executeUpdate();
            System.out.println("Empleado desactivado");
        }
    }
}