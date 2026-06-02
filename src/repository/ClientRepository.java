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
import modelos.User;
import utils.Session;

public class ClientRepository {
	
	public boolean save(Client client) {
		User user= Session.getCurrentUser();
		String sql = "insert into cliente (id_usuario, nombre, apellido, edad, ine, domicilio, comprobante_domicilio, numero_celular, correo_electronico, empleo, telf_empleo, domicilio_empleo, ingresos_mensuales, numero_cuenta_bancaria, nombre_banco, curp, reputacion) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			stmt.setInt(1, user.getId());
			stmt.setString(2, client.getNombre());
			stmt.setString(3, client.getApellido());
			stmt.setInt(4, client.getEdad());
			stmt.setString(5, client.getIneDireccion());
			stmt.setString(6, client.getDomicilio());
			stmt.setString(7, client.getComprobanteDomicilio());
			stmt.setString(8, client.getCelular());
			stmt.setString(9, client.getCorreoElectronico());
			stmt.setString(10, client.getEmpleo());
			stmt.setString(11, client.getTelfEmpleo());
			stmt.setString(12, client.getDomicilioEmpleo());
			stmt.setDouble(13, client.getIngresosMensuales());
			stmt.setString(14, client.getCuentaBancaria());
			stmt.setString(15, client.getBanco());
			stmt.setString(16, client.getCurp());
			stmt.setString(17, client.getReputacion());

			stmt.executeUpdate();
			
			return true;
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
		
	}
	public boolean update(Client client) {
		System.out.println("asdad"+ client.getIdCliente());
		String sql = "UPDATE cliente SET nombre = ?, apellido = ?, edad = ?, ine = ?, domicilio = ?, comprobante_domicilio = ?, numero_celular = ?, correo_electronico = ?, empleo = ?, telf_empleo = ?, domicilio_empleo = ?, ingresos_mensuales = ?, numero_cuenta_bancaria = ?, nombre_banco = ?, curp = ?, reputacion = ? WHERE id_cliente = ?";
		
		try (
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){

			stmt.setString(1, client.getNombre());
			stmt.setString(2, client.getApellido());
			stmt.setInt(3, client.getEdad());
			stmt.setString(4, client.getIneDireccion());
			stmt.setString(5, client.getDomicilio());
			stmt.setString(6, client.getComprobanteDomicilio());
			stmt.setString(7, client.getCelular());
			stmt.setString(8, client.getCorreoElectronico());
			stmt.setString(9, client.getEmpleo());
			stmt.setString(10, client.getTelfEmpleo());
			stmt.setString(11, client.getDomicilioEmpleo());
			stmt.setDouble(12, client.getIngresosMensuales());
			stmt.setString(13, client.getCuentaBancaria());
			stmt.setString(14, client.getBanco());
			stmt.setString(15, client.getCurp());
			stmt.setString(16, client.getReputacion());
			stmt.setInt(17, client.getIdCliente());

			stmt.executeUpdate();
			
			return true;
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
		
	}
	public boolean Delete(Client client) {
		String sql = "DELETE FROM cliente WHERE id_cliente=?";
		
		try (
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){

			stmt.setInt(1, client.getIdCliente());
			

			stmt.executeUpdate();
			
			return true;
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
		
	}
	public List<Client> getClients(){
		User user= Session.getCurrentUser();
		List<Client> clients=new ArrayList<Client>();
		String sql = "Select * From cliente Where id_usuario = ?";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setInt(1, user.getId());
				
				ResultSet rs=stmt.executeQuery();
				while(rs.next()) {
					Client client = new Client(
							rs.getInt("id_cliente"),
							rs.getInt("id_usuario"),
							rs.getString("nombre"),
							rs.getString("apellido"),
							rs.getInt("edad"),
							rs.getString("ine"),
							rs.getString("domicilio"),
							rs.getString("comprobante_domicilio"),
							rs.getString("numero_celular"),
							rs.getString("correo_electronico"),
							rs.getString("empleo"),
							rs.getString("domicilio_empleo"),
							rs.getString("telf_empleo"),
							rs.getDouble("ingresos_mensuales"),
							rs.getString("nombre_banco"),
							rs.getString("numero_cuenta_bancaria"),
							rs.getString("curp"),
							rs.getString("reputacion")
							);
					clients.add(client);
				}
				
				
				
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		return clients;
	}
	public Client getClientFromPrestamo(Prestamo prestamo){
		
		Client client;
		String sql = "Select * From cliente Where id_cliente = ?";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setInt(1, prestamo.getId_cliente());
				
				ResultSet rs=stmt.executeQuery();
				
				client = new Client(
						rs.getInt("id_cliente"),
						rs.getInt("id_usuario"),
						rs.getString("nombre"),
						rs.getString("apellido"),
						rs.getInt("edad"),
						rs.getString("ine"),
						rs.getString("domicilio"),
						rs.getString("comprobante_domicilio"),
						rs.getString("numero_celular"),
						rs.getString("correo_electronico"),
						rs.getString("empleo"),
						rs.getString("domicilio_empleo"),
						rs.getString("telf_empleo"),
						rs.getDouble("ingresos_mensuales"),
						rs.getString("nombre_banco"),
						rs.getString("numero_cuenta_bancaria"),
						rs.getString("curp"),
						rs.getString("reputacion")
					);
				return client;
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		return null;
	}
	
	public int getTotalNumeroDeCliente()
	{
	    User user = Session.getCurrentUser();
	    String sql = "SELECT COUNT(*) as total FROM cliente WHERE id_usuario = ?";
	    
	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, user.getId());
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getInt("total");
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return 0;
	}
}
