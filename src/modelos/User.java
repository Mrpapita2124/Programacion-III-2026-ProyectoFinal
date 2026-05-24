package modelos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
	
	private int id;
	private String nombre;
	private String apellido;
	private String correo;
	private String contrasena;
	private double capacidadPrestamo;
	private String foto;
	private String rol;
	private boolean guardar;
	
	public User() {
	}
	
	public User(int id, String correo, String contrasena) {
		this.id = id;
		this.correo = correo;
		this.contrasena = contrasena;
	}
	
	public User(String email, String contrasena) {
		this.correo = email;
		this.contrasena = contrasena;
	}
	
	public User(String nombre, String apellio, String email) {
		this.nombre = nombre;
		this.apellido = apellio;
		this.correo = email;
	}
	
	public User(String nombre, String apellido, String email, String contrasena) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = email;
		this.contrasena = contrasena;
		this.guardar=true;
	}
	public User(String nombre, String apellido, String email, String contrasena,String foto, boolean guardar) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = email;
		this.contrasena = contrasena;
		this.foto=foto;
		this.guardar=guardar;
	}
	
	public User(int id, String nombre, String apellido, String email, String contrasena, double capacidadPrestamo,String foto, boolean guardar, String rol) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = email;
		this.contrasena = contrasena;
		this.capacidadPrestamo=capacidadPrestamo;
		this.foto=foto;
		this.guardar=guardar;
		this.rol = rol;
	}
	
	
	public User(int id, String nombre, String correo, String foto, boolean guardar) {
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.foto = foto;
		this.guardar = guardar;
	}
	
	
	public String getRol()
	{
		return rol;
	}
	
	public void setRol(String rol)
	{
		this.rol = rol;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}

	public double getCapacidadPrestamo() {
		return capacidadPrestamo;
	}

	public void setCapacidadPrestamo(double capacidadPrestamo) {
		this.capacidadPrestamo = capacidadPrestamo;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String email) {
		this.correo = email;
	}


	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	
	public boolean isGuardar() {
		return guardar;
	}

	public void setGuardar(boolean guardar) {
		this.guardar = guardar;
	}

	public String toString() {
		return "Nombre: " + nombre +
					"\nApelldo: " + apellido +
					"\nEmail: " + correo +
					"\nContraseña: " + contrasena;
	}
	
	public String toCsv() {
		return nombre + "," +
					apellido + "," +
		           	correo + "," +
		           	contrasena;
	}
	
	public static User fromCsv(String userData)
	{
		String data[] = userData.split(",");
		
		String name = data[0];
		String apellido = data[1];
		String email = data[2];
		String contrasena = data[3];

		return new User(name, apellido, email, contrasena);
	}
	
	
}
