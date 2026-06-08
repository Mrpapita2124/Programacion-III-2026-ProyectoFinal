package vistas.formulario;

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

import modelos.Usuario;
import repositorios.UsuarioRepository;
import utilidades.Colores;
import utilidades.Fuentes;
import utilidades.GradientBackground;
import utilidades.PanelPersonalizable;
import utilidades.RoundedBorder;

public class FormularioRegistro extends JFrame{
    
	
	// Puro Fonts, ya depues hare otra clase de Fonts
   

    
    
    // Fields de Texto para Usuario
    private JTextField nombres;
    private JTextField apellidos;
    private JTextField capacidadPrestamo;
    private JTextField correo;
    private JTextField contraseña;
    
    // JBotones para Regsitro Controller
    private JButton seleccionar = new JButton(" Seleccionar");
    private JButton registrar = new JButton(" Registrarse");
   
    private UsuarioRepository userRepository;
    
    // Labels de Error
    private JLabel lblErrorNombre;
    private JLabel lblErrorApellido;
    private JLabel lblErrorCapacidadPrestamo;
    private JLabel lblErrorCorreo;
    private JLabel lblErrorContrasena;
   // public JLabel lblErrorFoto;
    ImageIcon iconoUsuarioFinal;
    JLabel iconoUsuario = new JLabel();
    
    //Chckboxes
    private JCheckBox guardar= new JCheckBox(" Guardar como usuario rapido");
    public FormularioRegistro()
    {
    	userRepository = new UsuarioRepository();
    	
    	GradientBackground gradientBg = new GradientBackground();
        gradientBg.setLayout(null);
        setContentPane(gradientBg);
    	
        iconoUsuarioFinal = escalarImagenLocal("/img/iconoWhite.png",200,200);
        iconoUsuarioFinal.setDescription("/img/iconoWhite.png");
        setSize(1050,700);
        setLayout(null);
        setResizable(false);
        setTitle("Registro");
        setLocationRelativeTo(null);
       // getContentPane().setBackground(Colores.BACKGROUND);
        
        Toolkit tk = Toolkit.getDefaultToolkit();
		ImageIcon imagenCursor = new ImageIcon("src\\img\\pointer_b.png");
		Cursor cursor = tk.createCustomCursor(imagenCursor.getImage(), new Point(0,0), "Cursor");
		this.setCursor(cursor);
        
		int panelY = 40;
		int panelAlto = 600;
		
        PanelPersonalizable fondo = new PanelPersonalizable();
        fondo.setBounds(165, panelY, 680, panelAlto);
        fondo.setBorder(new RoundedBorder(Colores.BORDE_POR_DEFECTO, 20, 2));
        fondo.setBackground(Colores.LOGIN_PANEL);
        

        JPanel panelComponentes = new JPanel();
        panelComponentes.setLayout(new BoxLayout(panelComponentes, BoxLayout.Y_AXIS));
        panelComponentes.setBounds(70, 50, 680, 850);
        panelComponentes.setOpaque(false);
        panelComponentes.setBorder(new EmptyBorder(20, 40, 20, 40));
        
        // Para centrar todos los componentes
        panelComponentes.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        
        ImageIcon iconoOriginal = new ImageIcon("src/img/registerWhite.png");
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon iconoRegistrar = new ImageIcon(imagenEscalada);
        
        JLabel saludo = new JLabel(" Registrate", iconoRegistrar, JLabel.LEFT);
        saludo.setHorizontalTextPosition(JLabel.RIGHT);  
        saludo.setVerticalTextPosition(JLabel.CENTER); 
        
        saludo.setOpaque(false);
        saludo.setFont(Fuentes.fuenteTitulo);
        saludo.setForeground(Colores.ENCABEZADOS_PRIMARIOS);
        saludo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,50)));
        panelComponentes.add(saludo);
        panelComponentes.add(Box.createRigidArea(new Dimension(0,65)));

        lblErrorNombre = crearErrorLabel();
        lblErrorApellido = crearErrorLabel();
        lblErrorCapacidadPrestamo= crearErrorLabel();
        lblErrorCorreo = crearErrorLabel();
        lblErrorContrasena = crearErrorLabel();

        
        crearComponentesDeRegistro(panelComponentes);
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,25)));
        
        JLabel perfil = new JLabel("Foto perfil");
        perfil.setOpaque(false);
        perfil.setFont(Fuentes.fuenteTitulo);
        perfil.setForeground(Colores.LOGIN_PANEL_TEXTO);
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

        crearBoton(registrar, "..\\img\\icono.png","Registrarse", Colores.BOTON_COLOR1, 150, 35, Fuentes.fuenteBoton);
        crearBoton(seleccionar,"..\\img\\icono.png" , "Seleccionar", Colores.BOTON_COLOR2, 105, 25, Fuentes.fuenteBotonChico);
        
        seleccionar.addMouseListener(new MouseAdapter() 
        {
			public void mouseEntered(MouseEvent e) 
			{
				cambiarFondo(seleccionar);
			}
			
			public void mouseExited(MouseEvent e) 
			{
				reiniciarFondo(seleccionar);
			}
		});
        registrar.addMouseListener(new MouseAdapter() 
        {
			public void mouseEntered(MouseEvent e) 
			{
				cambiarFondo(registrar);
			}
			
			public void mouseExited(MouseEvent e) 
			{
				reiniciarFondo(registrar);
			}
		});

        
        
        panelComponentes.add(seleccionar);
        panelComponentes.add(Box.createRigidArea(new Dimension(0,25)));
        
        
        // Boton checkbox "guardar"
        panelComponentes.add(Box.createRigidArea(new Dimension(0,25)));
        guardar.setAlignmentX(Component.CENTER_ALIGNMENT);
        guardar.setForeground(Colores.LOGIN_PANEL_TEXTO);
        guardar.setFont(Fuentes.setFontSegoe(2, 15));
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
		scrollPanel.getVerticalScrollBar().setBackground(Colores.FONDO);
		scrollPanel.getVerticalScrollBar().setOpaque(false);
		scrollPanel.getViewport().setBackground(Colores.FONDO);
		
		
		add(scrollPanel);
        add(fondo);

        setVisible(true);
    }
    public void seleccionarImagen() {
    	JFileChooser selector=new  JFileChooser();
    	selector.setDialogTitle("Selecciona tu foto de perfil");
    	FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagenes", "jpg","jpeg","png");
    	selector.setFileFilter(filtro);
    	int opcion= selector.showOpenDialog(this);
    	if(opcion==JFileChooser.APPROVE_OPTION) {
    		File archivo=selector.getSelectedFile();
    		iconoUsuario.setIcon(escalarImagen(archivo.getAbsolutePath(), 200, 200));
    		iconoUsuarioFinal.setDescription(archivo.getAbsolutePath());
    	}
    }
    
    private JTextField crearTextField(String placeholder, String campoNombre) 
    {
        JTextField campo = new JTextField(placeholder);
        
        int ancho = 400;
        int alto = 45;
        
        campo.setMaximumSize(new Dimension(ancho, alto));
        campo.setPreferredSize(new Dimension(ancho, alto));
        campo.setMinimumSize(new Dimension(ancho, alto));
        
        campo.setBackground(Colores.FONDO);
        campo.setForeground(Color.BLACK);
        campo.setFont(Fuentes.fuenteTextoCampo);
        campo.setAlignmentX(Component.CENTER_ALIGNMENT);
        campo.setName(campoNombre);
        
        return campo;
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
    private void asignarFocusListenerConPlaceholder(JTextField campoDeTexto, String placeholder) {
    	
    	campoDeTexto.setBackground(Colores.TEXTO_TABULADO_COLOR);
    	
        campoDeTexto.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campoDeTexto.getText().equals(placeholder)) {
                    campoDeTexto.setText("");
                    campoDeTexto.setForeground(Color.BLACK);
                    campoDeTexto.setBackground(Color.WHITE);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (campoDeTexto.getText().isEmpty()) {
                    campoDeTexto.setText(placeholder);
                    campoDeTexto.setForeground(Color.BLACK);
                    campoDeTexto.setBackground(Colores.FONDO_TEXTO_COLOR);
                } else {
                	campoDeTexto.setForeground(Color.BLACK);
                    campoDeTexto.setBackground(Colores.TEXTO_TABULADO_COLOR);
                }
            }
        });
    }
    
    private void crearBoton(JButton boton, String ruta,String titulo, Color colorBoton, int ancho, int largo, Font font)
    {
        int buttonAncho = ancho;   
        int buttonAlto = largo;   
        
        boton.setBackground(colorBoton);
        boton.setForeground(Color.black);
        boton.setToolTipText(titulo);
        boton.setFont(font);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(buttonAncho, buttonAlto));
        boton.setPreferredSize(new Dimension(buttonAncho, buttonAlto));
        boton.setMinimumSize(new Dimension(buttonAncho, buttonAlto));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private JLabel crearErrorLabel() 
    {
        JLabel label = new JLabel("");
        label.setFont(Fuentes.fuenteError);
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
        capacidadPrestamo= crearTextField("Capacidad de prestamo", "capacidadPrestamo");
        
        
        asignarFocusListenerConPlaceholder(nombres, "Nombre");
        asignarFocusListenerConPlaceholder(apellidos, "Apellido");
        asignarFocusListenerConPlaceholder(capacidadPrestamo, "Capacidad de prestamo");
        asignarFocusListenerConPlaceholder(correo, "correo@ejemplo.com");
        asignarFocusListenerConPlaceholder(contraseña, "Contraseña");
        
        
        
        JPanel panelNombreEnvoltorio = new JPanel();
        panelNombreEnvoltorio.setLayout(new BoxLayout(panelNombreEnvoltorio, BoxLayout.Y_AXIS));
        panelNombreEnvoltorio.setOpaque(false);
        panelNombreEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelNombreEnvoltorio.setMaximumSize(new Dimension(400, 70));
        
        nombres.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelNombreEnvoltorio.add(nombres);
        panelNombreEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));
        
        lblErrorNombre.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelNombreEnvoltorio.add(lblErrorNombre);
        
        panelComponentes.add(panelNombreEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Panel para Apellido
        JPanel panelApellidoEnvoltorio = new JPanel();
        panelApellidoEnvoltorio.setLayout(new BoxLayout(panelApellidoEnvoltorio, BoxLayout.Y_AXIS));
        panelApellidoEnvoltorio.setOpaque(false);
        panelApellidoEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelApellidoEnvoltorio.setMaximumSize(new Dimension(400, 70));
        
        apellidos.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelApellidoEnvoltorio.add(apellidos);
        panelApellidoEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));
        
        lblErrorApellido.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelApellidoEnvoltorio.add(lblErrorApellido);
        
        panelComponentes.add(panelApellidoEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));
        
     // Panel para Capacidad de prestamo
        JPanel panelCapacidadPrestamoEnvoltorio = new JPanel();
        panelCapacidadPrestamoEnvoltorio.setLayout(new BoxLayout(panelCapacidadPrestamoEnvoltorio, BoxLayout.Y_AXIS));
        panelCapacidadPrestamoEnvoltorio.setOpaque(false);
        panelCapacidadPrestamoEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCapacidadPrestamoEnvoltorio.setMaximumSize(new Dimension(400, 70));
        
        capacidadPrestamo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCapacidadPrestamoEnvoltorio.add(capacidadPrestamo);
        panelCapacidadPrestamoEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));
        
        lblErrorCapacidadPrestamo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCapacidadPrestamoEnvoltorio.add(lblErrorCapacidadPrestamo);
        
        panelComponentes.add(panelCapacidadPrestamoEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Panel para Correo
        JPanel panelCorreoEnvoltorio = new JPanel();
        panelCorreoEnvoltorio.setLayout(new BoxLayout(panelCorreoEnvoltorio, BoxLayout.Y_AXIS));
        panelCorreoEnvoltorio.setOpaque(false);
        panelCorreoEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCorreoEnvoltorio.setMaximumSize(new Dimension(400, 70));
        
        correo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCorreoEnvoltorio.add(correo);
        panelCorreoEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));
        
        lblErrorCorreo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCorreoEnvoltorio.add(lblErrorCorreo);
        
        panelComponentes.add(panelCorreoEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Panel para Contraseña
        JPanel panelContrasenaEnvoltorio = new JPanel();
        panelContrasenaEnvoltorio.setLayout(new BoxLayout(panelContrasenaEnvoltorio, BoxLayout.Y_AXIS));
        panelContrasenaEnvoltorio.setOpaque(false);
        panelContrasenaEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContrasenaEnvoltorio.setMaximumSize(new Dimension(400, 70));
        
        nombres.setBackground(Colores.FONDO_TEXTO_COLOR);
        apellidos.setBackground(Colores.FONDO_TEXTO_COLOR);
        correo.setBackground(Colores.FONDO_TEXTO_COLOR);
        contraseña.setBackground(Colores.FONDO_TEXTO_COLOR);
        
        contraseña.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelContrasenaEnvoltorio.add(contraseña);
        panelContrasenaEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));
        
        lblErrorContrasena.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelContrasenaEnvoltorio.add(lblErrorContrasena);
        
        panelComponentes.add(panelContrasenaEnvoltorio);
        
        panelComponentes.add(Box.createVerticalGlue());
    }
    
    Color defaultColor = Color.GRAY;
	Color clickedColor = Color.GRAY;
	
	private void cambiarFondo(JComponent c)
	{
		defaultColor = c.getBackground();
		c.setBackground(clickedColor);
		c.setForeground(Color.WHITE);
	}
	
	private void reiniciarFondo(JComponent c)
	{
		c.setBackground(defaultColor);
		c.setForeground(Color.BLACK);
	}
	
	public void registrarUsuario(Usuario usuario)
	{
		try
		{
			userRepository.guardar(usuario);
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
    public String getCapacidad() {
        String text = capacidadPrestamo.getText();
        return text.equals("Capacidad de prestamo") ? "" : text;
    }
    public Double getCapacidadPrestamoNumero() {
        Double capacidad = Double.parseDouble(capacidadPrestamo.getText());
        return capacidad;
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
	public UsuarioRepository getUserRepository() {
		return userRepository;
	}
	public void setUserRepository(UsuarioRepository userRepository) {
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
	
	public JTextField getCapacidadPrestamo() {
		return capacidadPrestamo;
	}
	public void setCapacidadPrestamo(JTextField capacidadPrestamo) {
		this.capacidadPrestamo = capacidadPrestamo;
	}
	public JLabel getLblErrorCapacidadPrestamo() {
		return lblErrorCapacidadPrestamo;
	}
	public void setLblErrorCapacidadPrestamo(JLabel lblErrorCapacidadPrestamo) {
		this.lblErrorCapacidadPrestamo = lblErrorCapacidadPrestamo;
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