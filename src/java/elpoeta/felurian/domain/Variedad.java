
package elpoeta.felurian.domain;

/**
 *
 * @author elpoeta
 */
public class Variedad {
    private Integer id;
    private String nombre;

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

    @Override
    public String toString() {
        return "Variedad{" + "id=" + id + ", nombre=" + nombre + '}';
    }
}
