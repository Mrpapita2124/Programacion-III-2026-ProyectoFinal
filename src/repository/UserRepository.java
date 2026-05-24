package repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import modelos.User;
import utils.PasswordUtils;

public class UserRepository {


	
	
	public boolean save(User user) {
		
		/*String sql = "SELECT id, email, password FROM users WHERE email = '" 
				+ email + "' AND password = '" + password + "'";*/
		
		String sql = "insert into usuario (nombre, apellido, correo_electronico, contraseña, capacidad_prestamo, url_foto, guardar, rol) values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			stmt.setString(1, user.getNombre());
			stmt.setString(2, user.getApellido());
			stmt.setString(3, user.getCorreo());
			stmt.setString(4, PasswordUtils.hashPassword(user.getContrasena()));
			stmt.setDouble(5, 10000);
			stmt.setString(6, user.getFoto());
			stmt.setBoolean(7, user.isGuardar());
			stmt.setString(8, "comun");
			stmt.executeUpdate();
			
			return true;
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
		
	}
		
	
	public List<User> getUsers() throws IOException {
		//System.out.println("calling get users");
		
		List<User> users = new ArrayList<>();
	    String sql = "SELECT * FROM usuario";
	    
	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement pst = connection.prepareStatement(sql);
	         java.sql.ResultSet rs = pst.executeQuery()) {
	        
	        while (rs.next()) {
	        	
	            User user = new User(
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
	            
	            users.add(user);
	        }
	        
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    
	    return users;
				
	}
	
	
	public boolean delete(int id) {
		
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
	
	
	public boolean update(int index, User updatedUser) throws IOException {

		String sql = "UPDATE usuario SET nombre = ?, apellido = ?, correo_electronico = ?,"
				+ " contraseña = ?, url_foto = ?, rol = ?, guardar = ? "
				+ "WHERE id_usuario = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement pst = connection.prepareStatement(sql)) {

			pst.setString(1, updatedUser.getNombre());
			pst.setString(2, updatedUser.getApellido());
			pst.setString(3, updatedUser.getCorreo());
			pst.setString(4, PasswordUtils.hashPassword(updatedUser.getContrasena()));
			pst.setString(5, updatedUser.getFoto());
			pst.setString(6, updatedUser.getRol());
			pst.setBoolean(7, updatedUser.isGuardar());
			pst.setInt(8, updatedUser.getId()); 

			int affectedRows = pst.executeUpdate();

			if(affectedRows > 0) {
				//System.out.println("Cambios guardados");
				return true;
			}			

		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		//System.out.println("No se hicieron cambios");
		return false;
	}
	public boolean update(User updatedUser) throws IOException {

		String sql = "UPDATE usuario SET nombre = ?, apellido = ?, correo_electronico = ?,"
				+ " contraseña = ?, capacidad_prestamo = ?, url_foto = ?, rol = ?, guardar = ? "
				+ "WHERE id_usuario = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement pst = connection.prepareStatement(sql)) {

			pst.setString(1, updatedUser.getNombre());
			pst.setString(2, updatedUser.getApellido());
			pst.setString(3, updatedUser.getCorreo());
			pst.setString(4, PasswordUtils.hashPassword(updatedUser.getContrasena()));
			pst.setDouble(5, updatedUser.getCapacidadPrestamo());
			pst.setString(6, updatedUser.getFoto());
			pst.setString(7, updatedUser.getRol());
			pst.setBoolean(8, updatedUser.isGuardar());
			pst.setInt(9, updatedUser.getId()); 

			int affectedRows = pst.executeUpdate();
			System.out.println("sadfadsf" + affectedRows);

			if(affectedRows > 0) {
				//System.out.println("Cambios guardados");
				return true;
			}			

		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		//System.out.println("No se hicieron cambios");
		return false;
	}	
	
	

}










