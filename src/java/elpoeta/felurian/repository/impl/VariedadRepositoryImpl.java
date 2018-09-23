
package elpoeta.felurian.repository.impl;

import elpoeta.felurian.conexion.Conexion;
import elpoeta.felurian.domain.Variedad;
import elpoeta.felurian.repository.CrudRepository;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author elpoeta
 */
public class VariedadRepositoryImpl implements CrudRepository<Variedad, Integer>{

       private static VariedadRepositoryImpl INSTANCE = null;
    private final static String SQL_VARIEDADES_SELECT = "SELECT * FROM variedad;";
    private final static String SQL_VARIEDAD_SELECT = "SELECT * FROM variedad WHERE id = ?;";
    
    private VariedadRepositoryImpl() throws ClassNotFoundException,
    IOException, SQLException {
}
    public static VariedadRepositoryImpl getInstance() throws ClassNotFoundException,
	IOException, SQLException {
		if (INSTANCE == null) {
			INSTANCE = new VariedadRepositoryImpl();
		}
		return INSTANCE;
	}
    @Override
    public Variedad buscarPorId(Integer id) throws ClassNotFoundException, IOException, SQLException {
                Variedad var = null;
		Connection conexion = null;
		PreparedStatement ptsmt = null;
		ResultSet rs = null;
		try {
		conexion = Conexion.getInstance().getConnection();
		ptsmt = conexion.prepareStatement(SQL_VARIEDAD_SELECT);
		ptsmt.setInt(1, id);
		rs = ptsmt.executeQuery();
		
		if(rs.next()) {
		    try {
		        var = new Variedad();
		        var.setId(rs.getInt("id"));
		        var.setNombre(rs.getString("nombre"));

		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		}
		} finally {
		try {
		    rs.close();
		} finally {
		    try {
		        ptsmt.close();
		    } finally {
		        conexion.close();
		    }
		}
		}
		return var;
    }

    @Override
    public List<Variedad> buscarTodos() throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertar(Variedad obj) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(Variedad obj) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrar(Integer id) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
