package controllers;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.cj.Session;

import config.Config;
import controllers.usuario.UsuarioController;
import controllers.usuario.FormularioUsuarioDialogController;
import modelos.Usuario;
import modelosTabla.ModeloTablaUsuario;
import repositorios.UsuarioRepository;
import utilidades.Sesion;
import vistas.otros.Ventana;
import vistas.otros.VentanaPrincipal;
import vistas.usuario.FormularioUsuarioDialog;

public class VentanaPrincipalController {
	UsuarioRepository usuarioRepository;
	UsuarioController usuarioController;
	private VentanaPrincipal ventanaPrincipal;
	
	public VentanaPrincipalController(VentanaPrincipal ventanaPrincipal) throws IOException {
		
		usuarioRepository = new UsuarioRepository();
		usuarioController = new UsuarioController(ventanaPrincipal.getUsuariosPanel(), this, usuarioRepository.getUsuariosComunes());
		this.ventanaPrincipal = ventanaPrincipal;
		
		cargarPreferenciasVentana();
		registrarListeners();
		
	}
	
	public void registrarListeners( ) {
		
		ventanaPrincipal.getmItemSalida().addActionListener(e -> handleClose());
		
		ventanaPrincipal.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new Ventana();
				guardarPreferenciasVentana();
				ventanaPrincipal.dispose();
			}
		});
		ventanaPrincipal.getBtnInicio().addActionListener(e -> ventanaPrincipal.mostrarVista(ventanaPrincipal.HOME));
		
		if(Sesion.getRol().equals("admin")) {
			ventanaPrincipal.getBtnUsuarios().addActionListener(e -> {
				try {
					mostrarUsuarios();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}else {
			//System.out.println("jala?");
			ventanaPrincipal.getBtnUsuarios().addActionListener(e -> {
				FormularioUsuarioDialog dialog = new FormularioUsuarioDialog(null, Sesion.getusuarioActual());
				new FormularioUsuarioDialogController(dialog, Sesion.getusuarioActual());
				dialog.setVisible(true);
			});
		}
		
		
	}
	
	public void mostrarUsuarios() throws IOException {
		
		try {
			List<Usuario> usuarios = usuarioRepository.getUsuariosComunes();
			
			ModeloTablaUsuario modeloTabla = new ModeloTablaUsuario(usuarios);
			
			ventanaPrincipal.getUsuariosPanel().setTableModel(modeloTabla);
			
			//System.out.println("Refresh Table");
			ventanaPrincipal.mostrarVista(ventanaPrincipal.USERS);
			
		}catch (IOException ex) {
			JOptionPane.showMessageDialog(ventanaPrincipal, ex.getMessage());
		}
		
	}
	
	
	
	private void guardarPreferenciasVentana() {
		Dimension tamanio = ventanaPrincipal.getSize();
		Point point = ventanaPrincipal.getLocation();
		
		Config.set("registration.window.width", 
				String.valueOf(tamanio.width));
		
		Config.set("registration.window.height", 
				String.valueOf(tamanio.height));
		
		Config.set("registration.window.x", 
				String.valueOf(point.x));
		
		Config.set("registration.window.y", 
				String.valueOf(point.y));
		
	}
	
	private void cargarPreferenciasVentana()
	{
		int ancho = Integer.parseInt(
				Config.get("registration.window.width"
						, "1382"));
		
		int alto = Integer.parseInt(
				Config.get("registration.window.height"
						, "784"));
		
		String valorX = Config.get("registration.window.x"
						, "");
		
		String valorY = Config.get("registration.window.y"
				, "");
		
		if(!valorX.isBlank() && !valorY.isBlank()) {
			ventanaPrincipal.setUbicacionVentana(Integer.parseInt(valorX), Integer.parseInt(valorY));
		}else {
			ventanaPrincipal.setLocationRelativeTo(null);
		}
		
		ventanaPrincipal.setTamanioVentana(ancho, alto);
	}
	
	
	private void handleClose() {
		int option = ventanaPrincipal.confirmarSalir();
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