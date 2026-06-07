package controllers.usuario;

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

import modelos.Usuario;
import repositorios.UsuarioRepository;
import utilidades.Colores;
import vistas.otros.Ventana;
import vistas.usuario.FormularioUsuarioDialog;

public class UserDialogController {

	private FormularioUsuarioDialog formularioUsuario;
	
	public UserDialogController(FormularioUsuarioDialog formularioUsuario) // Agregar nuevo user?
	{
		this.formularioUsuario = formularioUsuario;
		this.formularioUsuario.getSeleccionar().addActionListener(e -> this.formularioUsuario.seleccionarImagen());
		this.formularioUsuario.getBtnGuardar().addActionListener(e -> {
			try {
				validacionDeRegistro();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		});
		this.formularioUsuario.getIconoUsuario().setIcon(escalarImagenLocal("/img/icono.png", 200, 200));
		asignarWindowListener();
		
        asignarKeyListener(this.formularioUsuario.getTxtNombre());
        asignarKeyListener(this.formularioUsuario.getTxtApellido()); 
        
		asignarValidacion(this.formularioUsuario.getTxtNombre()); 
        asignarValidacion(this.formularioUsuario.getTxtApellido());
        asignarValidacion(this.formularioUsuario.getTxtCorreo());
        
	}
	
	public UserDialogController(FormularioUsuarioDialog formularioUsuario, Usuario usuario) // Para actulizar user?
	{
		this.formularioUsuario = formularioUsuario;
		this.formularioUsuario.setUser(usuario);
		this.formularioUsuario.getSeleccionar().addActionListener(e -> formularioUsuario.seleccionarImagen());
		this.formularioUsuario.getBtnGuardar().addActionListener(e -> {
			try {
				validacionDeActualizacion();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		asignarWindowListener();
		
        asignarKeyListener(this.formularioUsuario.getTxtNombre());
        asignarKeyListener(this.formularioUsuario.getTxtApellido()); 
        
		asignarValidacion(this.formularioUsuario.getTxtNombre()); 
        asignarValidacion(this.formularioUsuario.getTxtApellido());
        asignarValidacion(this.formularioUsuario.getTxtCorreo());
        this.formularioUsuario.getTxtApellido().setText(this.formularioUsuario.getUser().getApellido());
        this.formularioUsuario.getTxtNombre().setText(usuario.getNombre());
        this.formularioUsuario.getTxtCorreo().setText(usuario.getCorreo());
        this.formularioUsuario.getBtnGuardar().setText("Editar");
        try {
        	if(!usuario.getFoto().equals(null)) {
        		this.formularioUsuario.getIconoUsuario().setIcon(escalarImagen(usuario.getFoto(), 200, 200));
        		this.formularioUsuario.setIconDescription(usuario.getFoto());
        		
        	}
        	
		} catch (Exception e) {
			// TODO: handle exception
			this.formularioUsuario.getIconoUsuario().setIcon(escalarImagenLocal("/img/icono.png", 200, 200));
		}
        
        this.formularioUsuario.getGuardar().setSelected(usuario.isGuardar());
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
	    	
		java.net.URL imgURL = getClass().getResource(direccion);
        
        ImageIcon iconoOriginal = new ImageIcon(imgURL);
        Image imagenEscalada = iconoOriginal.getImage()
                .getScaledInstance(x, y, Image.SCALE_SMOOTH);
        ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
        iconoFinal.setDescription(direccion);
        return iconoFinal;
	 }
	private void asignarWindowListener()
	{
				formularioUsuario.getWindow().addWindowListener(new WindowListener() {
				
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
					((JDialog) formularioUsuario.getWindow()).getContentPane().setBackground(Color.GRAY);
					
				}

				@Override
				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub
					((JDialog) formularioUsuario.getWindow()).getContentPane().setBackground(Colores.FONDO);
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
				
				formularioUsuario.getBtnCancelar().addActionListener(e -> manejarCierre());
				
				formularioUsuario.addWindowListener(new WindowAdapter() 
				{
					@Override
					public void windowClosing(WindowEvent e) 
					{
						manejarCierre();
					}
				});
		
	}
	private String guardarImagen() {
    	try {
    		
			String ruta = formularioUsuario.getIconDescription();
    		String original = ruta;
    		System.out.println(ruta);
    		if(!original.equals("..\\img\\icono.png")) {
    			
	    			
	    		
	    		File fuente = new File(original);
	    		
	    		String extension = original.substring(original.lastIndexOf("."));
	    		
	    		String nuevoNombre = UUID.randomUUID() + extension;
	    		
	    		String carpeta = "." + File.separator + "images";
	    		
	    		File directorio = new File(carpeta);
	    		
	    		if(!directorio.exists()) {
	    			directorio.mkdir();
	    		}
	    		
	    		Path destino = Paths.get(carpeta, nuevoNombre);
	    		
	    		Files.copy(fuente.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
	    		
	    		return destino.toString();
    		}
    		return null;
    		
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    		return null;
    	}
    }
	
	private void manejarCierre() {
		int opcion = formularioUsuario.confirmarCierre();
		System.out.println(opcion);

		if (opcion == JOptionPane.YES_OPTION) {
			formularioUsuario.getWindow().dispose();
		}
	}

	private void validacionDeActualizacion() throws IOException
    {
		boolean valido = true;

		if (!validarNombre()) { valido = false; }
		if (!validarApellido()) { valido = false; }
		if (!validarCorreo()) { valido = false; }
		if (!validarContrasenia()) { valido = false; }

		
		if (valido) 
		{
			String foto=guardarImagen();
			formularioUsuario.getUser().setFoto(foto);
			formularioUsuario.getUser().setNombre(formularioUsuario.getTxtNombre().getText());
			formularioUsuario.getUser().setApellido(formularioUsuario.getTxtApellido().getText());
			formularioUsuario.getUser().setCorreo(formularioUsuario.getTxtCorreo().getText());
			formularioUsuario.getUser().setGuardar(formularioUsuario.getGuardar().isSelected());
			String nuevaContesena = new String(formularioUsuario.getTxtContraseña().getPassword());
			formularioUsuario.getUser().setContrasena(nuevaContesena);
			
			formularioUsuario.setGuardado(true);
			
			
			formularioUsuario.getWindow().dispose();
		}
		
    }
	private void validacionDeRegistro() throws IOException
    {
		boolean valido = true;

		if (!validarNombre()) { valido = false; }
		if (!validarApellido()) { valido = false; }
		if (!validarCorreo()) { valido = false; }
		if (!validarContrasenia()) { valido = false; }

		
		if (valido) 
		{
			String foto=guardarImagen();
			formularioUsuario.setUser(new Usuario("a", "a", "a", "a"));
			formularioUsuario.getUser().setNombre(formularioUsuario.getTxtNombre().getText());
			formularioUsuario.getUser().setApellido(formularioUsuario.getTxtApellido().getText());
			formularioUsuario.getUser().setCorreo(formularioUsuario.getTxtCorreo().getText());
			formularioUsuario.getUser().setFoto(foto);
			formularioUsuario.getUser().setGuardar(formularioUsuario.getGuardar().isSelected());
			
			String nuevaContesena = new String(formularioUsuario.getTxtContraseña().getPassword());
			formularioUsuario.getUser().setContrasena(nuevaContesena);
			formularioUsuario.setGuardado(true);
			
			
			formularioUsuario.getWindow().dispose();
		}
		
    }
	
	public boolean validarNombre() 
    {
    	if (formularioUsuario.getTxtNombre().getText().trim().isEmpty()) 
    	{
    		formularioUsuario.getTxtErrNombre().setText("El nombre es obligatorio");
			return false;
		}else {
			formularioUsuario.getTxtErrNombre().setText(" ");
		}

		return true;
    }
	
	public boolean validarContrasenia() 
    {
		String contrasenia = new String(formularioUsuario.getTxtContraseña().getPassword());
    	if (contrasenia.isEmpty()) 
    	{
    		formularioUsuario.getTxtErrContraseña().setText("La contraseña es obligatorio");
			return false;
		}else if (!contrasenia.matches(".*[!$?_*].*")) 
    	{
    		formularioUsuario.getTxtErrContraseña().setText("Necesita un caracter especial (! $ ? _ *)");
			return false;
		}else if (contrasenia.matches(".*\\s.*")) 
    	{
    		formularioUsuario.getTxtErrContraseña().setText("No debe tener espacios");
			return false;
		}else{
			formularioUsuario.getTxtErrContraseña().setText(" ");
		}

		return true;
    }
	
    public boolean validarApellido()
    {
    	if (formularioUsuario.getTxtApellido().getText().trim().isEmpty()) 
    	{
    		formularioUsuario.getTxtErrApellido().setText("El apellido es obligatorio");
			return false;
		}else {
			formularioUsuario.getTxtErrApellido().setText(" ");
		}

		return true;
    }
    
    public boolean validarCorreo()
    {
    	if (formularioUsuario.getTxtCorreo().getText().trim().isEmpty()) 
    	{
    		formularioUsuario.getTxtErrCorreo().setText("El email es obligatorio");
			return false;
		}else {
			formularioUsuario.getTxtErrCorreo().setText(" ");
		}
    	
    	if (formularioUsuario.getTxtCorreo().getText().trim().length() < 3) 
    	{
    		formularioUsuario.getTxtErrCorreo().setText("Email inválido! Es muy corta.");
    	    return false;
    	}else {
    		formularioUsuario.getTxtErrCorreo().setText(" ");
		}

		if (!formularioUsuario.getTxtCorreo().getText().contains("@")) 
		{
			formularioUsuario.getTxtErrCorreo().setText("Email inválido! Le falta @");
			return false;
		}else 
		{
			formularioUsuario.getTxtErrCorreo().setText(" ");
		}
		
		if (!formularioUsuario.getTxtCorreo().getText().contains(".com")) 
		{
			formularioUsuario.getTxtErrCorreo().setText("Email inválido! Le falta .com");
			return false;
		}else 
		{
			formularioUsuario.getTxtErrCorreo().setText(" ");
		}

		return true;
    }
    
    
    private void asignarValidacion(JTextField campoDeTexto)
    {
    	
    	switch(campoDeTexto.getText())
    	{
    		case "txtNombre":
    			campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
    				
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
    			campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
    					
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
    			 campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
    					
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
    
    private void asignarKeyListener(JTextField campoDeTexto)
    {
    	campoDeTexto.addKeyListener(new KeyAdapter() 
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
         		
         		if(campoDeTexto.getText().length() >= 20)
         		{
         			e.consume();
         		}
         	}
 		});
    }
}
