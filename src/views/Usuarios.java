package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controllers.UserPanelController;
import modelos.User;
import repository.UserRepository;
import tablemodels.UserTableModel;
import utils.Colores;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Usuarios extends JPanel {
	
	public Usuarios() {
		ArrayList<UserPanel> usuarios = new ArrayList<UserPanel>();
		UserRepository repository = new UserRepository();
		try {
			List<User> users = repository.getUsers(); 
			
			UserTableModel usersTable = new UserTableModel(users);
			
			for(int i = 0; i < usersTable.getRowCount(); i++) {
				if(usersTable.getValueAt(i, 4).toString().equals("true")) {
					UserPanel usuarioPanel = new UserPanel(usersTable.getUserAt(i));
					new UserPanelController(usuarioPanel);
					usuarios.add(usuarioPanel);
				}
			}
			
			
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setBackground(new Color(0, 0, 0, 0)); 
			setOpaque(false); 
			setBorder(new EmptyBorder(10, 20, 10, 20));
			
			JLabel titleQuick = new JLabel("CUENTAS GUARDADAS");
			titleQuick.setFont(new Font("Segoe UI", Font.BOLD, 12));
			titleQuick.setForeground(Colores.SECONDARY_HEADINGS);
			titleQuick.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(titleQuick);
			add(Box.createRigidArea(new Dimension(0, 5)));
			
			JLabel titleSign = new JLabel("Inicia Sesion");
			titleSign.setFont(new Font("Segoe UI", Font.BOLD, 28));
			titleSign.setForeground(Colores.PRIMARY_HEADINGS);
			titleSign.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(titleSign);
			add(Box.createRigidArea(new Dimension(0, 20)));
			
			
			// Aqui se ponen todas los cuardos o cartas, como quieras llamarlo
			for(int i = 0; i < usuarios.size(); i++) 
			{
				UserPanel usuario = usuarios.get(i);
				usuario.setAlignmentX(Component.CENTER_ALIGNMENT); 
				add(usuario);
				add(Box.createRigidArea(new Dimension(0, 12))); 
			}
			
			setVisible(true);
			
		} 
		catch (IOException ex) 
		{
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
	}
}