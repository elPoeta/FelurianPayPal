
package elpoeta.felurian.conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author elpoeta
 */
public class Conexion {
     private static Conexion INSTANCE = null;
     //private static String LABASE = "jdbc:mysql://localhost/db_felurianconlogin";
     private static String LABASE = "jdbc:hsqldb:file:/home/elpoeta/BBDD/feluriandb/felurian_db"; 
     //private static String LABASE = "jdbc:hsqldb:file:resources/feluriandb/felurian_db"; 
     private static String LABASEUSUARIO = "SA";  // "root";
     private static String LABASECLAVE = "";    //"root";

	
	public static Conexion getInstance() throws ClassNotFoundException, IOException, SQLException {
         if (INSTANCE == null) {
             INSTANCE = new Conexion();
         }
         return INSTANCE;
     }
     private Conexion() throws ClassNotFoundException,
             IOException, SQLException {
     }

     public Connection getConnection() throws ClassNotFoundException,
             IOException, SQLException {
         //Class.forName("com.mysql.cj.jdbc.Driver");
          Class.forName("org.hsqldb.jdbc.JDBCDriver");  
         return DriverManager.getConnection(LABASE, LABASEUSUARIO, LABASECLAVE);
     }
}
