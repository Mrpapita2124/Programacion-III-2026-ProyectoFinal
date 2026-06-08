package repositorios;

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
import modelos.Cliente;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import modelos.Usuario;
import utilidades.Sesion;

public class PrestamoRepository {
	
	public boolean guardar(Prestamo prestamo) {
	    EstadoPrestamoRepository estadoRepo = new EstadoPrestamoRepository();
	    ClienteRepository clienteRepo = new ClienteRepository();
	    Cliente cliente = clienteRepo.getClienteDePrestamo(prestamo);	    
	    
	    String sql = "insert into prestamo (id_usuario, id_cliente, estado, monto, numero_quincenas, monto_quincenal, monto_total, interes, interes_retraso, fecha) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ) {
	        stmt.setInt(1, cliente.getIdUsuario());
	        stmt.setInt(2, prestamo.getIdCliente());           
	        stmt.setString(3, prestamo.getEstado());            
	        stmt.setDouble(4, prestamo.getMonto());             
	        stmt.setInt(5, prestamo.getNumeroQuincenas());     
	        stmt.setDouble(6, prestamo.getMontoQuincenal());  
	        stmt.setDouble(7, prestamo.getMontoTotal());    
	        stmt.setDouble(8, prestamo.getInteres());           
	        stmt.setDouble(9, prestamo.getInteresRetraso());   
	        stmt.setDate(10, prestamo.getFecha());              

	        stmt.executeUpdate();
	        
	        try (ResultSet rs = stmt.getGeneratedKeys()) {
	            if (rs.next()) {
	                int idPrestamo = rs.getInt(1);  
	                
	                LocalDate fecha = prestamo.getFecha().toLocalDate();
	                fecha = fecha.plusDays(15);
	                Date nuevaFecha = Date.valueOf(fecha);
	                
	                
	                EstadoPrestamo estadoPrestamo = new EstadoPrestamo(
	                    0,                           
	                    idPrestamo,                  
	                    prestamo.getNumeroQuincenas(),  
	                    prestamo.getMontoTotal(),   
	                    nuevaFecha,                 
	                    prestamo.getMontoQuincenal(),  
	                    "correcto",                  
	                    0                            
	                );
	                estadoRepo.guardar(estadoPrestamo);
	            }
	        }
	        
	        return true;

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	public boolean actualizar(Prestamo prestamo) {
	    String sql = "UPDATE prestamo SET id_usuario = ?, id_cliente = ?, estado = ?, monto = ?, numero_quincenas = ?, monto_quincenal = ?, monto_total = ?, interes = ?, interes_retraso = ?, fecha = ? WHERE id_prestamo = ?";
	    ClienteRepository clienteRepo = new ClienteRepository();
	    Cliente cliente = clienteRepo.getClienteDePrestamo(prestamo);	

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	 	    stmt.setInt(1, cliente.getIdUsuario());
	        stmt.setInt(2, prestamo.getIdCliente());
	        stmt.setString(3, prestamo.getEstado());
	        stmt.setDouble(4, prestamo.getMonto());
	        stmt.setInt(5, prestamo.getNumeroQuincenas());
	        stmt.setDouble(6, prestamo.getMontoQuincenal());
	        stmt.setDouble(7, prestamo.getMontoTotal());
	        stmt.setDouble(8, prestamo.getInteres());
	        stmt.setDouble(9, prestamo.getInteresRetraso());
	        stmt.setDate(10, prestamo.getFecha());
	        stmt.setInt(11, prestamo.getIdPrestamo()); // clave primaria

	        stmt.executeUpdate();
	        return true;

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}

	public List<Prestamo> getPrestamosDeCliente(Cliente client){
		
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
	
	public List<Prestamo> getTodosPrestamos(){
		
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
	
	public Prestamo getPrestamoPorId(int idPrestamo) {
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
	            //System.out.println("No se encontró préstamo con id " + idPrestamo);
	        }
	        
	        return prestamo;
	        
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}

	public List<Prestamo> getPrestamosActivos(){
		
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
	
	public List<Prestamo> getPrestamosActivosDesdeUsuario(){
		
		List<Prestamo> prestamos=new ArrayList<Prestamo>();
		String sql = "Select p.*, c.nombre, c.apellido, c.ine From prestamo p Inner join cliente c on p.id_cliente = c.id_cliente Where p.estado=? AND p.id_usuario=?";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setString(1, "activo");
				stmt.setInt(2, Sesion.getusuarioActual().getId());
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
	public List<Prestamo> getPrestamosDesdeUsuario(){
		
		List<Prestamo> prestamos=new ArrayList<Prestamo>();
		String sql = "Select p.*, c.nombre, c.apellido, c.ine From prestamo p Inner join cliente c on p.id_cliente = c.id_cliente Where p.id_usuario=?";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setInt(1, Sesion.getusuarioActual().getId());
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
	public List<Prestamo> getPrestamosConcluidosDesdeUsuario(){
		
		List<Prestamo> prestamos=new ArrayList<Prestamo>();
		String sql = "Select p.*,c.nombre, c.apellido, c.ine From prestamo p Inner join cliente c on p.id_cliente=c.id_cliente Where estado = ? AND c.id_usuario=?";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setString(1, "concluso");
				stmt.setInt(2, Sesion.getusuarioActual().getId());
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
	public boolean getPrestamoTieneReputacion(Prestamo prestamo, String reputacion){
		
		List<Prestamo> prestamos=new ArrayList<Prestamo>();
		String sql = "SELECT EXISTS(SELECT 1 FROM prestamo p INNER JOIN cliente c ON p.id_cliente = c.id_cliente WHERE p.id_prestamo = ? AND c.reputacion = ?) AS resultado;";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setInt(1, prestamo.getIdPrestamo());
				stmt.setString(2, reputacion);
				ResultSet rs=stmt.executeQuery();
				
				if(rs.next()){
				    return rs.getBoolean("resultado");
				}
				
				
				
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		return false;
	}
	
	public boolean eliminarDesdeCliente(Cliente client) {
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
	
	public boolean eliminar(Prestamo prestamo) {
	    String sql = "Delete From prestamo Where id_prestamo=?";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ) {
	        stmt.setInt(1, prestamo.getIdPrestamo());
	           

	        stmt.executeUpdate();
	        
	        
	        return true;

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	public int getTotalNumeroDePrestamos()
	{
	    Usuario user = Sesion.getusuarioActual();
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
