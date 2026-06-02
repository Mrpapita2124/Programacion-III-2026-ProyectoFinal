package views;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

import controllers.LoginController;
import utils.Colores;
import utils.GradientBackground;
import utils.PanelPersonalizable;
import utils.RoundedBorder;


public class Ventana extends JFrame{
	
	public Ventana()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		setLayout(null);
		setBounds(25, 25, 1300, 700); // Hace lo mismo de setSize y setLocation.
		setResizable(false);  // No se puede cambiar de tamaño con el mouse.
		setTitle("Login Pestaña");  // Nombre de la Ventana
		setLocationRelativeTo(null);  // Pone la ventana en el centro
		
		Image icono = tk.getImage("src\\img\\icono.png");
		setIconImage(icono);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la penstaña, si no se queda abrierta.
		
		// Gradient
		GradientBackground gradientPanel = new GradientBackground();
		gradientPanel.setLayout(null);  // Keep your null layout
		setContentPane(gradientPanel);
		
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				getContentPane().setBackground(Colores.BACKGROUND);
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				getContentPane().setBackground(Color.GRAY);
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		ImageIcon cursorImage = new ImageIcon("src\\img\\pointer_b.png");
		Cursor myCursor = tk.createCustomCursor(cursorImage.getImage(), new Point(0,0), "Cursor");
		this.setCursor(myCursor);
		
		
		// Login Panel
		Login login = new Login();
		login.setBounds(750,140,300,400);
		login.setBackground(Colores.BACKGROUND);
		login.setOpaque(false);
		new LoginController(login);
		add(login);
		
		//Agregar a los usuarios
		Usuarios usuarios = new Usuarios();

		JScrollPane scrollUsuarios = new JScrollPane(usuarios);
		scrollUsuarios.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollUsuarios.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollUsuarios.setBounds(140, 140, 400, 450); 

		scrollUsuarios.setOpaque(false);
		scrollUsuarios.getViewport().setOpaque(false);
		scrollUsuarios.setBorder(null);

		scrollUsuarios.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
		    @Override
		    protected void configureScrollBarColors() {
		        this.thumbColor = Colores.BUTTON_COLOR1;
		        this.trackColor = new Color(0, 0, 0, 0); // Transparent track
		    }
		    @Override
		    protected JButton createDecreaseButton(int orientation) {
		        return createZeroButton();
		    }
		    @Override
		    protected JButton createIncreaseButton(int orientation) {
		        return createZeroButton();
		    }
		    private JButton createZeroButton() {
		        JButton button = new JButton();
		        button.setPreferredSize(new Dimension(0, 0));
		        return button;
		    }
		});
		scrollUsuarios.getVerticalScrollBar().setUnitIncrement(20);

		add(scrollUsuarios);
		
		PanelPersonalizable fondo1=new PanelPersonalizable();
		fondo1.setBounds(725, 120, 450, 450);
		fondo1.setBackground(Colores.LOGIN_PANEL1);
		fondo1.setBorder(new RoundedBorder(Colores.DEFAULT_BORDER, 20, 2));
		add(fondo1);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);  // Siempre agrega el set visible antes del final.
	}
	
}
