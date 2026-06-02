package controllers.prestamo;

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

import com.mysql.cj.protocol.a.LocalDateTimeValueEncoder;

import exceptions.InvalidMailException;
import exceptions.InvalidNumberException;
import modelos.Client;
import modelos.Prestamo;
import modelos.User;
import repository.ClientRepository;
import repository.EstadoPrestamoRepository;
import repository.PrestamoRepository;
import repository.RegisterRepository;
import repository.UserRepository;
import utils.Colores;
import utils.Session;
import views.Login;
import views.Ventana;
import views.formulario.FormularioGeneralCliente;
import views.formulario.FormularioGeneralPrestamo;
import views.formulario.FormularioRegistro;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;
import repository.RegisterRepository;

public class PrestamoFormController {
	private FormularioGeneralPrestamo formularioPrestamo;
	private PrestamoRepository prestamoRepository;
	private UserRepository userRepository;
	
	public PrestamoFormController(FormularioGeneralPrestamo form)
	{
		prestamoRepository=new PrestamoRepository();
		userRepository=new UserRepository();
		this.formularioPrestamo=form;
		
		formularioPrestamo.getRegistrar().addActionListener(e -> validacion());
		
		
		addWindowListener();
		
        asignarValidacion(this.formularioPrestamo.getMonto());
        asignarValidacion(this.formularioPrestamo.getInteresAtrasado());

	}
	
	private void addWindowListener()
	{
		formularioPrestamo.getWindow().addWindowListener(new WindowListener() {
				
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
					((JFrame) formularioPrestamo.getWindow()).getContentPane().setBackground(Color.GRAY);
					
				}

				@Override
				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub
					((JFrame) formularioPrestamo.getWindow()).getContentPane().setBackground(Colores.BACKGROUND);
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
	 

	 
	private void handleClose() {
		
		int option = formularioPrestamo.confirmExit();
		System.out.println(option);

		if (option == JOptionPane.YES_OPTION) {
			
			formularioPrestamo.getWindow().dispose();
		}
	}

	 
	 
// Validaciones de Formulario Registro
    
	private void validacion()
    {
		formularioPrestamo.resetearErrorLabels();

		boolean valid = true;

		
		if (!validarMonto()) { valid = false; }
		if (!validarInteresAtrasado()) { valid = false; }
		if (!validarDinero()) { valid = false; }

		
		//if (!validarFoto()) { valid = false; }
		
		if (valid) 
		{
			restarDineroUsuario();
			int idUsuario=Session.getCurrentUser().getId();
			int idCliente=formularioPrestamo.getClient().getIdCliente();
			int monto = Integer.parseInt(formularioPrestamo.getMonto().getText());
			double interesAtrasado=Double.parseDouble(formularioPrestamo.getInteresAtrasado().getText());
			int quincenas=formularioPrestamo.getNumQuincenas();
			double interes=formularioPrestamo.getCantInteres();
			Date fecha= Date.valueOf(LocalDate.now());
			double montoTotal=monto+monto*(interes/100);
			
				prestamoRepository.save(new Prestamo(idUsuario, idCliente, "activo", monto, quincenas, montoTotal/quincenas, montoTotal, interes, interesAtrasado, fecha));
				
				formularioPrestamo.getWindow().dispose();
			
		}
		
    }
	public boolean validarDinero() {
		if(Integer.parseInt(formularioPrestamo.getMonto().getText())>Session.getCurrentUser().getCapacidadPrestamo()) {
			return false;
		}
		return true;
	}
	public void restarDineroUsuario() {
		Session.getCurrentUser().setCapacidadPrestamo(Session.getCurrentUser().getCapacidadPrestamo()-Double.parseDouble(formularioPrestamo.getMonto().getText()));
		try {
			userRepository.update(Session.getCurrentUser());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public boolean validarMonto()
    {
    	String monto = formularioPrestamo.getMonto().getText().trim();
    	
    	if (monto.isEmpty() || monto.equals("Monto")) 
    	{
    		formularioPrestamo.getLblErrorMonto().setText("El monto es obligatorio");
			return false;
		} else {
			try {
				doubleMultipleExceptions(monto);
				
				formularioPrestamo.getLblErrorMonto().setText("");
			} catch (Exception e) {
				// TODO: handle exception
				formularioPrestamo.getLblErrorMonto().setText(e.getMessage());
				return false;
			}
			
		}

		return true;
    }
    public boolean validarInteresAtrasado()
    {
    	String interes = formularioPrestamo.getInteresAtrasado().getText().trim();
    	
    	if (interes.isEmpty() || interes.equals("Interes atrasado")) 
    	{
    		formularioPrestamo.getLblErrorInteresAtrasado().setText("El interes para pagos atrasados es obligatorio");
			return false;
		} else {
			try {
				numberExceptions(interes);
				formularioPrestamo.getLblErrorInteresAtrasado().setText("");
			} catch (Exception e) {
				// TODO: handle exception
				formularioPrestamo.getLblErrorInteresAtrasado().setText(e.getMessage());
				return false;
			}
			
		}

		return true;
    }
    
    public void numberExceptions(String numero) throws InvalidNumberException {
    	if(!numero.matches("\\d*")) {
    		throw new InvalidNumberException("Solo se permites numeros");
    	}
    }
    public void doubleMultipleExceptions(String numero) throws InvalidNumberException {
    	if(!numero.matches("[0-9.]*")) {
    		throw new InvalidNumberException("Solo se permites numeros y punto decimal");
    	}
    	if((Integer.parseInt(numero) % 500)!= 0 ) {
    		throw new InvalidNumberException("Solo se permites numeros que sean multiplos de 500");
    	}
    }
   
    
    private void asignarValidacion(JTextField jTextField)
    {
    	
    	switch(jTextField.getName().toString())
    	{
    	
        case "monto":
        	jTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarMonto(); }
                @Override public void removeUpdate(DocumentEvent e) { validarMonto(); }
                @Override public void changedUpdate(DocumentEvent e) { validarMonto(); }
            });
            break;
        case "interes-atrasado":
	    	jTextField.getDocument().addDocumentListener(new DocumentListener() {
	            @Override public void insertUpdate(DocumentEvent e) { validarInteresAtrasado(); }
	            @Override public void removeUpdate(DocumentEvent e) { validarInteresAtrasado(); }
	            @Override public void changedUpdate(DocumentEvent e) { validarInteresAtrasado(); }
	        });
	        break;
	
	   
		
       
    	}
	    
    }
    
}