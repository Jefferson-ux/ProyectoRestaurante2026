
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
