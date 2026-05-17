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

public class UserClientsPanel extends JPanel {
	private Font fontTitulo = new Font("Times New Roman", Font.BOLD, 35);
	private JButton btnRegister;
	public UserClientsPanel() {
		setBackground(Colores.BACKGROUND);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//navBAr
		JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		btnRegister = new JButton("Registrar Usuario");
		navbar.add(btnRegister);
		navbar.setMaximumSize(new Dimension(1600,25));
		add(navbar);
		
		
		JLabel saludo = new JLabel("Tus clientes"); 
        saludo.setOpaque(false);
        saludo.setFont(fontTitulo);
        saludo.setForeground(Colores.TEXT_COLOR);
        saludo.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(saludo);
		
		ClientCards clients= new ClientCards();
		JScrollPane scrollClients= new JScrollPane(clients);
		scrollClients.setMaximumSize(new Dimension(1200,clients.getAlto()));
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
	public JButton getBtnRegister() {
		return btnRegister;
	}
	public void setBtnRegister(JButton btnRegister) {
		this.btnRegister = btnRegister;
	}
	
}
