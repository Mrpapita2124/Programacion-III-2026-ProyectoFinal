package controllers.user;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import controllers.client.ClientFormController;
import views.VentanaPrincipal;
import views.formulario.FormularioGeneralCliente;
import views.user.UserClientsPanel;

public class UserClientsPanelController {
	UserClientsPanel clientPanel;
	
	public UserClientsPanelController(UserClientsPanel clientPanel) {
		this.clientPanel = clientPanel;
		VentanaPrincipal ventana= this.clientPanel.getAncestro();
		
		
		this.clientPanel.getBtnRegister().addActionListener(e -> {
			FormularioGeneralCliente form=new FormularioGeneralCliente();
			
			ClientFormController controller= new ClientFormController(form);
			form.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
	                // Aquí puedes ejecutar lógica en el principal
					ventana.getFilterViewController().refreshFilteredClientes();
				}
			});
		});
		
		this.clientPanel.getBtnFilter().addActionListener(e -> {
			
			// AQUIII
			ventana.showView(ventana.FILTROS);
		});
	}
	

}
