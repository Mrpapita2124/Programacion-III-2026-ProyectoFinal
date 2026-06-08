package controllers.usuario;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import controllers.cliente.FormularioClienteController;
import vistas.formulario.FormularioGeneralCliente;
import vistas.otros.VentanaPrincipal;
import vistas.usuario.ClientesUsuarioPanel;

public class ClientesUsuarioController {
	ClientesUsuarioPanel clientesPanel;
	
	public ClientesUsuarioController(ClientesUsuarioPanel clientesPanel) {
		this.clientesPanel = clientesPanel;
		VentanaPrincipal ventana= this.clientesPanel.getAncestro();
		
		
		this.clientesPanel.getBtnRegistro().addActionListener(e -> {
			FormularioGeneralCliente formulario=new FormularioGeneralCliente();
			
			FormularioClienteController FormularioController= new FormularioClienteController(formulario);
			formulario.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
	                // Aquí puedes ejecutar lógica en el principal
					ventana.getFiltroUsuarioControlador().refrescarClientesFiltrados();
				}
			});
		});
		
		this.clientesPanel.getBtnFiltro().addActionListener(e -> {
			
			// AQUIII
			ventana.mostrarVista(ventana.FILTROS);
		});
	}
	

}
