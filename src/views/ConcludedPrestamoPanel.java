package views;

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

import modelos.Client;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import modelos.User;
import repository.EstadoPrestamoRepository;
import utils.Colores;
import utils.PanelPersonalizable;

public class ConcludedPrestamoPanel extends JPanel {
	private Font fontTexto = new Font("Times New Roman", Font.BOLD, 35);
	private Prestamo prestamo;
	public ConcludedPrestamoPanel(Prestamo prestamo) 
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
		JLabel clientName= new JLabel(this.prestamo.getNombre());
		clientName.setAlignmentX(Component.CENTER_ALIGNMENT);
		clientName.setForeground(Color.BLACK);
		JLabel clientApellido= new JLabel(this.prestamo.getApellido());
		clientApellido.setAlignmentX(Component.CENTER_ALIGNMENT);
		clientApellido.setForeground(Color.BLACK);
		JLabel prestamoCantidad= new JLabel(String.valueOf(this.prestamo.getMonto()));
		prestamoCantidad.setAlignmentX(Component.CENTER_ALIGNMENT);
		prestamoCantidad.setForeground(Color.BLACK);
		
		
		
		clientName.setFont(fontTexto);
		clientApellido.setFont(fontTexto);
		prestamoInfo.add(clientName);
		prestamoInfo.add(clientApellido);
		prestamoInfo.add(prestamoCantidad);

		add(Box.createRigidArea(new Dimension(30,0)));
		add(icono);
		add(prestamoInfo);
		add(Box.createRigidArea(new Dimension(180,0)));
		
	}
	private ImageIcon escalarImagen(String direccion,int x,int y) throws Exception {
    	//System.out.println(direccion);
        ImageIcon iconoOriginal = new ImageIcon(direccion);

       
        Image imagenEscalada = iconoOriginal.getImage()
                .getScaledInstance(x, y, Image.SCALE_SMOOTH);

        
        ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
        iconoFinal.setDescription(direccion);
        
        if(iconoFinal.getDescription().equals("null"));
        
        return iconoFinal;
	}
	private ImageIcon escalarImagenLocal(String direccion,int x,int y) {
		System.out.println("nigga");
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

	public Font getFontTexto() {
		return fontTexto;
	}
	public void setFontTexto(Font fontTexto) {
		this.fontTexto = fontTexto;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}
	
	
}