package controllers.prestamo;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import controllers.PagoDeudaController;
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
import vistas.otros.PagoDeuda;
import vistas.otros.PrestamoInfoPanel;
import vistas.otros.VentanaPrincipal;
import vistas.usuario.ClientesUsuarioPanel;

public class PrestamoPanelController {
	private PrestamoInfoPanel prestamoPanel;
	private PrestamoRepository prestamoRepository;
	private EstadoPrestamoRepository estadoPrestamoRepository;
	private UsuarioRepository usuarioRepository;
	private EstadoPrestamo estadoPrestamo;
	private VentanaPrincipal ventana;
	private Actualizador actualizador;

	public PrestamoPanelController(PrestamoInfoPanel prestamoPanel, VentanaPrincipal ventana) {
		actualizador= new Actualizador();
		this.ventana=ventana;
		usuarioRepository=new UsuarioRepository();
		prestamoRepository=new PrestamoRepository();
		estadoPrestamoRepository = new EstadoPrestamoRepository();
		this.prestamoPanel = prestamoPanel;
		estadoPrestamo=estadoPrestamoRepository.getEstadoPrestamoDesdePrestamo(this.prestamoPanel.getPrestamo());
		
		this.prestamoPanel.getBtnCompletar().addActionListener(e -> {
			completarPrestamo(this.prestamoPanel.getPrestamo());
			
		});
		
		this.prestamoPanel.getBtnEliminar().addActionListener(e -> {
			eliminarPrestamo(this.prestamoPanel.getPrestamo());
		});
		this.prestamoPanel.getBtnInformacion().addActionListener(e -> {
		});
		this.prestamoPanel.getBtnDeuda().addActionListener(e -> new PagoDeudaController(new PagoDeuda(estadoPrestamo,ventana,this.prestamoPanel.getPrestamo())));
	}
	
	
	private void eliminarPrestamo(Prestamo prestamo) {
		double dineroGanado=prestamo.getMontoTotal()-estadoPrestamo.getMontoRestante();
		Usuario usuario= Sesion.getusuarioActual();
		usuario.setCapacidadPrestamo(usuario.getCapacidadPrestamo()-dineroGanado);
		usuario.setCapacidadPrestamo(usuario.getCapacidadPrestamo()+prestamo.getMonto());
		estadoPrestamoRepository.eliminarDesdePrestamo(prestamo);
		prestamoRepository.eliminar(prestamo);
		try {
			usuarioRepository.actualizar(usuario);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actualizador.actualizarReputacionClientes();
		this.ventana.reload();
		this.ventana.recargarPrestamos(true);
	}
	
	private void completarPrestamo(Prestamo prestamo) {
		
		if(estadoPrestamo.getQuincenasRestantes()>1) {
			estadoPrestamo.setQuincenasRestantes(estadoPrestamo.getQuincenasRestantes()-1);
			estadoPrestamo.setFechaProximoPago(Date.valueOf(estadoPrestamo.getFechaProximoPago().toLocalDate().plusDays(15)));
			estadoPrestamo.setMontoRestante(estadoPrestamo.getMontoRestante()-prestamo.getMontoQuincenal());
			estadoPrestamoRepository.actualizar(estadoPrestamo);
			Usuario usuario= Sesion.getusuarioActual();
			usuario.setCapacidadPrestamo(usuario.getCapacidadPrestamo()+prestamo.getMontoQuincenal());
			try {
				usuarioRepository.actualizar(usuario);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			estadoPrestamo.setQuincenasRestantes(estadoPrestamo.getQuincenasRestantes()-1);
			estadoPrestamo.setMontoRestante(estadoPrestamo.getMontoRestante()-prestamo.getMontoQuincenal());
			estadoPrestamoRepository.actualizar(estadoPrestamo);
			Usuario usuario= Sesion.getusuarioActual();
			usuario.setCapacidadPrestamo(usuario.getCapacidadPrestamo()+prestamo.getMontoQuincenal());
			try {
				usuarioRepository.actualizar(usuario);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(estadoPrestamo.getEstado().equals("atrasado")) {
				JOptionPane.showMessageDialog(ventana, "Quincenas concluidas", " Solicita al cliente que pague lo que falta", JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(ventana, "Prestamo cocnluido", " Felicidades, has concluido por completo tu prestamo", JOptionPane.INFORMATION_MESSAGE);
				estadoPrestamoRepository.eliminarDesdePrestamo(prestamo);
				prestamo.setEstado("concluso");
				prestamoRepository.actualizar(prestamo);
			}
		}
		actualizador.actualizarReputacionClientes();
		ventana.reload();
		ventana.recargarPrestamos(true);
	}
	
}
