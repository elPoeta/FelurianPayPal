
package elpoeta.felurian.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author elpoeta
 */
public class Carrito {
    private Integer id;
    private LocalDate fechaCreacion;
    private BigDecimal total;
    private int cantidadItems;
    private Usuario usuario;
    private TreeMap<Integer,CarritoItem> items;

    public Carrito() {
        this.items = new TreeMap<>();
        this.fechaCreacion = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getTotal() {
        return calcularTotal();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TreeMap<Integer, CarritoItem> getItems() {
        return this.items;
    }

        
     public void agregar(CarritoItem producto) {
         this.items.put( producto.getProducto().getId(), producto );
    }

    public void setItems(TreeMap<Integer, CarritoItem> items) {
        this.items = items;
    }
        
        
   
    private BigDecimal calcularTotal() {
        this.total = BigDecimal.ZERO;
         
         Iterator it = this.items.keySet().iterator();
          while(it.hasNext()){
               Integer key = (Integer) it.next();
         this.total = this.total.add(this.items.get(key).getSubtotal());
         }
       return this.total;
    }

     public void quitar(Integer id) {
        this.items.remove(id);
    }

    public int getCantidadItems() {
        return totalItems();
    }
    
    public void setCantidadItems(int cantidadItems) {
        this.cantidadItems = cantidadItems;
    }
     
    private int totalItems(){
         this.cantidadItems = 0;
         Iterator it = this.items.keySet().iterator();
          while(it.hasNext()){
               Integer key = (Integer) it.next();
          this.cantidadItems +=  this.items.get(key).getCantidad();
         }
       return this.cantidadItems;
    } 

    @Override
    public String toString() {
        return "Carrito{" + "id=" + id + ", fechaCreacion=" + fechaCreacion + ", total=" + total + ", cantidadItems=" + cantidadItems + ", usuario=" + usuario + ", items=" + items + '}';
    }
     
     

  
     
}
