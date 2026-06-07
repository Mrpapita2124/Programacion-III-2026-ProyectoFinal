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

import excepciones.CorreoInvalidoException;
import excepciones.NumeroInvalidoException;
import modelos.Cliente;
import modelos.Prestamo;
import modelos.Usuario;
import repositorios.ClienteRepository;
import repositorios.EstadoPrestamoRepository;
import repositorios.PrestamoRepository;
import repositorios.RegistroRepository;
import repositorios.UsuarioRepository;
import utilidades.Colores;
import utilidades.Sesion;
import vistas.formulario.FormularioGeneralCliente;
import vistas.formulario.FormularioGeneralPrestamo;
import vistas.formulario.FormularioRegistro;
import vistas.otros.Login;
import vistas.otros.Ventana;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

public class PrestamoFormController {
	private FormularioGeneralPrestamo formularioPrestamo;
	private PrestamoRepository prestamoRepository;
	private UsuarioRepository usuarioRepository;
	
	public PrestamoFormController(FormularioGeneralPrestamo formulario)
	{
		prestamoRepository=new PrestamoRepository();
		usuarioRepository=new UsuarioRepository();
		this.formularioPrestamo=formulario;
		
		formularioPrestamo.getRegistrar().addActionListener(e -> validacion());
		
		
		asignarWindowListener();
		
        asignarValidacion(this.formularioPrestamo.getMonto());
        asignarValidacion(this.formularioPrestamo.getInteresAtrasado());

	}
	
	private void asignarWindowListener()
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
					((JFrame) formularioPrestamo.getWindow()).getContentPane().setBackground(Colores.FONDO);
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
	 

	 
	private void manejarCierre() {
		
		int opcion = formularioPrestamo.confirmarCierre();
		System.out.println(opcion);

		if (opcion == JOptionPane.YES_OPTION) {
			
			formularioPrestamo.getWindow().dispose();
		}
	}

	 
	 
// Validaciones de Formulario Registro
    
	private void validacion()
    {
		formularioPrestamo.resetearErrorLabels();

		boolean valido = true;

		
		if (!validarMonto()) { valido = false; }
		if (!validarInteresAtrasado()) { valido = false; }
		if (!validarDinero()) { valido = false; }

		
		//if (!validarFoto()) { valid = false; }
		
		if (valido) 
		{
			restarDineroUsuario();
			int idUsuario=Sesion.getusuarioActual().getId();
			int idCliente=formularioPrestamo.getCliente().getIdCliente();
			int monto = Integer.parseInt(formularioPrestamo.getMonto().getText());
			double interesAtrasado=Double.parseDouble(formularioPrestamo.getInteresAtrasado().getText());
			int quincenas=formularioPrestamo.getNumQuincenas();
			double interes=formularioPrestamo.getCantInteres();
			Date fecha= Date.valueOf(LocalDate.now());
			double montoTotal=monto+monto*(interes/100);
			
				prestamoRepository.guardar(new Prestamo(idUsuario, idCliente, "activo", monto, quincenas, montoTotal/quincenas, montoTotal, interes, interesAtrasado, fecha));
				
				formularioPrestamo.getWindow().dispose();
			
		}
		
    }
	public boolean validarDinero() {
		if(Integer.parseInt(formularioPrestamo.getMonto().getText())>Sesion.getusuarioActual().getCapacidadPrestamo()) {
			return false;
		}
		return true;
	}
	public void restarDineroUsuario() {
		Sesion.getusuarioActual().setCapacidadPrestamo(Sesion.getusuarioActual().getCapacidadPrestamo()-Double.parseDouble(formularioPrestamo.getMonto().getText()));
		try {
			usuarioRepository.actualizar(Sesion.getusuarioActual());
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
				numeroExceptions(interes);
				formularioPrestamo.getLblErrorInteresAtrasado().setText("");
			} catch (Exception e) {
				// TODO: handle exception
				formularioPrestamo.getLblErrorInteresAtrasado().setText(e.getMessage());
				return false;
			}
			
		}

		return true;
    }
    
    public void numeroExceptions(String numero) throws NumeroInvalidoException {
    	if(!numero.matches("\\d*")) {
    		throw new NumeroInvalidoException("Solo se permites numeros");
    	}
    }
    public void doubleMultipleExceptions(String numero) throws NumeroInvalidoException {
    	if(!numero.matches("[0-9.]*")) {
    		throw new NumeroInvalidoException("Solo se permites numeros y punto decimal");
    	}
    	if((Integer.parseInt(numero) % 500)!= 0 ) {
    		throw new NumeroInvalidoException("Solo se permites numeros que sean multiplos de 500");
    	}
    }
   
    
    private void asignarValidacion(JTextField campoDeTexto)
    {
    	
    	switch(campoDeTexto.getName().toString())
    	{
    	
        case "monto":
        	campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { validarMonto(); }
                @Override public void removeUpdate(DocumentEvent e) { validarMonto(); }
                @Override public void changedUpdate(DocumentEvent e) { validarMonto(); }
            });
            break;
        case "interes-atrasado":
	    	campoDeTexto.getDocument().addDocumentListener(new DocumentListener() {
	            @Override public void insertUpdate(DocumentEvent e) { validarInteresAtrasado(); }
	            @Override public void removeUpdate(DocumentEvent e) { validarInteresAtrasado(); }
	            @Override public void changedUpdate(DocumentEvent e) { validarInteresAtrasado(); }
	        });
	        break;
	
	   
		
       
    	}
	    
    }
    
}