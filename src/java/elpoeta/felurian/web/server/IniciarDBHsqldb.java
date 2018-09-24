
package elpoeta.felurian.web.server;

import elpoeta.felurian.conexion.Conexion;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;

import org.hsqldb.server.ServerAcl.AclFormatException;


/**
 *
 * @author elpoeta
 */
@WebServlet(name = "IniciarDBHsqldb", urlPatterns = {"/IniciarDBHsqldb"}, loadOnStartup = 1)
public class IniciarDBHsqldb extends HttpServlet {
  Conexion con;
    @Override
    public void init() throws ServletException {
        super.init();

        try {
        System.out.println("Starting Database");
        HsqlProperties p = new HsqlProperties();
        p.setProperty("server.database.0", "file:/home/elpoeta/BBDD/feluriandb");
        //p.setProperty("server.database.0", "file:resources/feluriandb");
        p.setProperty("server.dbname.0", "felurian_db");
        p.setProperty("server.port", "9001");
        
        Server server = new Server();
        server.setProperties(p);
        server.setLogWriter(null);
        server.setErrWriter(null); 
        server.start();


    } catch (IOException ioex) {
        throw new ServletException(ioex);
    }   catch (AclFormatException ex) { 
            Logger.getLogger(IniciarDBHsqldb.class.getName()).log(Level.SEVERE, null, ex);
        } 
      
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

}
