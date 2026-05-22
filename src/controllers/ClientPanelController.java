package controllers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import repository.ClientRepository;
import views.ClientInfoView;
import views.ClientPanel;
import views.FormularioGeneralCliente;
import views.UserClientsPanel;
import views.VentanaPrincipal;

public class ClientPanelController {
	ClientPanel clientPanel;
	private ClientRepository clientRepository;

	public ClientPanelController(ClientPanel clientPanel,UserClientsPanel userPanel, VentanaPrincipal ventana) {
		clientRepository=new ClientRepository();
		
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
			clientRepository.Delete(this.clientPanel.getClient());
			
			ventana.reload();
			
		});
		this.clientPanel.getBtnInfo().addActionListener(e -> {
			ClientInfoView info = new ClientInfoView(this.clientPanel.getClient());
		});
	}
	public void refresh(VentanaPrincipal ventana) {
		
	}
}
