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
import modelos.Usuario;
import utilidades.Sesion;

public class ClienteRepository {
	
	
	
	public boolean guardar(Cliente cliente) {
		Usuario usuario= Sesion.getusuarioActual();
		String sql = "insert into cliente (id_usuario, nombre, apellido, edad, ine, domicilio, comprobante_domicilio, numero_celular, correo_electronico, empleo, telf_empleo, domicilio_empleo, ingresos_mensuales, numero_cuenta_bancaria, nombre_banco, curp, reputacion) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			stmt.setInt(1, usuario.getId());
			stmt.setString(2, cliente.getNombre());
			stmt.setString(3, cliente.getApellido());
			stmt.setInt(4, cliente.getEdad());
			stmt.setString(5, cliente.getIneDireccion());
			stmt.setString(6, cliente.getDomicilio());
			stmt.setString(7, cliente.getComprobanteDomicilio());
			stmt.setString(8, cliente.getCelular());
			stmt.setString(9, cliente.getCorreoElectronico());
			stmt.setString(10, cliente.getEmpleo());
			stmt.setString(11, cliente.getTelfEmpleo());
			stmt.setString(12, cliente.getDomicilioEmpleo());
			stmt.setDouble(13, cliente.getIngresosMensuales());
			stmt.setString(14, cliente.getCuentaBancaria());
			stmt.setString(15, cliente.getBanco());
			stmt.setString(16, cliente.getCurp());
			stmt.setString(17, cliente.getReputacion());

			stmt.executeUpdate();
			
			return true;
			
		}catch(SQLException ex) {
			//ex.printStackTrace();
		}
		return false;
		
	}
	public boolean actualizar(Cliente clienet) {
		String sql = "UPDATE cliente SET nombre = ?, apellido = ?, edad = ?, ine = ?, domicilio = ?, comprobante_domicilio = ?, numero_celular = ?, correo_electronico = ?, empleo = ?, telf_empleo = ?, domicilio_empleo = ?, ingresos_mensuales = ?, numero_cuenta_bancaria = ?, nombre_banco = ?, curp = ?, reputacion = ? WHERE id_cliente = ?";
		
		try (
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){

			stmt.setString(1, clienet.getNombre());
			stmt.setString(2, clienet.getApellido());
			stmt.setInt(3, clienet.getEdad());
			stmt.setString(4, clienet.getIneDireccion());
			stmt.setString(5, clienet.getDomicilio());
			stmt.setString(6, clienet.getComprobanteDomicilio());
			stmt.setString(7, clienet.getCelular());
			stmt.setString(8, clienet.getCorreoElectronico());
			stmt.setString(9, clienet.getEmpleo());
			stmt.setString(10, clienet.getTelfEmpleo());
			stmt.setString(11, clienet.getDomicilioEmpleo());
			stmt.setDouble(12, clienet.getIngresosMensuales());
			stmt.setString(13, clienet.getCuentaBancaria());
			stmt.setString(14, clienet.getBanco());
			stmt.setString(15, clienet.getCurp());
			stmt.setString(16, clienet.getReputacion());
			stmt.setInt(17, clienet.getIdCliente());

			stmt.executeUpdate();
			
			return true;
			
		}catch(SQLException ex) {
			//ex.printStackTrace();
		}
		return false;
		
	}
	public boolean eliminar(Cliente cliente) {
		String sql = "DELETE FROM cliente WHERE id_cliente=?";
		
		try (
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){

			stmt.setInt(1, cliente.getIdCliente());
			

			stmt.executeUpdate();
			
			return true;
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
		
	}
	public List<Cliente> getClients(){
		Usuario usuario= Sesion.getusuarioActual();
		List<Cliente> clients=new ArrayList<Cliente>();
		String sql = "Select * From cliente Where id_usuario = ?";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setInt(1, usuario.getId());
				
				ResultSet rs=stmt.executeQuery();
				while(rs.next()) {
					Cliente client = new Cliente(
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
	
	public boolean clienteTienePrestamoActivo(Cliente cliente) {
	    String sql = "SELECT EXISTS (SELECT 1 FROM prestamo p WHERE p.id_cliente = ? AND p.estado = 'activo') AS tiene_prestamo_activo";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, cliente.getIdCliente());

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBoolean("tiene_prestamo_activo");
	        }

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	

	public boolean clienteTienePrestamoEnRango(Cliente cliente, double minimo, double maximo) {
	    String sql = "SELECT EXISTS (SELECT 1 FROM prestamo p WHERE p.id_cliente = ? AND p.monto BETWEEN ? AND ?) AS tiene_prestamo_en_rango";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, cliente.getIdCliente());
	        stmt.setDouble(2, minimo);
	        stmt.setDouble(3, maximo);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBoolean("tiene_prestamo_en_rango");
	        }

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	public boolean clienteTienePrestamoMinimo(Cliente cliente, double minimo) {
	    String sql = "SELECT EXISTS (SELECT 1 FROM prestamo p WHERE p.id_cliente = ? AND p.monto>?) AS tiene_prestamo_minimo";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, cliente.getIdCliente());
	        stmt.setDouble(2, minimo);

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBoolean("tiene_prestamo_minimo");
	        }

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	public boolean clienteTienePrestamoMaximo(Cliente cliente, double maximo) {
	    String sql = "SELECT EXISTS (SELECT 1 FROM prestamo p WHERE p.id_cliente = ? AND p.monto<?) AS tiene_prestamo_maximo";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, cliente.getIdCliente());
	        stmt.setDouble(2, maximo);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBoolean("tiene_prestamo_maximo");
	        }

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	public boolean clienteTienePrestamoCorrecto(Cliente cliente) {
	    String sql = "SELECT EXISTS (SELECT 1 FROM estado_prestamo ep JOIN prestamo p ON ep.id_prestamo = p.id_prestamo WHERE p.id_cliente = ? AND ep.estado = 'correcto') AS tiene_estado_correcto";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, cliente.getIdCliente());

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBoolean("tiene_estado_correcto");
	        }

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false; // si algo falla, devolvemos false
	}
	public boolean clienteTienePrestamoAtrasado(Cliente cliente) {
	    String sql = "SELECT EXISTS (SELECT 1 FROM estado_prestamo ep JOIN prestamo p ON ep.id_prestamo = p.id_prestamo WHERE p.id_cliente = ? AND ep.estado = 'atrasado') AS tiene_estado_correcto";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, cliente.getIdCliente());

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBoolean("tiene_estado_correcto");
	        }

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	
	public Cliente getClienteDePrestamo(Prestamo prestamo){
		
		Cliente cliente;
		String sql = "Select * From cliente Where id_cliente = ?";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setInt(1, prestamo.getIdCliente());
				
				ResultSet rs=stmt.executeQuery();
				 if (rs.next()) {
					 cliente = new Cliente(
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
					 return cliente;
			        }
				
				
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		return null;
	}
	
	public static int getTotalNumeroDeClientes()
	{
	    Usuario usuario = Sesion.getusuarioActual();
	    String sql = "SELECT COUNT(*) as total FROM cliente WHERE id_usuario = ?";
	    
	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, usuario.getId());
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getInt("total");
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return 0;
	}
	
	public static boolean clientePrestamoAtrasadoStatic(Cliente cliente) {
	    String sql = "SELECT EXISTS (SELECT 1 FROM estado_prestamo ep JOIN prestamo p ON ep.id_prestamo = p.id_prestamo WHERE p.id_cliente = ? AND ep.estado = 'atrasado') AS tiene_estado_correcto";

	    try (
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	    ) {
	        stmt.setInt(1, cliente.getIdCliente());

	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getBoolean("tiene_estado_correcto");
	        }

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	public static int getTotalNumeroDePrestamosAtrasaados()
	{
		Usuario usuario= Sesion.getusuarioActual();
		List<Cliente> clients=new ArrayList<Cliente>();
		String sql = "Select * From cliente Where id_usuario = ?";
		try (
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
			){
				stmt.setInt(1, usuario.getId());
				
				ResultSet rs=stmt.executeQuery();
				while(rs.next()) {
					Cliente client = new Cliente(
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
		
		List<Cliente> clientesAtrasados = new ArrayList<Cliente>();
		
		for (Cliente client : clients) 
		{
			if(clientePrestamoAtrasadoStatic(client))
			{
				clientesAtrasados.add(client);
			}
				
		}
		
		return clientesAtrasados.size();
	}
}
