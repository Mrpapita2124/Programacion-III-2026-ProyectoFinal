package vistas.cliente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controllers.cliente.CartaClienteController;
import controllers.usuario.UsuarioCartaController;
import modelos.Cliente;
import modelos.Usuario;
import modelosTabla.ModeloTablaUsuario;
import repositorios.ClienteRepository;
import repositorios.UsuarioRepository;
import utilidades.Colores;
import utilidades.Fuentes;
import utilidades.PanelPersonalizable;
import utilidades.RoundedBorder;
import utilidades.Sesion;
import vistas.otros.VentanaPrincipal;
import vistas.usuario.ClientesUsuarioPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClienteCartas extends PanelPersonalizable 
{
	private Font fuenteTitulo = Fuentes.setFontSegoe(1,15);
	int alto;
	
	public ClienteCartas(ClientesUsuarioPanel usuarioPanel,VentanaPrincipal ventana) 
	{
		ArrayList<JPanel> cartasClientes = new ArrayList<JPanel>();
		
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
		
		List<Cliente> clientes = Sesion.getClientesFiltrados(); 
		
		
		if (clientes == null || clientes.isEmpty()) 
		{
			String mensaje = clientes == null ? "No se encontraron clientes con Filtro " : "No tienes clientes";
			JLabel voidMensaje = new JLabel(mensaje); 
			
			voidMensaje.setOpaque(false);
			voidMensaje.setFont(fuenteTitulo);
			voidMensaje.setForeground(Color.WHITE);
			voidMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
			alto=300;
			
			add(Box.createRigidArea(new Dimension(0, 250)));
			add(voidMensaje);
			
		}else {
			alto=0;
			//define alto
			if(clientes.size()%2 == 0) {
				
				alto=(clientes.size()/2) * 300;
			}else {
				alto=((clientes.size()/2)+1) * 300;
			}
			
			//Añade usuarios simullados"
			for(int i=0;i<clientes.size();i++) {
				
				PanelPersonalizable carta = new PanelPersonalizable();
				carta.setBackground(Colores.CLIENTE_CARTA_FONDO);
				carta.setBorder(new RoundedBorder(Colores.BORDE_POR_DEFECTO, 20, 2));
				carta.setOpaque(true);
				
				CartaCliente clientePaneles = new CartaCliente(clientes.get(i));
				new CartaClienteController(clientePaneles,usuarioPanel,ventana);
				clientePaneles.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
				
				carta.add(clientePaneles);
				cartasClientes.add(carta);
			}
			
			setLayout(new GridLayout(0,2, 15, 15));
			
			
			for(JPanel carta : cartasClientes) {
				
				add(carta);
			}
			if(clientes.size()%2!=0) {
				
				JPanel relleno = new JPanel();
				relleno.setBackground(new Color(0, 0, 0, 0));
	            relleno.setOpaque(false);
	            add(relleno);
			}
		}
			
			setVisible(true);
	}
	
	public int getAlto() {
		return alto;
	}
	public void setAlto(int alto) {
		this.alto = alto;
	}
	
}