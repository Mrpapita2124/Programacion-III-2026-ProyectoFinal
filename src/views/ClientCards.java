package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controllers.ClientPanelController;
import controllers.UserPanelController;
import modelos.Client;
import modelos.User;
import repository.ClientRepository;
import repository.UserRepository;
import tablemodels.UserTableModel;
import utils.Colores;
import utils.Fonts;
import utils.PanelPersonalizable;
import utils.RoundedBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClientCards extends PanelPersonalizable 
{
	private Font fontTitulo = Fonts.setFontSegoe(1,15);
	int alto;
	
	public ClientCards(UserClientsPanel userPanel,VentanaPrincipal ventana) 
	{
		ArrayList<JPanel> clientCards = new ArrayList<JPanel>();
		ClientRepository repository = new ClientRepository();
		
		setBackground(Colores.COLOR_GRADIENT1);
		setOpaque(true);
		
		List<Client> clients = repository.getClients(); 

		
		if (clients.isEmpty()) 
		{
			JLabel voidMessage = new JLabel("No tienes clientes"); 
			voidMessage.setOpaque(false);
			voidMessage.setFont(fontTitulo);
			voidMessage.setForeground(Color.WHITE);
			voidMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
			alto=300;
			
			add(Box.createRigidArea(new Dimension(0, 45)));
			add(voidMessage);
			
		}else {
			alto=0;
			//define alto
			if(clients.size()%2 == 0) {
				
				alto=(clients.size()/2) * 200;
			}else {
				alto=((clients.size()/2)+1) * 200;
			}
			
			//Añade usuarios simullados"
			for(int i=0;i<clients.size();i++) {
				
				PanelPersonalizable card=new PanelPersonalizable();
				card.setBackground(Colores.LOGIN_PANEL);
				card.setOpaque(true);
				
				ClientPanel clientPanel=new ClientPanel(clients.get(i));
				new ClientPanelController(clientPanel,userPanel,ventana);
				clientPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
				
				card.add(clientPanel);
				clientCards.add(card);
			}
			
			setLayout(new GridLayout(0,2, 15, 15));
			
			
			for(JPanel card : clientCards) {
				
				add(card);
			}
			if(clients.size()%2!=0) {
				
				JPanel relleno = new JPanel();
	            relleno.setBackground(Colores.COLOR_GRADIENT1);
	            relleno.setOpaque(true);
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