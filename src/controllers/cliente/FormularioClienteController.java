package controllers.cliente;

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

import excepciones.CorreoInvalidoException;
import excepciones.NumeroInvalidoException;
import modelos.Cliente;
import modelos.Usuario;
import repositorios.ClienteRepository;
import repositorios.RegistroRepository;
import repositorios.UsuarioRepository;
import utilidades.Colores;
import utilidades.Sesion;
import vistas.formulario.FormularioGeneralCliente;
import vistas.formulario.FormularioRegistro;
import vistas.otros.Login;
import vistas.otros.Ventana;
import vistas.otros.VentanaPrincipal;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FormularioClienteController {
	private ClienteRepository clientRepository;
	private FormularioGeneralCliente formularioCliente;
	
	
	public FormularioClienteController(FormularioGeneralCliente formulario)
	{
		clientRepository=new ClienteRepository();
		
		this.formularioCliente=formulario;
		
		formularioCliente.getRegistrar().addActionListener(e -> validacion());
		formularioCliente.getSeleccionar().addActionListener(e -> formularioCliente.seleccionarImagen());
		formularioCliente.getSeleccionarComprobante().addActionListener(e -> formularioCliente.seleccionarPDF());
		asignarWindowListener();

        
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
	
	private void asignarWindowListener()
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
					((JFrame) formularioCliente.getWindow()).getContentPane().setBackground(Colores.FONDO);
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
	    		
				String ruta = formularioCliente.getIconoDescription();
	    		String original = ruta;
	    		if(!original.equals("/img/icono.png")) {
	    			
		    			
		    		
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
	private String guardarPDF() {
    	try {
    		
			String ruta = formularioCliente.getComprobanteIconClientDirection();
    		String original = ruta;
    		if(!original.equals("/img/DocumentDefaultIcon.png")) {
    			
	    			
	    		
	    		File fuente = new File(original);
	    		
	    		String extension = original.substring(original.lastIndexOf("."));
	    		
	    		String nuevoNombre = UUID.randomUUID() + extension;
	    		
	    		String carpeta = "." + File.separator + "PDFs";
	    		
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
		
		int option = formularioCliente.confirmExit();

		if (option == JOptionPane.YES_OPTION) {
			
			formularioCliente.getWindow().dispose();
		}
	}

	 
	 
// Validaciones de Formulario Registro
    
	private void validacion()
    {
		formularioCliente.resetearErrorLabels();

		boolean valido = true;

		if (!validarINE()) { valido = false; }
		if (!validarDocumento()) { valido = false; }
		if (!validarNombre()) { valido = false; }
		if (!validarApellido()) { valido = false; }
		if (!validarEdad()) { valido = false; }
		if (!validarDomicilio()) { valido = false; }
		
		if (!validarNumeroCelular()) { valido = false; }
		if (!validarCorreo()) { valido = false; }
		if (!validarEmpleo()) { valido = false; }
		if (!validarTelefonoEmpleo()) { valido = false; }
		if (!validarDomicilioEMpleo()) { valido = false; }
		if (!validarIngresosMensuales()) { valido = false; }
		if (!validarBanco()) { valido = false; }
		if (!validarCuentaBancaria()) { valido = false; }
		if (!validarCurp()) { valido = false; }

		
		//if (!validarFoto()) { valid = false; }
		
		if (valido) 
		{
			int idUsuario=Sesion.getusuarioActual().getId();
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
			String foto=guardarImagen();
			String comprobante=guardarPDF();
			
			if(this.formularioCliente.isEdit()){
				int idClient=this.formularioCliente.getClient().getIdCliente();
				if( clientRepository.actualizar(new Cliente(idClient, idUsuario, nombre, apellido, edad, foto, domicilio, comprobante, numeroCelular, correo, empleo, domicilioEMpleo, telefonoEmpleo, ingresoMensual, banco, cuentaBancaria, curp,this.formularioCliente.getClient().getReputacion()))) {
					formularioCliente.getWindow().dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Correo, numero telefónico, curp o cuenta bancaria ya usados", " Datos repetidos", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}else {
				if( clientRepository.guardar(new Cliente(idUsuario, nombre, apellido, edad, foto, domicilio, comprobante, numeroCelular, correo, empleo, domicilioEMpleo, telefonoEmpleo, ingresoMensual, banco, cuentaBancaria, curp, "no medido"))) {
					formularioCliente.getWindow().dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Correo, numero telefónico, curp o cuenta bancaria ya usados", " Datos repetidos", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			

			
			
		}
		
    }
	private boolean validarINE() {
		ImageIcon icono =(ImageIcon) this.formularioCliente.getIneCliente().getIcon();
		String ruta = icono.getDescription();
		
    	
    	if (ruta=="/img/LicenseDefault.png") 
    	{
    		formularioCliente.getLblErrorFoto().setText("La foto es obligatoria");
			return false;
		}
    	
    	
		return true;
	}
	private boolean validarDocumento() {
		ImageIcon icono =(ImageIcon) this.formularioCliente.getComprobanteClient().getIcon();
		String ruta = icono.getDescription();
		
    	
    	if (ruta=="/img/DocumentDefaultIcon.png") 
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
		} else if(Double.parseDouble(edad)<18) {
			
			formularioCliente.getLblErrorEdad().setText("Debe ser mayor de edad");
			return false;
		}else if(Double.parseDouble(edad)>120) {
			
			formularioCliente.getLblErrorEdad().setText("Ingresa una edad valida");
			return false;
		}{
			formularioCliente.getLblErrorEdad().setText(" ");
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
    		formularioCliente.getLblErrorNumeroCelular().setText("El numero es obligatorio");
			return false;
		} else if (numeroCelular.length()<10) 
    	{
    		formularioCliente.getLblErrorNumeroCelular().setText("Ingresa un numero valido");
			return false;
		}{
			try {
				numeroExceptions(numeroCelular);
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
		} else if (telefonoEmpleo.length()<10) 
    	{
    		formularioCliente.getLblErrorTelefonoEmpleo().setText("Ingresa un telefono valido");
			return false;
		}{
			try {
				numeroExceptions(telefonoEmpleo);
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
				numeroExceptions(cuenta);
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
		} else if (curp.length()!=18 || curp.matches(".*[a-z].*")) 
    	{
    		formularioCliente.getLblErrorCurp().setText("Ingresa un curp valido");
			return false;
		}{
			formularioCliente.getLblErrorCurp().setText("");
		}

		return true;
    }
    public void emailExceptions(String correo) throws CorreoInvalidoException {
    	if (correo.length() < 5) 
    	{
    		
    	    throw new CorreoInvalidoException("Email inválido! Es muy corto.");
    	}

		if (!correo.contains("@")) 
		{
			
			throw new CorreoInvalidoException("Email inválido! Le falta @");
		}
		
		if (!correo.contains(".com")) 
		{
			throw new CorreoInvalidoException("Email inválido! Le falta un '.com'");
		}
		
		// Validación adicional para dominio
		String[] parts = correo.split("@");
		if (parts.length != 2 || !parts[1].contains(".")) {
			throw new CorreoInvalidoException("Email inválido! Formato incorrecto");
		}
    }
    public void numeroExceptions(String numero) throws NumeroInvalidoException {
    	if(!numero.matches("\\d*")) {
    		throw new NumeroInvalidoException("Solo se permites numeros");
    	}
    }
    public void doubleExceptions(String numero) throws NumeroInvalidoException {
    	if(!numero.matches("[0-9.]*")) {
    		throw new NumeroInvalidoException("Solo se permites numeros y punto decimal");
    	}
    }
   
    
    private void asignarValidacion(JTextField campoDeTexto)
    {
    	
    	switch(campoDeTexto.getName().toString())
    	{
    	case "nombres":
    		campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarNombre(); }
                @Override public void removeUpdate(DocumentEvent e) { validarNombre(); }
                @Override public void changedUpdate(DocumentEvent e) { validarNombre(); }
            });
            break;

        case "apellidos":
        	campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarApellido(); }
                @Override public void removeUpdate(DocumentEvent e) { validarApellido(); }
                @Override public void changedUpdate(DocumentEvent e) { validarApellido(); }
            });
            break;

        case "edad":
        	campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarEdad(); }
                @Override public void removeUpdate(DocumentEvent e) { validarEdad(); }
                @Override public void changedUpdate(DocumentEvent e) { validarEdad(); }
            });
            break;

        case "domicilio":
        	campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarDomicilio(); }
                @Override public void removeUpdate(DocumentEvent e) { validarDomicilio(); }
                @Override public void changedUpdate(DocumentEvent e) { validarDomicilio(); }
            });
            break;

        case "numeroCelular":
        	campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarNumeroCelular(); }
                @Override public void removeUpdate(DocumentEvent e) { validarNumeroCelular(); }
                @Override public void changedUpdate(DocumentEvent e) { validarNumeroCelular(); }
            });
            break;

        case "correo":
        	campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarCorreo(); }
                @Override public void removeUpdate(DocumentEvent e) { validarCorreo(); }
                @Override public void changedUpdate(DocumentEvent e) { validarCorreo(); }
            });
            break;

        case "empleo":
        	campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarEmpleo(); }
                @Override public void removeUpdate(DocumentEvent e) { validarEmpleo(); }
                @Override public void changedUpdate(DocumentEvent e) { validarEmpleo(); }
            });
            break;

        case "telefonoEmpleo":
        	campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarTelefonoEmpleo(); }
                @Override public void removeUpdate(DocumentEvent e) { validarTelefonoEmpleo(); }
                @Override public void changedUpdate(DocumentEvent e) { validarTelefonoEmpleo(); }
            });
            break;

        case "domicilioEmpleo":
        	campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarDomicilioEMpleo(); }
                @Override public void removeUpdate(DocumentEvent e) { validarDomicilioEMpleo(); }
                @Override public void changedUpdate(DocumentEvent e) { validarDomicilioEMpleo(); }
            });
            break;

        case "ingresoMensual":
        	campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarIngresosMensuales(); }
                @Override public void removeUpdate(DocumentEvent e) { validarIngresosMensuales(); }
                @Override public void changedUpdate(DocumentEvent e) { validarIngresosMensuales(); }
            });
            break;

        case "banco":
        	campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarBanco(); }
                @Override public void removeUpdate(DocumentEvent e) { validarBanco(); }
                @Override public void changedUpdate(DocumentEvent e) { validarBanco(); }
            });
            break;

        case "cuentaBancaria":
        	campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarCuentaBancaria(); }
                @Override public void removeUpdate(DocumentEvent e) { validarCuentaBancaria(); }
                @Override public void changedUpdate(DocumentEvent e) { validarCuentaBancaria(); }
            });
            break;

        case "curp":
        	campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarCurp(); }
                @Override public void removeUpdate(DocumentEvent e) { validarCurp(); }
                @Override public void changedUpdate(DocumentEvent e) { validarCurp(); }
            });
            break;
    			
    		
    	}
    }
    
    
    
}