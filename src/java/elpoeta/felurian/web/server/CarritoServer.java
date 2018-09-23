
package elpoeta.felurian.web.server;

import elpoeta.felurian.domain.Carrito;
import elpoeta.felurian.domain.CarritoItem;
import elpoeta.felurian.domain.Producto;
import elpoeta.felurian.repository.impl.ProductoRepositoryImpl;
import elpoeta.felurian.util.GsonUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author elpoeta
 */
@WebServlet(name = "CarritoServer", urlPatterns = {"/api/CarritoServer"})
public class CarritoServer extends HttpServlet {
    private Carrito carrito;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        carrito = (Carrito) request.getSession().getAttribute("carro");
        if( carrito == null ){
            carrito = new Carrito();
            request.getSession().setAttribute("carro", carrito);
        }
          response.getWriter().print(GsonUtil.CONVERTIR.toJson(carrito));
    }
      
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
        String texto = request.getReader().readLine();
        
        CarritoItem productoComprado = GsonUtil.CONVERTIR.fromJson(texto, CarritoItem.class);
        try {
            if(productoComprado.getCantidad()<=0){
                throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
            }
            Producto productoDB = ProductoRepositoryImpl.getInstance().buscarPorId(productoComprado.getProducto().getId());
            productoComprado.setProducto(productoDB);
           
        carrito = (Carrito) request.getSession().getAttribute("carro");
        carrito.agregar( productoComprado );
        carrito.getTotal();
        carrito.getCantidadItems();
       
         response.getWriter().print(GsonUtil.CONVERTIR.toJson(carrito));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CarritoServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CarritoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        Integer id =  GsonUtil.CONVERTIR.fromJson(req.getParameter("q"), Integer.class);
        carrito = (Carrito) req.getSession().getAttribute("carro");
     
        carrito.quitar( id );
        carrito.getCantidadItems();
        carrito.getTotal();
        
        resp.getWriter().print(GsonUtil.CONVERTIR.toJson(carrito));   

    }
    

}