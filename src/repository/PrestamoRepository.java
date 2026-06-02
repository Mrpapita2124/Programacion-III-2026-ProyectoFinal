package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import modelos.Client;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import modelos.User;
import utils.Session;

public class PrestamoRepository {
	
	public boolean save(Prestamo prestamo) {
	    EstadoPrestamoRepository estadoRepo = new EstadoPrestamoRepository();
	    User user = Session.getCurrentUser();
	    String sql = "insert into prestamo (id_usuario, id_cliente, estado, monto, numero_quincenas, monto_quincenal, monto_total, interes, interes_retraso, fecha) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ) {
	        stmt.setInt(1, user.getId());
	        stmt.setInt(2, prestamo.getId_cliente());           
	        stmt.setString(3, prestamo.getEstado());            
	        stmt.setDouble(4, prestamo.getMonto());             
	        stmt.setInt(5, prestamo.getNumero_quincenas());     
	        stmt.setDouble(6, prestamo.getMonto_quincenal());  
	        stmt.setDouble(7, prestamo.getMonto_total());    
	        stmt.setDouble(8, prestamo.getInteres());           
	        stmt.setDouble(9, prestamo.getInteres_retraso());   
	        stmt.setDate(10, prestamo.getFecha());              

	        stmt.executeUpdate();
	        
	        try (ResultSet rs = stmt.getGeneratedKeys()) {
	            if (rs.next()) {
	                int idPrestamo = rs.getInt(1);  
	                
	                LocalDate fecha = prestamo.getFecha().toLocalDate();
	                fecha = fecha.plusDays(15);
	                Date nuevaFecha = Date.valueOf(fecha);
	                
	                
	                EstadoPrestamo estadoPrestamo = new EstadoPrestamo(
	                    0,                           // id_estado_prestamo (autogenerado, poner 0)
	                    idPrestamo,                  
	                    prestamo.getNumero_quincenas(),  
	                    prestamo.getMonto_total(),   
	                    nuevaFecha,                 
	                    prestamo.getMonto_quincenal(),  
	                    "correcto",                  
	                    0                            
	                );
	                estadoRepo.save(estadoPrestamo);
	            }
	        }
	        
	        return true;

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	public boolean update(Prestamo prestamo) {
	    String sql = "UPDATE prestamo SET id_usuario = ?, id_cliente = ?, estado = ?, monto = ?, numero_quincenas = ?, monto_quincenal = ?, monto_total = ?, interes = ?, interes_retraso = ?, fecha = ? WHERE id_prestamo = ?";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        User user = Session.getCurrentUser();

	        stmt.setInt(1, user.getId());
	        stmt.setInt(2, prestamo.getId_cliente());
	        stmt.setString(3, prestamo.getEstado());
	        stmt.setDouble(4, prestamo.getMonto());
	        stmt.setInt(5, prestamo.getNumero_quincenas());
	        stmt.setDouble(6, prestamo.getMonto_quincenal());
	        stmt.setDouble(7, prestamo.getMonto_total());
	        stmt.setDouble(8, prestamo.getInteres());
	        stmt.setDouble(9, prestamo.getInteres_retraso());
	        stmt.setDate(10, prestamo.getFecha());
	        stmt.setInt(11, prestamo.getId_prestamo()); // clave primaria

	        stmt.executeUpdate();
	        return true;

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}

	public List<Prestamo> getClientPrestamos(Client client){
		
		List<Prestamo> prestamos=new ArrayList<Prestamo>();
		String sql = "Select p.*,c.nombre, c.apellido, c.ine From prestamo p Inner join cliente c on p.id_cliente=c.id_cliente Where p.id_cliente = ?";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setInt(1, client.getIdCliente());
				
				ResultSet rs=stmt.executeQuery();
				while(rs.next()) {
					Prestamo prestamo = new Prestamo(
					        rs.getInt("id_prestamo"),
					        rs.getInt("id_usuario"),
					        rs.getInt("id_cliente"),
					        rs.getString("estado"),
					        rs.getDouble("monto"),
					        rs.getInt("numero_quincenas"),
					        rs.getDouble("monto_quincenal"),
					        rs.getDouble("monto_total"),
					        rs.getDouble("interes"),
					        rs.getDouble("interes_retraso"),
					        rs.getDate("fecha"),   
					        rs.getString("nombre"),
					        rs.getString("apellido"),
					        rs.getString("ine")
					);

					prestamos.add(prestamo);
				}
				
				
				
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		return prestamos;
	}
	
	public List<Prestamo> getAllPrestamos(){
		
		List<Prestamo> prestamos=new ArrayList<Prestamo>();
		String sql = "Select p.*,c.nombre, c.apellido, c.ine From prestamo p Inner join cliente c on p.id_cliente=c.id_cliente";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				ResultSet rs=stmt.executeQuery();
				while(rs.next()) {
					Prestamo prestamo = new Prestamo(
					        rs.getInt("id_prestamo"),
					        rs.getInt("id_usuario"),
					        rs.getInt("id_cliente"),
					        rs.getString("estado"),
					        rs.getDouble("monto"),
					        rs.getInt("numero_quincenas"),
					        rs.getDouble("monto_quincenal"),
					        rs.getDouble("monto_total"),
					        rs.getDouble("interes"),
					        rs.getDouble("interes_retraso"),
					        rs.getDate("fecha"),   
					        rs.getString("nombre"),
					        rs.getString("apellido"),
					        rs.getString("ine")
					);

					prestamos.add(prestamo);
				}
				
				
				
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		return prestamos;
	}
	
	public Prestamo getPrestamoById(int idPrestamo) {
	    Prestamo prestamo = null;
	    String sql = "SELECT p.*, c.nombre, c.apellido, c.ine " +
	                 "FROM prestamo p INNER JOIN cliente c ON p.id_cliente = c.id_cliente " +
	                 "WHERE p.id_prestamo = ?";
	    
	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, idPrestamo);
	        
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            prestamo = new Prestamo(
	                rs.getInt("id_prestamo"),
	                rs.getInt("id_usuario"),
	                rs.getInt("id_cliente"),
	                rs.getString("estado"),
	                rs.getDouble("monto"),
	                rs.getInt("numero_quincenas"),
	                rs.getDouble("monto_quincenal"),
	                rs.getDouble("monto_total"),
	                rs.getDouble("interes"),
	                rs.getDouble("interes_retraso"),
	                rs.getDate("fecha"),   
	                rs.getString("nombre"),
	                rs.getString("apellido"),
	                rs.getString("ine")
	            );
	        } else {
	            System.out.println("No se encontró préstamo con id " + idPrestamo);
	        }
	        
	        return prestamo;
	        
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}

	public List<Prestamo> getAllActivePrestamos(){
		
		List<Prestamo> prestamos=new ArrayList<Prestamo>();
		String sql = "Select p.*,c.nombre, c.apellido, c.ine From prestamo p Inner join cliente c on p.id_cliente=c.id_cliente Where estado=?";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setString(1, "activo");
				ResultSet rs=stmt.executeQuery();
				while(rs.next()) {
					Prestamo prestamo = new Prestamo(
					        rs.getInt("id_prestamo"),
					        rs.getInt("id_usuario"),
					        rs.getInt("id_cliente"),
					        rs.getString("estado"),
					        rs.getDouble("monto"),
					        rs.getInt("numero_quincenas"),
					        rs.getDouble("monto_quincenal"),
					        rs.getDouble("monto_total"),
					        rs.getDouble("interes"),
					        rs.getDouble("interes_retraso"),
					        rs.getDate("fecha"),   
					        rs.getString("nombre"),
					        rs.getString("apellido"),
					        rs.getString("ine")
					);

					prestamos.add(prestamo);
				}
				
				
				
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		return prestamos;
	}
	
	public List<Prestamo> getAllActivePrestamosFromUser(){
		
		List<Prestamo> prestamos=new ArrayList<Prestamo>();
		String sql = "Select p.*, c.nombre, c.apellido, c.ine From prestamo p Inner join cliente c on p.id_cliente = c.id_cliente Where p.estado=? AND p.id_usuario=?";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setString(1, "activo");
				stmt.setInt(2, Session.getCurrentUser().getId());
				ResultSet rs=stmt.executeQuery();
				while(rs.next()) {
					Prestamo prestamo = new Prestamo(
					        rs.getInt("id_prestamo"),
					        rs.getInt("id_usuario"),
					        rs.getInt("id_cliente"),
					        rs.getString("estado"),
					        rs.getDouble("monto"),
					        rs.getInt("numero_quincenas"),
					        rs.getDouble("monto_quincenal"),
					        rs.getDouble("monto_total"),
					        rs.getDouble("interes"),
					        rs.getDouble("interes_retraso"),
					        rs.getDate("fecha"),   
					        rs.getString("nombre"),
					        rs.getString("apellido"),
					        rs.getString("ine")
					);

					prestamos.add(prestamo);
				}
				
				
				
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		
		return prestamos;
	}
	
	public List<Prestamo> getAllConcludePrestamos(){
		
		List<Prestamo> prestamos=new ArrayList<Prestamo>();
		String sql = "Select p.*,c.nombre, c.apellido, c.ine From prestamo p Inner join cliente c on p.id_cliente=c.id_cliente Where estado = ?";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setString(1, "concluso");
				ResultSet rs=stmt.executeQuery();
				while(rs.next()) {
					Prestamo prestamo = new Prestamo(
					        rs.getInt("id_prestamo"),
					        rs.getInt("id_usuario"),
					        rs.getInt("id_cliente"),
					        rs.getString("estado"),
					        rs.getDouble("monto"),
					        rs.getInt("numero_quincenas"),
					        rs.getDouble("monto_quincenal"),
					        rs.getDouble("monto_total"),
					        rs.getDouble("interes"),
					        rs.getDouble("interes_retraso"),
					        rs.getDate("fecha"),   
					        rs.getString("nombre"),
					        rs.getString("apellido"),
					        rs.getString("ine")
					);

					prestamos.add(prestamo);
				}
				
				
				
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		return prestamos;
	}
	
	public boolean deleteFromCliente(Client client) {
		EstadoPrestamoRepository estadoRepo= new EstadoPrestamoRepository();
	    User user = Session.getCurrentUser();
	    String sql = "Delete From prestamo Where id_cliente=?";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ) {
	        stmt.setInt(1, client.getIdCliente());
	           

	        stmt.executeUpdate();
	        
	        
	        return true;

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	public boolean delete(Prestamo prestamo) {
	    User user = Session.getCurrentUser();
	    String sql = "Delete From prestamo Where id_prestamo=?";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ) {
	        stmt.setInt(1, prestamo.getId_prestamo());
	           

	        stmt.executeUpdate();
	        
	        
	        return true;

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	public int getTotalNumeroDePrestamos()
	{
	    User user = Session.getCurrentUser();
	    String sql = "SELECT COUNT(*) FROM prestamo WHERE id_usuario = ?";
	    
	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, user.getId());
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return 0;
	}
}
