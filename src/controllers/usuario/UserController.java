package controllers.usuario;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import controllers.VentanaPrincipalController;
import modelos.Usuario;
import modelosTabla.ModeloTablaUsuario;
import repositorios.UsuarioRepository;
import services.PDFExporter;
import vistas.otros.Ventana;
import vistas.otros.VentanaPrincipal;
import vistas.usuario.FormularioUsuarioDialog;
import vistas.usuario.UsuariosTablaVista;

public class UserController {

	private UsuariosTablaVista usuarioTablaVista;
	private UsuarioRepository usuarioRepository;
	private ModeloTablaUsuario usuarioTablaModelo;
	private VentanaPrincipalController ventanaPrincipalController;
	private PDFExporter pdfExporter;

	
	public UserController(UsuariosTablaVista usuarioTablaVista, VentanaPrincipalController ventanaController, List<Usuario> usuarios) 
	{
		this.usuarioTablaModelo=new ModeloTablaUsuario(usuarios);
		this.usuarioTablaVista = usuarioTablaVista;
		this.ventanaPrincipalController = ventanaController;
		
		usuarioRepository = new UsuarioRepository();
		pdfExporter = new PDFExporter();
		
		
		// Boton Agregar ActionListener
		usuarioTablaVista.getBtnAdd().addActionListener(e -> {
			
			openForm(null,ventanaController);
			
			try {
				ventanaController.mostrarUsuarios(); // Solo sirve si uso esto porque cargar users ya esta en openForm().
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		});
		
		
		// Boton Editar ActionListener
		this.usuarioTablaVista.getBtnEdit().addActionListener(e -> {
			
			int row = usuarioTablaVista.getSelectedRow();
			
			if(row == -1) {
				JOptionPane.showMessageDialog(usuarioTablaVista, "Selecciona un usuario");
				return;
			}
			
			openForm(usuarioTablaModelo.getUserAt(row),ventanaController);

			try {
				ventanaPrincipalController.mostrarUsuarios();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		
		// Boton Delete ActionListener
		usuarioTablaVista.getBtnDelete().addActionListener(e -> {
			
			int row = usuarioTablaVista.getSelectedRow();
			
			if(row == -1) {
				JOptionPane.showMessageDialog(usuarioTablaVista, "Selecciona un usuario");
				return;
			}
			
			boolean deleted = usuarioRepository.eliminar(usuarioTablaModelo.getUserAt(usuarioTablaVista.getSelectedRow()).getId());
			
			if(deleted) 
			{
				usuarioTablaModelo.removeRow(usuarioTablaVista.getSelectedRow());
				
				try {
					ventanaPrincipalController.mostrarUsuarios();
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
			
		});
		
		this.usuarioTablaVista.getBtnPdf().addActionListener(e -> {
			
			generarPdf();
		});
	}
	
	
	private void openForm(Usuario usuario, VentanaPrincipalController ventanaController) {
		
		int fila = usuarioTablaVista.getSelectedRow();
		System.out.println(fila);
		//System.out.println("Opening Form");
		FormularioUsuarioDialog dialog;
		
		if(usuario==null) 
		{
			//System.out.println("user null");
			dialog = new FormularioUsuarioDialog(null, usuario);
			new UserDialogController(dialog);
		}
		else 
		{
			//System.out.println("user not null");
			dialog = new FormularioUsuarioDialog(null, usuario);
			new UserDialogController(dialog, usuario);
		}
		 
		dialog.setVisible(true);
		
		if(dialog.isGuardado()) {
			Usuario usuarioGuardado = dialog.getUser();
			
			try {
				if(usuario == null) {
					usuarioRepository.guardar(usuarioGuardado);
					usuarioTablaModelo.addRow(usuarioGuardado);
					
					JOptionPane.showMessageDialog(usuarioTablaVista.getWindow(), "Se guardo nuevo usuario.", "Confirmado", JOptionPane.INFORMATION_MESSAGE);
					
					//System.out.println("saved");
				}else {
					
					int fila1 = usuarioTablaVista.getSelectedRow();
					
					boolean actualizado = usuarioRepository.actualizar(fila1, usuarioGuardado);
					if(actualizado) {
						usuarioTablaModelo.updateRow(fila1, usuarioGuardado); //Actualiza el registro de la tabla
					}
					
					JOptionPane.showMessageDialog(usuarioTablaVista.getWindow(), "Se pudo modificar informacion.", "Confirmado", JOptionPane.INFORMATION_MESSAGE);
					
					//System.out.println("updated");
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(usuarioTablaVista, e.getMessage());
			}
			
		}
		
		cargarUsuarios();
	}
	
	
	public void cargarUsuarios() {	
		try {
			List<Usuario> usuarios = usuarioRepository.getUsuarios();
			
			if(usuarioTablaModelo == null) {
				usuarioTablaModelo = new ModeloTablaUsuario(usuarios);
				usuarioTablaVista.setTableModel(usuarioTablaModelo);
			}else {
				usuarioTablaModelo.setUsers(usuarios);
			}
			
		}catch (IOException ex) {
			JOptionPane.showMessageDialog(usuarioTablaVista, ex.getMessage());
		}
	}
	
	public void generarPdf()
	{
		File archivo = usuarioTablaVista.selectPdfFile();
		
		if(archivo == null) { return; }
		
		try
		{
			 pdfExporter.exportUsers(usuarioRepository.getUsuarios(), archivo); 
			
			if(Desktop.isDesktopSupported())
			{
				Desktop.getDesktop().open(archivo);
			}
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(usuarioTablaVista, "Error al exportar");
		}
	}
	
	private void manejarEliminarPerfil(int row) throws IOException {
		
		int option = usuarioTablaVista.confirmExit();

		if (option == JOptionPane.YES_OPTION) 
		{
			usuarioRepository.eliminar(row);
			ventanaPrincipalController.mostrarUsuarios();
		}
	}
	
}



