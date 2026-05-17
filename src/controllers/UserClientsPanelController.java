package controllers;

import views.FormularioGeneralCliente;
import views.UserClientsPanel;

public class UserClientsPanelController {
	UserClientsPanel clientPanel;

	public UserClientsPanelController(UserClientsPanel clientPanel) {
		this.clientPanel = clientPanel;
		this.clientPanel.getBtnRegister().addActionListener(e -> {
			
			ClientFormController controller= new ClientFormController(new FormularioGeneralCliente());
		});
	}
	

}
