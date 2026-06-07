package vistas.prestamo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.util.Graphics2DProxy;

import modelos.Cliente;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import modelos.Usuario;
import repositorios.EstadoPrestamoRepository;
import utilidades.Colores;
import utilidades.Fuentes;
import utilidades.PanelPersonalizable;

public class CartaPrestamoConcluso extends JPanel {
	
	private Prestamo prestamo;
	public CartaPrestamoConcluso(Prestamo prestamo) 
	{	
		this.prestamo=prestamo;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setAlignmentX(LEFT_ALIGNMENT);
		setBackground(Colores.LOGIN_PANEL);
		setVisible(true);
		//setSize(new Dimension(600,300));
		
		JPanel prestamoInfo=new JPanel();
		prestamoInfo.setForeground(Color.BLACK);
		prestamoInfo.setOpaque(false);
		prestamoInfo.setLayout(new BoxLayout(prestamoInfo,BoxLayout.Y_AXIS));
		
		JLabel icono;
		icono=new JLabel(escalarImagenLocal("..\\img\\check.png", 280, 280));
		icono.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel nombreCliente= new JLabel(this.prestamo.getNombre());
		nombreCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
		nombreCliente.setForeground(Color.BLACK);
		
		JLabel apellidoCliente= new JLabel(this.prestamo.getApellido());
		apellidoCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
		apellidoCliente.setForeground(Color.BLACK);
		
		JLabel cantidadPrestamo= new JLabel(String.valueOf(this.prestamo.getMonto()));
		cantidadPrestamo.setAlignmentX(Component.CENTER_ALIGNMENT);
		cantidadPrestamo.setForeground(Color.BLACK);
		
		
		
		nombreCliente.setFont(Fuentes.fontTexto);
		apellidoCliente.setFont(Fuentes.fontTexto);
		prestamoInfo.add(nombreCliente);
		prestamoInfo.add(apellidoCliente);
		prestamoInfo.add(cantidadPrestamo);

		add(Box.createRigidArea(new Dimension(30,0)));
		add(icono);
		add(prestamoInfo);
		add(Box.createRigidArea(new Dimension(180,0)));
		
	}
	private ImageIcon escalarImagenLocal(String direccion,int x,int y) {
		
	    ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(direccion));
	
	   
	    Image imagenEscalada = iconoOriginal.getImage()
	            .getScaledInstance(x, y, Image.SCALE_SMOOTH);
	
	    
	    ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
	    iconoFinal.setDescription(direccion);
	    return iconoFinal;
	}
	

	public Window getWindow() {
		return SwingUtilities.getWindowAncestor(this);
	}


	public Prestamo getPrestamo() {
		return prestamo;
	}
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}
	
	
}