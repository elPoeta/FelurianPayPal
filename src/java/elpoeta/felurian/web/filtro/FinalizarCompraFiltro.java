
package elpoeta.felurian.web.filtro;

import elpoeta.felurian.domain.Usuario;
import elpoeta.felurian.util.GsonUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author elPoeta
 */
@WebFilter(filterName = "FinalizarCompraFiltro", urlPatterns = {"/api/privado/*"})
public class FinalizarCompraFiltro implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
          System.out.println("... CompraFiltra ...............................");
        Usuario userActual = (Usuario)((HttpServletRequest)request).getSession().getAttribute("User");
        if ( userActual !=null ) { 
            System.out.println("...OK PrivadoFiltra ............................... " + userActual);
            chain.doFilter(request, response);
            
        } else {
            System.out.println("...UPA PrivadoFiltra ..............................." + userActual);
            response.getWriter().print(GsonUtil.CONVERTIR.toJson("error"));
            //request.getRequestDispatcher("../login.html").forward(request, response);
        }
    }

    @Override
    public void destroy() {
      
    }
    
    
}
