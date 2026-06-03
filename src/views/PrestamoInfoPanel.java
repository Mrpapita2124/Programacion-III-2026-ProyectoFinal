package views;

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

import modelos.Client;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import modelos.User;
import repository.EstadoPrestamoRepository;
import utils.Colores;
import utils.Fonts;
import utils.PanelPersonalizable;

public class PrestamoInfoPanel extends JPanel {
	private Font fontTexto = new Font("Times New Roman", Font.BOLD, 35);
	private JButton btnComplete;
	private JButton btnDelete;
	private JButton btnInfo;
	private JButton btnDebt;
	EstadoPrestamoRepository estadoRepository;
	private Prestamo prestamo;
	
	public PrestamoInfoPanel(Prestamo prestamo) 
	{	
		estadoRepository = new EstadoPrestamoRepository();
		this.prestamo = prestamo;
		
		EstadoPrestamo estadoPrestamo = estadoRepository.getEstadoPrestamoFromPrestamo(prestamo);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  
		setAlignmentX(LEFT_ALIGNMENT);
		setBackground(Colores.CLIENT_CARD_BG);
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
		
		JLabel clientName = new JLabel(this.prestamo.getNombre() + " " + this.prestamo.getApellido());
		clientName.setAlignmentX(Component.CENTER_ALIGNMENT);
		clientName.setForeground(Colores.PRIMARY_HEADINGS);
		clientName.setFont(fontTexto);
		
		JLabel prestamoCantidad = new JLabel("$" + String.valueOf(this.prestamo.getMonto()));
		prestamoCantidad.setAlignmentX(Component.CENTER_ALIGNMENT);
		prestamoCantidad.setForeground(Color.BLACK);
		prestamoCantidad.setFont(Fonts.setFontSegoe(1, 20));
		
		btnComplete = new JButton("Registrar pago");
		btnDelete = new JButton("Eliminar");
		btnInfo = new JButton("Info");
		btnDebt = new JButton("Pagar deuda");
		
	
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		buttonPanel.setOpaque(false);
		
		buttonPanel.add(btnInfo);
		buttonPanel.add(btnDelete);
		
		if(estadoPrestamo != null && estadoPrestamo.getQuincenasRestantes() != 0) 
		{
			buttonPanel.add(btnComplete);
		}
		
		if(estadoPrestamo != null && estadoPrestamo.getEstado().equals("atrasado")) 
		{
			buttonPanel.add(btnDebt);
		}
		
		add(Box.createRigidArea(new Dimension(0, 20)));  
		add(icono);
		add(Box.createRigidArea(new Dimension(0, 15))); 
		add(clientName);
		add(Box.createRigidArea(new Dimension(0, 5)));   
		add(prestamoCantidad);
		add(Box.createRigidArea(new Dimension(0, 20))); 
		add(buttonPanel);
		add(Box.createRigidArea(new Dimension(0, 20)));  
	}
	
	private ImageIcon escalarImagen(String direccion, int x, int y) throws Exception {
		ImageIcon iconoOriginal = new ImageIcon(direccion);
		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH);
		ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
		iconoFinal.setDescription(direccion);
		return iconoFinal;
	}
	
	private ImageIcon escalarImagenLocal(String direccion, int x, int y) {
		ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(direccion));
		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH);
		ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
		iconoFinal.setDescription(direccion);
		return iconoFinal;
	}
	
	// Getters y Setters
	public JButton getBtnComplete() {
		return btnComplete;
	}
	
	public void setBtnComplete(JButton btnComplete) {
		this.btnComplete = btnComplete;
	}
	
	public JButton getBtnDelete() {
		return btnDelete;
	}
	
	public void setBtnDelete(JButton btnDelete) {
		this.btnDelete = btnDelete;
	}
	
	public JButton getBtnInfo() {
		return btnInfo;
	}
	
	public void setBtnInfo(JButton btnInfo) {
		this.btnInfo = btnInfo;
	}
	
	public JButton getBtnDebt() {
		return btnDebt;
	}
	
	public void setBtnDebt(JButton btnDebt) {
		this.btnDebt = btnDebt;
	}
	
	public Prestamo getPrestamo() {
		return prestamo;
	}
	
	public Window getWindow() {
		return SwingUtilities.getWindowAncestor(this);
	}
}