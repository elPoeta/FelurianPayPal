
package elpoeta.felurian.repository.impl;

import elpoeta.felurian.conexion.Conexion;
import elpoeta.felurian.domain.SubCategoria;
import elpoeta.felurian.repository.CrudRepository;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elpoeta
 */
public class SubCategoriaRepositoryImpl implements CrudRepository<SubCategoria, Integer>{

    
   private static SubCategoriaRepositoryImpl INSTANCE = null;
    private final static String SQL_SUBCATEGORIAS_SELECT = "SELECT * FROM sub_categoria;";
    private final static String SQL_SUBCATEGORIA_SELECT = "SELECT * FROM sub_categoria WHERE id = ?;";
    private final static String SQL_SUBCATEGORIA_FINDBY_IDCATEGORIA = "SELECT * FROM sub_categoria WHERE categoria_id = ?;";
    
    private SubCategoriaRepositoryImpl() throws ClassNotFoundException,
    IOException, SQLException {
}
    public static SubCategoriaRepositoryImpl getInstance() throws ClassNotFoundException,
	IOException, SQLException {
		if (INSTANCE == null) {
			INSTANCE = new SubCategoriaRepositoryImpl();
		}
		return INSTANCE;
	}

    @Override
    public SubCategoria buscarPorId(Integer id) throws ClassNotFoundException,
		IOException, SQLException {
        SubCategoria subCat = null;
		Connection conexion = null;
		PreparedStatement ptsmt = null;
		ResultSet rs = null;
		try {
		conexion = Conexion.getInstance().getConnection();
		ptsmt = conexion.prepareStatement(SQL_SUBCATEGORIA_SELECT);
		ptsmt.setInt(1, id);
		rs = ptsmt.executeQuery();
		
		if(rs.next()) {
		    try {
		        subCat = new SubCategoria();
		        subCat.setId(rs.getInt("id"));
		        subCat.setNombre(rs.getString("nombre"));
		        subCat.setActiva(rs.getBoolean("is_activa"));
                        subCat.setIdCategoria(rs.getInt("categoria_id"));
                        
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
		return subCat;

    }

    @Override
    public List<SubCategoria> buscarTodos() throws ClassNotFoundException, IOException, SQLException {
        ArrayList<SubCategoria> lista = new ArrayList();
		Connection conexion = null;
		PreparedStatement ptsmt = null;
		ResultSet rs = null;
		try {
		conexion = Conexion.getInstance().getConnection();
		ptsmt = conexion.prepareStatement(SQL_SUBCATEGORIAS_SELECT);
		rs = ptsmt.executeQuery();
		SubCategoria subCat = null;
		while (rs.next()) {
		    try {
		        subCat = new SubCategoria();
		        subCat.setId(rs.getInt("id"));
		        subCat.setNombre(rs.getString("nombre"));
		        subCat.setActiva(rs.getBoolean("is_activa"));
                        subCat.setIdCategoria(rs.getInt("categoria_id"));
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		    lista.add(subCat);
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
		return lista;
    }

    @Override
    public void insertar(SubCategoria obj) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(SubCategoria obj) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrar(Integer id) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<SubCategoria> buscarPorIdCategoria(Integer id) throws ClassNotFoundException, IOException, SQLException{
        ArrayList<SubCategoria> lista = new ArrayList();
       		Connection conexion = null;
		PreparedStatement ptsmt = null;
		ResultSet rs = null;
		try {
		conexion = Conexion.getInstance().getConnection();
		ptsmt = conexion.prepareStatement(SQL_SUBCATEGORIA_FINDBY_IDCATEGORIA);
                ptsmt.setInt(1, id);
		rs = ptsmt.executeQuery();
		SubCategoria subCat = null;
		while (rs.next()) {
		    try {
		        subCat = new SubCategoria();
		        subCat.setId(rs.getInt("id"));
		        subCat.setNombre(rs.getString("nombre"));
		        subCat.setActiva(rs.getBoolean("is_activa"));
                        subCat.setIdCategoria(rs.getInt("categoria_id"));
                    
                    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		    lista.add(subCat);
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
		return lista;
    }
    
}
