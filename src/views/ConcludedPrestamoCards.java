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


public class ConcludedPrestamoCards extends PanelPersonalizable 
{
	private Font fontTitulo = Fonts.setFontSegoe(1,15);
	int alto;
	
	public ConcludedPrestamoCards(VentanaPrincipal ventana) 
	{
		ArrayList<JPanel> prestamoCards = new ArrayList<JPanel>();
		PrestamoRepository prestamoRepo = new PrestamoRepository();
		
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  // AÑADIDO: Layout vertical
        
		List<Prestamo> prestamos = prestamoRepo.getAllConcludePrestamos();

		if (prestamos.isEmpty()) 
		{
			JLabel voidMessage = new JLabel("No hay préstamos concluidos"); 
			voidMessage.setOpaque(false);
			voidMessage.setFont(fontTitulo);
			voidMessage.setForeground(Color.WHITE);
			voidMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
			alto = 150;
			
			add(Box.createRigidArea(new Dimension(0, 20)));
			add(voidMessage);
			
		} else {
			alto = 0;
			if(prestamos.size() % 2 == 0) {
				alto = (prestamos.size() / 2) * 300;
			} else {
				alto = ((prestamos.size() / 2) + 1) * 300;
			}
			
			
			add(Box.createRigidArea(new Dimension(0, 100)));
			
			JLabel sectionTitle = new JLabel("Préstamos Concluidos");
			sectionTitle.setFont(Fonts.setFontSegoe(1, 25));
			sectionTitle.setForeground(Colores.PRIMARY_HEADINGS);
			sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(sectionTitle);
			
			add(Box.createRigidArea(new Dimension(0, 50)));
			
			JPanel cardsContainer = new JPanel();
			cardsContainer.setLayout(new GridLayout(0, 2, 15, 15));
			cardsContainer.setOpaque(false);
			cardsContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
			
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
			
			for(JPanel card : prestamoCards) {
				cardsContainer.add(card);
			}
			
			if(prestamos.size() % 2 != 0) {
				JPanel relleno = new JPanel();
				relleno.setBackground(new Color(0, 0, 0, 0));
				relleno.setOpaque(false);
				cardsContainer.add(relleno);
			}
			
			add(cardsContainer);
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