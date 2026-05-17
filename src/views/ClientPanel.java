package views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;

import javax.swing.*;
import javax.swing.border.LineBorder;

import modelos.Client;
import modelos.User;
import utils.Colores;

public class ClientPanel extends JPanel {
	private Font fontTexto = new Font("Times New Roman", Font.BOLD, 17);
	private JButton btnEdit;
	private JButton btnDelete;
	Client client;
	public ClientPanel(Client client) 
	{	
		this.client=client;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Colores.PROFILE_PANEL);
		setVisible(true);
		setSize(new Dimension(600,300));
		setBorder(new LineBorder(Colores.LOGIN_PANEL, 10));
		JPanel clientInfo=new JPanel();
		clientInfo.setOpaque(false);
		clientInfo.setLayout(new BoxLayout(clientInfo,BoxLayout.Y_AXIS));
		JLabel icono;
		try {
			icono=new JLabel(escalarImagen(this.client.getIneDireccion(), 200, 200));
		} catch (Exception e) {
			// TODO: handle exception
			icono=new JLabel(escalarImagenLocal("..\\img\\LicenseDefault.png", 200, 200));
		}
		
		icono.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel clientName= new JLabel(this.client.getNombre());
		clientName.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel clientApellido= new JLabel(this.client.getApellido());
		clientApellido.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEdit=new JButton("Editar");
		btnDelete=new JButton("Eliminar");
		btnEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		clientName.setFont(fontTexto);
		clientApellido.setFont(fontTexto);
		clientInfo.add(clientName);
		clientInfo.add(clientApellido);
		clientInfo.add(btnEdit);
		clientInfo.add(btnDelete);
		add(Box.createRigidArea(new Dimension(30,0)));
		add(icono);
		add(clientInfo);
		
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
	private ImageIcon escalarImagenLocal(String direccion,int x,int y) {
		System.out.println("nigga");
	    ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(direccion));
	
	   
	    Image imagenEscalada = iconoOriginal.getImage()
	            .getScaledInstance(x, y, Image.SCALE_SMOOTH);
	
	    
	    ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
	    iconoFinal.setDescription(direccion);
	    return iconoFinal;
	}
	public JButton getSession() {
		return btnEdit;
	}
	public void setSession(JButton session) {
		this.btnEdit = session;
	}
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Window getWindow() {
		return SwingUtilities.getWindowAncestor(this);
	}
	public JButton getBtnEdit() {
		return btnEdit;
	}
	public void setBtnEdit(JButton btnEdit) {
		this.btnEdit = btnEdit;
	}
	public Font getFontTexto() {
		return fontTexto;
	}
	public void setFontTexto(Font fontTexto) {
		this.fontTexto = fontTexto;
	}
	public JButton getBtnDelete() {
		return btnDelete;
	}
	public void setBtnDelete(JButton btnDelete) {
		this.btnDelete = btnDelete;
	}
	
}
