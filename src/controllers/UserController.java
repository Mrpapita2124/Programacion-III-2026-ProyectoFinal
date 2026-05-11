package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import modelos.User;
import respository.UserRepository;
import services.PDFExporter;
import tablemodels.UserTableModel;
import views.UserFormDialog;
import views.UsersView;
import views.Ventana;
import views.VentanaPrincipal;

public class UserController {

	private UsersView userView;
	private UserRepository repo;
	private UserTableModel model;
	private VentanaPrincipalController ventanaPrincipalController;
	private PDFExporter pdfExporter;

	
	public UserController(UsersView view, VentanaPrincipalController ventanaController, List<User> users) 
	{
		this.model=new UserTableModel(users);
		this.userView = view;
		this.ventanaPrincipalController = ventanaController;
		
		repo = new UserRepository();
		pdfExporter = new PDFExporter();
		
		
		// Boton Agregar ActionListener
		view.getBtnAdd().addActionListener(e -> {
			
			openForm(null,ventanaController);
			
			try {
				ventanaController.showUsers(); // Solo sirve si uso esto porque cargar users ya esta en openForm().
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		});
		
		
		// Boton Editar ActionListener
		this.userView.getBtnEdit().addActionListener(e -> {
			
			int row = view.getSelectedRow();
			
			if(row == -1) {
				JOptionPane.showMessageDialog(view, "Selecciona un usuario");
				return;
			}
			
			openForm(model.getUserAt(row),ventanaController);

			try {
				ventanaPrincipalController.showUsers();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		
		// Boton Delete ActionListener
		view.getBtnDelete().addActionListener(e -> {
			
			int row = view.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(view, "Selecciona un usuario");
				return;
			}
			
			try 
			{
				handleBorrarPerfil(row);
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		});
		
		this.userView.getBtnPdf().addActionListener(e -> {
			
			generarPdf();
		});
	}
	
	
	private void openForm(User user, VentanaPrincipalController ventanaController) {
		
		int row = userView.getSelectedRow();
		System.out.println(row);
		UserFormDialog dialog;
		if(user==null) {
			dialog = new UserFormDialog(null, user);
			new UserDialogController(dialog);
		}else {
			dialog = new UserFormDialog(null, user);
			new UserDialogController(dialog, user);
		}
		 
		dialog.setVisible(true);
		
		if(dialog.isGuardado()) {
			User savedUser = dialog.getUser();
			
			try {
				if(user == null) {
					repo.save(savedUser);
				}else {
					
					repo.update(row, savedUser);
					
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(userView, e.getMessage());
			}
			
		}
		
		cargarUsers();
	}
	
	
	public void cargarUsers() {	
		
		//System.out.println("Carga usuarios");
		try {
			List<User> users = repo.getUsers();
			
			if(model == null) {
				model = new UserTableModel(users);
				userView.setTableModel(model);
			}else {
				model.setUsers(users);
			}
			
		}catch (IOException ex) {
			JOptionPane.showMessageDialog(userView, ex.getMessage());
		}
	}
	
	public void generarPdf()
	{
		File file = userView.selectPdfFile();
		
		if(file == null) { return; }
		
		try
		{
			pdfExporter.exportUsers(repo.getUsers(), file);
			
			if(Desktop.isDesktopSupported())
			{
				Desktop.getDesktop().open(file);
			}
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(userView, "Error al exportar");
		}
	}
	
	private void handleBorrarPerfil(int row) throws IOException {
		
		int option = userView.confirmExit();

		if (option == JOptionPane.YES_OPTION) 
		{
			repo.delete(row);
			ventanaPrincipalController.showUsers();
		}
	}
	
}



