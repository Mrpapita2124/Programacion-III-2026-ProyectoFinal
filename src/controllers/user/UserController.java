package controllers.user;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import controllers.VentanaPrincipalController;
import modelos.User;
import repository.UserRepository;
import services.PDFExporter;
import tablemodels.UserTableModel;
import views.Ventana;
import views.VentanaPrincipal;
import views.user.UserFormDialog;
import views.user.UsersView;

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
			
			boolean deleted = repo.delete(model.getUserAt(view.getSelectedRow()).getId());
			
			if(deleted) 
			{
				model.removeRow(view.getSelectedRow());
				
				try {
					ventanaPrincipalController.showUsers();
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
			
		});
		
		this.userView.getBtnPdf().addActionListener(e -> {
			
			generarPdf();
		});
	}
	
	
	private void openForm(User user, VentanaPrincipalController ventanaController) {
		
		int row = userView.getSelectedRow();
		System.out.println(row);
		//System.out.println("Opening Form");
		UserFormDialog dialog;
		
		if(user==null) 
		{
			//System.out.println("user null");
			dialog = new UserFormDialog(null, user);
			new UserDialogController(dialog);
		}
		else 
		{
			//System.out.println("user not null");
			dialog = new UserFormDialog(null, user);
			new UserDialogController(dialog, user);
		}
		 
		dialog.setVisible(true);
		
		if(dialog.isGuardado()) {
			User savedUser = dialog.getUser();
			
			try {
				if(user == null) {
					repo.save(savedUser);
					model.addRow(savedUser);
					
					JOptionPane.showMessageDialog(userView.getWindow(), "Se guardo nuevo usuario.", "Confirmado", JOptionPane.INFORMATION_MESSAGE);
					
					//System.out.println("saved");
				}else {
					
					int row1 = userView.getSelectedRow();
					
					boolean updated = repo.update(row1, savedUser);
					if(updated) {
						model.updateRow(row1, savedUser); //Actualiza el registro de la tabla
					}
					
					JOptionPane.showMessageDialog(userView.getWindow(), "Se pudo modificar informacion.", "Confirmado", JOptionPane.INFORMATION_MESSAGE);
					
					//System.out.println("updated");
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(userView, e.getMessage());
			}
			
		}
		
		cargarUsers();
	}
	
	
	public void cargarUsers() {	
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



