package vistas.usuario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

import repositorios.ClienteRepository;
import utilidades.Colores;
import utilidades.Fuentes;
import utilidades.Sesion;
import vistas.cliente.ClienteCartas;
import vistas.cliente.ClientesEstadisticas;
import vistas.otros.VentanaPrincipal;

public class ClientesUsuarioPanel extends JPanel {
	
	private Font fontTitulo = Fuentes.setFontSegoe(1,25);
	private Font fontBotones = Fuentes.setFontSegoe(0,14);
	
	private JButton btnRegistro;
	private JButton btnFiltro;
	ClienteCartas cartas;
	JScrollPane scrollClientes;
	VentanaPrincipal ventana;
	
	
	public ClientesUsuarioPanel(VentanaPrincipal ancestro) 
	{
		this.ventana=ancestro;
		
		// Make panel transparent to show gradient
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		/*
		//navBar
		JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		navbar.setOpaque(true); // Make navbar transparent
		btnRegister = new JButton("Registrar Usuario");
		navbar.add(btnRegister);
		navbar.setMaximumSize(new Dimension(1600,32));
		add(navbar);
		*/
		
		ClientesEstadisticas panelEstadisticas = new ClientesEstadisticas();
		panelEstadisticas.setOpaque(false); 
	    panelEstadisticas.setMaximumSize(new Dimension(1600, 150));
	    panelEstadisticas.setAlignmentX(Component.CENTER_ALIGNMENT);
	    add(panelEstadisticas);
		
		add(Box.createRigidArea(new Dimension(0, 45)));
		
		
		JPanel panelTitulo = new JPanel();
		panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.X_AXIS));
		panelTitulo.setOpaque(false);
		panelTitulo.setMaximumSize(new Dimension(1600, 50));
		panelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		JLabel capacidadPrestamo = new JLabel("  $" + String.format("%,.2f", Sesion.getusuarioActual().getCapacidadPrestamo()));
		capacidadPrestamo.setFont(Fuentes.setFontSegoe(1, 25));
		capacidadPrestamo.setForeground(Colores.BOTON_COLOR1);
		capacidadPrestamo.setPreferredSize(new Dimension(375, 30)); 
		capacidadPrestamo.setMaximumSize(new Dimension(375, 30));
		panelTitulo.add(capacidadPrestamo);

		panelTitulo.add(Box.createHorizontalGlue());

		JLabel saludo = new JLabel("Lista de Clientes"); 
		saludo.setOpaque(false);
		saludo.setFont(fontTitulo);
		saludo.setForeground(Colores.ENCABEZADOS_PRIMARIOS);
		saludo.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelTitulo.add(saludo);

		panelTitulo.add(Box.createHorizontalGlue());

		JPanel panelDeBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
		panelDeBotones.setOpaque(false);
        
        
        btnFiltro = new JButton("Filtrar");
        btnFiltro.setFont(fontBotones);
        btnFiltro.setBackground(Colores.BOTON_COLOR4);
        btnFiltro.setForeground(Color.WHITE);
        btnFiltro.setFocusPainted(false);
        btnFiltro.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnFiltro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnRegistro = new JButton("Registrar Cliente");
        btnRegistro.setFont(fontBotones);
        btnRegistro.setBackground(Colores.BOTON_COLOR3);
        btnRegistro.setForeground(Color.BLACK);
        btnRegistro.setFocusPainted(false);
        btnRegistro.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnRegistro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        int iconoTamanio = 20;
        
		try 
		{
			btnRegistro.setIcon(escalarImagen("src\\img\\cliente_agregar.png", iconoTamanio, iconoTamanio));
			btnFiltro.setIcon(escalarImagen("src\\img\\cliente_filtrar.png", iconoTamanio, iconoTamanio));
		} 
		catch (Exception e) 
		{
		}
        
		if(ClienteRepository.getTotalNumeroDeClientes() != 0)
		{
			panelDeBotones.add(btnFiltro);
		}

		panelDeBotones.add(btnRegistro);
        panelTitulo.add(panelDeBotones, BorderLayout.EAST);
        
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        
        add(panelTitulo);
		
		add(Box.createRigidArea(new Dimension(0, 30)));
		
		crearListaClientes(ancestro);
		
		scrollClientes= new JScrollPane(cartas);
		scrollClientes.setMaximumSize(new Dimension(1600,cartas.getAlto()));
		scrollClientes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollClientes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollClientes.setOpaque(false);
		scrollClientes.getViewport().setOpaque(false); // Make viewport transparent
		
		scrollClientes.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
		    @Override
		    protected void configureScrollBarColors() {
		        this.thumbColor = Colores.BOTON_COLOR1;       // color del "pulgar" (la parte que arrastras)
		        this.trackColor = Color.LIGHT_GRAY; // color del fondo
		    }
		    @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                return button;
            }


		    
		});
		scrollClientes.getVerticalScrollBar().setUnitIncrement(20);
		scrollClientes.getVerticalScrollBar().setOpaque(false);
		scrollClientes.setBorder(null);
		
		scrollClientes.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		add(scrollClientes);
	}
	
	public JButton getBtnRegistro() {
		return btnRegistro;
	}
	
	public JButton getBtnFiltro() {
		return btnFiltro;
	}
	
	public ClienteCartas getCartas() {
		return cartas;
	}
	
	public void setCartas(ClienteCartas cartas) {
		this.cartas = cartas;
	}
	
	public JScrollPane getScrollClientes() {
		return scrollClientes;
	}
	
	public void setScrollClientes(JScrollPane scrollClients) {
		this.scrollClientes = scrollClients;
	}
	
	public void crearListaClientes(VentanaPrincipal ventana) {
		cartas= new ClienteCartas(this,ventana);
	
		// Esto hace como un tipo refresh
		if (scrollClientes != null) 
		{
	        scrollClientes.setMaximumSize(new Dimension(1600, cartas.getAlto()));
	    }
	}
	
	public void setBtnRegistrar(JButton btnRegister) {
		this.btnRegistro = btnRegister;
	}
	
	public void setBtnFiltrar(JButton btnFilter) {
		this.btnFiltro = btnFilter;
	}
	
	public VentanaPrincipal getAncestro() {
		return ventana;
	}
	
	public void setAncestro(VentanaPrincipal ancestro) {
		this.ventana = ancestro;
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
	
}