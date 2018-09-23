
package elpoeta.felurian.repository.impl;

import elpoeta.felurian.conexion.Conexion;
import elpoeta.felurian.domain.Usuario;
import elpoeta.felurian.repository.CrudRepository;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author elpoeta
 */
public class UsuarioRepositoryImpl implements CrudRepository<Usuario, Integer>{
    private static UsuarioRepositoryImpl INSTANCE = null;
    private static final String SALT = "salt";
    private final static String SQL_USERS_SELECT = "SELECT * FROM usuario;";
    private final static String SQL_USER_SELECT = "SELECT * FROM usuario WHERE id = ?;";
    private final static String SQL_USER_FIND_BY_EMAIL = "SELECT * FROM usuario WHERE email = ?;";
    private final static String SQL_USER_INSERT = "INSERT INTO usuario (nombre, apellido, email, password, is_activo)values(?,?,?,?,?);";
    
    private UsuarioRepositoryImpl() throws ClassNotFoundException,
    IOException, SQLException {
}
    public static UsuarioRepositoryImpl getInstance() throws ClassNotFoundException,
	IOException, SQLException {
		if (INSTANCE == null) {
			INSTANCE = new UsuarioRepositoryImpl();
		}
		return INSTANCE;
	}

    @Override
    public Usuario buscarPorId(Integer id) throws ClassNotFoundException, IOException, SQLException {
        Usuario user = null;
		Connection conexion = null;
		PreparedStatement ptsmt = null;
		ResultSet rs = null;
		try {
		conexion = Conexion.getInstance().getConnection();
		ptsmt = conexion.prepareStatement(SQL_USER_SELECT);
		ptsmt.setInt(1, id);
		rs = ptsmt.executeQuery();
		
		if(rs.next()) {
		    try {
		        user = new Usuario();
		        user.setId(rs.getInt("id"));
		        user.setNombre(rs.getString("nombre"));
                        user.setApellido(rs.getString("apellido"));
                        //user.setTelefono(rs.getString("telefono"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));
                        user.setActivo(rs.getBoolean("is_activo"));
		     
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
		return user;
    }

    @Override
    public List<Usuario> buscarTodos() throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertar(Usuario user) throws ClassNotFoundException, IOException, SQLException {
         Connection c = null;
		   PreparedStatement ptsmt = null;
		   try {
		       c = Conexion.getInstance().getConnection();
		       ptsmt = c.prepareStatement(SQL_USER_INSERT);
                       ptsmt.setString(1, user.getNombre());
                       ptsmt.setString(2, user.getApellido());
		       ptsmt.setString(3,  user.getEmail());
                       String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12, new SecureRandom(SALT.getBytes())));
                       System.out.println("cryt > "+hashed);
		       ptsmt.setString(4, hashed);
                       ptsmt.setBoolean(5, true);
		       ptsmt.execute();
		   } finally {
		       try {
		           ptsmt.close();
		       } finally {
		           c.close();
		       }
		   }
    }

    @Override
    public void modificar(Usuario obj) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrar(Integer id) throws ClassNotFoundException, IOException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        public Usuario buscarPorEmail(String email) throws ClassNotFoundException, IOException, SQLException {
         Usuario user = null;
          System.out.println("Mail "+email);
		Connection conexion = null;
		PreparedStatement ptsmt = null;
		ResultSet rs = null;
		try {
		conexion = Conexion.getInstance().getConnection();
		ptsmt = conexion.prepareStatement(SQL_USER_FIND_BY_EMAIL);
		ptsmt.setString(1, email);
		rs = ptsmt.executeQuery();
		
		if(rs.next()) {
		    try {
                      user = new Usuario();
		      user.setId(rs.getInt("id"));
		      user.setNombre(rs.getString("nombre"));
                      user.setEmail(rs.getString("email"));
                      user.setPassword(rs.getString("password"));
                      user.setActivo(rs.getBoolean("is_activo"));
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
		return user;

    }
}
