package controllers.usuario;

import java.io.IOException;

import javax.swing.JOptionPane;

import controllers.VentanaPrincipalController;
import repositorios.ClienteRepository;
import repositorios.PrestamoRepository;
import utilidades.Actualizador;
import utilidades.Sesion;
import vistas.otros.VentanaPrincipal;
import vistas.usuario.UsuarioCarta;

public class UsuarioCartaController {
	UsuarioCarta usuarioCarta;
	ClienteRepository clienteRepository;
	public UsuarioCartaController(UsuarioCarta usuarioCarta) {
		clienteRepository= new ClienteRepository();
		this.usuarioCarta=usuarioCarta;
		this.usuarioCarta.getSessionButton().addActionListener(e -> {
			Sesion.login(this.usuarioCarta.getUser());
			Sesion.setClientesFiltrados(clienteRepository.getClients());
			Actualizador actualizador= new Actualizador();
			actualizador.actualizarPrestamos();
			actualizador.actualizarReputacionClientes();
			if(Sesion.getRol().toLowerCase().equals("admin")) 
			{
				JOptionPane.showMessageDialog(this.usuarioCarta.getWindow(), "Se inició la sesión con cuenta 'ADMIN'", " Sesión iniciada", JOptionPane.INFORMATION_MESSAGE);
				try {
					new VentanaPrincipalController(new VentanaPrincipal());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
				this.usuarioCarta.getWindow().dispose();
			}
			else if(Sesion.getRol().toLowerCase().equals("comun")) 
			{
				JOptionPane.showMessageDialog(this.usuarioCarta.getWindow(), "Se inició la sesión con cuenta 'COMUN'", " Sesión iniciada", JOptionPane.INFORMATION_MESSAGE);
				try {
					new VentanaPrincipalController(new VentanaPrincipal());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
				this.usuarioCarta.getWindow().dispose();
			}
		});
	}
}
