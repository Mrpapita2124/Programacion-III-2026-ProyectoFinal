package views.client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controllers.client.ClientPanelController;
import controllers.user.UserPanelController;
import modelos.Client;
import modelos.User;
import repository.ClientRepository;
import repository.UserRepository;
import tablemodels.UserTableModel;
import utils.Colores;
import utils.Fonts;
import utils.PanelPersonalizable;
import utils.RoundedBorder;
import utils.Session;
import views.VentanaPrincipal;
import views.user.UserClientsPanel;

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
		
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
		
		
		List<Client> clients = Session.getClientesFiltrados(); 
		
		if(clients != null)
		{
			int counter = 1;
			//System.out.println("\n------CLIENTES------------");
			for (Client client : clients) 
			{
				//System.out.println(counter + ": " + client.getNombre());
				counter++;
			}
		}
		
		
		if (clients == null || clients.isEmpty()) 
		{
			String message = clients == null ? "No se encontraron clientes con Filtro " : "No tienes clientes";
			JLabel voidMessage = new JLabel(message); 
			
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
				
				alto=(clients.size()/2) * 300;
			}else {
				alto=((clients.size()/2)+1) * 300;
			}
			
			//Añade usuarios simullados"
			for(int i=0;i<clients.size();i++) {
				
				PanelPersonalizable card = new PanelPersonalizable();
				card.setBackground(Colores.CLIENT_CARD_BG);
				card.setBorder(new RoundedBorder(Colores.DEFAULT_BORDER, 20, 2));
				card.setOpaque(true);
				
				ClientPanel clientListPanel = new ClientPanel(clients.get(i));
				new ClientPanelController(clientListPanel,userPanel,ventana);
				clientListPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
				
				card.add(clientListPanel);
				clientCards.add(card);
			}
			
			setLayout(new GridLayout(0,2, 15, 15));
			
			
			for(JPanel card : clientCards) {
				
				add(card);
			}
			if(clients.size()%2!=0) {
				
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