
package elpoeta.felurian.domain;

import java.math.BigDecimal;



/**
 *
 * @author elpoeta
 */
public class CarritoItem {
	private Integer id;
	private int cantidad;
	private BigDecimal subtotal;
	private Producto producto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        this.subtotal = calcularSubTotal();
        return  this.subtotal;
    }

    private BigDecimal calcularSubTotal(){
        return this.producto.getPrecio().multiply(new BigDecimal(cantidad));
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "CarritoItem{" + "id=" + id + ", cantidad=" + cantidad + ", subtotal=" + subtotal + ", producto=" + producto + '}';
    }
	
    
    
}
