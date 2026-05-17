package controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import exceptions.InvalidMailException;
import modelos.User;
import repository.LoginRepository;
import utils.Session;
import views.FormularioGeneralCliente;
import views.Login;
import views.VentanaPrincipal;


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
		String password = String.valueOf(login.getContraseña().getPassword()).trim();
		
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
	private void correoExceptions(String correo) throws InvalidMailException {
		if (!correo.contains("@")) 
	    {
	        
	        throw new InvalidMailException("* Debe contener ' @ ' *");
	       
	    }
	    
	    if (!correo.contains(".com")) 
	    {
	        throw new InvalidMailException("* Debe contener ' .com ' *");
	        
	    }
	}
	
	private boolean validarContrasena() 
	{
		String password = String.valueOf(login.getContraseña().getPassword());
		
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
		login.getWindow().dispose();
	}

	private void handleLogin() throws IOException {
		
		if (!evaluarCredenciales()) {
			return;
		}
		
		User user = loginRepository.login(
			login.getUsuario().getText().trim(), 
			String.valueOf(login.getContraseña().getPassword())
		);
		
		
		if (user == null){
			login.getMensajeCorreo().setText("* Credenciales erroneas *");
			return;
		}
		

		Session.login(user);
		
		if(Session.getRol().toLowerCase().equals("admin")) 
		{
			JOptionPane.showMessageDialog(login.getWindow(), "Se inició la sesión con cuenta 'ADMIN'", " Sesión iniciada", JOptionPane.INFORMATION_MESSAGE);
			new VentanaPrincipalController(new VentanaPrincipal());			
			login.getWindow().dispose();
		}
		else if(Session.getRol().toLowerCase().equals("comun")) 
		{
			JOptionPane.showMessageDialog(login.getWindow(), "Se inició la sesión con cuenta 'COMUN'", " Sesión iniciada", JOptionPane.INFORMATION_MESSAGE);
			new VentanaPrincipalController(new VentanaPrincipal());			
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
		login.getContraseña().addKeyListener(enterListener);
		
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
		
		login.getContraseña().getDocument().addDocumentListener(new DocumentListener() {
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
		
		
		
		login.getButtonIniciar().addActionListener(e -> {
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