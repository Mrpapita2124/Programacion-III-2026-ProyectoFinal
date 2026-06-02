package views.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;

import javax.swing.*;


import modelos.Client;
import utils.Colores;
import utils.Fonts;


public class ClientPanel extends JPanel 
{
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
		
		setBackground(Colores.CLIENT_CARD_BG);
		
		setVisible(true);
		setBorder(BorderFactory.createEmptyBorder(12, 10, 10, 10));
		
		JLabel icono;
		try {
			icono = new JLabel(escalarImagen(this.client.getIneDireccion(), 180, 160));
		} catch (Exception e) {
			icono = new JLabel(escalarImagenLocal("..\\img\\LicenseDefault.png", 180, 160));
		}
		
		icono.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JPanel clienteInfoPanel = new JPanel();
		clienteInfoPanel.setForeground(Color.BLACK);
		clienteInfoPanel.setOpaque(false);
		clienteInfoPanel.setLayout(new BoxLayout(clienteInfoPanel, BoxLayout.Y_AXIS));
		
		JPanel nombreStatusPanel = new JPanel();
		nombreStatusPanel.setLayout(new BoxLayout(nombreStatusPanel, BoxLayout.X_AXIS));
		nombreStatusPanel.setOpaque(false);
		nombreStatusPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		String clientNombreFormato = this.client.getNombre() + " " + this.client.getApellido();
		clientNombreFormato = clientNombreFormato.length() > 15 ? clientNombreFormato.substring(0, 15) + "..." : clientNombreFormato;
		
		JLabel clienteNombre = new JLabel(clientNombreFormato);
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
		
		
		btnEdit = new JButton();
		btnDelete = new JButton();
		btnInfo = new JButton();
		btnMoney = new JButton();
		
		int iconSize = 40;
		
		try {
			btnEdit.setIcon(escalarImagen("src\\img\\cliente_edit_icon.png", iconSize, iconSize));
			btnDelete.setIcon(escalarImagen("src\\img\\cliente_delete_icon.png", iconSize, iconSize));
			btnInfo.setIcon(escalarImagen("src\\img\\cliente_info_icon.png", iconSize, iconSize));
			btnMoney.setIcon(escalarImagen("src\\img\\cliente_prestamo_icon.png", iconSize, iconSize));
		} 
		catch (Exception e) {
			btnEdit.setText("E");
			btnDelete.setText("D");
			btnInfo.setText("I");
			btnMoney.setText("M");
		}
		
		configurarBotonIcono(btnEdit, iconSize);
		configurarBotonIcono(btnDelete, iconSize);
		configurarBotonIcono(btnInfo, iconSize);
		configurarBotonIcono(btnMoney, iconSize);
		
		btnEdit.setToolTipText("Editar cliente");
		btnDelete.setToolTipText("Eliminar cliente");
		btnInfo.setToolTipText("Ver información");
		btnMoney.setToolTipText("Hacer préstamo");
		
		
		// Panel horizontal para botones
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setOpaque(false);
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		int buttonWidth = 8;
		buttonPanel.add(Box.createHorizontalGlue());

		buttonPanel.add(btnEdit);
		buttonPanel.add(Box.createRigidArea(new Dimension(buttonWidth, 0)));
		buttonPanel.add(btnInfo);
		buttonPanel.add(Box.createRigidArea(new Dimension(buttonWidth, 0)));
		buttonPanel.add(btnMoney);
		buttonPanel.add(Box.createRigidArea(new Dimension(buttonWidth, 0)));
		buttonPanel.add(btnDelete);
		buttonPanel.add(Box.createRigidArea(new Dimension(40, 0)));
		
		
		clienteInfoPanel.add(Box.createRigidArea(new Dimension(0, 0)));
		clienteInfoPanel.add(nombreStatusPanel);  
		clienteInfoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		clienteInfoPanel.add(clienteCorreo);
		clienteInfoPanel.add(Box.createRigidArea(new Dimension(0, 2)));
		clienteInfoPanel.add(prestamosStatus);
		clienteInfoPanel.add(Box.createRigidArea(new Dimension(0, 25)));
		clienteInfoPanel.add(buttonPanel);
		
		add(icono);
		add(Box.createRigidArea(new Dimension(15, 0)));
		add(clienteInfoPanel);
	}
	

	private void configurarBotonIcono(JButton button, int iconoSize) {
		button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		button.setContentAreaFilled(false);  
		button.setFocusPainted(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setPreferredSize(new Dimension(iconoSize+10, iconoSize+10));
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