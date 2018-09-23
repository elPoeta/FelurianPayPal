
package elpoeta.felurian.web.server;

import elpoeta.felurian.domain.Producto;
import elpoeta.felurian.repository.impl.ProductoRepositoryImpl;
import elpoeta.felurian.util.GsonUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author elpoeta
 */
@WebServlet(name = "ProductoSubcategoria", urlPatterns = {"/api/ProductoSubcategoria"})
public class ProductoSubcategoria extends HttpServlet {
    
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
         response.setContentType("text/html;charset=UTF-8");
	         PrintWriter out = response.getWriter();
	         try {
                     Integer parametro = GsonUtil.CONVERTIR.fromJson(request.getParameter("q"), Integer.class);
                
                        List<Producto> listado =  ProductoRepositoryImpl.getInstance().buscarPorSubCategoria(parametro);
	                String resultado = GsonUtil.CONVERTIR.toJson(listado); 
	                out.println("" + resultado);
                    
	             

	         } catch (ClassNotFoundException ex) {
	             out.println("Verificar:" + ex.getMessage());
	         } catch (SQLException ex) {
	             out.println("Verificar:" + ex.getMessage());
	         } catch (Exception ex) {
	             out.println("Verificar:" + ex.getMessage());
	         } finally {
	             out.close();
	         }
    }


}
