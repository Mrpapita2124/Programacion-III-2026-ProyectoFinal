package vistas.otros;

import java.awt.Color;
import java.awt.Cursor;
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

import utilidades.Colores;
import utilidades.Fuentes;
import utilidades.PanelPersonalizable;


public class Login extends JPanel{

	public int x;
	public int y;
	public boolean logrado;
	
	private Font fuenteTexto = Fuentes.setFontSegoe(2, 17); 
	private Font fuenteBoton = Fuentes.setFontSegoe(1, 17);
	private Font fuenteTitulo = Fuentes.setFontSegoe(1, 30);
	
	JLabel mensajeCorreo = new JLabel(" ");
	JLabel mensajeContraseña = new JLabel(" ");
	JTextField correo = new JTextField(30);
	JPasswordField contrasenia = new JPasswordField(30);
	JButton botonIniciar= new JButton("Iniciar Sesión");
	JButton Registrarse = new JButton(" Registrarse   ");
	
	public Login(){	
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(50,100,50,0));
		setBackground(Colores.LOGIN_PANEL);
		
		configurarMensajes();
		configurarTextos();
		
		JLabel saludo = new JLabel("  Bienvenido!");
		saludo.setForeground(Colores.LOGIN_PANEL_TEXTO);
		saludo.setFont(fuenteTitulo);
		add(saludo);
		
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(mensajeCorreo);
		
		add(correo);
		add(Box.createRigidArea(new Dimension(0,20)));
		add(mensajeContraseña);
		
		add(contrasenia);
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
		
		crearBoton(botonIniciar, "..\\img\\login.png", "Clic para Iniciar Sesión!");
		
		
		botonesPanel.add(botonIniciar);
		
		botonIniciar.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) 
			{
				cambiarFondo(botonIniciar);
			}
			
			public void mouseExited(MouseEvent e) 
			{
				reiniciarFondo(botonIniciar);
			}
		});
		
		

		crearBoton(Registrarse, "..\\img\\enter.png", "Clic para Registrarse!");
		
		
		Registrarse.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) 
			{
				cambiarFondo(Registrarse);
			}
			
			public void mouseExited(MouseEvent e) 
			{
				reiniciarFondo(Registrarse);
			}
		});
		
		add(botonesPanel);
		botonesPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		botonesPanel.add(Registrarse);
	}
	
	private void configurarMensajes() {
		
		mensajeCorreo.setForeground(Color.RED);
		mensajeCorreo.setFont(fuenteTexto);
		mensajeCorreo.setVisible(true);
		
		mensajeContraseña.setForeground(Color.RED);
		mensajeContraseña.setFont(new Font("Times New Roman", Font.BOLD, 16));
		mensajeContraseña.setVisible(true);
	}
	
	private void configurarTextos() {
		
		// Configurar correo con placeholder
		correo.setFont(fuenteTexto);
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
		
		contrasenia.setFont(fuenteTexto);
		contrasenia.setForeground(Color.GRAY);
		contrasenia.setBackground(Color.WHITE);
		contrasenia.setMaximumSize(new Dimension(670,50));
		contrasenia.setEchoChar((char) 0); 

		contrasenia.setText("Contraseña");

		contrasenia.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				String password = new String(contrasenia.getPassword());
				if (password.equals("Contraseña")) {
					contrasenia.setText("");
					contrasenia.setForeground(Color.GRAY);
					contrasenia.setBackground(Color.WHITE);
					contrasenia.setEchoChar('•'); 
				}
				else
				{
					contrasenia.setForeground(Color.GRAY);
					contrasenia.setBackground(Color.WHITE);
				}
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				String password = new String(contrasenia.getPassword());
				if (password.isEmpty()) {
					contrasenia.setText("Contraseña");
					contrasenia.setForeground(Color.GRAY);
					contrasenia.setBackground(Color.WHITE);
					contrasenia.setEchoChar((char) 0); 
				} else {
					contrasenia.setBackground(Color.GRAY);
					contrasenia.setForeground(Color.WHITE);
					contrasenia.setEchoChar('•'); 
				}
			}
		});
	}
	
	private void crearBoton(JButton boton, String ruta, String descripcion)
	{
		boton.setBackground(Colores.BOTON_COLOR1);
		boton.setForeground(Color.black);
		boton.setToolTipText(descripcion);
		boton.setFont(fuenteBoton);
		boton.setIconTextGap(10);
		
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		try 
		{
			Image icono = ImageIO.read(getClass().getResource(ruta));
			icono = icono.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			boton.setIcon(new ImageIcon(icono));
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
	
	public String getContraseniaReal() {
		String texto = new String(contrasenia.getPassword());
		return texto.equals("Contraseña") ? "" : texto;
	}
	
	Color colorDefecto = Colores.BOTON_COLOR1;
	Color colorClickeado = Colores.CLICK_COLOR;
	
	private void cambiarFondo(JComponent c)
	{
		colorDefecto = c.getBackground();
		c.setBackground(colorClickeado);
		c.setForeground(Color.WHITE);
	}
	
	private void reiniciarFondo(JComponent c)
	{
		c.setBackground(colorDefecto);
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

	public Font getFuenteTexto() {
		return fuenteTexto;
	}

	public void setFuenteTexto(Font fontTexto) {
		this.fuenteTexto = fontTexto;
	}

	public Font getFuenteBoton() {
		return fuenteBoton;
	}

	public void setFuenteBoton(Font fontBoton) {
		this.fuenteBoton = fontBoton;
	}

	public Font getFuenteTitulo() {
		return fuenteTitulo;
	}

	public void setFuenteTitulo(Font fontTitulo) {
		this.fuenteTitulo = fontTitulo;
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

	public JPasswordField getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(JPasswordField contraseña) {
		this.contrasenia = contraseña;
	}

	public JButton getBotonIniciar() {
		return botonIniciar;
	}

	public void setBotonIniciar(JButton buttonIniciar) {
		this.botonIniciar = buttonIniciar;
	}

	public JButton getRegistrarse() {
		return Registrarse;
	}

	public void setRegistrarse(JButton registrarse) {
		Registrarse = registrarse;
	}

	public Color getColorDefecto() {
		return colorDefecto;
	}

	public void setColorDefecto(Color defaultColor) {
		this.colorDefecto = defaultColor;
	}

	public Color getColorClickeado() {
		return colorClickeado;
	}

	public void setColorClickeado(Color clickedColor) {
		this.colorClickeado = clickedColor;
	}
}