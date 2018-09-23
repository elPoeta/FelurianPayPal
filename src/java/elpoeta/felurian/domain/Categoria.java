
package elpoeta.felurian.domain;

import java.util.List;

/**
 *
 * @author elpoeta
 */
public class Categoria {
    private Integer id;
    private String nombre;
    private Boolean activa;
    private List<SubCategoria> subCategorias;

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

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public List<SubCategoria> getSubCategorias() {
        return subCategorias;
    }

    public void setSubCategorias(List<SubCategoria> subCategorias) {
        this.subCategorias = subCategorias;
    }

    @Override
    public String toString() {
        return "Categoria{" + "id=" + id + ", nombre=" + nombre + ", activa=" + activa + ", subCategorias=" + subCategorias + '}';
    }    
}
