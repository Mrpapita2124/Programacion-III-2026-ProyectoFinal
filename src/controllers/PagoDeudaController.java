package controllers;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import excepciones.NumeroInvalidoException;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import modelos.Usuario;
import repositorios.EstadoPrestamoRepository;
import repositorios.PrestamoRepository;
import repositorios.UsuarioRepository;
import utilidades.Actualizador;
import utilidades.Colores;
import utilidades.Sesion;
import vistas.otros.PagoDeuda;

public class PagoDeudaController {
	private PagoDeuda pagoDeuda;
	private EstadoPrestamoRepository estadoRepository;
	private PrestamoRepository prestamoRepository;
	private UsuarioRepository usuarioRepository;
	private Actualizador actualizador;
	public PagoDeudaController(PagoDeuda pagoDeuda) {
		actualizador=new Actualizador();
		usuarioRepository= new UsuarioRepository();
		estadoRepository= new EstadoPrestamoRepository();
		prestamoRepository= new PrestamoRepository();
		this.pagoDeuda = pagoDeuda;
		pagoDeuda.getBtnPagar().addActionListener(e -> {
			if(validarMonto()) {
				pagarMonto();
				JOptionPane.showMessageDialog(pagoDeuda, "Deuda pago registrado con éxito.", " Se hizo pago deuda", JOptionPane.INFORMATION_MESSAGE);
				
				this.pagoDeuda.getVentana().recargarPrestamos(true);
		    	this.pagoDeuda.dispose();
		    	
			}
		});
		this.pagoDeuda.getDinero().addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				pagoDeuda.reiniciarErrorLabels();
				validarMonto();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	public boolean validarMonto()
    {
    	String monto = pagoDeuda.getDinero().getText().trim();
    	
    	if (monto.isEmpty() || monto.equals("Monto")) 
    	{
    		pagoDeuda.getLblErrorDinero().setText("El monto es obligatorio");
			return false;
		} else {
			try {
				doubleMultipleExceptions(monto);
				
				pagoDeuda.getLblErrorDinero().setText("");
			} catch (Exception e) {
				// TODO: handle exception
				pagoDeuda.getLblErrorDinero().setText(e.getMessage());
				return false;
			}
			
		}
    	
		return true;
    }
	public void doubleMultipleExceptions(String numero) throws NumeroInvalidoException {
    	if(!numero.matches("[0-9.]*")) {
    		throw new NumeroInvalidoException("Solo se permites numeros y punto decimal");
    	}
    	if(this.pagoDeuda.getEstadoPrestamo().getDineroAtrasado()<Double.parseDouble(this.pagoDeuda.getDinero().getText())) {
    		throw new NumeroInvalidoException("Esta ingresando mas dinero que el debido");
    	}
    }
	public void pagarMonto(){
		Usuario usuario= Sesion.getusuarioActual();
		EstadoPrestamo estado=this.pagoDeuda.getEstadoPrestamo();
		Prestamo prestamo=this.pagoDeuda.getPrestamo();
		estado.setDineroAtrasado(estado.getDineroAtrasado()-Double.parseDouble(this.pagoDeuda.getDinero().getText()));
		estado.setMontoRestante(estado.getMontoRestante()-Double.parseDouble(this.pagoDeuda.getDinero().getText()));
		usuario.setCapacidadPrestamo(usuario.getCapacidadPrestamo()+Double.parseDouble(this.pagoDeuda.getDinero().getText()));
		if(estado.getDineroAtrasado()==0) {
			estado.setEstado("correcto");
		}
		if(estado.getQuincenasRestantes()==0&&estado.getDineroAtrasado()==0) {
			estadoRepository.eliminarDesdePrestamo(prestamo);
			prestamo.setEstado("concluso");
			actualizador.actualizarReputacionClientes();
		}else {
			estadoRepository.actualizar(estado);
		}
		prestamoRepository.actualizar(prestamo);
		try {
			usuarioRepository.actualizarSinContrasenia(usuario);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
