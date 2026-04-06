
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

    /** * Inserta un empleado */
    public void insertarEmpleado(String Nombre, String Apellido, String Fechanac, String Fechareg, String Direccion, String correo1, String telefono1, int genero) throws SQLException{
        if (existeEmpleadoGenero(Nombre, 0)){
            throw new IllegalArgumentException("El nombre del empleado ya esta registrado.");
        }
        String sql = "{CALL insertar_producto(?,?,?,?,?,?,?,?,?)}";//Llamada al procedimiento
        try (PreparedStatement ps = conn.prepareCall(sql)){
            ps.setString(1, Nombre);
            ps.setString(2, Apellido);
            ps.setString(3, Fechanac);
            ps.setString(4, Fechareg);
            ps.setString(5, Fechareg);
            ps.setString(6, Direccion);
            ps.setString(7, correo1);
            ps.setString(8, telefono1);
            ps.setInt(9, genero);
            ps.execute();
            System.out.println("Empleado insertado");
        }                   
    }    

    /** * Actualiza un empleado (14 parámetros según tu SQL) */
    public void modificarEmpleado(int id, String newNombre, String newApellido, String newFechanac, String newFechareg, String newDireccion, String newcorreo1, String newcorreo2, String newtelefono1, String newtelefono2, String newObservacion, int genero, int estado) throws SQLException{
        if (existeEmpleadoGenero(newNombre, id)){
            throw new IllegalArgumentException("El nombre del empleado ya esta registrado.");
        }
        String sql = "{CALL Update_Empleado(?,?,?,?,?,?,?,?,?,?,?,?,?)}";/*Llamada al procedimiento*/
        try (PreparedStatement ps = conn.prepareCall(sql)){
            ps.setInt(1,id);
            ps.setString(2, newNombre);
            ps.setString(3, newApellido);
            ps.setString(4, newFechanac);
            ps.setString(5, newFechareg);
            ps.setString(6, newFechareg);
            ps.setString(7, newDireccion);
            ps.setString(8, newcorreo1);
            ps.setString(9, newcorreo2);
            ps.setString(10, newtelefono1);
            ps.setString(11, newtelefono2);
            ps.setString(11, newObservacion);
            ps.setInt(11, genero);
            ps.setInt(12, estado);
            ps.executeUpdate();
            System.out.println("Empleado modificado");
        }                   
    }    

    /** * Baja lógica por DNI */
    public void darDeBajaEmpleado(String dni) throws SQLException {
        String sql = "{CALL Desactivar_Empleado(?)}";
        CallableStatement cs = conn.prepareCall(sql);
        cs.setString(1, dni); //Solo se pasa el codigo, el estado lo maneja el procedure
        cs.execute();
        }
    }