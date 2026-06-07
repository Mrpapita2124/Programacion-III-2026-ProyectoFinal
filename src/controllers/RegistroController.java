package controllers;

import java.awt.Color; 
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import modelos.Usuario;
import repositorios.RegistroRepository;
import repositorios.UsuarioRepository;
import utilidades.Colores;
import vistas.formulario.FormularioRegistro;
import vistas.otros.Login;
import vistas.otros.Ventana;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class RegistroController {
	private RegistroRepository registroRepository;
	private FormularioRegistro formularioRegsitro;
	private Window loginVentana;
	
	public RegistroController(Window loginVentana)
	{
		registroRepository=new RegistroRepository();
		this.loginVentana = loginVentana;
		formularioRegsitro = new FormularioRegistro();
		formularioRegsitro.getRegistrar().addActionListener(e -> validacionDeRegistro());
		formularioRegsitro.getSeleccionar().addActionListener(e -> formularioRegsitro.seleccionarImagen());
		agregarWindowListener();
		
        asignarKeyListener(formularioRegsitro.getNombres());
        asignarKeyListener(formularioRegsitro.getApellidos()); 
        
		asignarValidacion(formularioRegsitro.getNombres()); 
        asignarValidacion(formularioRegsitro.getApellidos());
        asignarValidacion(formularioRegsitro.getCorreoFieldd());
        asignarValidacion(formularioRegsitro.getContraseField());
	}
	
	private void agregarWindowListener()
	{
			formularioRegsitro.getWindow().addWindowListener(new WindowListener() {
				
				@Override
				public void windowOpened(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowIconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowDeiconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowDeactivated(WindowEvent e) {
					// TODO Auto-generated method stub
					((JFrame) formularioRegsitro.getWindow()).getContentPane().setBackground(Color.GRAY);
					
				}

				@Override
				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub
					((JFrame) formularioRegsitro.getWindow()).getContentPane().setBackground(Colores.FONDO);
				}
				
				@Override
				public void windowClosing(WindowEvent e) {
					
					manejarCierre();
				}
				
				@Override
				public void windowClosed(WindowEvent e) {
				}
				
				
			});

			
	}
	 
	private String guardarImagen() {
	    	try {
	    		
				String ruta = formularioRegsitro.getIconDescription();
	    		String original = ruta;
	    		System.out.println(ruta);
	    		if(!original.equals("/img/iconoWhite.png")) {
	    			
		    			
		    		
		    		File fuente = new File(original);
		    		
		    		String extension = original.substring(original.lastIndexOf("."));
		    		
		    		String nuevoNombre = UUID.randomUUID() + extension;
		    		
		    		String carpeta = "." + File.separator + "images";
		    		
		    		File directorio = new File(carpeta);
		    		
		    		if(!directorio.exists()) {
		    			directorio.mkdir();
		    		}
		    		
		    		Path destination = Paths.get(carpeta, nuevoNombre);
		    		
		    		Files.copy(fuente.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
		    		
		    		return destination.toString();
	    		}
	    		return null;
	    		
	    		
	    	}catch(Exception ex) {
	    		ex.printStackTrace();
	    		return null;
	    	}
	    }
	 
	private void manejarCierre() {
		
		int option = formularioRegsitro.confirmExit();
		System.out.println(option);

		if (option == JOptionPane.YES_OPTION) {
			loginVentana.dispose();
			new Ventana();
			formularioRegsitro.getWindow().dispose();
		}
	}

	 
	 
// Validaciones de Formulario Registro
    
	private void validacionDeRegistro()
    {
    	formularioRegsitro.resetearErrorLabels();

		boolean valido = true;

		if (!validarNombre()) { valido = false; }
		if (!validarApellido()) { valido = false; }
		if (!validarCorreo()) { valido = false; }
		if (!validarConstasena()) { valido = false; }
		//if (!validarFoto()) { valid = false; }
		
		if (valido) 
		{
			String nombre = formularioRegsitro.getNombre();
			String apellido = formularioRegsitro.getApellido();
			String correo = formularioRegsitro.getCorreo();
			String contrasena = formularioRegsitro.getContraseña();
			
			String foto=guardarImagen();
			boolean guardar=formularioRegsitro.getGuardar().isSelected();
			
			registroRepository.registro(correo, contrasena, nombre, apellido, foto, guardar);
			
			
			//formularioRegsitro.registerUser(new User(nombre, apellido, correo, contrasena,foto, guardar));
			
			
			new Ventana();
			formularioRegsitro.getWindow().dispose();
			loginVentana.dispose();
		}
		
    }
	
	public boolean validarNombre() 
    {
    	String nombre = formularioRegsitro.getNombres().getText().trim();
    	
    	if (nombre.isEmpty() || nombre.equals("Nombre")) 
    	{
    		formularioRegsitro.getLblErrorNombre().setText("El nombre es obligatorio");
			return false;
		} else {
			formularioRegsitro.getLblErrorNombre().setText("");
		}

		return true;
    }
	
    public boolean validarApellido()
    {
    	String apellido = formularioRegsitro.getApellidos().getText().trim();
    	
    	if (apellido.isEmpty() || apellido.equals("Apellido")) 
    	{
    		formularioRegsitro.getLblErrorApellido().setText("El apellido es obligatorio");
			return false;
		} else {
			formularioRegsitro.getLblErrorApellido().setText("");
		}

		return true;
    }
    
    public boolean validarCorreo()
    {
    	String correo = formularioRegsitro.getCorreoFieldd().getText().trim();
    	
    	if (correo.isEmpty() || correo.equals("correo@ejemplo.com")) 
    	{
    		formularioRegsitro.getLblErrorCorreo().setText("El correo es obligatorio");
			return false;
		}
    	
    	if (correo.length() < 5) 
    	{
    		formularioRegsitro.getLblErrorCorreo().setText("Correo inválido! Es muy corto.");
    	    return false;
    	}

		if (!correo.contains("@")) 
		{
			formularioRegsitro.getLblErrorCorreo().setText("Correo inválido! Le falta @");
			return false;
		}
		
		if (!correo.contains(".com")) 
		{
			formularioRegsitro.getLblErrorCorreo().setText("Correo inválido! Le falta un '.com'");
			return false;
		}
		
		// Validación adicional para dominio
		String[] parts = correo.split("@");
		if (parts.length != 2 || !parts[1].contains(".")) {
			formularioRegsitro.getLblErrorCorreo().setText("Email inválido! Formato incorrecto");
			return false;
		}
		
		formularioRegsitro.getLblErrorCorreo().setText("");
		return true;
    }
    
    public boolean validarConstasena()
    {
    	String contrasena = formularioRegsitro.getContraseField().getText();
    	
    	if (contrasena.isEmpty() || contrasena.equals("Contraseña")) 
    	{
    		formularioRegsitro.getLblErrorContrasena().setText("La contraseña es obligatoria");
			return false;
		}
    	
    	if (contrasena.length() < 5) 
    	{
    		formularioRegsitro.getLblErrorContrasena().setText("La contraseña es muy corta, mínimo 5 caracteres");
    	    return false;
    	}
    	
    	if (!contrasena.matches(".*[!$?_*].*")) // Regex que checa que por lo menos hay uno de estos char
    	{
    		formularioRegsitro.getLblErrorContrasena().setText("Necesita un caracter especial (! $ ? _ *)");
    	    return false;
    	}
    	
    	if (contrasena.matches(".*\\s.*")) // Regex que checa si hay espacios entre texto
    	{
    		formularioRegsitro.getLblErrorContrasena().setText("La contraseña no puede tener espacios");
    	    return false;
    	}

    	formularioRegsitro.getLblErrorContrasena().setText("");
		return true;
    }
  
    
    private void asignarValidacion(JTextField campoDeTexto)
    {
    	
    	switch(campoDeTexto.getName().toString())
    	{
    		case "nombres":
    			campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
    				
    				@Override
    				public void removeUpdate(DocumentEvent e) {
    					validarNombre();
    				}
    				
    				@Override
    				public void insertUpdate(DocumentEvent e) {
    					validarNombre();
    				}
    				
    				@Override
    				public void changedUpdate(DocumentEvent e) {
    					validarNombre();
    				}
    			});
    			break;
    			
    		case "apellidos":
    			campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
    					
    					@Override
    					public void removeUpdate(DocumentEvent e) {
    						validarApellido();
    					}
    					
    					@Override
    					public void insertUpdate(DocumentEvent e) {
    						validarApellido();
    					}
    					
    					@Override
    					public void changedUpdate(DocumentEvent e) {
    						validarApellido();
    					}
    				});
    			break;
    			
    		case "correo":
    			 campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
    					
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
    						validarCorreo();
    					}
    				});
    			break;
    			
    		case "contraseña":
    			formularioRegsitro.getContraseField().getDocument().addDocumentListener(new DocumentListener() {
    				
    				@Override
    				public void removeUpdate(DocumentEvent e) {
    					validarConstasena();
    				}
    				
    				@Override
    				public void insertUpdate(DocumentEvent e) {
    					validarConstasena();
    				}
    				
    				@Override
    				public void changedUpdate(DocumentEvent e) {
    					validarConstasena();
    				}
    			});
    			break;
    	}
    }
    
    private void asignarKeyListener(JTextField campoDeTexto)
    {
    	campoDeTexto.addKeyListener(new KeyAdapter() 
         {
         	public void keyTyped(KeyEvent e)
         	{
         		// Solo para nombres y apellidos, no para correo y contraseña
         		if (campoDeTexto.getName().equals("nombres") || campoDeTexto.getName().equals("apellidos")) {
         			if(!Character.isSpaceChar(e.getKeyChar()))
         			{
         				if(Character.isDigit(e.getKeyChar()) || !Character.isAlphabetic(e.getKeyChar()))
         				{
         					e.consume();
         				}
         			}
         		}
         		
         		if(campoDeTexto.getText().length() >= 20)
         		{
         			e.consume();
         		}
         	}
 		});
    }
    
}