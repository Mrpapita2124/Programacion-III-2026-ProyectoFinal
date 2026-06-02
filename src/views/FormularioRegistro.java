package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.formdev.flatlaf.ui.FlatListCellBorder.Selected;

import modelos.User;
import repository.UserRepository;
import utils.Colores;
import utils.Fonts;
import utils.GradientBackground;
import utils.PanelPersonalizable;
import utils.RoundedBorder;

public class FormularioRegistro extends JFrame{
    
	
	// Puro Fonts, ya depues hare otra clase de Fonts
    public Font fuente;
    private Font fontError = Fonts.setFontSegoe(2, 12);
    private Font fontTextoCampo = Fonts.setFontSegoe(2, 15);
    private Font fontBoton = Fonts.setFontSegoe(1, 20);
    private Font fontTitulo = Fonts.setFontSegoe(1, 35);
    private Font fontBotonChico = Fonts.setFontSegoe(1, 12);
    
    
    // Fields de Texto para Usuario
    private JTextField nombres;
    private JTextField apellidos;
    private JTextField correo;
    private JTextField contraseña;
    
    // JBotones para Regsitro Controller
    private JButton seleccionar = new JButton(" Seleccionar");
    private JButton registrar = new JButton(" Registrarse");
   
    private UserRepository userRepository;
    
    // Labels de Error
    private JLabel lblErrorNombre;
    private JLabel lblErrorApellido;
    private JLabel lblErrorCorreo;
    private JLabel lblErrorContrasena;
   // public JLabel lblErrorFoto;
    ImageIcon iconoUsuarioFinal;
    JLabel iconoUsuario = new JLabel();
    
    //Chckboxes
    private JCheckBox guardar= new JCheckBox(" Guardar como usuario rapido");
    public FormularioRegistro()
    {
    	userRepository = new UserRepository();
    	
    	GradientBackground gradientBg = new GradientBackground();
        gradientBg.setLayout(null);
        setContentPane(gradientBg);
    	
        iconoUsuarioFinal = escalarImagenLocal("..\\img\\iconoWhite.png",200,200);
        iconoUsuarioFinal.setDescription("..\\img\\iconoWhite.png");
        setSize(1050,700);
        setLayout(null);
        setResizable(false);
        setTitle("Registro");
        setLocationRelativeTo(null);
       // getContentPane().setBackground(Colores.BACKGROUND);
        
        Toolkit tk = Toolkit.getDefaultToolkit();
		ImageIcon cursorImage = new ImageIcon("src\\img\\pointer_b.png");
		Cursor myCursor = tk.createCustomCursor(cursorImage.getImage(), new Point(0,0), "Cursor");
		this.setCursor(myCursor);
        
		int panelY = 40;
		int panelAlto = 600;
		
        PanelPersonalizable fondo = new PanelPersonalizable();
        fondo.setBounds(165, panelY, 680, panelAlto);
        fondo.setBorder(new RoundedBorder(Colores.DEFAULT_BORDER, 20, 2));
        fondo.setBackground(Colores.LOGIN_PANEL);
        

        JPanel panelComponentes = new JPanel();
        panelComponentes.setLayout(new BoxLayout(panelComponentes, BoxLayout.Y_AXIS));
        panelComponentes.setBounds(70, 50, 680, 850);
        panelComponentes.setOpaque(false);
        panelComponentes.setBorder(new EmptyBorder(20, 40, 20, 40));
        
        // Para centrar todos los componentes
        panelComponentes.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        
        ImageIcon originalIcon = new ImageIcon("src/img/registerWhite.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon iconoRegistrar = new ImageIcon(scaledImage);
        
        JLabel saludo = new JLabel(" Registrate", iconoRegistrar, JLabel.LEFT);
        saludo.setHorizontalTextPosition(JLabel.RIGHT);  
        saludo.setVerticalTextPosition(JLabel.CENTER); 
        
        saludo.setOpaque(false);
        saludo.setFont(fontTitulo);
        saludo.setForeground(Colores.PRIMARY_HEADINGS);
        saludo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,50)));
        panelComponentes.add(saludo);
        panelComponentes.add(Box.createRigidArea(new Dimension(0,65)));

        lblErrorNombre = crearErrorLabel();
        lblErrorApellido = crearErrorLabel();
        lblErrorCorreo = crearErrorLabel();
        lblErrorContrasena = crearErrorLabel();
        //lblErrorFoto = crearErrorLabel();
        //lblErrorFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        crearComponentesDeRegistro(panelComponentes);
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,25)));
        
        JLabel perfil = new JLabel("Foto perfil");
        perfil.setOpaque(false);
        perfil.setFont(fontTitulo);
        perfil.setForeground(Colores.LOGIN_PANEL_TEXT);
        perfil.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelComponentes.add(perfil);
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,15)));
        
        iconoUsuario.setIcon(iconoUsuarioFinal);
        iconoUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconoUsuario.setPreferredSize(new Dimension(200,200));
        iconoUsuario.setVisible(true);
        panelComponentes.add(iconoUsuario);
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,10)));
       
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new BoxLayout(panelBoton, BoxLayout.Y_AXIS));
        panelBoton.setOpaque(false);
        panelBoton.setAlignmentX(Component.CENTER_ALIGNMENT);

        crearBoton(registrar, "..\\img\\icono.png","Registrarse", Colores.BUTTON_COLOR1, 150, 35, fontBoton);
        crearBoton(seleccionar,"..\\img\\icono.png" , "Seleccionar", Colores.BUTTON_COLOR2, 105, 25, fontBotonChico);
        
        seleccionar.addMouseListener(new MouseAdapter() 
        {
			public void mouseEntered(MouseEvent e) 
			{
				changeBackground(seleccionar);
			}
			
			public void mouseExited(MouseEvent e) 
			{
				resetBackground(seleccionar);
			}
		});
        registrar.addMouseListener(new MouseAdapter() 
        {
			public void mouseEntered(MouseEvent e) 
			{
				changeBackground(registrar);
			}
			
			public void mouseExited(MouseEvent e) 
			{
				resetBackground(registrar);
			}
		});

        
        
        panelComponentes.add(seleccionar);
        panelComponentes.add(Box.createRigidArea(new Dimension(0,25)));
        
        
        // Boton checkbox "guardar"
        panelComponentes.add(Box.createRigidArea(new Dimension(0,25)));
        guardar.setAlignmentX(Component.CENTER_ALIGNMENT);
        guardar.setForeground(Colores.LOGIN_PANEL_TEXT);
        guardar.setFont(Fonts.setFontSegoe(2, 15));
        panelComponentes.add(guardar);
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,15)));
        panelBoton.add(registrar);
        panelComponentes.add(panelBoton);
        
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Scroll Pane
        JScrollPane scrollPanel= new JScrollPane(panelComponentes);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setBounds(145, panelY, 710, panelAlto);
		scrollPanel.getViewport().setOpaque(false);
		scrollPanel.setOpaque(false);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(20); // Hacer que el scroll se baje mas rapido
		scrollPanel.setBorder(null);
		scrollPanel.getVerticalScrollBar().setBackground(Colores.BACKGROUND);
		scrollPanel.getVerticalScrollBar().setOpaque(false);
		scrollPanel.getViewport().setBackground(Colores.BACKGROUND);
		
		
		add(scrollPanel);
        add(fondo);

        setVisible(true);
    }
    public void chooseImage() {
    	JFileChooser chooser=new  JFileChooser();
    	chooser.setDialogTitle("Selecciona tu foto de perfil");
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagenes", "jpg","jpeg","png");
    	chooser.setFileFilter(filter);
    	int option= chooser.showOpenDialog(this);
    	if(option==JFileChooser.APPROVE_OPTION) {
    		File file=chooser.getSelectedFile();
    		iconoUsuario.setIcon(escalarImagen(file.getAbsolutePath(), 200, 200));
    		iconoUsuarioFinal.setDescription(file.getAbsolutePath());
    	}
    }
    
    private JTextField crearTextField(String placeholder, String JTextFieldName) 
    {
        JTextField textField = new JTextField(placeholder);
        
        int fieldWidth = 400;
        int fieldHeight = 45;
        
        textField.setMaximumSize(new Dimension(fieldWidth, fieldHeight));
        textField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        textField.setMinimumSize(new Dimension(fieldWidth, fieldHeight));
        
        textField.setBackground(Colores.BACKGROUND);
        textField.setForeground(Color.BLACK);
        textField.setFont(fontTextoCampo);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setName(JTextFieldName);
        
        return textField;
    }
    private ImageIcon escalarImagen(String direccion,int x,int y) {
    	
        ImageIcon iconoOriginal = new ImageIcon(direccion);

       
        Image imagenEscalada = iconoOriginal.getImage()
                .getScaledInstance(x, y, Image.SCALE_SMOOTH);

        
        ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
        iconoFinal.setDescription(direccion);
        return iconoFinal;
    }
    private ImageIcon escalarImagenLocal(String direccion,int x,int y) {
    	
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(direccion));

       
        Image imagenEscalada = iconoOriginal.getImage()
                .getScaledInstance(x, y, Image.SCALE_SMOOTH);

        
        ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
        iconoFinal.setDescription(direccion);
        return iconoFinal;
    }
    private void asignarFocusListenerConPlaceholder(JTextField textField, String placeholder) {
    	
    	textField.setBackground(Colores.TABBED_TEXT_COLOR);
    	
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    textField.setBackground(Color.WHITE);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.BLACK);
                    textField.setBackground(Colores.BG_TEXT_COLOR);
                } else {
                	textField.setForeground(Color.BLACK);
                    textField.setBackground(Colores.TABBED_TEXT_COLOR);
                }
            }
        });
    }
    
    private void crearBoton(JButton button, String ruta,String titulo, Color colorBoton, int ancho, int largo, Font font)
    {
        int buttonWidth = ancho;   
        int buttonHeight = largo;   
        
        button.setBackground(colorBoton);
        button.setForeground(Color.black);
        button.setToolTipText(titulo);
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
        
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        /*
        try 
        {
            Image icono = ImageIO.read(getClass().getResource(ruta));
            icono = icono.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(icono));
        }
        catch (Exception ex)
        {
            System.out.println("No se pudo poner el icono");
        }*/
    }
    
    private JLabel crearErrorLabel() 
    {
        JLabel label = new JLabel("");
        label.setFont(fontError);
        label.setForeground(Color.RED);
        label.setMaximumSize(new Dimension(400, 20));
        label.setPreferredSize(new Dimension(400, 20));
        label.setMinimumSize(new Dimension(400, 20));
        label.setAlignmentX(Component.LEFT_ALIGNMENT); // CAMBIADO: Alinear a la izquierda
        label.setHorizontalAlignment(SwingConstants.LEFT); // CAMBIADO: Texto alineado a la izquierda
        return label;
    }
    
    public void resetearErrorLabels() 
    {
		lblErrorNombre.setText("");
		lblErrorApellido.setText("");
		lblErrorCorreo.setText("");
		lblErrorContrasena.setText("");
		//lblErrorFoto.setText("");
    }
    
    public void crearComponentesDeRegistro(JPanel panelComponentes)
    {
        nombres = crearTextField("Nombre", "nombres");
        apellidos = crearTextField("Apellido", "apellidos");
        correo = crearTextField("correo@ejemplo.com", "correo");
        contraseña = crearTextField("Contraseña", "contraseña");
        
        
        asignarFocusListenerConPlaceholder(nombres, "Nombre");
        asignarFocusListenerConPlaceholder(apellidos, "Apellido");
        asignarFocusListenerConPlaceholder(correo, "correo@ejemplo.com");
        asignarFocusListenerConPlaceholder(contraseña, "Contraseña");
        
        
        JPanel panelNombreWrapper = new JPanel();
        panelNombreWrapper.setLayout(new BoxLayout(panelNombreWrapper, BoxLayout.Y_AXIS));
        panelNombreWrapper.setOpaque(false);
        panelNombreWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelNombreWrapper.setMaximumSize(new Dimension(400, 70));
        
        nombres.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelNombreWrapper.add(nombres);
        panelNombreWrapper.add(Box.createRigidArea(new Dimension(0, 5)));
        
        lblErrorNombre.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelNombreWrapper.add(lblErrorNombre);
        
        panelComponentes.add(panelNombreWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Panel para Apellido
        JPanel panelApellidoWrapper = new JPanel();
        panelApellidoWrapper.setLayout(new BoxLayout(panelApellidoWrapper, BoxLayout.Y_AXIS));
        panelApellidoWrapper.setOpaque(false);
        panelApellidoWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelApellidoWrapper.setMaximumSize(new Dimension(400, 70));
        
        apellidos.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelApellidoWrapper.add(apellidos);
        panelApellidoWrapper.add(Box.createRigidArea(new Dimension(0, 5)));
        
        lblErrorApellido.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelApellidoWrapper.add(lblErrorApellido);
        
        panelComponentes.add(panelApellidoWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Panel para Correo
        JPanel panelCorreoWrapper = new JPanel();
        panelCorreoWrapper.setLayout(new BoxLayout(panelCorreoWrapper, BoxLayout.Y_AXIS));
        panelCorreoWrapper.setOpaque(false);
        panelCorreoWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCorreoWrapper.setMaximumSize(new Dimension(400, 70));
        
        correo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCorreoWrapper.add(correo);
        panelCorreoWrapper.add(Box.createRigidArea(new Dimension(0, 5)));
        
        lblErrorCorreo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCorreoWrapper.add(lblErrorCorreo);
        
        panelComponentes.add(panelCorreoWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Panel para Contraseña
        JPanel panelContrasenaWrapper = new JPanel();
        panelContrasenaWrapper.setLayout(new BoxLayout(panelContrasenaWrapper, BoxLayout.Y_AXIS));
        panelContrasenaWrapper.setOpaque(false);
        panelContrasenaWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContrasenaWrapper.setMaximumSize(new Dimension(400, 70));
        
        nombres.setBackground(Colores.BG_TEXT_COLOR);
        apellidos.setBackground(Colores.BG_TEXT_COLOR);
        correo.setBackground(Colores.BG_TEXT_COLOR);
        contraseña.setBackground(Colores.BG_TEXT_COLOR);
        
        contraseña.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelContrasenaWrapper.add(contraseña);
        panelContrasenaWrapper.add(Box.createRigidArea(new Dimension(0, 5)));
        
        lblErrorContrasena.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelContrasenaWrapper.add(lblErrorContrasena);
        
        panelComponentes.add(panelContrasenaWrapper);
        
        panelComponentes.add(Box.createVerticalGlue());
    }
    
    Color defaultColor = Color.GRAY;
	Color clickedColor = Color.GRAY;
	
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
	
	public void registerUser(User user)
	{
		try
		{
			userRepository.save(user);
			System.out.println("Se Registro Usuario!");
			JOptionPane.showMessageDialog(this, "Usuario registrado");
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	public JLabel getIconoUsuario() {
		return iconoUsuario;
	}
	public void setIconoUsuario(JLabel iconoUsuario) {
		this.iconoUsuario = iconoUsuario;
	}
	
	public int confirmExit() 
	{
	    return JOptionPane.showConfirmDialog(
	        this,
	        "¿Seguro que deseas regresar? Se perderán todos los datos",
	        "¿Seguro?",
	        JOptionPane.YES_NO_OPTION
	    );
	}
    
	
    
   // Getters
    public String getNombre() {
        String text = nombres.getText();
        return text.equals("Nombre") ? "" : text;
    }
    
    public String getApellido() {
        String text = apellidos.getText();
        return text.equals("Apellido") ? "" : text;
    }
    
    public String getCorreo() {
        String text = correo.getText();
        return text.equals("correo@ejemplo.com") ? "" : text;
    }
    
    public String getContraseña() {
        String text = contraseña.getText();
        return text.equals("Contraseña") ? "" : text;
    }
    
    public Window getWindow()
    {
    	return this;
    }
	public JCheckBox getGuardar() {
		return guardar;
	}
	public void setGuardar(JCheckBox guardar) {
		this.guardar = guardar;
	}
	public String getIconDescription() {
		return iconoUsuarioFinal.getDescription();
	}
	public Font getFuente() {
		return fuente;
	}
	public void setFuente(Font fuente) {
		this.fuente = fuente;
	}
	public Font getFontError() {
		return fontError;
	}
	public void setFontError(Font fontError) {
		this.fontError = fontError;
	}
	public Font getFontTextoCampo() {
		return fontTextoCampo;
	}
	public void setFontTextoCampo(Font fontTextoCampo) {
		this.fontTextoCampo = fontTextoCampo;
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
	public JTextField getNombres() {
		return nombres;
	}
	public void setNombres(JTextField nombres) {
		this.nombres = nombres;
	}
	public JTextField getApellidos() {
		return apellidos;
	}
	public void setApellidos(JTextField apellidos) {
		this.apellidos = apellidos;
	}
	public JButton getSeleccionar() {
		return seleccionar;
	}
	public void setSeleccionar(JButton seleccionar) {
		this.seleccionar = seleccionar;
	}
	public JButton getRegistrar() {
		return registrar;
	}
	public void setRegistrar(JButton registrar) {
		this.registrar = registrar;
	}
	public UserRepository getUserRepository() {
		return userRepository;
	}
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public JLabel getLblErrorNombre() {
		return lblErrorNombre;
	}
	public void setLblErrorNombre(JLabel lblErrorNombre) {
		this.lblErrorNombre = lblErrorNombre;
	}
	public JLabel getLblErrorApellido() {
		return lblErrorApellido;
	}
	public void setLblErrorApellido(JLabel lblErrorApellido) {
		this.lblErrorApellido = lblErrorApellido;
	}
	public JLabel getLblErrorCorreo() {
		return lblErrorCorreo;
	}
	public void setLblErrorCorreo(JLabel lblErrorCorreo) {
		this.lblErrorCorreo = lblErrorCorreo;
	}
	public JLabel getLblErrorContrasena() {
		return lblErrorContrasena;
	}
	public void setLblErrorContrasena(JLabel lblErrorContrasena) {
		this.lblErrorContrasena = lblErrorContrasena;
	}
	public ImageIcon getIconoUsuarioFinal() {
		return iconoUsuarioFinal;
	}
	public void setIconoUsuarioFinal(ImageIcon iconoUsuarioFinal) {
		this.iconoUsuarioFinal = iconoUsuarioFinal;
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
	
	public void setCorreo(JTextField correo) {
		this.correo = correo;
	}
	public JTextField getCorreoFieldd() {
		return correo;
	}
	public void setContraseña(JTextField contraseña) {
		this.contraseña = contraseña;
	}
	public JTextField getContraseField() {
		return contraseña;
	}
	
    
}