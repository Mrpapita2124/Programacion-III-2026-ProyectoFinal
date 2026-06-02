package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Window;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import modelos.User;
import utils.Colores;
import utils.Fonts;

public class UserPanel extends JPanel {
	private Font fontNombre = Fonts.FONT_SEGOE_BOLD;
	private Font fontEmail = Fonts.FONT_SEGOE;
	private JButton session;
	private User user;
	private boolean seleccionado = false;
	
	public UserPanel(User user) 
	{	
		this.user = user;
		inicializarPanel();
		crearComponentes();
	}
	
	private void inicializarPanel() 
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(Colores.CARD_BG);
		setOpaque(true);
		
		setBorder(BorderFactory.createCompoundBorder(
			new LineBorder(Colores.DEFAULT_BORDER, 1, true),
			new EmptyBorder(12, 20, 12, 20)
		));

		// Esto cambia tamano de cartas pero si le mueves aqui tambien se tiene que hacer en la clase Usuario
		setPreferredSize(new Dimension(320, 90));
		setMinimumSize(new Dimension(320, 90));
		setMaximumSize(new Dimension(320, 90));
		
		//setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); // Ya descubri como hacer que el mouse se ponga como una mano que peudes selecionar
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(320, 90);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(320, 90);
	}
	
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(320, 90);
	}
	
	private void crearComponentes() {
		
		JLabel avatar = crearAvatar();
		avatar.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBackground(Colores.CARD_BG);
		infoPanel.setBorder(new EmptyBorder(0, 12, 0, 0));
		
		String nombreCompleto = (user.getNombre() != null ? user.getNombre() : "") + 
							   " " + (user.getApellido() != null ? user.getApellido() : "");
		
		nombreCompleto = nombreCompleto.length() > 15 ? nombreCompleto.substring(0, 15) + "..." : nombreCompleto;
		
		
		JLabel lblNombre = new JLabel(nombreCompleto.trim());
		lblNombre.setFont(fontNombre);
		lblNombre.setForeground(Colores.PRIMARY_HEADINGS);
		
		// Correo electrónico
		String email = user.getCorreo() != null ? user.getCorreo() : "";
		
		email = email.length() > 20 ? email.substring(0, 20) + "..." : email;
		
		JLabel lblEmail = new JLabel(email);
		lblEmail.setFont(fontEmail);
		lblEmail.setForeground(Colores.SECONDARY_HEADINGS);
	
		infoPanel.add(lblNombre);
		infoPanel.add(Box.createRigidArea(new Dimension(0, 6)));
		infoPanel.add(lblEmail);
		
		// Boton para iniciar sesin
		session = new JButton("Iniciar");
		session.setFont(new Font("Segoe UI", Font.BOLD, 12));
		session.setBackground(Colores.LOGIN_BUTTON);
		session.setForeground(Colores.BUTTON_TEXT);
		session.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
		session.setFocusPainted(false);
		session.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		session.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		
		session.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				session.setBackground(Colores.LOGIN_BUTTON_HOVER);
			}
			
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				session.setBackground(Colores.LOGIN_BUTTON);
			}
		});
		
		add(avatar);
		add(infoPanel);
		add(Box.createHorizontalGlue());
		add(session);
		
		
		// Aqui puse lo de picarle a la carta para reemplazar el boton de inicar. Si quieres verlo solo descomenta esto y ya.
		/*
		addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				if (!seleccionado) 
				{
					setBorder(BorderFactory.createCompoundBorder(
							new LineBorder(Colores.FOCUS_RING, 2, true),
							new EmptyBorder(11, 19, 11, 19)
						));
					
					setBackground(Colores.INPUT_BORDER);
					infoPanel.setBackground(Colores.INPUT_BORDER);
				}
			}
			
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				if (!seleccionado) 
				{
					setBorder(BorderFactory.createCompoundBorder(
							new LineBorder(Colores.INPUT_BORDER, 1, true),
							new EmptyBorder(12, 20, 12, 20)
						));
					
					setBackground(Colores.CARD_BG);
					infoPanel.setBackground(Colores.CARD_BG);
				}
			}
			
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				setSeleccionado(true);
			}
		});
		*/
	}
	
	// Gracias chatGPT 
	private JLabel crearAvatar() {
		JLabel avatar = new JLabel() 
		{
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				if (getIcon() == null) 
				{
					g2d.setColor(Colores.AVATAR_GRADIENT1);
					g2d.fillOval(0, 0, 55, 55);
					
					g2d.setColor(Color.WHITE);
					g2d.setFont(new Font("Segoe UI", Font.BOLD, 22));
					
					// Aqui puse todo lo de inciales en caso que el usuario pues no haya puesto foto, mira abajo para ver el metodo.
					String iniciales = obtenerIniciales();
					
					int anchoTexto = g2d.getFontMetrics().stringWidth(iniciales);
					int altoTexto = g2d.getFontMetrics().getAscent();
					g2d.drawString(iniciales, (55 - anchoTexto) / 2, (55 + altoTexto) / 2 - 2);
				}
				
				g2d.dispose();
				super.paintComponent(g);
			}
		};
		
		avatar.setPreferredSize(new Dimension(55, 55));
		avatar.setMinimumSize(new Dimension(55, 55));
		avatar.setMaximumSize(new Dimension(55, 55));
		
		// Intento poner la foto de perfil
		try {
			if (user.getFoto() != null && !user.getFoto().isEmpty()) 
			{
				
				ImageIcon iconoOriginal = new ImageIcon(user.getFoto());
				Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);
				avatar.setIcon(new ImageIcon(imagenEscalada));
			} 
			else 
			{
				avatar.setIcon(null);
			}
		} 
		catch (Exception e) 
		{
			avatar.setIcon(null);
		}
		
		return avatar;
	}
	
	// Si no tiene foto de perfil se pone los primeros iniciales. Eje: JC -> Jose Carlos, literal lo unico que me salio bien.
	private String obtenerIniciales() 
	{
		String iniciales = "";
		
		if (user.getNombre() != null && !user.getNombre().isEmpty()) 
		{
			iniciales += user.getNombre().charAt(0);
		}
		
		if (user.getApellido() != null && !user.getApellido().isEmpty()) 
		{
			iniciales += user.getApellido().charAt(0);
		}
		
		return iniciales.isEmpty() ? "N/A" : iniciales.toUpperCase(); // Esto en caso que algo salga vacio pero segun yo no puede pasar.
	}
	
	
	// Puse esto para reemplazar lo de el boton 'iniciar' y solo picarle a la carta se me hizo buena idea pero no se.
	public void setSeleccionado(boolean seleccionado) 
	{
		this.seleccionado = seleccionado;
		
		if (seleccionado) 
		{
			setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(Colores.FOCUS_RING, 2, true),
				new EmptyBorder(11, 19, 11, 19)
			));
			setBackground(Colores.DEFAULT_BORDER);
		} 
		else 
		{
			setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(Colores.DEFAULT_BORDER, 1, true),
				new EmptyBorder(12, 20, 12, 20)
			));
			setBackground(Colores.CARD_BG);
		}
		
		
		revalidate();
		repaint();
	}
	
	public boolean isSeleccionado() {
		return seleccionado;
	}
	
	public JButton getSessionButton() {
		return session;
	}
	
	public void setSession(JButton session) {
		this.session = session;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Window getWindow() {
		return SwingUtilities.getWindowAncestor(this);
	}
}