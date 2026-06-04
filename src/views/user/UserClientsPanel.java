package views.user;

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

import repository.ClientRepository;
import utils.Colores;
import utils.Fonts;
import views.VentanaPrincipal;
import views.client.ClientCards;
import views.client.ClientesEstadisticas;

public class UserClientsPanel extends JPanel {
	
	private Font fontTitulo = Fonts.setFontSegoe(1,25);
	private Font fontBotones = Fonts.setFontSegoe(0,14);
	
	private JButton btnRegister;
	private JButton btnFilter;
	ClientCards clients;
	JScrollPane scrollClients;
	VentanaPrincipal ancestro;
	
	
	public UserClientsPanel(VentanaPrincipal ancestro) 
	{
		this.ancestro=ancestro;
		
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
		
		ClientesEstadisticas statsPanel = new ClientesEstadisticas();
		statsPanel.setOpaque(false); // Make stats panel transparent
	    statsPanel.setMaximumSize(new Dimension(1600, 150));
	    statsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    add(statsPanel);
		
		add(Box.createRigidArea(new Dimension(0, 45)));
		
		
		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setOpaque(false);
		titlePanel.setMaximumSize(new Dimension(1600, 50));
		titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel saludo = new JLabel("Lista de Clientes"); 
        saludo.setOpaque(false);
        saludo.setFont(fontTitulo);
        saludo.setForeground(Colores.PRIMARY_HEADINGS);
        titlePanel.add(saludo, BorderLayout.WEST);
        

        JPanel panelDeBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelDeBotones.setOpaque(false);
        
        // BOTON FILTAR PAPITAAAA ----------------
        
        btnFilter = new JButton("Filtrar");
        btnFilter.setFont(fontBotones);
        btnFilter.setBackground(Colores.BUTTON_COLOR4);
        btnFilter.setForeground(Color.WHITE);
        btnFilter.setFocusPainted(false);
        btnFilter.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnFilter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnRegister = new JButton("Registrar Cliente");
        btnRegister.setFont(fontBotones);
        btnRegister.setBackground(Colores.BUTTON_COLOR3);
        btnRegister.setForeground(Color.BLACK);
        btnRegister.setFocusPainted(false);
        btnRegister.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        int iconSize = 20;
		
		try 
		{
			btnRegister.setIcon(escalarImagen("src\\img\\cliente_agregar.png", iconSize, iconSize));
			btnFilter.setIcon(escalarImagen("src\\img\\cliente_filtrar.png", iconSize, iconSize));
		} 
		catch (Exception e) 
		{
			System.out.println("No se cargo lo iconos de agregar cliente y filtar cliente!");
		}
        
		if(ClientRepository.getTotalNumeroDeCliente() != 0)
		{
			panelDeBotones.add(btnFilter);
		}
        panelDeBotones.add(btnRegister);
        
        titlePanel.add(panelDeBotones, BorderLayout.EAST);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        
        add(titlePanel);
		
		add(Box.createRigidArea(new Dimension(0, 30)));
		
		createClientList(ancestro);
		
		scrollClients= new JScrollPane(clients);
		scrollClients.setMaximumSize(new Dimension(1600,clients.getAlto()));
		scrollClients.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollClients.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollClients.setOpaque(false);
		scrollClients.getViewport().setOpaque(false); // Make viewport transparent
		
		scrollClients.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
		    @Override
		    protected void configureScrollBarColors() {
		        this.thumbColor = Colores.BUTTON_COLOR1;       // color del "pulgar" (la parte que arrastras)
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
		scrollClients.getVerticalScrollBar().setUnitIncrement(20);
		scrollClients.getVerticalScrollBar().setOpaque(false);
		scrollClients.setBorder(null);
		
		scrollClients.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		add(scrollClients);
	}
	
	public JButton getBtnRegister() {
		return btnRegister;
	}
	
	public JButton getBtnFilter() {
		return btnFilter;
	}
	
	public ClientCards getClients() {
		return clients;
	}
	
	public void setClients(ClientCards clients) {
		this.clients = clients;
	}
	
	public JScrollPane getScrollClients() {
		return scrollClients;
	}
	
	public void setScrollClients(JScrollPane scrollClients) {
		this.scrollClients = scrollClients;
	}
	
	public void createClientList(VentanaPrincipal ventana) {
		clients= new ClientCards(this,ventana);
	
		// Esto hace como un tipo refresh
		if (scrollClients != null) 
		{
	        scrollClients.setMaximumSize(new Dimension(1600, clients.getAlto()));
	    }
	}
	
	public void setBtnRegister(JButton btnRegister) {
		this.btnRegister = btnRegister;
	}
	
	public void setBtnFilter(JButton btnFilter) {
		this.btnFilter = btnFilter;
	}
	
	public VentanaPrincipal getAncestro() {
		return ancestro;
	}
	
	public void setAncestro(VentanaPrincipal ancestro) {
		this.ancestro = ancestro;
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