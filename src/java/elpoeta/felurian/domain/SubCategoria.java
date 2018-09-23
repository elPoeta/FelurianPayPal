
package elpoeta.felurian.domain;

/**
 *
 * @author elpoeta
 */
public class SubCategoria {
    private Integer id;
    private String nombre;
    private Boolean activa;
    private Integer idCategoria;

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

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "SubCategoria{" + "id=" + id + ", nombre=" + nombre + ", activa=" + activa + ", idCategoria=" + idCategoria + '}';
    }


    
}
