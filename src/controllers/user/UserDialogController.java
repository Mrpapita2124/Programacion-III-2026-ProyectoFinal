package controllers.user;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import modelos.User;
import repository.UserRepository;
import utils.Colores;
import views.Ventana;
import views.user.UserFormDialog;

public class UserDialogController {

	private UserFormDialog userFromDialog;
	
	public UserDialogController(UserFormDialog userFromDialog) // Agregar nuevo user?
	{
		this.userFromDialog = userFromDialog;
		userFromDialog.getSeleccionar().addActionListener(e -> userFromDialog.chooseImage());
		userFromDialog.getBtnGuardar().addActionListener(e -> {
			try {
				validacionDeRegistro();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		});
		this.userFromDialog.getIconoUsuario().setIcon(escalarImagenLocal("..\\img\\icono.png", 200, 200));
		addWindowListener();
		
        asignarKeyListener(userFromDialog.getTxtNombre());
        asignarKeyListener(userFromDialog.getTxtApellido()); 
        
		asignarValidacion(userFromDialog.getTxtNombre()); 
        asignarValidacion(userFromDialog.getTxtApellido());
        asignarValidacion(userFromDialog.getTxtCorreo());
        
	}
	
	public UserDialogController(UserFormDialog userFromDialog, User user) // Para actulizar user?
	{
		this.userFromDialog = userFromDialog;
		this.userFromDialog.setUser(user);
		userFromDialog.getSeleccionar().addActionListener(e -> userFromDialog.chooseImage());
		userFromDialog.getBtnGuardar().addActionListener(e -> {
			try {
				validacionDeActualizacion();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		addWindowListener();
		
        asignarKeyListener(userFromDialog.getTxtNombre());
        asignarKeyListener(userFromDialog.getTxtApellido()); 
        
		asignarValidacion(userFromDialog.getTxtNombre()); 
        asignarValidacion(userFromDialog.getTxtApellido());
        asignarValidacion(userFromDialog.getTxtCorreo());
        this.userFromDialog.getTxtApellido().setText(this.userFromDialog.getUser().getApellido());
        this.userFromDialog.getTxtNombre().setText(user.getNombre());
        this.userFromDialog.getTxtCorreo().setText(user.getCorreo());
        this.userFromDialog.getBtnGuardar().setText("Editar");
        try {
        	if(!user.getFoto().equals(null)) {
        		this.userFromDialog.getIconoUsuario().setIcon(escalarImagen(user.getFoto(), 200, 200));
        		this.userFromDialog.setIconDescription(user.getFoto());
        		
        	}
        	
		} catch (Exception e) {
			// TODO: handle exception
			this.userFromDialog.getIconoUsuario().setIcon(escalarImagenLocal("..\\img\\icono.png", 200, 200));
		}
        
        this.userFromDialog.getGuardar().setSelected(user.isGuardar());
	}
	
	private ImageIcon escalarImagen(String direccion,int x,int y) {
    	
        ImageIcon iconoOriginal = new ImageIcon(direccion);

       
        Image imagenEscalada = iconoOriginal.getImage()
                .getScaledInstance(x, y, Image.SCALE_SMOOTH);

        
        ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
        iconoFinal.setDescription(direccion);
        return iconoFinal;
	}
	private ImageIcon escalarImagenLocal(String direccion,int x,int y) {
	    	
	        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(direccion));
	
	       
	        Image imagenEscalada = iconoOriginal.getImage()
	                .getScaledInstance(x, y, Image.SCALE_SMOOTH);
	
	        
	        ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
	        iconoFinal.setDescription(direccion);
	        return iconoFinal;
	 }
	private void addWindowListener()
	{
				userFromDialog.getWindow().addWindowListener(new WindowListener() {
				
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
					((JDialog) userFromDialog.getWindow()).getContentPane().setBackground(Color.GRAY);
					
				}

				@Override
				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub
					((JDialog) userFromDialog.getWindow()).getContentPane().setBackground(Colores.BACKGROUND);
				}
				
				@Override
				public void windowClosing(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void windowClosed(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
				
				userFromDialog.getBtnCancelar().addActionListener(e -> handleClose());
				
				userFromDialog.addWindowListener(new WindowAdapter() 
				{
					@Override
					public void windowClosing(WindowEvent e) 
					{
						handleClose();
					}
				});
		
	}
	private String saveImage() {
    	try {
    		
			String ruta = userFromDialog.getIconDescription();
    		String original = ruta;
    		System.out.println(ruta);
    		if(!original.equals("..\\img\\icono.png")) {
    			
	    			
	    		
	    		File source = new File(original);
	    		
	    		String extension = original.substring(original.lastIndexOf("."));
	    		
	    		String newName = UUID.randomUUID() + extension;
	    		
	    		String folder = "." + File.separator + "images";
	    		
	    		File directory = new File(folder);
	    		
	    		if(!directory.exists()) {
	    			directory.mkdir();
	    		}
	    		
	    		Path destination = Paths.get(folder, newName);
	    		
	    		Files.copy(source.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
	    		
	    		return destination.toString();
    		}
    		return null;
    		
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    		return null;
    	}
    }
	
	private void handleClose() {
		int option = userFromDialog.confirmExit();
		System.out.println(option);

		if (option == JOptionPane.YES_OPTION) {
			userFromDialog.getWindow().dispose();
		}
	}

	private void validacionDeActualizacion() throws IOException
    {
		boolean valid = true;

		if (!validarNombre()) { valid = false; }
		if (!validarApellido()) { valid = false; }
		if (!validarCorreo()) { valid = false; }
		if (!validarContraseña()) { valid = false; }

		
		if (valid) 
		{
			String foto=saveImage();
			userFromDialog.getUser().setFoto(foto);
			userFromDialog.getUser().setNombre(userFromDialog.getTxtNombre().getText());
			userFromDialog.getUser().setApellido(userFromDialog.getTxtApellido().getText());
			userFromDialog.getUser().setCorreo(userFromDialog.getTxtCorreo().getText());
			userFromDialog.getUser().setGuardar(userFromDialog.getGuardar().isSelected());
			String nuevaContesena = new String(userFromDialog.getTxtContraseña().getPassword());
			userFromDialog.getUser().setContrasena(nuevaContesena);
			
			userFromDialog.setGuardado(true);
			
			
			userFromDialog.getWindow().dispose();
		}
		
    }
	private void validacionDeRegistro() throws IOException
    {
		boolean valid = true;

		if (!validarNombre()) { valid = false; }
		if (!validarApellido()) { valid = false; }
		if (!validarCorreo()) { valid = false; }
		if (!validarContraseña()) { valid = false; }

		
		if (valid) 
		{
			String foto=saveImage();
			userFromDialog.setUser(new User("a", "a", "a", "a"));
			userFromDialog.getUser().setNombre(userFromDialog.getTxtNombre().getText());
			userFromDialog.getUser().setApellido(userFromDialog.getTxtApellido().getText());
			userFromDialog.getUser().setCorreo(userFromDialog.getTxtCorreo().getText());
			userFromDialog.getUser().setFoto(foto);
			userFromDialog.getUser().setGuardar(userFromDialog.getGuardar().isSelected());
			
			String nuevaContesena = new String(userFromDialog.getTxtContraseña().getPassword());
			userFromDialog.getUser().setContrasena(nuevaContesena);
			userFromDialog.setGuardado(true);
			
			
			userFromDialog.getWindow().dispose();
		}
		
    }
	
	public boolean validarNombre() 
    {
    	if (userFromDialog.getTxtNombre().getText().trim().isEmpty()) 
    	{
    		userFromDialog.getTxtErrNombre().setText("El nombre es obligatorio");
			return false;
		}else {
			userFromDialog.getTxtErrNombre().setText(" ");
		}

		return true;
    }
	
	public boolean validarContraseña() 
    {
		String contraseña = new String(userFromDialog.getTxtContraseña().getPassword());
    	if (contraseña.isEmpty()) 
    	{
    		userFromDialog.getTxtErrContraseña().setText("La contraseña es obligatorio");
			return false;
		}else if (!contraseña.matches(".*[!$?_*].*")) 
    	{
    		userFromDialog.getTxtErrContraseña().setText("Necesita un caracter especial (! $ ? _ *)");
			return false;
		}else if (contraseña.matches(".*\\s.*")) 
    	{
    		userFromDialog.getTxtErrContraseña().setText("No debe tener espacios");
			return false;
		}else{
			userFromDialog.getTxtErrContraseña().setText(" ");
		}

		return true;
    }
	
    public boolean validarApellido()
    {
    	if (userFromDialog.getTxtApellido().getText().trim().isEmpty()) 
    	{
    		userFromDialog.getTxtErrApellido().setText("El apellido es obligatorio");
			return false;
		}else {
			userFromDialog.getTxtErrApellido().setText(" ");
		}

		return true;
    }
    
    public boolean validarCorreo()
    {
    	if (userFromDialog.getTxtCorreo().getText().trim().isEmpty()) 
    	{
    		userFromDialog.getTxtErrCorreo().setText("El email es obligatorio");
			return false;
		}else {
			userFromDialog.getTxtErrCorreo().setText(" ");
		}
    	
    	if (userFromDialog.getTxtCorreo().getText().trim().length() < 3) 
    	{
    		userFromDialog.getTxtErrCorreo().setText("Email inválido! Es muy corta.");
    	    return false;
    	}else {
    		userFromDialog.getTxtErrCorreo().setText(" ");
		}

		if (!userFromDialog.getTxtCorreo().getText().contains("@")) 
		{
			userFromDialog.getTxtErrCorreo().setText("Email inválido! Le falta @");
			return false;
		}else 
		{
			userFromDialog.getTxtErrCorreo().setText(" ");
		}
		
		if (!userFromDialog.getTxtCorreo().getText().contains(".com")) 
		{
			userFromDialog.getTxtErrCorreo().setText("Email inválido! Le falta .com");
			return false;
		}else 
		{
			userFromDialog.getTxtErrCorreo().setText(" ");
		}

		return true;
    }
    
    
    private void asignarValidacion(JTextField JtextField)
    {
    	
    	switch(JtextField.getText())
    	{
    		case "txtNombre":
    			JtextField.getDocument().addDocumentListener(new DocumentListener() {
    				
    				@Override
    				public void removeUpdate(DocumentEvent e) {
    					// TODO Auto-generated method stub
    					validarNombre();
    				}
    				
    				@Override
    				public void insertUpdate(DocumentEvent e) {
    					// TODO Auto-generated method stub
    					validarNombre();
    				}
    				
    				@Override
    				public void changedUpdate(DocumentEvent e) {
    					// TODO Auto-generated method stub
    					validarNombre();
    				}
    			});
    			break;
    			
    		case "txtApellido":
    			JtextField.getDocument().addDocumentListener(new DocumentListener() {
    					
    					@Override
    					public void removeUpdate(DocumentEvent e) {
    						// TODO Auto-generated method stub
    						validarApellido();
    					}
    					
    					@Override
    					public void insertUpdate(DocumentEvent e) {
    						// TODO Auto-generated method stub
    						validarApellido();
    					}
    					
    					@Override
    					public void changedUpdate(DocumentEvent e) {
    						// TODO Auto-generated method stub
    						validarApellido();
    					}
    				});
    			break;
    			
    		case "txtCorreo":
    			 JtextField.getDocument().addDocumentListener(new DocumentListener() {
    					
    					@Override
    					public void removeUpdate(DocumentEvent e) {
    						// TODO Auto-generated method stub
    						validarCorreo();
    					}
    					
    					@Override
    					public void insertUpdate(DocumentEvent e) {
    						// TODO Auto-generated method stub
    						validarCorreo();
    					}
    					
    					@Override
    					public void changedUpdate(DocumentEvent e) {
    						// TODO Auto-generated method stub
    						validarCorreo();
    					}
    				});
    			break;
    	}
    }
    
    private void asignarKeyListener(JTextField JtextField)
    {
    	JtextField.addKeyListener(new KeyAdapter() 
         {
         	public void keyTyped(KeyEvent e)
         	{
         		
         		if(!Character.isSpaceChar(e.getKeyChar()))
         		{
         			if(Character.isDigit(e.getKeyChar()) || !Character.isAlphabetic(e.getKeyChar()))
         			{
         				e.consume();
         			}
         		}
         		
         		if(JtextField.getText().length() >= 20)
         		{
         			e.consume();
         		}
         	}
 		});
    }
}
