
package elpoeta.felurian.domain;

import java.math.BigDecimal;

/**
 *
 * @author elpoeta
 */
public class Producto {
    private Integer id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private Integer stock;
    private String imagen;
    private Boolean disponible;
    private SubCategoria subCategoria;
    private Bodega bodega;
    private Variedad variedad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        if(precio.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("el precio debe ser mayor que cero");
        } 
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }



    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public Variedad getVariedad() {
        return variedad;
    }

    public void setVariedad(Variedad variedad) {
        this.variedad = variedad;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", descripcion=" + descripcion + ", stock=" + stock + ", imagen=" + imagen + ", disponible=" + disponible + ", subCategoria=" + subCategoria + ", bodega=" + bodega + ", variedad=" + variedad + '}';
    }

  
    
}
