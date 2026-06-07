package vistas.cliente;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.*;

import repositorios.ClienteRepository;
import repositorios.PrestamoRepository;
import utilidades.Colores;
import utilidades.Fuentes;
import utilidades.PanelPersonalizable;
import utilidades.RoundedBorder;
import utilidades.Sesion;
import vistas.otros.VentanaPrincipal;


public class ClientesEstadisticas extends PanelPersonalizable {

	public ClientesEstadisticas() 
	{
        setLayout(new GridLayout(1, 3, 20, 0));
        setBackground(Colores.COLOR_GRADIENTE1);
        setBorder(new RoundedBorder(Colores.BORDE_POR_DEFECTO, 35, 2));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));
        
        ClienteRepository clienteRepository = new ClienteRepository();
        PrestamoRepository prestamoRepository = new PrestamoRepository();
        
        add(crearCardEstadisticas("TOTAL CLIENTES", String.valueOf(ClienteRepository.getTotalNumeroDeClientes()), "Clientes registrados", Colores.ENCABEZADOS_PRIMARIOS, false));
        add(crearCardEstadisticas("PRESTAMOS ACTIVOS", String.valueOf(prestamoRepository.getTotalNumeroDePrestamos()), "En seguimiento", Colores.ENCABEZADOS_PRIMARIOS, true));
        add(crearCardEstadisticas("PRESTAMOS ATRASADOS", String.valueOf(ClienteRepository.getTotalNumeroDePrestamosAtrasaados()), "Atención requerida", Colores.ENCABEZADOS_PRIMARIOS, false));
    }
    
	private JPanel crearCardEstadisticas(String titulo, String valor, String subtexto, Color color, boolean clickable) 
	{
	    PanelPersonalizable carta = new PanelPersonalizable() {
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
	    
	    carta.setLayout(new BoxLayout(carta, BoxLayout.Y_AXIS));
	    
	    carta.setBackground(Colores.LOGIN_PANEL);
	    carta.setOpaque(false); 
	    
	    javax.swing.border.Border cardBorder = BorderFactory.createCompoundBorder(
	        new RoundedBorder(Colores.BORDE_POR_DEFECTO, 25, 1),
	        BorderFactory.createEmptyBorder(25, 25, 15, 15)
	    );
	    
	    carta.setBorder(cardBorder);
	    carta.setFocusable(false);
	    
	    if(clickable) 
	    {
	        carta.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        carta.addMouseListener(new java.awt.event.MouseAdapter() 
	        {
	            @Override
	            public void mouseClicked(java.awt.event.MouseEvent evt) 
	            {
	                //System.out.println("PRESTAMOS ACTIVOS clicked!");
	                VentanaPrincipal frame = (VentanaPrincipal) SwingUtilities.getWindowAncestor(carta);
	                frame.mostrarVista(VentanaPrincipal.PRESTAMOS);
	            }
	            
	            @Override
	            public void mouseEntered(java.awt.event.MouseEvent evt) 
	            {
	                carta.setBackground(Colores.CLIENTE_CARTA_FONDO);
	                carta.repaint();
	            }
	            
	            @Override
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	                carta.setBackground(Colores.LOGIN_PANEL);
	                carta.repaint();
	            }
	        });
	    }
	    
	    JLabel tituloLbl = new JLabel(titulo);
	    tituloLbl.setFont(Fuentes.setFontSegoe(1, 14));
	    tituloLbl.setForeground(Color.LIGHT_GRAY);
	    tituloLbl.setAlignmentX(LEFT_ALIGNMENT);
	    
	    JPanel horizontalPanel = new JPanel();
	    horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
	    horizontalPanel.setOpaque(false);
	    horizontalPanel.setAlignmentX(LEFT_ALIGNMENT);
	    
	    JLabel valorLbl = new JLabel(valor);
	    valorLbl.setFont(Fuentes.setFontSegoe(1, 28));
	    valorLbl.setForeground(color);
	    
	    JLabel subTxtLbl = new JLabel(subtexto);
	    subTxtLbl.setFont(Fuentes.setFontSegoe(3, 12));
	    subTxtLbl.setForeground(Colores.FONDO_TEXTO_COLOR);
	    
	    horizontalPanel.add(valorLbl);
	    horizontalPanel.add(Box.createRigidArea(new Dimension(15, 0))); 
	    horizontalPanel.add(subTxtLbl);
	    
	    carta.add(Box.createRigidArea(new Dimension(0, 5)));
	    carta.add(tituloLbl);
	    carta.add(Box.createRigidArea(new Dimension(0, 10)));
	    carta.add(horizontalPanel); 
	    carta.add(Box.createRigidArea(new Dimension(0, 5)));
	    
	    return carta;
	}
}
