package controllers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import modelos.Client;
import modelos.Prestamo;
import repository.ClientRepository;
import repository.EstadoPrestamoRepository;
import repository.PrestamoRepository;
import views.ClientInfoView;
import views.ClientPanel;
import views.FormularioGeneralCliente;
import views.FormularioGeneralPrestamo;
import views.UserClientsPanel;
import views.VentanaPrincipal;

public class ClientPanelController {
	private ClientPanel clientPanel;
	private ClientRepository clientRepository;
	private PrestamoRepository prestamoRepository;
	private EstadoPrestamoRepository estadoPrestamoRepository;

	public ClientPanelController(ClientPanel clientPanel,UserClientsPanel userPanel, VentanaPrincipal ventana) {
		clientRepository=new ClientRepository();
		prestamoRepository=new PrestamoRepository();
		estadoPrestamoRepository = new EstadoPrestamoRepository();
		this.clientPanel = clientPanel;
		this.clientPanel.getBtnEdit().addActionListener(e -> {
			FormularioGeneralCliente form=new FormularioGeneralCliente(this.clientPanel.getClient());
			
			ClientFormController controller= new ClientFormController(form);
			form.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
	                // Aquí puedes ejecutar lógica en el principal
	                ventana.reload();
	            }
			});
			
		});
		
		this.clientPanel.getBtnDelete().addActionListener(e -> {
			deleteEverythingFromClient(this.clientPanel.getClient());
			clientRepository.Delete(this.clientPanel.getClient());
			ventana.reloadPrestamos(false);
			ventana.reload();
			
		});
		this.clientPanel.getBtnInfo().addActionListener(e -> {
			ClientInfoView info = new ClientInfoView(this.clientPanel.getClient());
		});
		this.clientPanel.getBtnMoney().addActionListener(e -> {
			FormularioGeneralPrestamo form = new FormularioGeneralPrestamo(ventana,this.clientPanel.getClient());
			new PrestamoFormController(form);
			form.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
	                // Aquí puedes ejecutar lógica en el principal
	                ventana.reloadPrestamos(true);
	            }
			});
		});
	}
	private void deleteEverythingFromClient(Client client) {
		List<Prestamo> prestamos=prestamoRepository.getClientPrestamos(client);
		for(Prestamo prestamo:prestamos) {
			estadoPrestamoRepository.deleteFromPrestamo(prestamo);
		}
		prestamoRepository.deleteFromCliente(client);
		
	}
	
}
