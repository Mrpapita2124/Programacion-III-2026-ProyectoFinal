package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import modelos.Client;
import modelos.EstadoPrestamo;
import modelos.Prestamo;

public class EstadoPrestamoRepository {
	public boolean save(EstadoPrestamo estadoPrestamo) {
	    String sql = "insert into estado_prestamo (id_prestamo, monto_restante, fecha_proximo_pago, monto_proximo_pago, estado, dinero_atrasado) values (?, ?, ?, ?, ?, ?)";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, estadoPrestamo.getId_prestamo());        
	        stmt.setDouble(2, estadoPrestamo.getMonto_restante());    
	        stmt.setDate(3, estadoPrestamo.getFecha_proximo_pago());     
	        stmt.setDouble(4, estadoPrestamo.getMonto_proximo_pago());   
	        stmt.setString(5, estadoPrestamo.getEstado());                
	        stmt.setDouble(6, estadoPrestamo.getDinero_atrasado());       

	        stmt.executeUpdate();
	        return true;

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	public String getClientPrestamosEstado(Prestamo prestamo){
		
		EstadoPrestamo estadoPrestamo;
		String sql = "Select estado From estado_prestamo Where id_prestamo = ?";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setInt(1, prestamo.getId_prestamo());
				
				ResultSet rs=stmt.executeQuery();
				if(rs.next()) {
					String estado = rs.getString("estado"); 
					return estado;
				}
				   // "correcto" o "atrasado"
				        
				return null;
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		return null;
	}
	public boolean deleteFromCliente(Prestamo prestamo) {
	    String sql = "Delete From estado_prestamo Where id_prestamo=?";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, prestamo.getId_prestamo());        
	             

	        stmt.executeUpdate();
	        return true;

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}

}
