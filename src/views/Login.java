package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import utils.Colores;
import utils.PanelPersonalizable;


public class Login extends JPanel{

	public int x;
	public int y;
	public boolean logrado;
	private Font fontTexto = new Font("Times New Roman", Font.ITALIC, 17);
	private Font fontBoton = new Font("Times New Roman", Font.BOLD, 17);
	private Font fontTitulo = new Font("Times New Roman", Font.BOLD, 30);
	JLabel mensajeCorreo = new JLabel(" ");
	JLabel mensajeContraseña = new JLabel(" ");
	JTextField correo = new JTextField(30);
	JPasswordField contraseña = new JPasswordField(30);
	JButton buttonIniciar= new JButton("Iniciar Sesión");
	JButton Registrarse = new JButton(" Registrarse   ");
	
	public Login(){	
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(50,100,50,0));
		setBackground(Colores.LOGIN_PANEL);
		
		configurarMensajes();
		configurarTextos();
		
		JLabel saludo = new JLabel("  Bienvenido!");
		saludo.setForeground(Colores.LOGIN_PANEL_TEXT);
		saludo.setFont(fontTitulo);
		add(saludo);
		
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(mensajeCorreo);
		
		add(correo);
		add(Box.createRigidArea(new Dimension(0,20)));
		add(mensajeContraseña);
		
		add(contraseña);
		add(Box.createRigidArea(new Dimension(0,40)));
		
		configurarBotones();
		FlatLightLaf.setup();
	}
	
	
	private void configurarBotones()
	
	{
		PanelPersonalizable botonesPanel = new PanelPersonalizable();
		botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));
		botonesPanel.setBackground(Colores.LOGIN_PANEL1);
		botonesPanel.setBorder(new EmptyBorder(0,15,0,0));
		
		
		crearBoton(buttonIniciar, "..\\img\\login.png", "Clic para Iniciar Sesión!");
		
		
		botonesPanel.add(buttonIniciar);
		
		buttonIniciar.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) 
			{
				changeBackground(buttonIniciar);
			}
			
			public void mouseExited(MouseEvent e) 
			{
				resetBackground(buttonIniciar);
			}
		});
		
		

		crearBoton(Registrarse, "..\\img\\enter.png", "Clic para Registrarse!");
		
		
		Registrarse.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) 
			{
				changeBackground(Registrarse);
			}
			
			public void mouseExited(MouseEvent e) 
			{
				resetBackground(Registrarse);
			}
		});
		
		add(botonesPanel);
		botonesPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		botonesPanel.add(Registrarse);
	}
	
	private void configurarMensajes() {
		
		mensajeCorreo.setForeground(Color.RED);
		mensajeCorreo.setFont(fontTexto);
		mensajeCorreo.setVisible(true);
		
		mensajeContraseña.setForeground(Color.RED);
		mensajeContraseña.setFont(new Font("Times New Roman", Font.BOLD, 16));
		mensajeContraseña.setVisible(true);
	}
	
	private void configurarTextos() {
		
		// Configurar correo con placeholder
		correo.setFont(fontTexto);
		correo.setForeground(Color.GRAY);
		correo.setBackground(Color.WHITE);
		correo.setMaximumSize(new Dimension(670,50));

		correo.setText("Correo Electrónico");
		
		// Focus listener para placeholder del correo
		correo.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (correo.getText().equals("Correo Electrónico")) {
					correo.setText("");
					correo.setForeground(Color.BLACK);
					correo.setBackground(Color.WHITE);
				}
				else
				{
					correo.setForeground(Color.BLACK);
					correo.setBackground(Color.WHITE);
				}
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				if (correo.getText().isEmpty()) {
					correo.setText("Correo Electrónico");
					correo.setForeground(Color.GRAY);
					correo.setBackground(Color.WHITE);
				} else {
					correo.setBackground(Color.LIGHT_GRAY);
					correo.setForeground(Color.WHITE);
				}
			}
		});
		
		contraseña.setFont(fontTexto);
		contraseña.setForeground(Color.GRAY);
		contraseña.setBackground(Color.WHITE);
		contraseña.setMaximumSize(new Dimension(670,50));
		contraseña.setEchoChar((char) 0); 

		contraseña.setText("Contraseña");

		contraseña.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				String password = new String(contraseña.getPassword());
				if (password.equals("Contraseña")) {
					contraseña.setText("");
					contraseña.setForeground(Color.GRAY);
					contraseña.setBackground(Color.WHITE);
					contraseña.setEchoChar('•'); 
				}
				else
				{
					contraseña.setForeground(Color.GRAY);
					contraseña.setBackground(Color.WHITE);
				}
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				String password = new String(contraseña.getPassword());
				if (password.isEmpty()) {
					contraseña.setText("Contraseña");
					contraseña.setForeground(Color.GRAY);
					contraseña.setBackground(Color.WHITE);
					contraseña.setEchoChar((char) 0); 
				} else {
					contraseña.setBackground(Color.GRAY);
					contraseña.setForeground(Color.WHITE);
					contraseña.setEchoChar('•'); 
				}
			}
		});
	}
	
	private void crearBoton(JButton button, String ruta, String descripcion)
	{
		button.setBackground(Colores.BUTTON_COLOR1);
		button.setForeground(Color.black);
		button.setToolTipText(descripcion);
		button.setFont(fontBoton);
		button.setIconTextGap(10);
		
		try 
		{
			Image icono = ImageIO.read(getClass().getResource(ruta));
			icono = icono.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			button.setIcon(new ImageIcon(icono));
		}
		catch (Exception ex)
		{
			System.out.println("No se pudo poner el icono");
		}
	}
	
	
	public String getCorreoReal() {
		String texto = correo.getText();
		return texto.equals("Correo Electrónico") ? "" : texto;
	}
	
	public String getContraseñaReal() {
		String texto = new String(contraseña.getPassword());
		return texto.equals("Contraseña") ? "" : texto;
	}
	
	Color defaultColor = Colores.BUTTON_COLOR1;
	Color clickedColor = Colores.CLICK_COLOR;
	
	private void changeBackground(JComponent c)
	{
		defaultColor = c.getBackground();
		c.setBackground(clickedColor);
		c.setForeground(Color.WHITE);
	}
	
	private void resetBackground(JComponent c)
	{
		c.setBackground(defaultColor);
		c.setForeground(Color.BLACK);
	}

	public Window getWindow() {
		return SwingUtilities.getWindowAncestor(this);
	}

	public boolean isLogrado() {
		return logrado;
	}

	public void setLogrado(boolean logrado) {
		this.logrado = logrado;
	}

	public Font getFontTexto() {
		return fontTexto;
	}

	public void setFontTexto(Font fontTexto) {
		this.fontTexto = fontTexto;
	}

	public Font getFontBoton() {
		return fontBoton;
	}

	public void setFontBoton(Font fontBoton) {
		this.fontBoton = fontBoton;
	}

	public Font getFontTitulo() {
		return fontTitulo;
	}

	public void setFontTitulo(Font fontTitulo) {
		this.fontTitulo = fontTitulo;
	}

	public JLabel getMensajeCorreo() {
		return mensajeCorreo;
	}

	public void setMensajeCorreo(JLabel mensajeCorreo) {
		this.mensajeCorreo = mensajeCorreo;
	}

	public JLabel getMensajeContraseña() {
		return mensajeContraseña;
	}

	public void setMensajeContraseña(JLabel mensajeContraseña) {
		this.mensajeContraseña = mensajeContraseña;
	}

	public JTextField getUsuario() {
		return correo;
	}

	public void setUsuario(JTextField usuario) {
		this.correo = usuario;
	}

	public JPasswordField getContraseña() {
		return contraseña;
	}

	public void setContraseña(JPasswordField contraseña) {
		this.contraseña = contraseña;
	}

	public JButton getButtonIniciar() {
		return buttonIniciar;
	}

	public void setButtonIniciar(JButton buttonIniciar) {
		this.buttonIniciar = buttonIniciar;
	}

	public JButton getRegistrarse() {
		return Registrarse;
	}

	public void setRegistrarse(JButton registrarse) {
		Registrarse = registrarse;
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	public Color getClickedColor() {
		return clickedColor;
	}

	public void setClickedColor(Color clickedColor) {
		this.clickedColor = clickedColor;
	}
}