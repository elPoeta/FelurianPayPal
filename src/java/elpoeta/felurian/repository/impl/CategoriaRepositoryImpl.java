
package elpoeta.felurian.repository.impl;

import elpoeta.felurian.conexion.Conexion;
import elpoeta.felurian.domain.Categoria;
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
public class CategoriaRepositoryImpl implements CrudRepository<Categoria, Integer>{

   private static CategoriaRepositoryImpl INSTANCE = null;
    private final static String SQL_CATEGORIAS_SELECT = "SELECT * FROM categoria;";
    private final static String SQL_CATEGORIA_SELECT = "SELECT * FROM categoria WHERE id = ?;";
    
    private CategoriaRepositoryImpl() throws ClassNotFoundException,
    IOException, SQLException {
}
    public static CategoriaRepositoryImpl getInstance() throws ClassNotFoundException,
	IOException, SQLException {
		if (INSTANCE == null) {
			INSTANCE = new CategoriaRepositoryImpl();
		}
		return INSTANCE;
	}

    @Override
    public Categoria buscarPorId(Integer id) throws ClassNotFoundException,
		IOException, SQLException {
        Categoria cat = null;
		Connection conexion = null;
		PreparedStatement ptsmt = null;
		ResultSet rs = null;
		try {
		conexion = Conexion.getInstance().getConnection();
		ptsmt = conexion.prepareStatement(SQL_CATEGORIA_SELECT);
		ptsmt.setInt(1, id);
		rs = ptsmt.executeQuery();
		List<SubCategoria> subCatLista = null;
		if(rs.next()) {
		    try {
		        cat = new Categoria();
		        cat.setId(rs.getInt("id"));
		        cat.setNombre(rs.getString("nombre"));
		        cat.setActiva(rs.getBoolean("is_activa"));
                       // if(subCatLista == null){
                           // subCatLista = new ArrayList<>();
                            subCatLista = SubCategoriaRepositoryImpl.getInstance().buscarPorIdCategoria(rs.getInt("id"));
                        cat.setSubCategorias(subCatLista);
                        //}
		        
		 
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
		return cat;

    }

    @Override
    public List<Categoria> buscarTodos() throws ClassNotFoundException, IOException, SQLException {
        ArrayList<Categoria> lista = new ArrayList();
		Connection conexion = null;
		PreparedStatement ptsmt = null;
		ResultSet rs = null;
		try {
		conexion = Conexion.getInstance().getConnection();
		ptsmt = conexion.prepareStatement(SQL_CATEGORIAS_SELECT);
		rs = ptsmt.executeQuery();
		Categoria cat = null;
                List<SubCategoria> subCatLista = null;
		while (rs.next()) {
		    try {
		        cat = new Categoria();
		        cat.setId(rs.getInt("id"));
		        cat.setNombre(rs.getString("nombre"));
		        cat.setActiva(rs.getBoolean("is_activa"));
                        subCatLista = SubCategoriaRepositoryImpl.getInstance().buscarPorIdCategoria(cat.getId());
                        cat.setSubCategorias(subCatLista);
		 
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		    lista.add(cat);
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
    public void insertar(Categoria obj) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(Categoria obj) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrar(Integer id) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
