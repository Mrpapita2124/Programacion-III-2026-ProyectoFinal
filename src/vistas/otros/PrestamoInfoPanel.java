package vistas.otros;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Window;

import javax.swing.*;


import modelos.EstadoPrestamo;
import modelos.Prestamo;
import repositorios.EstadoPrestamoRepository;
import utilidades.Colores;
import utilidades.Fuentes;


public class PrestamoInfoPanel extends JPanel {
	private Font fuenteTexto =  Fuentes.setFontSegoe(1,35);
	private JButton btnCompletar;
	private JButton btnEliminar;
	private JButton btnDeuda;
	EstadoPrestamoRepository estadoRepository;
	private Prestamo prestamo;
	
	public PrestamoInfoPanel(Prestamo prestamo) 
	{	
		estadoRepository = new EstadoPrestamoRepository();
		this.prestamo = prestamo;
		
		EstadoPrestamo estadoPrestamo = estadoRepository.getEstadoPrestamoDesdePrestamo(prestamo);
		if (estadoPrestamo == null) 
		{
			System.err.println("\n* EstadoPrestamo no econtrado para prestamo ID: " + prestamo.getIdPrestamo());
	    	estadoPrestamo = new EstadoPrestamo(prestamo.getIdPrestamo(), 0, 0.0, null, 0.0, "sin_estado", 0.0);
		}
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  
		setAlignmentX(LEFT_ALIGNMENT);
		setBackground(Colores.CLIENTE_CARTA_FONDO);
		setVisible(true);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.setOpaque(false);
		topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		int iconoSize = 60;
		JLabel icono;
		
		if(estadoPrestamo != null && estadoPrestamo.getEstado().equals("correcto")) {
			icono = new JLabel(escalarImagenLocal("/img/greenMoney.png", iconoSize, iconoSize));  
		} else if(estadoPrestamo != null && estadoPrestamo.getEstado().equals("atrasado")){
			icono = new JLabel(escalarImagenLocal("/img/redMoney.png", iconoSize, iconoSize));  
		} else {
			icono = new JLabel(escalarImagenLocal("/img/check.png", iconoSize, iconoSize));  
		}
		
		topPanel.add(icono);
		topPanel.add(Box.createHorizontalGlue());
		
		JLabel estadoLabel = new JLabel();
		estadoLabel.setFont(Fuentes.setFontSegoe(1, 15));
		
		String estadoTexto = "";
		Color estadoColor = null;
		
		if(prestamo.getEstado().equals("concluso")) 
		{
			estadoTexto = "CONCLUSO";
			estadoColor = new Color(76, 175, 80);
		} 
		else if(estadoPrestamo != null && estadoPrestamo.getEstado().equals("atrasado")) {
			estadoTexto = "ATRASADO";
			estadoColor = new Color(225, 20, 16);
		} 
		else 
		{
			estadoTexto = "ACTIVO";
			estadoColor = Color.YELLOW.darker();
		}
		
		estadoLabel.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
		estadoLabel.setBackground(estadoColor);
		estadoLabel.setOpaque(true);
		estadoLabel.setText(estadoTexto);
		estadoLabel.setForeground(estadoColor.darker().darker());
		estadoLabel.setFont(estadoLabel.getFont().deriveFont(Font.BOLD));
		
		topPanel.add(estadoLabel);
		
		
		JLabel nombreCliente = new JLabel(this.prestamo.getNombre() + " " + this.prestamo.getApellido());
		nombreCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
		nombreCliente.setForeground(Colores.ENCABEZADOS_PRIMARIOS);
		nombreCliente.setFont(fuenteTexto);
		
		JLabel prestamoCantidad = new JLabel(String.format( "Monto Total: $%.2f     Monto Restante: $%.2f", this.prestamo.getMontoTotal(), 
			    estadoPrestamo.getMontoRestante()));
		
		prestamoCantidad.setAlignmentX(Component.CENTER_ALIGNMENT);
		Color prestamoCantidadColor = null;
		
		EstadoPrestamoRepository estadoPrestamoRepo = new EstadoPrestamoRepository();
		String estado = estadoPrestamoRepo.getClientPrestamosEstado(prestamo);
	    
	    if (estado != null && estado.equals("atrasado")) 
	    {
	        prestamoCantidadColor = new Color(225, 20, 16);
	    } 
	    else 
	    {
	        prestamoCantidadColor = Color.YELLOW.darker();
	    }
	    
	    if(prestamo.getEstado().equals("concluso"))
	    {
	    	prestamoCantidadColor = new Color(76, 175, 80);
	    }
	    
	    prestamoCantidad.setForeground(prestamoCantidadColor);
		prestamoCantidad.setFont(Fuentes.setFontSegoe(1, 20));
		
		JLabel datosExtra;
		if(estadoPrestamo.getQuincenasRestantes()==0) {
			datosExtra = new JLabel(" ");
		}else {
			datosExtra = new JLabel("Fecha: " + estadoPrestamo.getFechaProximoPago() + "      Quincenas Restantes: " + estadoPrestamo.getQuincenasRestantes() + "      Monto Quincenal: " + this.prestamo.getMontoQuincenal());
		}
		
	    datosExtra.setAlignmentX(Component.CENTER_ALIGNMENT);
	    datosExtra.setForeground(Color.WHITE);
	    datosExtra.setFont(Fuentes.setFontSegoe(1, 17));
	    
		btnCompletar = new JButton();
		btnEliminar = new JButton();
		btnDeuda = new JButton();
		
		
		int iconSize = 40;
		
		try {
			btnCompletar.setIcon(escalarImagen("src\\img\\prestamo_registrar_pago.png", iconSize, iconSize));
			btnEliminar.setIcon(escalarImagen("src\\img\\cliente_delete_icon.png", iconSize, iconSize));
			btnDeuda.setIcon(escalarImagen("src\\img\\prestamo_pagar_deuda.png", iconSize, iconSize));
		} 
		catch (Exception e) {
			btnCompletar.setText("PP");
			btnEliminar.setText("D");
			btnDeuda.setText("PD");
		}
		
		configurarBotonIcono(btnCompletar, iconSize);
		configurarBotonIcono(btnEliminar, iconSize);
		configurarBotonIcono(btnDeuda, iconSize);
		
		btnCompletar.setToolTipText("Registrar Pago");
		btnEliminar.setToolTipText("Eliminar Prestamo");
		btnDeuda.setToolTipText("Pagar Deuda");

		
		
		
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		panelBotones.setOpaque(false);
		
		
		if(estadoPrestamo != null && estadoPrestamo.getQuincenasRestantes() != 0) 
		{
			panelBotones.add(btnCompletar);
		}
		
		if(estadoPrestamo != null && estadoPrestamo.getEstado().equals("atrasado")) 
		{
			panelBotones.add(btnDeuda);
		}
		
		panelBotones.add(btnEliminar);
		
		add(Box.createRigidArea(new Dimension(0, 10)));  
		add(topPanel);
		add(Box.createRigidArea(new Dimension(0, 20))); 
		add(nombreCliente);
		add(Box.createRigidArea(new Dimension(0, 5)));   
		add(prestamoCantidad);
		add(Box.createRigidArea(new Dimension(0, 15))); 
		add(datosExtra);
		add(Box.createRigidArea(new Dimension(0, 30))); 
		add(panelBotones);
		add(Box.createRigidArea(new Dimension(0, 20)));  
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
	
	private ImageIcon escalarImagenLocal(String direccion, int x, int y) {
		ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(direccion));
		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH);
		ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
		iconoFinal.setDescription(direccion);
		return iconoFinal;
	}
	
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