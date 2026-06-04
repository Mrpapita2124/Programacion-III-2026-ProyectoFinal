package controllers.user;

import java.io.IOException;

import javax.swing.JOptionPane;

import controllers.VentanaPrincipalController;
import repository.ClientRepository;
import repository.PrestamoRepository;
import utils.Session;
import utils.Updater;
import views.VentanaPrincipal;
import views.user.UserPanel;

public class UserPanelController {
	UserPanel userPanel;
	ClientRepository clientRepository;
	public UserPanelController(UserPanel userPanel) {
		clientRepository= new ClientRepository();
		this.userPanel=userPanel;
		this.userPanel.getSessionButton().addActionListener(e -> {
			Session.login(this.userPanel.getUser());
			Session.setClientesFiltrados(clientRepository.getClients());
			Updater update= new Updater();
			update.updateEverything();
			update.updateClientsEvaluation();
			if(Session.getRol().toLowerCase().equals("admin")) 
			{
				JOptionPane.showMessageDialog(this.userPanel.getWindow(), "Se inició la sesión con cuenta 'ADMIN'", " Sesión iniciada", JOptionPane.INFORMATION_MESSAGE);
				try {
					new VentanaPrincipalController(new VentanaPrincipal());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
				this.userPanel.getWindow().dispose();
			}
			else if(Session.getRol().toLowerCase().equals("comun")) 
			{
				JOptionPane.showMessageDialog(this.userPanel.getWindow(), "Se inició la sesión con cuenta 'COMUN'", " Sesión iniciada", JOptionPane.INFORMATION_MESSAGE);
				try {
					new VentanaPrincipalController(new VentanaPrincipal());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
				this.userPanel.getWindow().dispose();
			}
		});
	}
}
