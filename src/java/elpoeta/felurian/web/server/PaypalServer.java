
package elpoeta.felurian.web.server;

import com.paypal.api.payments.*;
import com.paypal.base.rest.*;
import elpoeta.felurian.domain.Carrito;
import static elpoeta.felurian.util.CredencialApi.*;
import elpoeta.felurian.util.GsonUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;


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
                 sumaItems = sumaItems.add(new BigDecimal(items.get(i).getPrice()));
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
        
        
        
        
        
        
        
        
        
        
        
        
        /* Payment createdPayment = null;

        APIContext apiContext = new APIContext(CLIENTE_ID, CLIENTE_SECRET, MODO);
  
        if (req.getParameter("PayerID") != null) {
            Payment payment = new Payment();
            payment.setId(req.getParameter("paymentId"));

            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(req.getParameter("PayerID"));
            try {
                createdPayment = payment.execute(apiContext, paymentExecution);
                System.out.println(createdPayment);
            } catch (PayPalRESTException e) {
                System.err.println(e.getDetails());
            }
        } else {

  
            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");


            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl("http://localhost:8084/FelurianPayPal/cancel");
            redirectUrls.setReturnUrl("http://localhost:8084/FelurianPayPal/ejecutarpago");
             
            List<Item> items = new ArrayList();
            Iterator it =  carrito.getItems().keySet().iterator();
            while(it.hasNext()){
               Integer key = (Integer) it.next();
                Item item = new Item();
                item.setName(carrito.getItems().get(key).getProducto().getNombre());
                item.setQuantity(String.valueOf(carrito.getItems().get(key).getCantidad()));
                item.setPrice(String.valueOf(carrito.getItems().get(key).getProducto().getPrecio()));
                item.setTax("0.00");
                item.setCurrency("USD");
                items.add(item);
        
         }
             
            ItemList itemList = new ItemList();
            itemList.setItems(items);

            Details details = new Details();
            details.setShipping("0.00");
            details.setSubtotal(String.valueOf(carrito.getTotal()));
            details.setTax("0.00");
           

            Amount amount = new Amount();
            amount.setCurrency("USD");
            amount.setTotal(String.valueOf(carrito.getTotal()));
            amount.setDetails(details);
          
            
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setDescription("Descripcion de pago");
            transaction.setItemList(itemList);
            
            List<Transaction> transactions = new ArrayList();
            transactions.add(transaction);


            Payment payment = new Payment();
            payment.setIntent("sale");
            payment.setPayer(payer);
            payment.setRedirectUrls(redirectUrls);
            payment.setTransactions(transactions);

       
            try {

                createdPayment = payment.create(apiContext);
                Iterator links = createdPayment.getLinks().iterator();
                while (links.hasNext()) {
                    Links link = (Links) links.next();
                    if (link.getRel().equalsIgnoreCase("approval_url")) {
                       
                         link.getHref();
                    }
                }
            } catch (PayPalRESTException e) {
                System.err.println(e.getDetails());
            }
        }
        return createdPayment;
    }*/
   
}


 /*
    public Payment crearPago(HttpServletRequest req, HttpServletResponse resp) {
        Payment createdPayment = null;
        System.out.println("CREARPAGO");
       Amount amount = new Amount();
amount.setCurrency("USD");
amount.setTotal("1.00");
        System.out.println("amount>>> "+amount.getTotal());
Transaction transaction = new Transaction();
transaction.setAmount(amount);
      System.out.println("TRANSACCTION>>> "+transaction.getAmount());
List<Transaction> transactions = new ArrayList();
transactions.add(transaction);
        System.out.println("LISTTRANSACCTION>>> "+transactions.get(0).getAmount());
Payer payer = new Payer();
payer.setPaymentMethod("paypal");
  System.out.println("PAYER>>> "+payer.getPaymentMethod());
Payment payment = new Payment();
        System.out.println("new payment");
payment.setIntent("sale");
System.out.println("set intent");
payment.setPayer(payer);
System.out.println("set payer");
payment.setTransactions(transactions);

System.out.println("PAYMENT "+payment.getIntent());

RedirectUrls redirectUrls = new RedirectUrls();
redirectUrls.setCancelUrl("https://example.com/cancel");
redirectUrls.setReturnUrl("https://example.com/return");
payment.setRedirectUrls(redirectUrls);

        System.out.println("ANTES TRY");
try {
     System.out.println("TRY");
    APIContext apiContext = new APIContext(CLIENTE_ID, CLIENTE_SECRET, "sandbox");
    createdPayment = payment.create(apiContext);
     System.out.println(createdPayment.toString());
} catch (PayPalRESTException e) {
    // Handle errors
} catch (Exception ex) {
    // Handle errors
}
        
        return createdPayment;
    }
*/