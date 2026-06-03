package controllers;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;

import exceptions.InvalidNumberException;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import modelos.User;
import repository.EstadoPrestamoRepository;
import repository.PrestamoRepository;
import repository.UserRepository;
import utils.Colores;
import utils.Session;
import utils.Updater;
import views.PayDebtView;

public class PayDebtController {
	private PayDebtView payDebtView;
	private EstadoPrestamoRepository estadoRepository;
	private PrestamoRepository prestamoRepository;
	private UserRepository userRepository;
	private Updater updater;
	public PayDebtController(PayDebtView payDebtView) {
		updater=new Updater();
		userRepository= new UserRepository();
		estadoRepository= new EstadoPrestamoRepository();
		prestamoRepository= new PrestamoRepository();
		this.payDebtView = payDebtView;
		payDebtView.getBtnPay().addActionListener(e -> {
			if(validarMonto()) {
				pagarMonto();
		    	this.payDebtView.dispose();
		    	this.payDebtView.getVentana().reloadPrestamos(true);
			}
		});
		this.payDebtView.getMoney().addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				payDebtView.resetearErrorLabels();
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
    	String monto = payDebtView.getMoney().getText().trim();
    	
    	if (monto.isEmpty() || monto.equals("Monto")) 
    	{
    		payDebtView.getLblErrorMoney().setText("El monto es obligatorio");
			return false;
		} else {
			try {
				doubleMultipleExceptions(monto);
				
				payDebtView.getLblErrorMoney().setText("");
			} catch (Exception e) {
				// TODO: handle exception
				payDebtView.getLblErrorMoney().setText(e.getMessage());
				return false;
			}
			
		}
    	
		return true;
    }
	public void doubleMultipleExceptions(String numero) throws InvalidNumberException {
    	if(!numero.matches("[0-9.]*")) {
    		throw new InvalidNumberException("Solo se permites numeros y punto decimal");
    	}
    	if(this.payDebtView.getEstadoPrestamo().getDinero_atrasado()<Double.parseDouble(this.payDebtView.getMoney().getText())) {
    		throw new InvalidNumberException("Esta ingresando mas dinero que el debido");
    	}
    }
	public void pagarMonto(){
		User user= Session.getCurrentUser();
		EstadoPrestamo estado=this.payDebtView.getEstadoPrestamo();
		Prestamo prestamo=this.payDebtView.getPrestamo();
		estado.setDinero_atrasado(estado.getDinero_atrasado()-Double.parseDouble(this.payDebtView.getMoney().getText()));
		estado.setMonto_restante(estado.getMonto_restante()-Double.parseDouble(this.payDebtView.getMoney().getText()));
		user.setCapacidadPrestamo(user.getCapacidadPrestamo()+Double.parseDouble(this.payDebtView.getMoney().getText()));
		if(estado.getDinero_atrasado()==0) {
			estado.setEstado("correcto");
		}
		if(estado.getQuincenasRestantes()==0) {
			estadoRepository.deleteFromPrestamo(prestamo);
			
			prestamo.setEstado("concluso");
			updater.updateClientsEvaluation();
		}else {
			estadoRepository.update(estado);
		}
		prestamoRepository.update(prestamo);
		try {
			userRepository.update(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
