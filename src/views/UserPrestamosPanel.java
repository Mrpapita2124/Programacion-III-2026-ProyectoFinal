package views;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

import utils.Colores;

public class UserPrestamosPanel extends JPanel {
	private Font fontTitulo = new Font("Times New Roman", Font.BOLD, 35);
	
	PrestamoCards prestamos;
	ConcludedPrestamoCards concludedPrestamos;
	JScrollPane scrollClients;
	VentanaPrincipal ancestro;
	public UserPrestamosPanel(VentanaPrincipal ancestro) {
		this.ancestro=ancestro;
		setBackground(Colores.BACKGROUND);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//navBAr
		concludedPrestamos= new ConcludedPrestamoCards(ancestro);
		prestamos=new PrestamoCards(ancestro);
		
		
		JLabel saludo = new JLabel("Tus clientes"); 
        saludo.setOpaque(false);
        saludo.setFont(fontTitulo);
        saludo.setForeground(Colores.TEXT_COLOR);
        saludo.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(saludo);
		//createClientList(ancestro);
		JPanel allPrestamos= new JPanel();
		allPrestamos.setSize(new Dimension(1200,concludedPrestamos.getAlto()+prestamos.getAlto()));
		allPrestamos.setLayout(new BoxLayout(allPrestamos, BoxLayout.Y_AXIS));
		allPrestamos.add(prestamos);
		allPrestamos.add(concludedPrestamos);
		
		scrollClients= new JScrollPane(allPrestamos);
		scrollClients.setMaximumSize(new Dimension(1200,600));
		scrollClients.setSize(new Dimension(1200,concludedPrestamos.getAlto()+prestamos.getAlto()));
		scrollClients.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollClients.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollClients.setOpaque(false);
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
		add(scrollClients);
	}
	
	
	public PrestamoCards getPrestamos() {
		return prestamos;
	}


	public void setPrestamos(PrestamoCards prestamos) {
		this.prestamos = prestamos;
	}

	public JScrollPane getScrollClients() {
		return scrollClients;
	}
	public void setScrollClients(JScrollPane scrollClients) {
		this.scrollClients = scrollClients;
	}
	/*public void createClientList(VentanaPrincipal ventana) {
		clients= new ClientCards(this,ventana);
	}*/
	
	public VentanaPrincipal getAncestro() {
		return ancestro;
	}
	public void setAncestro(VentanaPrincipal ancestro) {
		this.ancestro = ancestro;
	}
	
}