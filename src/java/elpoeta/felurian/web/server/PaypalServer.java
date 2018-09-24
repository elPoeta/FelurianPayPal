
package elpoeta.felurian.web.server;

import com.paypal.api.payments.*;
import com.paypal.base.rest.*;
import elpoeta.felurian.domain.Carrito;
import static elpoeta.felurian.util.CredencialApi.*;
import elpoeta.felurian.util.GsonUtil;
import java.io.IOException;
import java.math.BigDecimal;



import java.util.ArrayList;
import java.util.Iterator;
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
@WebServlet(name = "PaypalServer", urlPatterns = {"/api/privado/PaypalServer"})
public class PaypalServer extends HttpServlet {
   private Carrito carrito;
   
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Payment pay = crearPago(req, resp);
        System.out.println(pay);
        resp.getWriter().print(GsonUtil.CONVERTIR.toJson(pay));
        
    }
   
    private Payment crearPago(HttpServletRequest req, HttpServletResponse resp) {
        carrito = (Carrito) req.getSession().getAttribute("carro");  
        Payment createdPayment = null;
        try {
             APIContext apiContext = new APIContext(CLIENTE_ID, CLIENTE_SECRET, MODO);
           
                  if (req.getParameter("payerID") != null) {
                        System.out.println("Ejecutando Payment");
                        Payment payment = new Payment();
          
                        payment.setId(req.getParameter("paymentID"));
               
                        PaymentExecution paymentExecution = new PaymentExecution();
                        paymentExecution.setPayerId(req.getParameter("payerID"));


                createdPayment = payment.execute(apiContext, paymentExecution);
                System.out.println("Ejecutando Payment - Request :: \n " + Payment.getLastRequest());
                System.out.println("Ejecutando Payment - Response :: \n " + Payment.getLastResponse());
                
            } else {
            
                Payer payer = new Payer();
                payer.setPaymentMethod("paypal");

                List<Item> items = new ArrayList();
            Iterator it =  carrito.getItems().keySet().iterator();
            while(it.hasNext()){
               Integer key = (Integer) it.next();
                Item item = new Item();
                item.setName(carrito.getItems().get(key).getProducto().getNombre());
                item.setQuantity(String.valueOf(carrito.getItems().get(key).getCantidad()));
                BigDecimal precio = carrito.getItems().get(key).getProducto().getPrecio().divide(new BigDecimal(40)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        
                item.setPrice(String.valueOf(precio)); 
               //item.setPrice(String.valueOf(carrito.getItems().get(key).getProducto().getPrecio()));
               // item.setTax("0.00");
                item.setCurrency("USD");
                items.add(item);
        
           }
            BigDecimal sumaItems = BigDecimal.ZERO;
             for(int i=0; i<items.size(); i++){
                 BigDecimal p = new BigDecimal(items.get(i).getPrice());
                 p = p.multiply(new BigDecimal(items.get(i).getQuantity()));
                 sumaItems = sumaItems.add(p);
             }
            ItemList itemList = new ItemList();
            itemList.setItems(items);
                     
            Details details = new Details();
            //details.setShipping("0.00");
            //details.setSubtotal(String.valueOf(carrito.getTotal().divide(new BigDecimal(40)).setScale(2, BigDecimal.ROUND_DOWN)));
            details.setSubtotal(String.valueOf(sumaItems));
            //details.setTax("0.00");
           

            Amount amount = new Amount();
            amount.setCurrency("USD");
            amount.setTotal(String.valueOf(sumaItems));
            amount.setDetails(details);
          
            
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            //transaction.setDescription("Descripcion de pago");
            transaction.setItemList(itemList);
          
            List<Transaction> transactions = new ArrayList();
            transactions.add(transaction);

            Payment payment = new Payment();
            payment.setIntent("sale");
            payment.setPayer(payer);
            payment.setTransactions(transactions);
            
            RedirectUrls redirectUrls = new RedirectUrls();
                redirectUrls.setCancelUrl(req.getScheme() + "://"
                       + req.getServerName() + ":" + req.getServerPort()
                        + req.getContextPath() + "/");
                redirectUrls.setReturnUrl(req.getScheme() + "://"
                        + req.getServerName() + ":" + req.getServerPort()
                        + req.getContextPath() + "/");
                
                payment.setRedirectUrls(redirectUrls);    
                payment.setRedirectUrls(redirectUrls);
            
                try {
                    createdPayment = payment.create(apiContext);
                    System.out.println("Creando payment con id = "
                            + createdPayment.getId() + " y status = "
                            + createdPayment.getState());
                   
                    Iterator<Links> links = createdPayment.getLinks().iterator();
                    while (links.hasNext()) {
                        Links link = links.next();
                        if (link.getRel().equalsIgnoreCase("approval_url")) {
                            req.setAttribute("redirectURL", link.getHref());
                        }
                    }
                   
                } catch (PayPalRESTException e) {
                    e.printStackTrace();
             
                }
            }
        } catch (Exception e) {
            System.out.println("Creando Payment Exception ");
            e.printStackTrace();
        }
        return createdPayment;
       
       }
}