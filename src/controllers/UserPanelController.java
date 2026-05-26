package controllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import utils.Session;
import utils.Updater;
import views.UserPanel;
import views.VentanaPrincipal;

public class UserPanelController {
	UserPanel userPanel;
	public UserPanelController(UserPanel userPanel) {
		this.userPanel=userPanel;
		this.userPanel.getSessionButton().addActionListener(e -> {
			Session.login(this.userPanel.getUser());
			Updater update= new Updater();
			update.updateEverything();
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
