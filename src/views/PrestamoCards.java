package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controllers.ClientPanelController;
import controllers.UserPanelController;
import modelos.Client;
import modelos.Prestamo;
import modelos.User;
import repository.ClientRepository;
import repository.PrestamoRepository;
import repository.UserRepository;
import tablemodels.UserTableModel;
import utils.Colores;
import utils.PanelPersonalizable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PrestamoCards extends JPanel {
	private Font fontTitulo = new Font("Times New Roman", Font.BOLD, 35);
	int alto=0;
	public PrestamoCards(VentanaPrincipal ventana) {
		ArrayList<JPanel> prestamoCards = new ArrayList<JPanel>();
		
		PrestamoRepository repository = new PrestamoRepository();
		setBackground(Colores.BACKGROUND);
		
		List<Prestamo> prestamos = repository.getAllPrestamos();
		System.out.println(prestamos.size());
		if (prestamos.isEmpty()) {
			JLabel voidMessage = new JLabel("No tienes prestamos"); 
			voidMessage.setOpaque(false);
			voidMessage.setFont(fontTitulo);
			voidMessage.setForeground(Color.WHITE);
			voidMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
			alto=300;
			add(voidMessage);
			
		}else {
			alto=0;
			//define alto
			if(prestamos.size()%2==0) {
				
				alto=(prestamos.size()/2)*300;
			}else {
				alto=((prestamos.size()/2)+1)*300;
			}
			//Añade usuarios simullados"
			for(int i=0;i<prestamos.size();i++) {
				PanelPersonalizable card=new PanelPersonalizable();
				//card.setBounds(130, 120, 950, 450);
				card.setBackground(Colores.LOGIN_PANEL);
				
				
					PrestamoPanel prestamoPanel=new PrestamoPanel(prestamos.get(i));
					//new UserPanelController(clientPanel);
					
					//new ClientPanelController(clientPanel,userPanel,ventana);
					card.add(prestamoPanel);
					//clientPanel.setMaximumSize(new Dimension(600,300));
					prestamoCards.add(card);
					
			}
			
			//System.out.println(alto);
			
			setOpaque(false);
			setLayout(new GridLayout(0,2));
			
			
			for(JPanel card : prestamoCards) {
				
				add(card);
			}
			if(prestamos.size()%2!=0) {
				JPanel relleno = new JPanel();
				relleno.setBackground(Colores.BACKGROUND);
				add(relleno);
				
			}
		}
			
			setVisible(true);
		
		
		
		
	}
	private ImageIcon escalarImagen(String direccion,int x,int y) {
	    	//System.out.println(direccion);
	        ImageIcon iconoOriginal = new ImageIcon(direccion);
	
	       
	        Image imagenEscalada = iconoOriginal.getImage()
	                .getScaledInstance(x, y, Image.SCALE_SMOOTH);
	
	        
	        ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
	        iconoFinal.setDescription(direccion);
	        return iconoFinal;
	}
	private ImageIcon escalarImagenLocal(String direccion,int x,int y) {
    	System.out.println(direccion);
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(direccion));

       
        Image imagenEscalada = iconoOriginal.getImage()
                .getScaledInstance(x, y, Image.SCALE_SMOOTH);

        
        ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
        iconoFinal.setDescription(direccion);
        return iconoFinal;
	}
	public int getAlto() {
		return alto;
	}
	public void setAlto(int alto) {
		this.alto = alto;
	}
	
}