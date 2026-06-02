package controllers.client;

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

import exceptions.InvalidMailException;
import exceptions.InvalidNumberException;
import modelos.Client;
import modelos.User;
import repository.ClientRepository;
import repository.RegisterRepository;
import repository.UserRepository;
import utils.Colores;
import utils.Session;
import views.Login;
import views.Ventana;
import views.formulario.FormularioGeneralCliente;
import views.formulario.FormularioRegistro;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import repository.RegisterRepository;

public class ClientFormController {
	private ClientRepository clientRepository;
	private FormularioGeneralCliente formularioCliente;
	
	
	public ClientFormController(FormularioGeneralCliente form)
	{
		clientRepository=new ClientRepository();
		
		this.formularioCliente=form;
		
		formularioCliente.getRegistrar().addActionListener(e -> validacion());
		formularioCliente.getSeleccionar().addActionListener(e -> formularioCliente.chooseImage());
		formularioCliente.getSeleccionarComprobante().addActionListener(e -> formularioCliente.choosePDF());
		addWindowListener();
		
        asignarKeyListener(formularioCliente.getNombres());
        asignarKeyListener(formularioCliente.getApellidos()); 
        
        asignarValidacion(formularioCliente.getNombres());
        asignarValidacion(formularioCliente.getApellidos());
        asignarValidacion(formularioCliente.getEdad());
        asignarValidacion(formularioCliente.getDomicilio());
        asignarValidacion(formularioCliente.getNumeroCelular());
        asignarValidacion(formularioCliente.getCorreoFieldd());
        asignarValidacion(formularioCliente.getEmpleo());
        asignarValidacion(formularioCliente.getTelefonoEmpleo());
        asignarValidacion(formularioCliente.getDomicilioEmpleo());
        asignarValidacion(formularioCliente.getIngresoMensual());
        asignarValidacion(formularioCliente.getBanco());
        asignarValidacion(formularioCliente.getCuentaBancaria());
        asignarValidacion(formularioCliente.getCurp());

	}
	
	private void addWindowListener()
	{
		formularioCliente.getWindow().addWindowListener(new WindowListener() {
				
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
					((JFrame) formularioCliente.getWindow()).getContentPane().setBackground(Color.GRAY);
					
				}

				@Override
				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub
					((JFrame) formularioCliente.getWindow()).getContentPane().setBackground(Colores.BACKGROUND);
				}
				
				@Override
				public void windowClosing(WindowEvent e) {
					
					handleClose();
				}
				
				@Override
				public void windowClosed(WindowEvent e) {
				}
				
				
			});

			
	}
	 
	private String saveImage() {
	    	try {
	    		
				String ruta = formularioCliente.getIconDescription();
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
	private String savePDF() {
    	try {
    		
			String ruta = formularioCliente.getComprobanteIconClientDirection();
    		String original = ruta;
    		System.out.println(ruta);
    		if(!original.equals("..\\img\\DocumentDefaultIcon.png")) {
    			
	    			
	    		
	    		File source = new File(original);
	    		
	    		String extension = original.substring(original.lastIndexOf("."));
	    		
	    		String newName = UUID.randomUUID() + extension;
	    		
	    		String folder = "." + File.separator + "PDFs";
	    		
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
		
		int option = formularioCliente.confirmExit();
		System.out.println(option);

		if (option == JOptionPane.YES_OPTION) {
			
			formularioCliente.getWindow().dispose();
		}
	}

	 
	 
// Validaciones de Formulario Registro
    
	private void validacion()
    {
		formularioCliente.resetearErrorLabels();

		boolean valid = true;

		if (!validarINE()) { valid = false; }
		if (!validarDocumento()) { valid = false; }
		if (!validarNombre()) { valid = false; }
		if (!validarApellido()) { valid = false; }
		if (!validarEdad()) { valid = false; }
		if (!validarDomicilio()) { valid = false; }
		
		if (!validarNumeroCelular()) { valid = false; }
		if (!validarCorreo()) { valid = false; }
		if (!validarEmpleo()) { valid = false; }
		if (!validarTelefonoEmpleo()) { valid = false; }
		if (!validarDomicilioEMpleo()) { valid = false; }
		if (!validarIngresosMensuales()) { valid = false; }
		if (!validarBanco()) { valid = false; }
		if (!validarCuentaBancaria()) { valid = false; }
		if (!validarCurp()) { valid = false; }

		
		//if (!validarFoto()) { valid = false; }
		
		if (valid) 
		{
			int idUsuario=Session.getCurrentUser().getId();
			String nombre = formularioCliente.getNombre();
			String apellido = formularioCliente.getApellido();
			int edad = Integer.parseInt(formularioCliente.getEdad().getText());
			String domicilio = formularioCliente.getDomicilio().getText();
			String numeroCelular = formularioCliente.getNumeroCelular().getText();
			String correo = formularioCliente.getCorreo();
			String empleo = formularioCliente.getEmpleo().getText();
			String telefonoEmpleo= formularioCliente.getTelefonoEmpleo().getText();
			String domicilioEMpleo=formularioCliente.getDomicilioEmpleo().getText();
			Double ingresoMensual = Double.parseDouble(formularioCliente.getIngresoMensual().getText());
			String banco = formularioCliente.getBanco().getText();
			String cuentaBancaria = formularioCliente.getCuentaBancaria().getText();
			String curp = formularioCliente.getCurp().getText();
			String foto=saveImage();
			String comprobante=savePDF();
			
			if(this.formularioCliente.isEdit()){
				System.out.println("sdfsdfsdf");
				int idClient=this.formularioCliente.getClient().getIdCliente();
				clientRepository.update(new Client(idClient, idUsuario, nombre, apellido, edad, foto, domicilio, comprobante, numeroCelular, correo, empleo, domicilioEMpleo, telefonoEmpleo, ingresoMensual, banco, cuentaBancaria, curp, "regular"));
			}else {
				clientRepository.save(new Client(idUsuario, nombre, apellido, edad, foto, domicilio, comprobante, numeroCelular, correo, empleo, domicilioEMpleo, telefonoEmpleo, ingresoMensual, banco, cuentaBancaria, curp, "regular"));

			}
			

			formularioCliente.getWindow().dispose();
			
		}
		
    }
	private boolean validarINE() {
		ImageIcon icon =(ImageIcon) this.formularioCliente.getIneClient().getIcon();
		String ruta = icon.getDescription();
		
    	
    	if (ruta=="..\\img\\LicenseDefault.png") 
    	{
    		formularioCliente.getLblErrorFoto().setText("La foto es obligatoria");
			return false;
		}
    	
    	
		return true;
	}
	private boolean validarDocumento() {
		ImageIcon icon =(ImageIcon) this.formularioCliente.getComprobanteClient().getIcon();
		String ruta = icon.getDescription();
		
    	
    	if (ruta=="..\\img\\DocumentDefaultIcon.png") 
    	{
    		formularioCliente.getLblErrorDocumento().setText("Elk documento es obligatoria");
			return false;
		}
    	
    	
		return true;
	}
	public boolean validarNombre() 
    {
    	String nombre = formularioCliente.getNombres().getText().trim();
    	
    	if (nombre.isEmpty() || nombre.equals("Nombre")) 
    	{
    		formularioCliente.getLblErrorNombre().setText("El nombre es obligatorio");
			return false;
		} else {
			formularioCliente.getLblErrorNombre().setText("");
		}

		return true;
    }
	
    public boolean validarApellido()
    {
    	String apellido = formularioCliente.getApellidos().getText().trim();
    	
    	if (apellido.isEmpty() || apellido.equals("Apellido")) 
    	{
    		formularioCliente.getLblErrorApellido().setText("El apellido es obligatorio");
			return false;
		} else {
			formularioCliente.getLblErrorApellido().setText("");
		}

		return true;
    }
    public boolean validarEdad()
    {
    	String edad = formularioCliente.getEdad().getText().trim();
    	
    	if (edad.isEmpty() || edad.equals("Edad")) 
    	{
    		formularioCliente.getLblErrorEdad().setText("La edad es obligatoria");
			return false;
		} else {
			try {
				numberExceptions(edad);
				formularioCliente.getLblErrorEdad().setText("");
			} catch (Exception e) {
				// TODO: handle exception
				formularioCliente.getLblErrorEdad().setText(e.getMessage());
				return false;
			}
			
		}

		return true;
    }
    public boolean validarDomicilio()
    {
    	String domicilio = formularioCliente.getDomicilio().getText().trim();
    	
    	if (domicilio.isEmpty() || domicilio.equals("Direccion")) 
    	{
    		formularioCliente.getLblErrorDomicilio().setText("El domicilio es obligatorio");
			return false;
		} else {
			formularioCliente.getLblErrorDomicilio().setText("");
		}

		return true;
    }
   
    public boolean validarNumeroCelular()
    {
    	String numeroCelular = formularioCliente.getNumeroCelular().getText().trim();
    	
    	if (numeroCelular.isEmpty() || numeroCelular.equals("Numero celular")) 
    	{
    		formularioCliente.getLblErrorNumeroCelular().setText("El apellido es obligatorio");
			return false;
		} else {
			try {
				numberExceptions(numeroCelular);
				formularioCliente.getLblErrorNumeroCelular().setText("");
			} catch (Exception e) {
				// TODO: handle exception
				formularioCliente.getLblErrorNumeroCelular().setText(e.getMessage());
				return false;
			}
			
		}

		return true;
    }
    
    public boolean validarCorreo()
    {
    	String correo = formularioCliente.getCorreoFieldd().getText().trim();
    	
    	if (correo.isEmpty() || correo.equals("correo@ejemplo.com")) 
    	{
    		formularioCliente.getLblErrorCorreo().setText("El email es obligatorio");
			return false;
		}else {
			try {
				emailExceptions(correo);
				formularioCliente.getLblErrorCorreo().setText("");
			} catch (Exception e) {
				// TODO: handle exception
				formularioCliente.getLblErrorCorreo().setText(e.getMessage());
				return false;
			}
		}
		return true;
    }
    public boolean validarEmpleo()
    {
    	String empleo = formularioCliente.getEmpleo().getText().trim();
    	
    	if (empleo.isEmpty() || empleo.equals("Empleo")) 
    	{
    		formularioCliente.getLblErrorEmpleo().setText("El empleo es obligatorio");
			return false;
		} else {
			formularioCliente.getLblErrorEmpleo().setText("");
		}

		return true;
    }
    public boolean validarTelefonoEmpleo()
    {
    	String telefonoEmpleo = formularioCliente.getTelefonoEmpleo().getText().trim();
    	
    	if (telefonoEmpleo.isEmpty() || telefonoEmpleo.equals("Telefono empleo")) 
    	{
    		formularioCliente.getLblErrorTelefonoEmpleo().setText("El telefono es obligatorio");
			return false;
		} else {
			try {
				numberExceptions(telefonoEmpleo);
				formularioCliente.getLblErrorTelefonoEmpleo().setText("");
			} catch (Exception e) {
				// TODO: handle exception
				formularioCliente.getLblErrorTelefonoEmpleo().setText(e.getMessage());
				return false;
			}
			
		}

		return true;
    }
    public boolean validarDomicilioEMpleo()
    {
    	String domicilioEmpleo = formularioCliente.getDomicilioEmpleo().getText().trim();
    	
    	if (domicilioEmpleo.isEmpty() || domicilioEmpleo.equals("Domicilio empleo")) 
    	{
    		formularioCliente.getLblErrorDomicilioEmpleo().setText("El Domicilio del empleo es obligatorio");
			return false;
		} else {
			formularioCliente.getLblErrorDomicilioEmpleo().setText("");
		}

		return true;
    }
    public boolean validarIngresosMensuales()
    {
    	String ingreso = formularioCliente.getIngresoMensual().getText().trim();
    	
    	if (ingreso.isEmpty() || ingreso.equals("Ingreso mensual")) 
    	{
    		formularioCliente.getLblErrorIngresoMensual().setText("El Ingreso mensual es obligatorio");
			return false;
		} else {
			try {
				doubleExceptions(ingreso);
				formularioCliente.getLblErrorIngresoMensual().setText("");
			} catch (Exception e) {
				// TODO: handle exception
				formularioCliente.getLblErrorIngresoMensual().setText(e.getMessage());
				return false;
			}
		}

		return true;
    }
    public boolean validarBanco()
    {
    	String banco = formularioCliente.getBanco().getText().trim();
    	
    	if (banco.isEmpty() || banco.equals("Nombre Banco")) 
    	{
    		formularioCliente.getLblErrorBanco().setText("El banco es obligatorio");
			return false;
		} else {
			formularioCliente.getLblErrorBanco().setText("");
		}

		return true;
    }
    public boolean validarCuentaBancaria()
    {
    	String cuenta = formularioCliente.getCuentaBancaria().getText().trim();
    	
    	if (cuenta.isEmpty() || cuenta.equals("Cuenta bancaria")) 
    	{
    		formularioCliente.getLblErrorCuentaBancaria().setText("La cuenta es obligatorio");
			return false;
		} else {
			try {
				numberExceptions(cuenta);
				formularioCliente.getLblErrorCuentaBancaria().setText("");
			} catch (Exception e) {
				// TODO: handle exception
				formularioCliente.getLblErrorCuentaBancaria().setText(e.getMessage());
				return false;
			}
			
		}

		return true;
    }
    public boolean validarCurp()
    {
    	String curp = formularioCliente.getCurp().getText().trim();
    	
    	if (curp.isEmpty() || curp.equals("Curp")) 
    	{
    		formularioCliente.getLblErrorCurp().setText("El curp es obligatorio");
			return false;
		} else {
			formularioCliente.getLblErrorCurp().setText("");
		}

		return true;
    }
    public void emailExceptions(String correo) throws InvalidMailException {
    	if (correo.length() < 5) 
    	{
    		
    	    throw new InvalidMailException("Email inválido! Es muy corto.");
    	}

		if (!correo.contains("@")) 
		{
			
			throw new InvalidMailException("Email inválido! Le falta @");
		}
		
		if (!correo.contains(".com")) 
		{
			throw new InvalidMailException("Email inválido! Le falta un '.com'");
		}
		
		// Validación adicional para dominio
		String[] parts = correo.split("@");
		if (parts.length != 2 || !parts[1].contains(".")) {
			throw new InvalidMailException("Email inválido! Formato incorrecto");
		}
    }
    public void numberExceptions(String numero) throws InvalidNumberException {
    	if(!numero.matches("\\d*")) {
    		throw new InvalidNumberException("Solo se permites numeros");
    	}
    }
    public void doubleExceptions(String numero) throws InvalidNumberException {
    	if(!numero.matches("[0-9.]*")) {
    		throw new InvalidNumberException("Solo se permites numeros y punto decimal");
    	}
    }
   /*public boolean validarFoto()
    {
    	ImageIcon icon =(ImageIcon) formularioRegsitro.getIconoUsuario().getIcon();
		String ruta = icon.getDescription();
		
    	
    	if (ruta=="..\\img\\icono.png") 
    	{
    		formularioRegsitro.lblErrorFoto.setText("La foto es obligatoria");
			return false;
		}
    	
    	
		return true;
    }*/
    
    private void asignarValidacion(JTextField jTextField)
    {
    	
    	switch(jTextField.getName().toString())
    	{
    	case "nombres":
    		jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarNombre(); }
                @Override public void removeUpdate(DocumentEvent e) { validarNombre(); }
                @Override public void changedUpdate(DocumentEvent e) { validarNombre(); }
            });
            break;

        case "apellidos":
        	jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarApellido(); }
                @Override public void removeUpdate(DocumentEvent e) { validarApellido(); }
                @Override public void changedUpdate(DocumentEvent e) { validarApellido(); }
            });
            break;

        case "edad":
        	jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarEdad(); }
                @Override public void removeUpdate(DocumentEvent e) { validarEdad(); }
                @Override public void changedUpdate(DocumentEvent e) { validarEdad(); }
            });
            break;

        case "domicilio":
        	jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarDomicilio(); }
                @Override public void removeUpdate(DocumentEvent e) { validarDomicilio(); }
                @Override public void changedUpdate(DocumentEvent e) { validarDomicilio(); }
            });
            break;

        case "numeroCelular":
        	jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarNumeroCelular(); }
                @Override public void removeUpdate(DocumentEvent e) { validarNumeroCelular(); }
                @Override public void changedUpdate(DocumentEvent e) { validarNumeroCelular(); }
            });
            break;

        case "correo":
        	jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarCorreo(); }
                @Override public void removeUpdate(DocumentEvent e) { validarCorreo(); }
                @Override public void changedUpdate(DocumentEvent e) { validarCorreo(); }
            });
            break;

        case "empleo":
        	jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarEmpleo(); }
                @Override public void removeUpdate(DocumentEvent e) { validarEmpleo(); }
                @Override public void changedUpdate(DocumentEvent e) { validarEmpleo(); }
            });
            break;

        case "telefonoEmpleo":
        	jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarTelefonoEmpleo(); }
                @Override public void removeUpdate(DocumentEvent e) { validarTelefonoEmpleo(); }
                @Override public void changedUpdate(DocumentEvent e) { validarTelefonoEmpleo(); }
            });
            break;

        case "domicilioEmpleo":
        	jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarDomicilioEMpleo(); }
                @Override public void removeUpdate(DocumentEvent e) { validarDomicilioEMpleo(); }
                @Override public void changedUpdate(DocumentEvent e) { validarDomicilioEMpleo(); }
            });
            break;

        case "ingresoMensual":
        	jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarIngresosMensuales(); }
                @Override public void removeUpdate(DocumentEvent e) { validarIngresosMensuales(); }
                @Override public void changedUpdate(DocumentEvent e) { validarIngresosMensuales(); }
            });
            break;

        case "banco":
        	jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarBanco(); }
                @Override public void removeUpdate(DocumentEvent e) { validarBanco(); }
                @Override public void changedUpdate(DocumentEvent e) { validarBanco(); }
            });
            break;

        case "cuentaBancaria":
        	jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarCuentaBancaria(); }
                @Override public void removeUpdate(DocumentEvent e) { validarCuentaBancaria(); }
                @Override public void changedUpdate(DocumentEvent e) { validarCuentaBancaria(); }
            });
            break;

        case "curp":
        	jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarCurp(); }
                @Override public void removeUpdate(DocumentEvent e) { validarCurp(); }
                @Override public void changedUpdate(DocumentEvent e) { validarCurp(); }
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
         		// Solo para nombres y apellidos, no para correo y contraseña
         		if (JtextField.getName().equals("nombres") || JtextField.getName().equals("apellidos")) {
         			if(!Character.isSpaceChar(e.getKeyChar()))
         			{
         				if(Character.isDigit(e.getKeyChar()) || !Character.isAlphabetic(e.getKeyChar()))
         				{
         					e.consume();
         				}
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