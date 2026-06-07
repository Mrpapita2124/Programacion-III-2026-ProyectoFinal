package controllers.cliente;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import controllers.prestamo.PrestamoFormController;
import modelos.Cliente;
import modelos.Prestamo;
import repositorios.ClienteRepository;
import repositorios.EstadoPrestamoRepository;
import repositorios.PrestamoRepository;
import vistas.cliente.CartaCliente;
import vistas.cliente.ClientInfoView;
import vistas.formulario.FormularioGeneralCliente;
import vistas.formulario.FormularioGeneralPrestamo;
import vistas.otros.VentanaPrincipal;
import vistas.usuario.ClientesUsuarioPanel;

public class ClientPanelController {
	private CartaCliente clientPanel;
	private ClienteRepository clientRepository;
	private PrestamoRepository prestamoRepository;
	private EstadoPrestamoRepository estadoPrestamoRepository;

	public ClientPanelController(CartaCliente clientPanel,ClientesUsuarioPanel userPanel, VentanaPrincipal ventana) {
		clientRepository=new ClienteRepository();
		prestamoRepository=new PrestamoRepository();
		estadoPrestamoRepository = new EstadoPrestamoRepository();
		this.clientPanel = clientPanel;
		
		this.clientPanel.getBtnEditar().addActionListener(e -> {
			FormularioGeneralCliente form=new FormularioGeneralCliente(this.clientPanel.getCliente());
			
			FormularioClienteController controller= new FormularioClienteController(form);
			form.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
	                // Aquí puedes ejecutar lógica en el principal
					ventana.getFiltroUsuarioControlador().refrescarClientesFiltrados();
	            }
			});
			
		});
		
		this.clientPanel.getBtnEliminar().addActionListener(e -> {
			deleteEverythingFromClient(this.clientPanel.getCliente());
			clientRepository.eliminar(this.clientPanel.getCliente());
			ventana.getFiltroUsuarioControlador().refrescarClientesFiltrados();
			ventana.recargarPrestamos(true);
            ventana.getFilterPrestamoViewController().refrescarPrestamosFiltrados(true);
		});
		this.clientPanel.getBtnInformacion().addActionListener(e -> {
			ClientInfoView info = new ClientInfoView(this.clientPanel.getCliente());
		});
		this.clientPanel.getBtnDinero().addActionListener(e -> {
			FormularioGeneralPrestamo form = new FormularioGeneralPrestamo(ventana,this.clientPanel.getCliente());
			new PrestamoFormController(form);
			form.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
	                // Aquí puedes ejecutar lógica en el principal
					ventana.reload();
	                ventana.recargarPrestamos(true);
	                ventana.getFilterPrestamoViewController().refrescarPrestamosFiltrados(true);
	            }
			});
		});
	}
	private void deleteEverythingFromClient(Cliente client) {
		List<Prestamo> prestamos=prestamoRepository.getPrestamosDeCliente(client);
		for(Prestamo prestamo:prestamos) {
			estadoPrestamoRepository.eliminarDesdePrestamo(prestamo);
		}
		prestamoRepository.eliminarDesdeCliente(client);
		
	}
	
}
