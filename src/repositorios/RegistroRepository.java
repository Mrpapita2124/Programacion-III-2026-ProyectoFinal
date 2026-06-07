package repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DatabaseConnection;
import modelos.Usuario;
import utilidades.UtilidadesContrasenia;

public class RegistroRepository {
	
	
	public boolean registro(String correo, String contrasena,String nombre,String apellido,String foto,boolean guardar) {
		
		/*String sql = "SELECT id, email, password FROM users WHERE email = '" 
				+ email + "' AND password = '" + password + "'";*/
		
		String sql = "insert into usuario (nombre, apellido, correo_electronico, contraseña, capacidad_prestamo, url_foto, guardar, rol) values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			stmt.setString(1, nombre);
			stmt.setString(2, apellido);
			stmt.setString(3, correo);
			stmt.setString(4, UtilidadesContrasenia.hashearContraseña(contrasena));
			stmt.setDouble(5, 100000);
			stmt.setString(6, foto);
			stmt.setBoolean(7, guardar);
			stmt.setString(8, "comun");
			stmt.executeUpdate();
			
			return true;
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
		
	}
}
