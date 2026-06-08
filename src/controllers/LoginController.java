package controllers;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import excepciones.CorreoInvalidoException;
import modelos.Usuario;
import repositorios.LoginRepository;
import utilidades.Colores;
import utilidades.Sesion;
import vistas.formulario.FormularioGeneralCliente;
import vistas.otros.Login;
import vistas.otros.VentanaPrincipal;


public class LoginController {

	private Login login;
	private LoginRepository loginRepository;

	public LoginController(Login login) 
	{
		this.login = login;
		this.loginRepository = new LoginRepository();
		addListeners();
	}

	private void resetearCredenciales() 
	{
		login.getMensajeCorreo().setText(" ");
		login.getMensajeContraseña().setText(" ");
	}

	private boolean evaluarCredenciales() 
	{
		resetearCredenciales();
		
		String email = login.getUsuario().getText().trim();
		String password = String.valueOf(login.getContrasenia().getPassword()).trim();
		
		boolean valid = true;
		
		if (email.isEmpty() || email.trim().equals("Correo Electrónico")) {
			login.getMensajeCorreo().setText("* Correo obligatorio *");
			valid = false;
		}
		
		if (password.isEmpty() || password.equals("Contraseña"))  {
			login.getMensajeContraseña().setText("* Contraseña obligatoria *");
			valid = false;
		}
		
		return valid;
	}
	
	private boolean validarCorreo() 
	{
	    String correo = login.getUsuario().getText().trim(); 
	    
	    if (correo.isEmpty() || correo.equals("Correo Electrónico")) 
	    {
	        login.getMensajeCorreo().setText("* Correo obligatorio *");
	        return false;
	    }else {
	    	try {
				correoExceptions(correo);
			} catch (Exception e) {
				// TODO: handle exception
				login.getMensajeCorreo().setText(e.getMessage());
				return false;
			}
	    }
	    
	    
	    login.getMensajeCorreo().setText(" ");
	    return true;
	}
	private void correoExceptions(String correo) throws CorreoInvalidoException {
		if (!correo.contains("@")) 
	    {
	        
	        throw new CorreoInvalidoException("* Debe contener ' @ ' *");
	       
	    }
	    
	    if (!correo.contains(".com")) 
	    {
	        throw new CorreoInvalidoException("* Debe contener ' .com ' *");
	        
	    }
	}
	
	private boolean validarContrasena() 
	{
		String password = String.valueOf(login.getContrasenia().getPassword());
		
		if (password.isEmpty() || password.equals("Contraseña")) 
		{
			login.getMensajeContraseña().setText("* Contraseña obligatoria *");
			return false;
		}
		
		login.getMensajeContraseña().setText(" ");
		return true;
	}

	private void handleRegistration() {
		//ClientFormController form = new ClientFormController(login.getWindow());
		new RegistroController(login.getWindow());
		
	}

	private void handleLogin() throws IOException {
		
		if (!evaluarCredenciales()) {
			return;
		}
		
		Usuario user = loginRepository.login(
			login.getUsuario().getText().trim(), 
			String.valueOf(login.getContrasenia().getPassword())
		);
		
		
		if (user == null){
			login.getMensajeCorreo().setText("* Credenciales erroneas *");
			return;
		}
		

		Sesion.login(user);
		
		if(Sesion.getRol().toLowerCase().equals("admin")) 
		{
			JOptionPane.showMessageDialog(login.getWindow(), "Se inició la sesión con cuenta 'ADMIN'", " Sesión iniciada", JOptionPane.INFORMATION_MESSAGE);
			
			VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
			VentanaPrincipalController ventanaPrinController = new VentanaPrincipalController(ventanaPrincipal);	
			
			login.getWindow().dispose();
		}
		else if(Sesion.getRol().toLowerCase().equals("comun")) 
		{
			JOptionPane.showMessageDialog(login.getWindow(), "Se inició la sesión con cuenta 'COMUN'", " Sesión iniciada", JOptionPane.INFORMATION_MESSAGE);

			VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
			new VentanaPrincipalController(ventanaPrincipal);			
			login.getWindow().dispose();
		}
	}
	
	private void addListeners() {
		// Enter key listener for both fields
		KeyAdapter enterListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					try 
					{
						handleLogin();
					} 
					catch (IOException e1) 
					{
						e1.printStackTrace();
					}
				}
			}
		};
		
		login.getUsuario().addKeyListener(enterListener);
		login.getContrasenia().addKeyListener(enterListener);
		
		login.getUsuario().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				validarCorreo();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				validarCorreo();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
		
		login.getContrasenia().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				validarContrasena();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				validarContrasena();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				validarContrasena();
			}
		});
		
		
		
		login.getBotonIniciar().addActionListener(e -> {
			try {
				handleLogin();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		login.getRegistrarse().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleRegistration();
			}
		});
		
		
	}
	
	
}