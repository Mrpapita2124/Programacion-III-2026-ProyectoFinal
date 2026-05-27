package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.util.Graphics2DProxy;

import modelos.Client;
import modelos.User;
import utils.Colores;
import utils.Fonts;
import utils.PanelPersonalizable;

public class ClientPanel extends JPanel {
	private Font fontTexto = Fonts.setFontSegoe(0,25);
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnInfo;
	private JButton btnMoney;
	Client client;
	

	public ClientPanel(Client client) 
	{	
		this.client = client;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setAlignmentX(LEFT_ALIGNMENT);
		setBackground(Colores.LOGIN_PANEL);
		setVisible(true);
		setBorder(BorderFactory.createEmptyBorder(12, 10, 10, 10));
		
		JLabel icono;
		try {
			icono = new JLabel(escalarImagen(this.client.getIneDireccion(), 180, 120));
		} catch (Exception e) {
			icono = new JLabel(escalarImagenLocal("..\\img\\LicenseDefault.png", 180, 120));
		}
		
		icono.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel clienteInfo = new JPanel();
		clienteInfo.setForeground(Color.BLACK);
		clienteInfo.setOpaque(false);
		clienteInfo.setLayout(new BoxLayout(clienteInfo, BoxLayout.Y_AXIS));
		
		JPanel nombreStatusPanel = new JPanel();
		nombreStatusPanel.setLayout(new BoxLayout(nombreStatusPanel, BoxLayout.X_AXIS));
		nombreStatusPanel.setOpaque(false);
		nombreStatusPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel clienteNombre = new JLabel(this.client.getNombre() + " " + this.client.getApellido());
		clienteNombre.setForeground(Colores.PRIMARY_HEADINGS);
		clienteNombre.setFont(Fonts.setFontSegoe(1, 25));
		
		JLabel cartaStatus = new JLabel("Activo");
		cartaStatus.setForeground(Colores.LOGIN_PANEL);
		cartaStatus.setBackground(new Color(76, 175, 80));
		cartaStatus.setOpaque(true);
		cartaStatus.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
		cartaStatus.setFont(Fonts.setFontSegoe(1, 11));
		
		nombreStatusPanel.add(clienteNombre);
		nombreStatusPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		nombreStatusPanel.add(cartaStatus);
		nombreStatusPanel.add(Box.createHorizontalGlue());
		
		JLabel clienteCorreo = new JLabel(this.client.getCorreoElectronico());
		clienteCorreo.setAlignmentX(Component.LEFT_ALIGNMENT);
		clienteCorreo.setForeground(Color.GRAY);
		clienteCorreo.setFont(Fonts.setFontSegoe(0, 15));
		
		// Prestamo status
		JLabel prestamosStatus = new JLabel("Estatus Prestamo: Al dia");
		prestamosStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
		prestamosStatus.setForeground(Colores.BG_TEXT_COLOR);
		prestamosStatus.setFont(Fonts.setFontSegoe(0, 12));
		
		// Botones de Card
		btnEdit = new JButton("Editar");
		btnDelete = new JButton("Eliminar");
		btnInfo = new JButton("Info");
		btnMoney = new JButton("Hacer prestamo");
		
		crearBoton(btnEdit, Colores.BUTTON_COLOR1);
		crearBoton(btnDelete, Colores.BUTTON_COLOR1);
		crearBoton(btnInfo, Colores.BUTTON_COLOR1);
		crearBoton(btnMoney, Colores.BUTTON_COLOR1);
		
		
		// Panel horizontal para botones
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setOpaque(false);
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		buttonPanel.add(btnEdit);
		buttonPanel.add(Box.createRigidArea(new Dimension(8, 0)));
		buttonPanel.add(btnDelete);
		buttonPanel.add(Box.createRigidArea(new Dimension(8, 0)));
		buttonPanel.add(btnInfo);
		buttonPanel.add(Box.createRigidArea(new Dimension(8, 0)));
		buttonPanel.add(btnMoney);
		
		clienteInfo.add(Box.createRigidArea(new Dimension(0, 0)));
		clienteInfo.add(nombreStatusPanel);  
		clienteInfo.add(Box.createRigidArea(new Dimension(0, 8)));
		clienteInfo.add(clienteCorreo);
		clienteInfo.add(Box.createRigidArea(new Dimension(0, 2)));
		clienteInfo.add(prestamosStatus);
		clienteInfo.add(Box.createRigidArea(new Dimension(0, 25)));
		clienteInfo.add(buttonPanel);
		
		add(icono);
		add(Box.createRigidArea(new Dimension(15, 0)));
		add(clienteInfo);
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
	
	private void crearBoton(JButton button, Color bgColor) 
	{
	    button.setBackground(bgColor);
	    button.setForeground(Colores.PRIMARY_HEADINGS);
	    button.setFocusPainted(false);
	    button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
	    button.setFont(Fonts.setFontSegoe(1, 12));
	    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	
	// Getters and Setters
	public JButton getSession() {
		return btnEdit;
	}
	public void setSession(JButton session) {
		this.btnEdit = session;
	}
	
	public JButton getBtnMoney() {
		return btnMoney;
	}
	public void setBtnMoney(JButton btnMoney) {
		this.btnMoney = btnMoney;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Window getWindow() {
		return SwingUtilities.getWindowAncestor(this);
	}
	public JButton getBtnEdit() {
		return btnEdit;
	}
	public void setBtnEdit(JButton btnEdit) {
		this.btnEdit = btnEdit;
	}
	public Font getFontTexto() {
		return fontTexto;
	}
	public void setFontTexto(Font fontTexto) {
		this.fontTexto = fontTexto;
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
	
}