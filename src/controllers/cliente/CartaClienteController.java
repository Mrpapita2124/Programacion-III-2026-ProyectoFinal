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

public class CartaClienteController {
	private CartaCliente cartaCliente;
	private ClienteRepository clientRepository;
	private PrestamoRepository prestamoRepository;
	private EstadoPrestamoRepository estadoPrestamoRepository;

	public CartaClienteController(CartaCliente cartaCliente,ClientesUsuarioPanel clientesUsuarioPanel, VentanaPrincipal ventana) {
		clientRepository=new ClienteRepository();
		prestamoRepository=new PrestamoRepository();
		estadoPrestamoRepository = new EstadoPrestamoRepository();
		this.cartaCliente = cartaCliente;
		
		this.cartaCliente.getBtnEditar().addActionListener(e -> {
			FormularioGeneralCliente form=new FormularioGeneralCliente(this.cartaCliente.getCliente());
			
			FormularioClienteController controller= new FormularioClienteController(form);
			form.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent e) {
	                // Aquí puedes ejecutar lógica en el principal
					ventana.getFiltroUsuarioControlador().refrescarClientesFiltrados();
	            }
			});
			
		});
		
		this.cartaCliente.getBtnEliminar().addActionListener(e -> {
			deleteEverythingFromClient(this.cartaCliente.getCliente());
			clientRepository.eliminar(this.cartaCliente.getCliente());
			ventana.getFiltroUsuarioControlador().refrescarClientesFiltrados();
			ventana.recargarPrestamos(true);
            ventana.getFilterPrestamoViewController().refrescarPrestamosFiltrados(true);
		});
		this.cartaCliente.getBtnInformacion().addActionListener(e -> {
			ClientInfoView info = new ClientInfoView(this.cartaCliente.getCliente());
		});
		this.cartaCliente.getBtnDinero().addActionListener(e -> {
			FormularioGeneralPrestamo form = new FormularioGeneralPrestamo(ventana,this.cartaCliente.getCliente());
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
