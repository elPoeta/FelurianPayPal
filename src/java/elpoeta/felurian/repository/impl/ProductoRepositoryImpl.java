
package elpoeta.felurian.repository.impl;

import elpoeta.felurian.conexion.Conexion;
import elpoeta.felurian.domain.Bodega;
import elpoeta.felurian.domain.Producto;
import elpoeta.felurian.domain.SubCategoria;
import elpoeta.felurian.domain.Variedad;
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
public class ProductoRepositoryImpl implements CrudRepository<Producto, Integer>{
    
   private static ProductoRepositoryImpl INSTANCE = null;
    private final static String SQL_PRODUCTOS_SELECT = "SELECT * FROM producto;";
    private final static String SQL_PRODUCTO_SELECT = "SELECT * FROM producto WHERE id = ?;";
//    private final static String SQL_PRODUCTOS_SELECT_BY_CATEGORIA = "SELECT producto.id, producto.nombre, producto.precio, "
//                                                                  + " producto.imagen, producto.descripcion,producto.variedad_id, producto.sub_categoria_id,"
//                                                                  + " producto.is_disponible, producto.stock, producto.bodega_id "
//                                                                  + " FROM producto INNER JOIN sub_categoria on sub_categoria.id = producto.sub_categoria_id "
//                                                                  + " INNER JOIN categoria ON sub_categoria.categoria_id = categoria.id" 
//                                                                  +" WHERE categoria.id = ?;";
    
    private final static String SQL_PRODUCTOS_SELECT_BY_CATEGORIA = "SELECT producto.id, producto.nombre, producto.precio, "
                                                                  + "producto.imagen, producto.is_disponible, producto.stock "
                                                                  + "FROM producto INNER JOIN sub_categoria on sub_categoria.id = producto.sub_categoria_id "
                                                                  + "INNER JOIN categoria ON sub_categoria.categoria_id = categoria.id " 
                                                                  +"WHERE categoria.id = ?;";    
    private final static String SQL_PRODUCTOS_SELECT_BY_SUBCATEGORIA = "SELECT * from producto WHERE producto.sub_categoria_id = ?;";   
    
    private ProductoRepositoryImpl() throws ClassNotFoundException,
    IOException, SQLException {
}
    public static ProductoRepositoryImpl getInstance() throws ClassNotFoundException,
	IOException, SQLException {
		if (INSTANCE == null) {
			INSTANCE = new ProductoRepositoryImpl();
		}
		return INSTANCE;
	}
    @Override
    public Producto buscarPorId(Integer id) throws ClassNotFoundException, IOException, SQLException {
          Producto prod = null;
		Connection conexion = null;
		PreparedStatement ptsmt = null;
		ResultSet rs = null;
		try {
		conexion = Conexion.getInstance().getConnection();
		ptsmt = conexion.prepareStatement(SQL_PRODUCTO_SELECT);
		ptsmt.setInt(1, id);
		rs = ptsmt.executeQuery();
	
		if(rs.next()) {
		    try {
		        prod = new Producto();
		        prod.setId(rs.getInt("id"));
		        prod.setNombre(rs.getString("nombre"));
		        prod.setPrecio(rs.getBigDecimal("precio"));
                        prod.setDescripcion(rs.getString("descripcion"));
                        prod.setStock(rs.getInt("stock"));
                        prod.setImagen(rs.getString("imagen"));
		        prod.setDisponible(rs.getBoolean("is_disponible"));
		        SubCategoria sc = SubCategoriaRepositoryImpl.getInstance().buscarPorId(rs.getInt("sub_categoria_id"));
                        prod.setSubCategoria(sc);
                        Bodega bodega = BodegaRepositoryImpl.getInstance().buscarPorId(rs.getInt("bodega_id"));
                        prod.setBodega(bodega);
                        Variedad variedad = VariedadRepositoryImpl.getInstance().buscarPorId(rs.getInt("variedad_id"));
                        prod.setVariedad(variedad);
                        
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
		return prod;

    }

    @Override
    public List<Producto> buscarTodos() throws ClassNotFoundException, IOException, SQLException {
         ArrayList<Producto> lista = new ArrayList();
		Connection conexion = null;
		PreparedStatement ptsmt = null;
		ResultSet rs = null;
		try {
		conexion = Conexion.getInstance().getConnection();
		ptsmt = conexion.prepareStatement(SQL_PRODUCTOS_SELECT);
		rs = ptsmt.executeQuery();
		Producto prod = null;
               
		while (rs.next()) {
		    try {
		        prod = new Producto();
		        prod.setId(rs.getInt("id"));
		        prod.setNombre(rs.getString("nombre"));
		        prod.setPrecio(rs.getBigDecimal("precio"));
                        prod.setDescripcion(rs.getString("descripcion"));
                        prod.setStock(rs.getInt("stock"));
                        prod.setImagen(rs.getString("imagen"));
		        prod.setDisponible(rs.getBoolean("is_disponible"));
		        SubCategoria sc = SubCategoriaRepositoryImpl.getInstance().buscarPorId(rs.getInt("sub_categoria_id"));
                        prod.setSubCategoria(sc);
                        Bodega bodega = BodegaRepositoryImpl.getInstance().buscarPorId(rs.getInt("bodega_id"));
                        prod.setBodega(bodega);
                        Variedad variedad = VariedadRepositoryImpl.getInstance().buscarPorId(rs.getInt("variedad_id"));
                        prod.setVariedad(variedad);
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		    lista.add(prod);
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
    public void insertar(Producto obj) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(Producto obj) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrar(Integer id) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Producto> buscarPorCategoria(Integer id) throws ClassNotFoundException, IOException, SQLException {
         ArrayList<Producto> lista = new ArrayList();
		Connection conexion = null;
		PreparedStatement ptsmt = null;
		ResultSet rs = null;
                try {
		conexion = Conexion.getInstance().getConnection();
		ptsmt = conexion.prepareStatement(SQL_PRODUCTOS_SELECT_BY_CATEGORIA);
                    
                ptsmt.setInt(1, id);
		rs = ptsmt.executeQuery();
		Producto prod = null;
               
		while (rs.next()) {
		    try {
		        prod = new Producto();
		        prod.setId(rs.getInt("id"));
		        prod.setNombre(rs.getString("nombre"));
		        prod.setPrecio(rs.getBigDecimal("precio"));
                        //prod.setDescripcion(rs.getString("descripcion"));
                        prod.setStock(rs.getInt("stock"));
                        prod.setImagen(rs.getString("imagen"));
		        prod.setDisponible(rs.getBoolean("is_disponible"));
		        //SubCategoria sc = SubCategoriaRepositoryImpl.getInstance().buscarPorId(rs.getInt("sub_categoria_id"));
                        //prod.setSubCategoria(sc);
                        //Bodega bodega = BodegaRepositoryImpl.getInstance().buscarPorId(rs.getInt("bodega_id"));
                        //prod.setBodega(bodega);
                        //Variedad variedad = VariedadRepositoryImpl.getInstance().buscarPorId(rs.getInt("variedad_id"));
                        //prod.setVariedad(variedad);
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		    lista.add(prod);
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
    
    public List<Producto> buscarPorSubCategoria(Integer id) throws ClassNotFoundException, IOException, SQLException {
         ArrayList<Producto> lista = new ArrayList();
		Connection conexion = null;
		PreparedStatement ptsmt = null;
		ResultSet rs = null;
                try {
		conexion = Conexion.getInstance().getConnection();
		ptsmt = conexion.prepareStatement(SQL_PRODUCTOS_SELECT_BY_SUBCATEGORIA);
                    
                ptsmt.setInt(1, id);
		rs = ptsmt.executeQuery();
		Producto prod = null;
               
		while (rs.next()) {
		    try {
		        prod = new Producto();
		        prod.setId(rs.getInt("id"));
		        prod.setNombre(rs.getString("nombre"));
		        prod.setPrecio(rs.getBigDecimal("precio"));
                        //prod.setDescripcion(rs.getString("descripcion"));
                        prod.setStock(rs.getInt("stock"));
                        prod.setImagen(rs.getString("imagen"));
		        prod.setDisponible(rs.getBoolean("is_disponible"));
		        //SubCategoria sc = SubCategoriaRepositoryImpl.getInstance().buscarPorId(rs.getInt("sub_categoria_id"));
                        //prod.setSubCategoria(sc);
                        //Bodega bodega = BodegaRepositoryImpl.getInstance().buscarPorId(rs.getInt("bodega_id"));
                        //prod.setBodega(bodega);
                        //Variedad variedad = VariedadRepositoryImpl.getInstance().buscarPorId(rs.getInt("variedad_id"));
                        //prod.setVariedad(variedad);
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		    lista.add(prod);
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
