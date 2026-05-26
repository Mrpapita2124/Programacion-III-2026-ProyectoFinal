package controllers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import modelos.Client;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import modelos.User;
import repository.ClientRepository;
import repository.EstadoPrestamoRepository;
import repository.PrestamoRepository;
import repository.UserRepository;
import utils.Session;
import views.ClientInfoView;
import views.ClientPanel;
import views.FormularioGeneralCliente;
import views.FormularioGeneralPrestamo;
import views.PayDebtView;
import views.PrestamoPanel;
import views.UserClientsPanel;
import views.VentanaPrincipal;

public class PrestamoPanelController {
	private PrestamoPanel prestamoPanel;
	private ClientRepository clientRepository;
	private PrestamoRepository prestamoRepository;
	private EstadoPrestamoRepository estadoPrestamoRepository;
	private UserRepository userRepository;
	private EstadoPrestamo estadoPrestamo;
	private VentanaPrincipal ventana;

	public PrestamoPanelController(PrestamoPanel prestamoPanel, VentanaPrincipal ventana) {
		this.ventana=ventana;
		userRepository=new UserRepository();
		clientRepository=new ClientRepository();
		prestamoRepository=new PrestamoRepository();
		estadoPrestamoRepository = new EstadoPrestamoRepository();
		this.prestamoPanel = prestamoPanel;
		estadoPrestamo=estadoPrestamoRepository.getEstadoPrestamoFromPrestamo(this.prestamoPanel.getPrestamo());
		
		this.prestamoPanel.getBtnComplete().addActionListener(e -> {
			completarPrestamo(this.prestamoPanel.getPrestamo());
		});
		
		this.prestamoPanel.getBtnDelete().addActionListener(e -> {
			deletePrestamo(this.prestamoPanel.getPrestamo());
		});
		this.prestamoPanel.getBtnInfo().addActionListener(e -> {
		});
		this.prestamoPanel.getBtnDebt().addActionListener(e -> new PayDebtController(new PayDebtView(estadoPrestamo,ventana,this.prestamoPanel.getPrestamo())));
	}
	private void deletePrestamo(Prestamo prestamo) {
		double earned=prestamo.getMonto_total()-estadoPrestamo.getMonto_restante();
		User user= Session.getCurrentUser();
		user.setCapacidadPrestamo(user.getCapacidadPrestamo()-earned);
		user.setCapacidadPrestamo(user.getCapacidadPrestamo()+prestamo.getMonto());
		estadoPrestamoRepository.deleteFromPrestamo(prestamo);
		prestamoRepository.delete(prestamo);
		try {
			userRepository.update(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.ventana.reloadPrestamos(true);
	}
	private void completarPrestamo(Prestamo prestamo) {
		
		if(estadoPrestamo.getQuincenasRestantes()>1) {
			estadoPrestamo.setQuincenasRestantes(estadoPrestamo.getQuincenasRestantes()-1);
			estadoPrestamo.setFecha_proximo_pago(Date.valueOf(estadoPrestamo.getFecha_proximo_pago().toLocalDate().plusDays(15)));
			estadoPrestamo.setMonto_restante(estadoPrestamo.getMonto_restante()-prestamo.getMonto_quincenal());
			estadoPrestamoRepository.update(estadoPrestamo);
			User user= Session.getCurrentUser();
			user.setCapacidadPrestamo(user.getCapacidadPrestamo()+prestamo.getMonto_quincenal());
			try {
				userRepository.update(user);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			estadoPrestamo.setQuincenasRestantes(estadoPrestamo.getQuincenasRestantes()-1);
			estadoPrestamo.setMonto_restante(estadoPrestamo.getMonto_restante()-prestamo.getMonto_quincenal());
			estadoPrestamoRepository.update(estadoPrestamo);
			User user= Session.getCurrentUser();
			user.setCapacidadPrestamo(user.getCapacidadPrestamo()+prestamo.getMonto_quincenal());
			try {
				userRepository.update(user);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(estadoPrestamo.getEstado().equals("atrasado")) {
				JOptionPane.showMessageDialog(ventana, "Quincenas concluidas", " Solicita al cliente que pague lo que falta", JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(ventana, "Prestamo cocnluido", " Felicidades, has concluido por completo tu prestamo", JOptionPane.INFORMATION_MESSAGE);
				estadoPrestamoRepository.deleteFromPrestamo(prestamo);
				prestamo.setEstado("concluso");
				prestamoRepository.update(prestamo);
			}
		}
		ventana.reloadPrestamos(true);
	}
	
}
