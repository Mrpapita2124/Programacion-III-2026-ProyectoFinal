package utils;

import java.util.List;
import modelos.Client;
import modelos.Prestamo;
import modelos.User;

public class Session {
	
	private static User currentUser;
	private static List<Client> clientesFiltrados; 
	private static List<Prestamo> prestamosFiltrados;
	
	public static void login(User user) {
		currentUser = user;
		clientesFiltrados = null;  
	}
	
	public static User getCurrentUser() {
		return currentUser;
	}
	
	public static void logout() {
		currentUser = null;
		clientesFiltrados = null;  
	}
	
	public static boolean isLoggedIn() {
		return currentUser != null;
	}
	
	public static String getRol( ) {
		return currentUser.getRol();
	}
	
	public static List<Client> getClientesFiltrados() {
		return clientesFiltrados;
	}
	
	public static void setClientesFiltrados(List<Client> clients) {
		clientesFiltrados = clients;
	}

	public static List<Prestamo> getPrestamosFiltrados() {
		return prestamosFiltrados;
	}

	public static void setPrestamosFiltrados(List<Prestamo> prestamosFiltrados) {
		Session.prestamosFiltrados = prestamosFiltrados;
	}
	
}