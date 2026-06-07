package utilidades;

import java.util.List;
import modelos.Cliente;
import modelos.Prestamo;
import modelos.Usuario;

public class Sesion {
	
	private static Usuario usuarioActual;
	private static List<Cliente> clientesFiltrados; 
	private static List<Prestamo> prestamosFiltrados;
	
	public static void login(Usuario user) {
		usuarioActual = user;
		clientesFiltrados = null;  
	}
	
	public static Usuario getusuarioActual() {
		return usuarioActual;
	}
	
	public static void logout() {
		usuarioActual = null;
		clientesFiltrados = null;  
	}
	
	public static boolean isLoggedIn() {
		return usuarioActual != null;
	}
	
	public static String getRol( ) {
		return usuarioActual.getRol();
	}
	
	public static List<Cliente> getClientesFiltrados() {
		return clientesFiltrados;
	}
	
	public static void setClientesFiltrados(List<Cliente> clients) {
		clientesFiltrados = clients;
	}

	public static List<Prestamo> getPrestamosFiltrados() {
		return prestamosFiltrados;
	}

	public static void setPrestamosFiltrados(List<Prestamo> prestamosFiltro) {
		prestamosFiltrados = prestamosFiltro;
	}
	
	
}