package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import utils.Colores;
import utils.Fonts;
import utils.PanelPersonalizable;


public class ClientesEstadisticas extends PanelPersonalizable {

	public ClientesEstadisticas() 
	{
        setLayout(new GridLayout(1, 3, 20, 0));
        setBackground(Colores.COLOR_GRADIENT1);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));
        
        add(crearCardEstadisticas("TOTAL CLIENTES", "1,284", "+12% este mes", Colores.PRIMARY_HEADINGS));
        add(crearCardEstadisticas("PRESTAMOS ACTIVOS", "856", "74% de la cartera", Colores.PRIMARY_HEADINGS));
        add(crearCardEstadisticas("PRESTAMOS ATRASADOS", "42", "Atención requerida", Colores.PRIMARY_HEADINGS));
    }
    
    private JPanel crearCardEstadisticas(String titulo, String valor, String subtexto, Color color) 
    {
    	PanelPersonalizable card = new PanelPersonalizable();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Colores.LOGIN_PANEL);
        
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colores.DEFAULT_BORDER, 1),
            BorderFactory.createEmptyBorder(25, 25, 15, 15)
        ));
        
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
