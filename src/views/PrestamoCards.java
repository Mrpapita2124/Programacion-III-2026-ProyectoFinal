package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controllers.PrestamoPanelController;
import modelos.Prestamo;
import repository.PrestamoRepository;
import utils.Colores;
import utils.Fonts;
import utils.PanelPersonalizable;
import utils.RoundedBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;


public class PrestamoCards extends PanelPersonalizable 
{
	private Font fontTitulo = Fonts.setFontSegoe(1,15);
	int alto;
	
	public PrestamoCards(VentanaPrincipal ventana) 
	{
		ArrayList<JPanel> prestamoCards = new ArrayList<JPanel>();
		PrestamoRepository prestamoRepo = new PrestamoRepository();
		
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
		
		List<Prestamo> prestamos = prestamoRepo.getAllActivePrestamosFromUser();

		if (prestamos.isEmpty()) 
		{
			JLabel voidMessage = new JLabel("No tienes préstamos activos"); 
			voidMessage.setOpaque(false);
			voidMessage.setFont(fontTitulo);
			voidMessage.setForeground(Color.WHITE);
			voidMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
			alto = 300;
			
			add(Box.createRigidArea(new Dimension(0, 45)));
			add(voidMessage);
			
		} else {
			alto = 0;
			// define alto
			if(prestamos.size() % 2 == 0) {
				alto = (prestamos.size() / 2) * 300;
			} else {
				alto = ((prestamos.size() / 2) + 1) * 300;
			}
			
			for(int i = 0; i < prestamos.size(); i++) {
				PanelPersonalizable card = new PanelPersonalizable();
				card.setBackground(Colores.CLIENT_CARD_BG);
				card.setBorder(new RoundedBorder(Colores.DEFAULT_BORDER, 20, 2));
				card.setOpaque(true);
				
				PrestamoInfoPanel prestamoPanel = new PrestamoInfoPanel(prestamos.get(i));
				new PrestamoPanelController(prestamoPanel, ventana);
				prestamoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				
				card.add(prestamoPanel);
				prestamoCards.add(card);
			}
			
			setLayout(new GridLayout(0, 2, 15, 15));
			
			for(JPanel card : prestamoCards) {
				add(card);
			}
			
			if(prestamos.size() % 2 != 0) {
				JPanel relleno = new JPanel();
				relleno.setBackground(new Color(0, 0, 0, 0));
				relleno.setOpaque(false);
				add(relleno);
			}
		}
		
		setVisible(true);
	}
	
	public int getAlto() {
		return alto;
	}
	
	public void setAlto(int alto) {
		this.alto = alto;
	}
}