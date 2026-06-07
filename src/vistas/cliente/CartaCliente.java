package vistas.cliente;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;

import javax.swing.*;


import modelos.Cliente;
import repositorios.ClienteRepository;
import utilidades.Colores;
import utilidades.Fuentes;


public class CartaCliente extends JPanel 
{
	private Font fuenteTexto = Fuentes.setFontSegoe(0,25);
	private JButton btnEditar;
	private JButton btnEliminar;
	private JButton btnInformacion;
	private JButton btnDinero;
	Cliente cliente;
	

	public CartaCliente(Cliente cliente) 
	{	
		this.cliente = cliente;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setAlignmentX(LEFT_ALIGNMENT);
		
		setBackground(Colores.CLIENTE_CARTA_FONDO);
		
		setVisible(true);
		setBorder(BorderFactory.createEmptyBorder(12, 10, 10, 10));
		
		JLabel icono;
		try {
			icono = new JLabel(escalarImagen(this.cliente.getIneDireccion(), 180, 160));
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
		
		
		String clientNombreFormato = this.cliente.getNombre() + " " + this.cliente.getApellido();
		clientNombreFormato = clientNombreFormato.length() > 13 ? clientNombreFormato.substring(0, 13) + "..." : clientNombreFormato;
		
		JLabel clienteNombre = new JLabel(clientNombreFormato);
		clienteNombre.setForeground(Colores.ENCABEZADOS_PRIMARIOS);
		clienteNombre.setFont(Fuentes.setFontSegoe(1, 25));
		
		
		ClienteRepository clienteRepo = new ClienteRepository();
		String estatus = clienteRepo.clienteTienePrestamoActivo(this.cliente) ? "Activo" : "Inactivo";
		Color estatusColor = clienteRepo.clienteTienePrestamoActivo(this.cliente) ? new Color(76, 175, 80) : new Color(225, 20, 16);
		
		JLabel cartaStatus = new JLabel(estatus);
		cartaStatus.setForeground(Colores.LOGIN_PANEL);
		cartaStatus.setBackground(estatusColor);
		cartaStatus.setOpaque(true);
		cartaStatus.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
		cartaStatus.setFont(Fuentes.setFontSegoe(1, 11));
		
		nombreStatusPanel.add(clienteNombre);
		nombreStatusPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		nombreStatusPanel.add(cartaStatus);
		nombreStatusPanel.add(Box.createHorizontalGlue());
		
		JLabel clienteCorreo = new JLabel(this.cliente.getCorreoElectronico());
		clienteCorreo.setAlignmentX(Component.LEFT_ALIGNMENT);
		clienteCorreo.setForeground(Color.GRAY);
		clienteCorreo.setFont(Fuentes.setFontSegoe(0, 15));
		
		// Prestamo status
		JLabel reputacionLbl = new JLabel("Reputacion: " + this.cliente.getReputacion());
		reputacionLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
		reputacionLbl.setForeground(Colores.FONDO_TEXTO_COLOR);
		reputacionLbl.setFont(Fuentes.setFontSegoe(0, 12));
		
		
		btnEditar = new JButton();
		btnEliminar = new JButton();
		btnInformacion = new JButton();
		btnDinero = new JButton();
		
		int iconSize = 40;
		
		try {
			btnEditar.setIcon(escalarImagen("src\\img\\cliente_edit_icon.png", iconSize, iconSize));
			btnEliminar.setIcon(escalarImagen("src\\img\\cliente_delete_icon.png", iconSize, iconSize));
			btnInformacion.setIcon(escalarImagen("src\\img\\cliente_info_icon.png", iconSize, iconSize));
			btnDinero.setIcon(escalarImagen("src\\img\\cliente_prestamo_icon.png", iconSize, iconSize));
		} 
		catch (Exception e) {
			btnEditar.setText("E");
			btnEliminar.setText("D");
			btnInformacion.setText("I");
			btnDinero.setText("M");
		}
		
		configurarBotonIcono(btnEditar, iconSize);
		configurarBotonIcono(btnEliminar, iconSize);
		configurarBotonIcono(btnInformacion, iconSize);
		configurarBotonIcono(btnDinero, iconSize);
		
		btnEditar.setToolTipText("Editar cliente");
		btnEliminar.setToolTipText("Eliminar cliente");
		btnInformacion.setToolTipText("Ver información");
		btnDinero.setToolTipText("Hacer préstamo");
		
		
		// Panel horizontal para botones
		JPanel boonPanel = new JPanel();
		boonPanel.setLayout(new BoxLayout(boonPanel, BoxLayout.X_AXIS));
		boonPanel.setOpaque(false);
		boonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		int anchoBoton = 8;
		boonPanel.add(Box.createHorizontalGlue());

		boonPanel.add(btnEditar);
		boonPanel.add(Box.createRigidArea(new Dimension(anchoBoton, 0)));
		boonPanel.add(btnInformacion);
		boonPanel.add(Box.createRigidArea(new Dimension(anchoBoton, 0)));
		boonPanel.add(btnDinero);
		boonPanel.add(Box.createRigidArea(new Dimension(anchoBoton, 0)));
		boonPanel.add(btnEliminar);
		boonPanel.add(Box.createRigidArea(new Dimension(40, 0)));
		
		
		clienteInfoPanel.add(Box.createRigidArea(new Dimension(0, 0)));
		clienteInfoPanel.add(nombreStatusPanel);  
		clienteInfoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		clienteInfoPanel.add(clienteCorreo);
		clienteInfoPanel.add(Box.createRigidArea(new Dimension(0, 2)));
		clienteInfoPanel.add(reputacionLbl);
		clienteInfoPanel.add(Box.createRigidArea(new Dimension(0, 25)));
		clienteInfoPanel.add(boonPanel);
		
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
	public JButton getSesion() {
		return btnEditar;
	}
	public void setSesion(JButton sesion) {
		this.btnEditar = sesion;
	}
	
	public JButton getBtnDinero() {
		return btnDinero;
	}
	public void setBtnDinero(JButton btnDinero) {
		this.btnDinero = btnDinero;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setClient(Cliente cliente) {
		this.cliente = cliente;
	}
	public Window getWindow() {
		return SwingUtilities.getWindowAncestor(this);
	}
	public JButton getBtnEditar() {
		return btnEditar;
	}
	public void setBtnEditar(JButton btnEditar) {
		this.btnEditar = btnEditar;
	}
	public Font getFuenteTexto() {
		return fuenteTexto;
	}
	public void setFuenteTexto(Font fuenteTexto) {
		this.fuenteTexto = fuenteTexto;
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
	
}