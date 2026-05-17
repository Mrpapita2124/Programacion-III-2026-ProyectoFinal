package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controllers.UserPanelController;
import modelos.User;
import repository.UserRepository;
import tablemodels.UserTableModel;
import utils.Colores;
import utils.PanelPersonalizable;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Usuarios extends JPanel {
	private Font fontTitulo = new Font("Times New Roman", Font.BOLD, 35);
	public Usuarios() {
		ArrayList<UserPanel> usuarios = new ArrayList<UserPanel>();
		UserRepository repository = new UserRepository();
		try 
		{
			List<User> users = repository.getUsers(); 
			
		
			UserTableModel usersTable=new UserTableModel(users);
			int alto=0;
			
			
			//define alto
			if(usersTable.getNumGuardar()%2==0) {
				
				alto=(usersTable.getNumGuardar()/2)*300;
			}else {
				alto=((usersTable.getNumGuardar()/2)+1)*300;
			}
			//Añade usuarios simullados"
			for(int i=0;i<usersTable.getRowCount();i++) {
				if(usersTable.getValueAt(i, 4).toString().equals("true")) {
					UserPanel usuarioPanel=new UserPanel(usersTable.getUserAt(i));
					new UserPanelController(usuarioPanel);
					usuarios.add(usuarioPanel);
					
				}
				
				
			}
			
			//System.out.println(alto);
			setPreferredSize(new Dimension(600,alto));
			setOpaque(false);
			setLayout(new GridLayout(0,2));
			
			
			for(UserPanel usuario : usuarios) {
				
				add(usuario);
			}
			if(usuarios.size()%2!=0) {
				PanelPersonalizable relleno = new PanelPersonalizable();
				relleno.setBackground(Colores.LOGIN_PANEL);
				add(relleno);
			}
			setVisible(true);
		} 
		catch (IOException ex) 
		{
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
		
		
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
}
