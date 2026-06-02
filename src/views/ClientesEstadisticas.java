package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.*;

import repository.ClientRepository;
import repository.PrestamoRepository;
import utils.Colores;
import utils.Fonts;
import utils.PanelPersonalizable;
import utils.RoundedBorder;
import utils.Session;


public class ClientesEstadisticas extends PanelPersonalizable {

	public ClientesEstadisticas() 
	{
        setLayout(new GridLayout(1, 3, 20, 0));
        setBackground(Colores.COLOR_GRADIENT1);
        setBorder(new RoundedBorder(Colores.DEFAULT_BORDER, 35, 2));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));
        
        ClientRepository clientRepository = new ClientRepository();
        PrestamoRepository prestamoRepository = new PrestamoRepository();
        
        add(crearCardEstadisticas("TOTAL CLIENTES", String.valueOf(clientRepository.getTotalNumeroDeCliente()), "+12% este mes", Colores.PRIMARY_HEADINGS, false));
        add(crearCardEstadisticas("PRESTAMOS ACTIVOS", String.valueOf(prestamoRepository.getTotalNumeroDePrestamos()), "74% de la cartera", Colores.PRIMARY_HEADINGS, true));
        add(crearCardEstadisticas("PRESTAMOS ATRASADOS", "42", "Atención requerida", Colores.PRIMARY_HEADINGS, false));
    }
    
	private JPanel crearCardEstadisticas(String titulo, String valor, String subtexto, Color color, boolean clickable) 
	{
	    PanelPersonalizable card = new PanelPersonalizable() {
	        @Override
	        protected void paintComponent(Graphics g) 
	        {
	            Graphics2D g2d = (Graphics2D) g.create();
	            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            
	            g2d.setColor(getBackground());
	            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
	            g2d.dispose();
	            
	            super.paintComponent(g);
	        }
	    };
	    
	    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
	    
	    card.setBackground(Colores.LOGIN_PANEL);
	    card.setOpaque(false); 
	    
	    javax.swing.border.Border cardBorder = BorderFactory.createCompoundBorder(
	        new RoundedBorder(Colores.DEFAULT_BORDER, 25, 1),
	        BorderFactory.createEmptyBorder(25, 25, 15, 15)
	    );
	    
	    card.setBorder(cardBorder);
	    card.setFocusable(false);
	    
	    if(clickable) 
	    {
	        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        card.addMouseListener(new java.awt.event.MouseAdapter() 
	        {
	            @Override
	            public void mouseClicked(java.awt.event.MouseEvent evt) 
	            {
	                //System.out.println("PRESTAMOS ACTIVOS clicked!");
	                VentanaPrincipal frame = (VentanaPrincipal) SwingUtilities.getWindowAncestor(card);
	                frame.showView(VentanaPrincipal.PRESTAMOS);
	            }
	            
	            @Override
	            public void mouseEntered(java.awt.event.MouseEvent evt) 
	            {
	                card.setBackground(Colores.CLIENT_CARD_BG);
	                card.repaint();
	            }
	            
	            @Override
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	                card.setBackground(Colores.LOGIN_PANEL);
	                card.repaint();
	            }
	        });
	    }
	    
	    JLabel tituloLbl = new JLabel(titulo);
	    tituloLbl.setFont(Fonts.setFontSegoe(1, 14));
	    tituloLbl.setForeground(Color.LIGHT_GRAY);
	    tituloLbl.setAlignmentX(LEFT_ALIGNMENT);
	    
	    JPanel horizontalPanel = new JPanel();
	    horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
	    horizontalPanel.setOpaque(false);
	    horizontalPanel.setAlignmentX(LEFT_ALIGNMENT);
	    
	    JLabel valorLbl = new JLabel(valor);
	    valorLbl.setFont(Fonts.setFontSegoe(1, 28));
	    valorLbl.setForeground(color);
	    
	    JLabel subTxtLbl = new JLabel(subtexto);
	    subTxtLbl.setFont(Fonts.setFontSegoe(3, 12));
	    subTxtLbl.setForeground(Colores.BG_TEXT_COLOR);
	    
	    horizontalPanel.add(valorLbl);
	    horizontalPanel.add(Box.createRigidArea(new Dimension(15, 0))); 
	    horizontalPanel.add(subTxtLbl);
	    
	    card.add(Box.createRigidArea(new Dimension(0, 5)));
	    card.add(tituloLbl);
	    card.add(Box.createRigidArea(new Dimension(0, 10)));
	    card.add(horizontalPanel); 
	    card.add(Box.createRigidArea(new Dimension(0, 5)));
	    
	    return card;
	}
}
