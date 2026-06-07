package repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import modelos.Cliente;
import modelos.EstadoPrestamo;
import modelos.Prestamo;

public class EstadoPrestamoRepository {
	public boolean guardar(EstadoPrestamo estadoPrestamo) {
	    String sql = "insert into estado_prestamo (id_prestamo, quincenas_restantes, monto_restante, fecha_proximo_pago, monto_proximo_pago, estado, dinero_atrasado) values (?, ?, ?, ?, ?, ?, ?)";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, estadoPrestamo.getIdPrestamo());        
	        stmt.setInt(2, estadoPrestamo.getQuincenasRestantes());   
	        stmt.setDouble(3, estadoPrestamo.getMontoRestante());    
	        stmt.setDate(4, estadoPrestamo.getFechaProximoPago());     
	        stmt.setDouble(5, estadoPrestamo.getMontoProximoPago());   
	        stmt.setString(6, estadoPrestamo.getEstado());                
	        stmt.setDouble(7, estadoPrestamo.getDineroAtrasado());       

	        stmt.executeUpdate();
	        return true;

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	public String getClientPrestamosEstado(Prestamo prestamo){
		
		
		String sql = "Select estado From estado_prestamo Where id_prestamo = ?";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setInt(1, prestamo.getIdPrestamo());
				
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
	public boolean eliminarDesdePrestamo(Prestamo prestamo) {
	    String sql = "Delete From estado_prestamo Where id_prestamo=?";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, prestamo.getIdPrestamo());        
	             

	        stmt.executeUpdate();
	        return true;

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	public List<EstadoPrestamo> getTodosLosEstadoPrestamo() {
	    List<EstadoPrestamo> estados = new ArrayList<>();
	    String sql = "SELECT * FROM estado_prestamo";
	    
	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	       
	        
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            EstadoPrestamo estadoPrestamo = new EstadoPrestamo(
	                rs.getInt("id_estado_prestamo"),
	                rs.getInt("id_prestamo"),
	                rs.getInt("quincenas_restantes"),
	                rs.getDouble("monto_restante"),
	                rs.getDate("fecha_proximo_pago"),
	                rs.getDouble("monto_proximo_pago"),
	                rs.getString("estado"),
	                rs.getDouble("dinero_atrasado")
	            );
	            estados.add(estadoPrestamo);
	        }
	        
	        if (estados.isEmpty()) {
	            System.out.println("No se encontraron estados para este préstamo.");
	        }
	        
	        return estados;
	        
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}

	public EstadoPrestamo getEstadoPrestamoDesdePrestamo(Prestamo prestamo) {
	    EstadoPrestamo estadoPrestamo = null;
	    String sql = "SELECT * FROM estado_prestamo WHERE id_prestamo = ?";
	    
	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, prestamo.getIdPrestamo());
	        
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            estadoPrestamo = new EstadoPrestamo(
	                rs.getInt("id_estado_prestamo"),
	                rs.getInt("id_prestamo"),
	                rs.getInt("quincenas_restantes"),
	                rs.getDouble("monto_restante"),
	                rs.getDate("fecha_proximo_pago"),
	                rs.getDouble("monto_proximo_pago"),
	                rs.getString("estado"),
	                rs.getDouble("dinero_atrasado")
	            );
	           // System.out.println("Estado encontrado para préstamo: " + prestamo.getIdPrestamo());
	        } else {
	           // System.out.println("No se encontró estado para préstamo ID: " + prestamo.getIdPrestamo());
	        }
	        
	        return estadoPrestamo;
	        
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	
	public boolean actualizar(EstadoPrestamo estadoPrestamo) {
	    String sql = "UPDATE estado_prestamo SET id_prestamo = ?, quincenas_restantes = ?, monto_restante = ?, fecha_proximo_pago = ?, monto_proximo_pago = ?, estado = ?, dinero_atrasado = ? WHERE id_estado_prestamo = ?";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, estadoPrestamo.getIdPrestamo());
	        stmt.setInt(2, estadoPrestamo.getQuincenasRestantes());
	        stmt.setDouble(3, estadoPrestamo.getMontoRestante());
	        stmt.setDate(4, estadoPrestamo.getFechaProximoPago()); 
	        stmt.setDouble(5, estadoPrestamo.getMontoProximoPago());
	        stmt.setString(6, estadoPrestamo.getEstado());
	        stmt.setDouble(7, estadoPrestamo.getDineroAtrasado());
	        stmt.setInt(8, estadoPrestamo.getIdEstadoPrestamo()); 

	        stmt.executeUpdate();
	        return true;

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}


}
