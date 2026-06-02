package controllers;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import config.Config;
import modelos.User;
import repository.UserRepository;
import tablemodels.UserTableModel;
import views.Ventana;
import views.VentanaPrincipal;

public class VentanaPrincipalController {
	UserRepository repository;
	UserController controller;
	private VentanaPrincipal ventanaPrincipal;
	
	public VentanaPrincipalController(VentanaPrincipal ventanaPrincipal) throws IOException {
		
		repository = new UserRepository();
		controller = new UserController(ventanaPrincipal.getUsersPanel(), this,repository.getUsers());
		this.ventanaPrincipal = ventanaPrincipal;
		
		cargarWindowPreferences();
		registerListeners();
		
	}
	
	public void registerListeners( ) {
		
		ventanaPrincipal.getmItemExit().addActionListener(e -> handleClose());
		
		ventanaPrincipal.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new Ventana();
				guardarWindowPreferences();
				ventanaPrincipal.dispose();
			}
		});
		
		//ventanaPrincipal.getBtnPrestamo().addActionListener(e -> ventanaPrincipal.showView(ventanaPrincipal.PRESTAMOS));
		
		ventanaPrincipal.getBtnUsers().addActionListener(e -> {
			try {
				showUsers();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		ventanaPrincipal.getBtnHome().addActionListener(e -> ventanaPrincipal.showView(ventanaPrincipal.HOME));
		
	}
	
	public void showUsers() throws IOException {
		
		try {
			List<User> users = repository.getUsers();
			
			UserTableModel model = new UserTableModel(users);
			
			ventanaPrincipal.getUsersPanel().setTableModel(model);
			
			//System.out.println("Refresh Table");
			ventanaPrincipal.showView(ventanaPrincipal.USERS);
			
		}catch (IOException ex) {
			JOptionPane.showMessageDialog(ventanaPrincipal, ex.getMessage());
		}
		
	}
	
	
	
	private void guardarWindowPreferences() {
		Dimension size = ventanaPrincipal.getSize();
		Point point = ventanaPrincipal.getLocation();
		
		Config.set("registration.window.width", 
				String.valueOf(size.width));
		
		Config.set("registration.window.height", 
				String.valueOf(size.height));
		
		Config.set("registration.window.x", 
				String.valueOf(point.x));
		
		Config.set("registration.window.y", 
				String.valueOf(point.y));
		
	}
	
	private void cargarWindowPreferences()
	{
		int width = Integer.parseInt(
				Config.get("registration.window.width"
						, "500"));
		
		int height = Integer.parseInt(
				Config.get("registration.window.height"
						, "500"));
		
		String xValue = Config.get("registration.window.x"
						, "");
		
		String yValue = Config.get("registration.window.y"
				, "");
		
		if(!xValue.isBlank() && !yValue.isBlank()) {
			ventanaPrincipal.setWindowLocation(Integer.parseInt(xValue), Integer.parseInt(yValue));
		}else {
			ventanaPrincipal.setLocationRelativeTo(null);
		}
		
		ventanaPrincipal.setWindowSize(width, height);
	}
	
	
	private void handleClose() {
		int option = ventanaPrincipal.confirmExit();
		System.out.println(option);

		if (option == JOptionPane.YES_OPTION) {
			new Ventana();
			ventanaPrincipal.dispose();
		}
	}

	public VentanaPrincipal getVentanaPrincipal() {
		return ventanaPrincipal;
	}

	public void setVentanaPrincipal(VentanaPrincipal ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
	}
	
	
}