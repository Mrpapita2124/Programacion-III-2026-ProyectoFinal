package repositorios;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import modelos.Usuario;
import utilidades.UtilidadesContrasenia;

public class UsuarioRepository {


	
	
	public boolean guardar(Usuario usuario) {
		
		/*String sql = "SELECT id, email, password FROM users WHERE email = '" 
				+ email + "' AND password = '" + password + "'";*/
		
		String sql = "insert into usuario (nombre, apellido, correo_electronico, contraseña, capacidad_prestamo, url_foto, guardar, rol) values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			stmt.setString(1, usuario.getNombre());
			stmt.setString(2, usuario.getApellido());
			stmt.setString(3, usuario.getCorreo());
			stmt.setString(4, UtilidadesContrasenia.hashearContraseña(usuario.getContrasena()));
			stmt.setDouble(5, usuario.getCapacidadPrestamo());
			stmt.setString(6, usuario.getFoto());
			stmt.setBoolean(7, usuario.isGuardar());
			stmt.setString(8, "comun");
			stmt.executeUpdate();
			
			return true;
			
		}catch(SQLException ex) {
			//ex.printStackTrace();
			System.out.println("sexo tilin");
		}
		return false;
		
	}
		
	
	public List<Usuario> getUsuarios() throws IOException {
		//System.out.println("calling get users");
		
		List<Usuario> usuarios = new ArrayList<>();
	    String sql = "SELECT * FROM usuario";
	    
	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement pst = connection.prepareStatement(sql);
	         java.sql.ResultSet rs = pst.executeQuery()) {
	        
	        while (rs.next()) {
	        	
	            Usuario user = new Usuario(
	            rs.getInt("id_usuario"),
	            rs.getString("nombre"),
	            rs.getString("apellido"),
	            rs.getString("correo_electronico"),
	            rs.getString("contraseña"),
	            rs.getDouble("capacidad_prestamo"),
	            rs.getString("url_foto"),
	            rs.getBoolean("guardar"),
	            rs.getString("rol")
	            );
	            
	            usuarios.add(user);
	        }
	        
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    
	    return usuarios;
				
	}
	public List<Usuario> getUsuariosComunes() throws IOException {
		//System.out.println("calling get users");
		
		List<Usuario> usuarios = new ArrayList<>();
	    String sql = "SELECT * FROM usuario WHERE rol='comun'";
	    
	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement pst = connection.prepareStatement(sql);
	         java.sql.ResultSet rs = pst.executeQuery()) {
	        
	        while (rs.next()) {
	        	
	            Usuario user = new Usuario(
	            rs.getInt("id_usuario"),
	            rs.getString("nombre"),
	            rs.getString("apellido"),
	            rs.getString("correo_electronico"),
	            rs.getString("contraseña"),
	            rs.getDouble("capacidad_prestamo"),
	            rs.getString("url_foto"),
	            rs.getBoolean("guardar"),
	            rs.getString("rol")
	            );
	            
	            usuarios.add(user);
	        }
	        
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    
	    return usuarios;
				
	}
	
	
	public boolean eliminar(int id) {
		
		String sql = "DELETE FROM usuario WHERE id_usuario = ?";
		
		try(Connection connection = DatabaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql)) {
			
			pst.setInt(1, id);
			int affectedRows = pst.executeUpdate();
			if(affectedRows > 0) {
				System.out.println("Se eliminó");
				return true;
			}
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return false;
		
	}
	
	
	public boolean actualizar(int indice, Usuario usuarioActualizado) throws IOException {

		String sql = "UPDATE usuario SET nombre = ?, apellido = ?, correo_electronico = ?,"
				+ " contraseña = ?, url_foto = ?, rol = ?, guardar = ? "
				+ "WHERE id_usuario = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement pst = connection.prepareStatement(sql)) {

			pst.setString(1, usuarioActualizado.getNombre());
			pst.setString(2, usuarioActualizado.getApellido());
			pst.setString(3, usuarioActualizado.getCorreo());
			pst.setString(4, UtilidadesContrasenia.hashearContraseña(usuarioActualizado.getContrasena()));
			pst.setString(5, usuarioActualizado.getFoto());
			pst.setString(6, usuarioActualizado.getRol());
			pst.setBoolean(7, usuarioActualizado.isGuardar());
			pst.setInt(8, usuarioActualizado.getId()); 

			int affectedRows = pst.executeUpdate();

			if(affectedRows > 0) {
				//System.out.println("Cambios guardados");
				return true;
			}			

		}catch(SQLException ex) {
			//ex.printStackTrace();
		}
		
		//System.out.println("No se hicieron cambios");
		return false;
	}
	public boolean actualizar(Usuario usuarioActualizado) throws IOException {

		String sql = "UPDATE usuario SET nombre = ?, apellido = ?, correo_electronico = ?,"
				+ " contraseña = ?, capacidad_prestamo = ?, url_foto = ?, rol = ?, guardar = ? "
				+ "WHERE id_usuario = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement pst = connection.prepareStatement(sql)) {

			pst.setString(1, usuarioActualizado.getNombre());
			pst.setString(2, usuarioActualizado.getApellido());
			pst.setString(3, usuarioActualizado.getCorreo());
			pst.setString(4, UtilidadesContrasenia.hashearContraseña(usuarioActualizado.getContrasena()));
			pst.setDouble(5, usuarioActualizado.getCapacidadPrestamo());
			pst.setString(6, usuarioActualizado.getFoto());
			pst.setString(7, usuarioActualizado.getRol());
			pst.setBoolean(8, usuarioActualizado.isGuardar());
			pst.setInt(9, usuarioActualizado.getId()); 

			int affectedRows = pst.executeUpdate();
			System.out.println("sadfadsf" + affectedRows);

			if(affectedRows > 0) {
				//System.out.println("Cambios guardados");
				return true;
			}			

		}catch(SQLException ex) {
			//ex.printStackTrace();
		}
		
		//System.out.println("No se hicieron cambios");
		return false;
	}	
	
	public boolean actualizarSinContrasenia(Usuario usuarioActualizado) throws IOException {

		String sql = "UPDATE usuario SET nombre = ?, apellido = ?, correo_electronico = ?,"
				+ " capacidad_prestamo = ?, url_foto = ?, rol = ?, guardar = ? "
				+ "WHERE id_usuario = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement pst = connection.prepareStatement(sql)) {

			pst.setString(1, usuarioActualizado.getNombre());
			pst.setString(2, usuarioActualizado.getApellido());
			pst.setString(3, usuarioActualizado.getCorreo());
			pst.setDouble(4, usuarioActualizado.getCapacidadPrestamo());
			pst.setString(5, usuarioActualizado.getFoto());
			pst.setString(6, usuarioActualizado.getRol());
			pst.setBoolean(7, usuarioActualizado.isGuardar());
			pst.setInt(8, usuarioActualizado.getId()); 

			int affectedRows = pst.executeUpdate();
			

			if(affectedRows > 0) {
				//System.out.println("Cambios guardados");
				return true;
			}			

		}catch(SQLException ex) {
			//ex.printStackTrace();
		}
		
		//System.out.println("No se hicieron cambios");
		return false;
	}

}










