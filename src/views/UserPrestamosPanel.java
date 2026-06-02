package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

import utils.Colores;
import utils.Fonts;

public class UserPrestamosPanel extends JPanel {

	private Font fontTitulo = Fonts.setFontSegoe(1, 25);
	
	PrestamoCards prestamos;
	ConcludedPrestamoCards concludedPrestamos;
	JScrollPane scrollPrestamos;
	VentanaPrincipal ancestro;
	
	public UserPrestamosPanel(VentanaPrincipal ancestro) {
		this.ancestro = ancestro;
		
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(Box.createRigidArea(new Dimension(0, 25)));
		
		JLabel heading = new JLabel("Mis Préstamos"); 
		heading.setOpaque(false);
		heading.setFont(fontTitulo);
		heading.setForeground(Colores.PRIMARY_HEADINGS);
		heading.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(heading);
		
		add(Box.createRigidArea(new Dimension(0, 50)));
		
		JPanel allPrestamos = new JPanel();
		allPrestamos.setLayout(new BoxLayout(allPrestamos, BoxLayout.Y_AXIS));
		allPrestamos.setOpaque(false);
		
		prestamos = new PrestamoCards(ancestro);
		prestamos.setAlignmentX(Component.CENTER_ALIGNMENT);
		allPrestamos.add(prestamos);
		
		
		concludedPrestamos = new ConcludedPrestamoCards(ancestro);
		concludedPrestamos.setAlignmentX(Component.CENTER_ALIGNMENT);
		allPrestamos.add(concludedPrestamos);
		
		// Scroll
		scrollPrestamos = new JScrollPane(allPrestamos);
		scrollPrestamos.setMaximumSize(new Dimension(1400, 600));
		scrollPrestamos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPrestamos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPrestamos.setOpaque(false);
		scrollPrestamos.getViewport().setOpaque(false);
		scrollPrestamos.setBorder(null);
		scrollPrestamos.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		
		scrollPrestamos.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Colores.BUTTON_COLOR1;
				this.trackColor = Color.LIGHT_GRAY;
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
		scrollPrestamos.getVerticalScrollBar().setUnitIncrement(20);
		scrollPrestamos.getVerticalScrollBar().setOpaque(false);
		
		add(scrollPrestamos);
	}
	
	public PrestamoCards getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(PrestamoCards prestamos) {
		this.prestamos = prestamos;
	}
	
	public ConcludedPrestamoCards getConcludedPrestamos() {
		return concludedPrestamos;
	}

	public void setConcludedPrestamos(ConcludedPrestamoCards concludedPrestamos) {
		this.concludedPrestamos = concludedPrestamos;
	}

	public JScrollPane getScrollPrestamos() {
		return scrollPrestamos;
	}
	
	public void setScrollPrestamos(JScrollPane scrollPrestamos) {
		this.scrollPrestamos = scrollPrestamos;
	}
	
	public VentanaPrincipal getAncestro() {
		return ancestro;
	}
	
	public void setAncestro(VentanaPrincipal ancestro) {
		this.ancestro = ancestro;
	}
}