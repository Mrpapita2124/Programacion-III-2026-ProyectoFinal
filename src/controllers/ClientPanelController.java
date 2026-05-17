package controllers;

import repository.ClientRepository;
import views.ClientPanel;
import views.FormularioGeneralCliente;

public class ClientPanelController {
	ClientPanel clientPanel;
	private ClientRepository clientRepository;

	public ClientPanelController(ClientPanel clientPanel) {
		clientRepository=new ClientRepository();
		this.clientPanel = clientPanel;
		this.clientPanel.getBtnEdit().addActionListener(e -> {
			ClientFormController controller= new ClientFormController(new FormularioGeneralCliente(clientPanel.getClient()));
		});
		
		this.clientPanel.getBtnDelete().addActionListener(e -> {
			clientRepository.Delete(clientPanel.getClient());
		});
	}
	
}
