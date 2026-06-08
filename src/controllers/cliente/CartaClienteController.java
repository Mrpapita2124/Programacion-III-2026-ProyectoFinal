package controllers.cliente;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import controllers.prestamo.PrestamoFormController;
import modelos.Cliente;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import modelos.Usuario;
import repositorios.ClienteRepository;
import repositorios.EstadoPrestamoRepository;
import repositorios.PrestamoRepository;
import repositorios.UsuarioRepository;
import utilidades.Actualizador;
import utilidades.Sesion;
import vistas.cliente.CartaCliente;
import vistas.cliente.ClientInfoView;
import vistas.formulario.FormularioGeneralCliente;
import vistas.formulario.FormularioGeneralPrestamo;
import vistas.otros.Ventana;
import vistas.otros.VentanaPrincipal;
import vistas.usuario.ClientesUsuarioPanel;

public class CartaClienteController {
	private CartaCliente cartaCliente;
	private ClienteRepository clientRepository;
	private PrestamoRepository prestamoRepository;
	private EstadoPrestamoRepository estadoPrestamoRepository;
	private UsuarioRepository usuarioRepository;
	private Actualizador actualizador;

	public CartaClienteController(CartaCliente cartaCliente,ClientesUsuarioPanel clientesUsuarioPanel, VentanaPrincipal ventana) {
		actualizador= new Actualizador();
		usuarioRepository= new UsuarioRepository();
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
					ventana.getFiltroClientesControlador().refrescarClientesFiltrados();
	            }
			});
			
		});
		
		this.cartaCliente.getBtnEliminar().addActionListener(e -> {
			int opcion = JOptionPane.showConfirmDialog(
			        ventana,
			        "¿Seguro que deseas eliminar cliente? Se perderán todos los datos",
			        "Eliminar Cliente",
			        JOptionPane.YES_NO_OPTION
			    );
			
			if(opcion == JOptionPane.YES_OPTION)
			{
				deleteEverythingFromClient(this.cartaCliente.getCliente(), ventana);
				clientRepository.eliminar(this.cartaCliente.getCliente());
				ventana.getFiltroClientesControlador().refrescarClientesFiltrados();
				ventana.recargarPrestamos(true);
	            ventana.getFilterPrestamoViewController().refrescarPrestamosFiltrados(true);
			}
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
	private void deleteEverythingFromClient(Cliente client, VentanaPrincipal ventana) {
		List<Prestamo> prestamos=prestamoRepository.getPrestamosDeCliente(client);
		for(Prestamo prestamo:prestamos) {
			//estadoPrestamoRepository.eliminarDesdePrestamo(prestamo);
			eliminarPrestamo(prestamo, ventana);
		}
		//prestamoRepository.eliminarDesdeCliente(client);
		
	}
	private void eliminarPrestamo(Prestamo prestamo, VentanaPrincipal ventana) {
		EstadoPrestamo estadoPrestamo= estadoPrestamoRepository.getEstadoPrestamoDesdePrestamo(prestamo);
		if(estadoPrestamo == null)
		{
		
			prestamoRepository.eliminar(prestamo);
	        ventana.getFilterPrestamoViewController().refrescarPrestamosFiltrados(false);
	        ventana.reload();
	        ventana.recargarPrestamos(true);
	        return;
		}
	
		double dineroGanado=prestamo.getMontoTotal()-estadoPrestamo.getMontoRestante();
		Usuario usuario= Sesion.getusuarioActual();
		usuario.setCapacidadPrestamo(usuario.getCapacidadPrestamo()-dineroGanado);
		usuario.setCapacidadPrestamo(usuario.getCapacidadPrestamo()+prestamo.getMonto());
		estadoPrestamoRepository.eliminarDesdePrestamo(prestamo);
		prestamoRepository.eliminar(prestamo);
		try {
			usuarioRepository.actualizarSinContrasenia(usuario);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actualizador.actualizarReputacionClientes();
		ventana.reload();
		ventana.recargarPrestamos(true);
	}
}
