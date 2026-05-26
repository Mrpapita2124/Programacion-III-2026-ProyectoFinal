package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.util.Graphics2DProxy;

import modelos.Client;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import modelos.User;
import repository.EstadoPrestamoRepository;
import utils.Colores;
import utils.PanelPersonalizable;

public class PrestamoPanel extends JPanel {
	private Font fontTexto = new Font("Times New Roman", Font.BOLD, 35);
	private JButton btnComplete;
	private JButton btnDelete;
	private JButton btnInfo;
	private JButton btnDebt;
	EstadoPrestamoRepository estadoRepository;
	private Prestamo prestamo;
	public PrestamoPanel(Prestamo prestamo) 
	{	
		estadoRepository=new EstadoPrestamoRepository();
		this.prestamo=prestamo;
		
		EstadoPrestamo estadoPrestamo=estadoRepository.getEstadoPrestamoFromPrestamo(prestamo);
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + estadoPrestamo.getId_estado_prestamo());
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setAlignmentX(LEFT_ALIGNMENT);
		setBackground(Colores.LOGIN_PANEL);
		setVisible(true);
		//setSize(new Dimension(600,300));
		
		JPanel prestamoInfo=new JPanel();
		prestamoInfo.setForeground(Color.BLACK);
		prestamoInfo.setOpaque(false);
		prestamoInfo.setLayout(new BoxLayout(prestamoInfo,BoxLayout.Y_AXIS));
		JLabel icono;
		
			// TODO: handle exception
		if(estadoPrestamo.getEstado().equals("correcto")) {
			icono=new JLabel(escalarImagenLocal("..\\img\\greenMoney.png", 280, 280));
		}else {
			icono=new JLabel(escalarImagenLocal("..\\img\\redMoney.png", 280, 280));
		}
			
		
		
		icono.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel clientName= new JLabel(this.prestamo.getNombre());
		clientName.setAlignmentX(Component.CENTER_ALIGNMENT);
		clientName.setForeground(Color.BLACK);
		JLabel clientApellido= new JLabel(this.prestamo.getApellido());
		clientApellido.setAlignmentX(Component.CENTER_ALIGNMENT);
		clientApellido.setForeground(Color.BLACK);
		JLabel prestamoCantidad= new JLabel(String.valueOf(this.prestamo.getMonto()));
		prestamoCantidad.setAlignmentX(Component.CENTER_ALIGNMENT);
		prestamoCantidad.setForeground(Color.BLACK);
		btnComplete=new JButton("Registrar pago");
		btnDelete=new JButton("Eliminar");
		btnInfo=new JButton("Info");
		btnDebt=new JButton("Pagar deuda");
		
		
		clientName.setFont(fontTexto);
		clientApellido.setFont(fontTexto);
		prestamoInfo.add(clientName);
		prestamoInfo.add(clientApellido);
		prestamoInfo.add(prestamoCantidad);
		if(estadoPrestamo.getQuincenasRestantes()!=0) {
			prestamoInfo.add(btnComplete);
		}
		
		prestamoInfo.add(btnDelete);
		prestamoInfo.add(btnInfo);
		if(estadoPrestamo.getEstado().equals("atrasado")) {
			prestamoInfo.add(btnDebt);
		}

		add(Box.createRigidArea(new Dimension(30,0)));
		add(icono);
		add(prestamoInfo);
		add(Box.createRigidArea(new Dimension(180,0)));
		
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
		return btnComplete;
	}
	public void setSession(JButton session) {
		this.btnComplete = session;
	}

	public JButton getBtnDebt() {
		return btnDebt;
	}
	public void setBtnDebt(JButton btnDebt) {
		this.btnDebt = btnDebt;
	}
	public Window getWindow() {
		return SwingUtilities.getWindowAncestor(this);
	}
	public JButton getBtnComplete() {
		return btnComplete;
	}
	public void setBtnComplete(JButton btnEdit) {
		this.btnComplete = btnEdit;
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
	public JButton getBtnInfo() {
		return btnInfo;
	}
	public void setBtnInfo(JButton btnInfo) {
		this.btnInfo = btnInfo;
	}
	public Prestamo getPrestamo() {
		return prestamo;
	}
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}
	
	
}