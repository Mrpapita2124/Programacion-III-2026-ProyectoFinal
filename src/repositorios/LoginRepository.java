package repositorios;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import config.DatabaseConnection;
import modelos.Usuario;
import utilidades.UtilidadesContrasenia;

public class LoginRepository {

	public Usuario login(String correo, String contrasena) {
		
		
		String sql = "SELECT id_usuario, correo_electronico, contraseña, rol, nombre FROM usuario WHERE correo_electronico = ?";
		
		try (
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			
			stmt.setString(1, correo);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) 
			{
				String hashedPassword = rs.getString("contraseña");
				//System.out.println(hashedPassword);
				
				boolean contraseniaValida = UtilidadesContrasenia.comprobarContrasenia(contrasena, hashedPassword);
				
				if(!contraseniaValida) 
					return null;
				
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id_usuario"));
				usuario.setCorreo(rs.getString("correo_electronico"));
				usuario.setNombre(rs.getString("nombre"));
				usuario.setRol(rs.getString("rol"));

				return usuario;
			}
		}
		catch(SQLException ex) 
		{
			ex.printStackTrace();
		}

		return null;
	}
	
	
}







