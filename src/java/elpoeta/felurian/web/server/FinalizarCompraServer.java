/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elpoeta.felurian.web.server;

import elpoeta.felurian.domain.Carrito;
import elpoeta.felurian.util.GsonUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author elPoeta
 */
@WebServlet(name = "FinalizarCompraServer", urlPatterns = {"/api/privado/FinalizarCompraServer"})
public class FinalizarCompraServer extends HttpServlet {
    private Carrito carrito;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        carrito = (Carrito) request.getSession().getAttribute("carro");
        response.getWriter().print(GsonUtil.CONVERTIR.toJson(carrito));
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
             request.getSession().setAttribute("carro",null);
    }

}
