
package elpoeta.felurian.repository.impl;

import elpoeta.felurian.conexion.Conexion;
import elpoeta.felurian.domain.Bodega;
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
public class BodegaRepositoryImpl implements CrudRepository<Bodega, Integer>{
       private static BodegaRepositoryImpl INSTANCE = null;
    private final static String SQL_BODEGAS_SELECT = "SELECT * FROM bodega;";
    private final static String SQL_BODEGA_SELECT = "SELECT * FROM bodega WHERE id = ?;";
    
    private BodegaRepositoryImpl() throws ClassNotFoundException,
    IOException, SQLException {
}
    public static BodegaRepositoryImpl getInstance() throws ClassNotFoundException,
	IOException, SQLException {
		if (INSTANCE == null) {
			INSTANCE = new BodegaRepositoryImpl();
		}
		return INSTANCE;
	}
    @Override
    public Bodega buscarPorId(Integer id) throws ClassNotFoundException, IOException, SQLException {
                Bodega bod = null;
		Connection conexion = null;
		PreparedStatement ptsmt = null;
		ResultSet rs = null;
		try {
		conexion = Conexion.getInstance().getConnection();
		ptsmt = conexion.prepareStatement(SQL_BODEGA_SELECT);
		ptsmt.setInt(1, id);
		rs = ptsmt.executeQuery();
		
		if(rs.next()) {
		    try {
		        bod = new Bodega();
		        bod.setId(rs.getInt("id"));
		        bod.setNombre(rs.getString("nombre"));

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
		return bod;
    }

    @Override
    public List<Bodega> buscarTodos() throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertar(Bodega obj) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(Bodega obj) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrar(Integer id) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
