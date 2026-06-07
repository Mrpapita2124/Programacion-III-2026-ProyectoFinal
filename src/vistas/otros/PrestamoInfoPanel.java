package vistas.otros;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Window;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.util.Graphics2DProxy;

import modelos.Cliente;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import modelos.Usuario;
import repositorios.ClienteRepository;
import repositorios.EstadoPrestamoRepository;
import utilidades.Colores;
import utilidades.Fuentes;
import utilidades.PanelPersonalizable;

public class PrestamoInfoPanel extends JPanel {
	private Font fuenteTexto =  Fuentes.setFontSegoe(1,35);
	private JButton btnCompletar;
	private JButton btnEliminar;
	private JButton btnInformacion;
	private JButton btnDeuda;
	EstadoPrestamoRepository estadoRepository;
	private Prestamo prestamo;
	
	public PrestamoInfoPanel(Prestamo prestamo) 
	{	
		estadoRepository = new EstadoPrestamoRepository();
		this.prestamo = prestamo;
		
		EstadoPrestamo estadoPrestamo = estadoRepository.getEstadoPrestamoDesdePrestamo(prestamo);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  
		setAlignmentX(LEFT_ALIGNMENT);
		setBackground(Colores.CLIENTE_CARTA_FONDO);
		setVisible(true);
		
		JLabel icono;
		
		int iconoSize = 60;
		
		if(estadoPrestamo != null && estadoPrestamo.getEstado().equals("correcto")) {
			icono = new JLabel(escalarImagenLocal("/img/greenMoney.png", iconoSize, iconoSize));  
		} else if(estadoPrestamo != null && estadoPrestamo.getEstado().equals("atrasado")){
			icono = new JLabel(escalarImagenLocal("/img/redMoney.png", iconoSize, iconoSize));  
		} else {
			icono = new JLabel(escalarImagenLocal("/img/check.png", iconoSize, iconoSize));  
		}
		
		icono.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel nombreCliente = new JLabel(this.prestamo.getNombre() + " " + this.prestamo.getApellido());
		nombreCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
		nombreCliente.setForeground(Colores.ENCABEZADOS_PRIMARIOS);
		nombreCliente.setFont(fuenteTexto);
		
		JLabel prestamoCantidad = new JLabel("$" + String.valueOf(this.prestamo.getMontoTotal()) + " - $" + String.valueOf(estadoPrestamo.getMontoRestante()));
		prestamoCantidad.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Color prestamoCantidadColor = Color.GRAY;
		
		EstadoPrestamoRepository estadoPrestamoRepo = new EstadoPrestamoRepository();
		
		String estado = estadoPrestamoRepo.getClientPrestamosEstado(prestamo);
	    
	    if (estado != null && estado.equals("atrasado")) 
	    {
	        prestamoCantidadColor = Color.RED;
	    } 
	    else 
	    {
	        prestamoCantidadColor = Color.LIGHT_GRAY;
	    }
	    
	    if(prestamo.getEstado().equals("concluso"))
	    {
	    	prestamoCantidadColor = Color.GREEN;
	    }
	    
	    prestamoCantidad.setForeground(prestamoCantidadColor);
		prestamoCantidad.setFont(Fuentes.setFontSegoe(1, 20));
		JLabel datosExtra;
		if(estadoPrestamo.getQuincenasRestantes()==0) {
			datosExtra = new JLabel(" ");
		}else {
			datosExtra = new JLabel("F: " + estadoPrestamo.getFechaProximoPago() + " - Q:" + estadoPrestamo.getQuincenasRestantes() + " - MQ: " + this.prestamo.getMontoQuincenal());
		}
		
	    datosExtra.setAlignmentX(Component.CENTER_ALIGNMENT);
	    datosExtra.setForeground(Color.WHITE);
	    datosExtra.setFont(Fuentes.setFontSegoe(1, 17));
	    
		btnCompletar = new JButton("Registrar pago");
		btnEliminar = new JButton("Eliminar");
		btnInformacion = new JButton("Info");
		btnDeuda = new JButton("Pagar deuda");
		
	
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		panelBotones.setOpaque(false);
		
		panelBotones.add(btnInformacion);
		panelBotones.add(btnEliminar);
		
		if(estadoPrestamo != null && estadoPrestamo.getQuincenasRestantes() != 0) 
		{
			panelBotones.add(btnCompletar);
		}
		
		if(estadoPrestamo != null && estadoPrestamo.getEstado().equals("atrasado")) 
		{
			panelBotones.add(btnDeuda);
		}
		
		add(Box.createRigidArea(new Dimension(0, 20)));  
		add(icono);
		add(Box.createRigidArea(new Dimension(0, 15))); 
		add(nombreCliente);
		add(Box.createRigidArea(new Dimension(0, 5)));   
		add(prestamoCantidad);
		add(datosExtra);
		add(Box.createRigidArea(new Dimension(0, 10))); 
		add(panelBotones);
		add(Box.createRigidArea(new Dimension(0, 20)));  
	}
	
	
	
	private ImageIcon escalarImagenLocal(String direccion, int x, int y) {
		ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(direccion));
		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH);
		ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
		iconoFinal.setDescription(direccion);
		return iconoFinal;
	}
	
	// Getters y Setters
	public JButton getBtnCompletar() {
		return btnCompletar;
	}
	
	public void setBtnCompletar(JButton btnCompletar) {
		this.btnCompletar = btnCompletar;
	}
	
	public JButton getBtnEliminar() {
		return btnEliminar;
	}
	
	public void setBtnEliminar(JButton btnEliminar) {
		this.btnEliminar = btnEliminar;
	}
	
	public JButton getBtnInformacion() {
		return btnInformacion;
	}
	
	public void setBtnInformacion(JButton btnInformacion) {
		this.btnInformacion = btnInformacion;
	}
	
	public JButton getBtnDeuda() {
		return btnDeuda;
	}
	
	public void setBtnDeuda(JButton btnDeuda) {
		this.btnDeuda = btnDeuda;
	}
	
	public Prestamo getPrestamo() {
		return prestamo;
	}
	
	public Window getWindow() {
		return SwingUtilities.getWindowAncestor(this);
	}
}