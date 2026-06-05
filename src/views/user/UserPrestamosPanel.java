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

import utils.Colores;
import utils.Fonts;
import views.VentanaPrincipal;
import views.prestamo.ConcludedPrestamoCards;
import views.prestamo.PrestamoCards;

public class UserPrestamosPanel extends JPanel {

	private Font fontTitulo = Fonts.setFontSegoe(1, 35);
	private Font fontBotones = Fonts.setFontSegoe(0, 14);
	
	private JButton btnFilter;
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
		
		JPanel panelTitulo = new JPanel(new BorderLayout());
		panelTitulo.setOpaque(false);
		panelTitulo.setMaximumSize(new Dimension(1200, 50));
		panelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel izqPanel = new JPanel();
		izqPanel.setOpaque(false);
		izqPanel.setPreferredSize(new Dimension(80, 30));
		
		JLabel tituloLbl = new JLabel("Mis Préstamos"); 
		tituloLbl.setOpaque(false);
		tituloLbl.setFont(fontTitulo);
		tituloLbl.setForeground(Colores.PRIMARY_HEADINGS);
		
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
		btnPanel.setOpaque(false);
		
		// BOTON FILTAR PERO EN PRESTAMOOSSS
		
		btnFilter = new JButton("Filtrar");
		btnFilter.setFont(fontBotones);
		btnFilter.setBackground(Colores.BUTTON_COLOR4);
		btnFilter.setForeground(Color.WHITE);
		btnFilter.setFocusPainted(false);
		btnFilter.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
		btnFilter.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		int iconSize = 20;
		
		try 
		{
			btnFilter.setIcon(escalarImagen("src\\img\\cliente_filtrar.png", iconSize, iconSize));
		} 
		catch (Exception e) 
		{
			System.out.println("No se cargo lo iconos de filtar cliente!");
		}
		
		btnPanel.add(btnFilter);
		
		panelTitulo.add(izqPanel, BorderLayout.WEST);
		panelTitulo.add(tituloLbl, BorderLayout.CENTER);
		panelTitulo.add(btnPanel, BorderLayout.EAST);
		
		panelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		add(panelTitulo);
		
		
		
		add(Box.createRigidArea(new Dimension(0, 50)));
		
		JPanel allPrestamos = new JPanel();
		allPrestamos.setLayout(new BoxLayout(allPrestamos, BoxLayout.Y_AXIS));
		allPrestamos.setOpaque(false);
		
		prestamos = new PrestamoCards(ancestro);
		prestamos.setAlignmentX(Component.CENTER_ALIGNMENT);
		allPrestamos.add(prestamos);
		
		/*
		concludedPrestamos = new ConcludedPrestamoCards(ancestro);
		concludedPrestamos.setAlignmentX(Component.CENTER_ALIGNMENT);
		allPrestamos.add(concludedPrestamos);
		*/
		
		
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
	
	public JButton getBtnFilter() {
		return btnFilter;
	}
	
	public void setBtnFilter(JButton btnFilter) {
		this.btnFilter = btnFilter;
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