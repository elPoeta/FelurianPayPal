
package elpoeta.felurian.repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author elpoeta
 */
public interface CrudRepository<T,K> {
    
    T buscarPorId(K id) throws ClassNotFoundException,IOException, SQLException;
    List<T> buscarTodos() throws ClassNotFoundException,IOException, SQLException;
    void insertar(T obj) throws ClassNotFoundException,IOException, SQLException;
    void modificar(T obj) throws ClassNotFoundException,IOException, SQLException;
    void borrar(K id) throws ClassNotFoundException,IOException, SQLException;
}
