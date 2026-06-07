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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.formdev.flatlaf.ui.FlatListCellBorder.Selected;
import com.itextpdf.kernel.pdf.PdfDocument;

import modelos.Cliente;
import modelos.Usuario;
import repositorios.UsuarioRepository;
import utilidades.Colores;
import utilidades.Fuentes;
import utilidades.PanelPersonalizable;

public class FormularioGeneralCliente extends JFrame{
    
	
	// Puro Fonts, ya depues hare otra clase de Fonts
  
    
    
    
    // Fields de Texto para Usuario
    private JTextField nombres;
    private JTextField apellidos;
    private JTextField edad;
    private JTextField domicilio;
    private JTextField numeroCelular;
    private JTextField correo;
    private JTextField empleo;
    private JTextField telefonoEmpleo;
    private JTextField domicilioEmpleo;
    private JTextField ingresoMensual;
    private JTextField banco;
    private JTextField cuentaBancaria;
    private JTextField curp;

    
    // JBotones para Regsitro Controller
    private JButton seleccionar = new JButton(" Seleccionar");
    private JButton seleccionarComprobante = new JButton(" Seleccionar");
    private JButton registrar = new JButton(" Registrarse");
   
    private UsuarioRepository usuarioRepository;
    
    // Labels de Error
    
    private JLabel lblErrorNombre;
    private JLabel lblErrorApellido;
    private JLabel lblErrorEdad;
    private JLabel lblErrorDomicilio;
    private JLabel lblErrorNumeroCelular;
    private JLabel lblErrorCorreo;
    private JLabel lblErrorEmpleo;
    private JLabel lblErrorTelefonoEmpleo;
    private JLabel lblErrorDomicilioEmpleo;
    private JLabel lblErrorIngresoMensual;
    private JLabel lblErrorBanco;
    private JLabel lblErrorCuentaBancaria;
    private JLabel lblErrorCurp;
    private JLabel lblErrorFoto;
    private JLabel lblErrorDocumento;

    Cliente cliente;
   // public JLabel lblErrorFoto;
    private ImageIcon ineIconoCliente;
    private JLabel ineCliente = new JLabel();
    
    private ImageIcon comprobanteIconoCliente;
    private JLabel comprobanteCliente = new JLabel();
    
    private boolean edicion;
    //Chckboxes
    private JCheckBox guardar= new JCheckBox("Guardar como usuario rapido");
    public FormularioGeneralCliente()
    {
    	edicion=false;
    	usuarioRepository = new UsuarioRepository();
    	
        ineIconoCliente = escalarImagenLocal("/img/LicenseDefault.png",200,200);
        ineIconoCliente.setDescription("/img/LicenseDefault.png");
        
        comprobanteIconoCliente = escalarImagenLocal("/img/DocumentDefaultIcon.png",200,200);
        comprobanteIconoCliente.setDescription("/img/DocumentDefaultIcon.png");
        setSize(850,550);
        setLayout(null);
        setResizable(false);
        setTitle("Registro");
        setLocationRelativeTo(null);
        getContentPane().setBackground(Colores.FONDO);
        
        Toolkit tk = Toolkit.getDefaultToolkit();
		ImageIcon imagenCursor = new ImageIcon("src\\img\\pointer_b.png");
		Cursor cursor = tk.createCustomCursor(imagenCursor.getImage(), new Point(0,0), "Cursor");
		this.setCursor(cursor);
        
        PanelPersonalizable fondo = new PanelPersonalizable();
        fondo.setBounds(75, 50, 680, 450);
        fondo.setBackground(Colores.LOGIN_PANEL);
        
        // Shadow
        PanelPersonalizable fondo2 = new PanelPersonalizable();
        fondo2.setBounds(72, 46, 687, 457);
        fondo2.setBackground(Colores.SOMBRA_COLOR);
        

        JPanel panelComponentes = new JPanel();
        panelComponentes.setLayout(new BoxLayout(panelComponentes, BoxLayout.Y_AXIS));
        panelComponentes.setBounds(70, 50, 680, 850);
        panelComponentes.setOpaque(false);
        panelComponentes.setBorder(new EmptyBorder(20, 40, 20, 40));
        
        // Para centrar todos los componentes
        panelComponentes.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JLabel saludo = new JLabel("Registrar cliente"); 
        saludo.setOpaque(false);
        saludo.setFont(Fuentes.fuenteTitulo);
        saludo.setForeground(Colores.TEXTO_COLOR);
        saludo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelComponentes.add(saludo);
        panelComponentes.add(Box.createRigidArea(new Dimension(0,80)));

        lblErrorNombre = crearErrorLabel();
        lblErrorApellido = crearErrorLabel();
        lblErrorEdad = crearErrorLabel();
        lblErrorDomicilio = crearErrorLabel();
        lblErrorNumeroCelular = crearErrorLabel();
        lblErrorCorreo = crearErrorLabel();
        lblErrorEmpleo = crearErrorLabel();
        lblErrorTelefonoEmpleo = crearErrorLabel();
        lblErrorDomicilioEmpleo = crearErrorLabel();
        lblErrorIngresoMensual = crearErrorLabel();
        lblErrorBanco = crearErrorLabel();
        lblErrorCuentaBancaria = crearErrorLabel();
        lblErrorCurp = crearErrorLabel();
        lblErrorFoto=crearErrorLabel();
        lblErrorDocumento = crearErrorLabel();
        
        
        crearComponentesDeRegistro(panelComponentes);
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,25)));
        
        
       
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new BoxLayout(panelBoton, BoxLayout.Y_AXIS));
        panelBoton.setOpaque(false);
        panelBoton.setAlignmentX(Component.CENTER_ALIGNMENT);

        crearBoton(registrar, "..\\img\\icono.png","Registrarse", Colores.BOTON_COLOR1, 150, 35, Fuentes.fuenteBoton);
       
        
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

        
        
        
        
        
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,10)));
        panelBoton.add(registrar);
        panelComponentes.add(panelBoton);
        
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Scroll Pane
        JScrollPane scrollPanel= new JScrollPane(panelComponentes);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setBounds(70, 50, 710, 450);
		scrollPanel.getViewport().setOpaque(false);
		scrollPanel.setOpaque(false);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(20); // Hacer que el scroll se baje mas rapido
		scrollPanel.setBorder(null);
		scrollPanel.getVerticalScrollBar().setBackground(Colores.FONDO);
		scrollPanel.getVerticalScrollBar().setOpaque(false);
		scrollPanel.getViewport().setBackground(Colores.FONDO);
		
		add(scrollPanel);
        add(fondo);
        add(fondo2);
        asignarKeyListeners();
        setVisible(true);
    }
    public FormularioGeneralCliente(Cliente cliente)
    {
    	this.cliente=cliente;
    	edicion=true;
    	usuarioRepository = new UsuarioRepository();
    	
        ineIconoCliente = escalarImagenLocal("/img/LicenseDefault.png",200,200);
        ineIconoCliente.setDescription("/img/LicenseDefault.png");
        
        comprobanteIconoCliente = escalarImagenLocal("/img/DocumentDefaultIcon.png",200,200);
        comprobanteIconoCliente.setDescription("/img/DocumentDefaultIcon.png");
        setSize(850,550);
        setLayout(null);
        setResizable(false);
        setTitle("Registro");
        setLocationRelativeTo(null);
        getContentPane().setBackground(Colores.FONDO);
        
        Toolkit tk = Toolkit.getDefaultToolkit();
		ImageIcon cursorImage = new ImageIcon("src\\img\\pointer_b.png");
		Cursor myCursor = tk.createCustomCursor(cursorImage.getImage(), new Point(0,0), "Cursor");
		this.setCursor(myCursor);
        
        PanelPersonalizable fondo = new PanelPersonalizable();
        fondo.setBounds(75, 50, 680, 450);
        fondo.setBackground(Colores.LOGIN_PANEL);
        
        // Shadow
        PanelPersonalizable fondo2 = new PanelPersonalizable();
        fondo2.setBounds(72, 46, 687, 457);
        fondo2.setBackground(Colores.SOMBRA_COLOR);
        

        JPanel panelComponentes = new JPanel();
        panelComponentes.setLayout(new BoxLayout(panelComponentes, BoxLayout.Y_AXIS));
        panelComponentes.setBounds(70, 50, 680, 850);
        panelComponentes.setOpaque(false);
        panelComponentes.setBorder(new EmptyBorder(20, 40, 20, 40));
        
        // Para centrar todos los componentes
        panelComponentes.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JLabel saludo = new JLabel("Registrar cliente"); 
        saludo.setOpaque(false);
        saludo.setFont(Fuentes.fuenteTitulo);
        saludo.setForeground(Colores.TEXTO_COLOR);
        saludo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelComponentes.add(saludo);
        panelComponentes.add(Box.createRigidArea(new Dimension(0,80)));

        lblErrorNombre = crearErrorLabel();
        lblErrorApellido = crearErrorLabel();
        lblErrorEdad = crearErrorLabel();
        lblErrorDomicilio = crearErrorLabel();
        lblErrorNumeroCelular = crearErrorLabel();
        lblErrorCorreo = crearErrorLabel();
        lblErrorEmpleo = crearErrorLabel();
        lblErrorTelefonoEmpleo = crearErrorLabel();
        lblErrorDomicilioEmpleo = crearErrorLabel();
        lblErrorIngresoMensual = crearErrorLabel();
        lblErrorBanco = crearErrorLabel();
        lblErrorCuentaBancaria = crearErrorLabel();
        lblErrorCurp = crearErrorLabel();
        lblErrorFoto=crearErrorLabel();
        lblErrorDocumento = crearErrorLabel();
        
        
        crearComponentesDeRegistro(panelComponentes);
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,25)));
        
        
       
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new BoxLayout(panelBoton, BoxLayout.Y_AXIS));
        panelBoton.setOpaque(false);
        panelBoton.setAlignmentX(Component.CENTER_ALIGNMENT);

        crearBoton(registrar, "..\\img\\icono.png","Registrarse", Colores.BOTON_COLOR1, 150, 35, Fuentes.fuenteBoton);
       
        
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

        
        
        
        
        
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,10)));
        panelBoton.add(registrar);
        panelComponentes.add(panelBoton);
        
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Scroll Pane
        JScrollPane scrollPanel= new JScrollPane(panelComponentes);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setBounds(70, 50, 710, 450);
		scrollPanel.getViewport().setOpaque(false);
		scrollPanel.setOpaque(false);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(20); // Hacer que el scroll se baje mas rapido
		scrollPanel.setBorder(null);
		scrollPanel.getVerticalScrollBar().setBackground(Colores.FONDO);
		scrollPanel.getVerticalScrollBar().setOpaque(false);
		scrollPanel.getViewport().setBackground(Colores.FONDO);
		asignarValores(cliente);
		add(scrollPanel);
        add(fondo);
        add(fondo2);
        asignarKeyListeners();
        setVisible(true);
    }
    public void asignarValores(Cliente cliente) {
    	ineCliente.setIcon(escalarImagen(cliente.getIneDireccion(), 200, 200));
    	ineIconoCliente.setDescription(cliente.getIneDireccion());
    	
    	PDDocument document;
		try {
			document = Loader.loadPDF(new File(cliente.getComprobanteDomicilio()));
			PDFRenderer render = new PDFRenderer(document);
			BufferedImage imagen = render.renderImageWithDPI(0, 150);
			comprobanteCliente.setIcon(escalarBufferedImagen(imagen, cliente.getComprobanteDomicilio(), 200, 200));
			comprobanteIconoCliente.setDescription(cliente.getComprobanteDomicilio());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    	nombres.setText(cliente.getNombre());
    	apellidos.setText(cliente.getApellido());
    	edad.setText(String.valueOf(cliente.getEdad()));
    	domicilio.setText(cliente.getDomicilio());
    	numeroCelular.setText(cliente.getCelular());
    	correo.setText(cliente.getCorreoElectronico());
    	empleo.setText(cliente.getEmpleo());
    	telefonoEmpleo.setText(cliente.getTelfEmpleo());
    	domicilioEmpleo.setText(cliente.getDomicilioEmpleo());
    	ingresoMensual.setText(String.valueOf(cliente.getIngresosMensuales()));
    	banco.setText(cliente.getBanco());
    	cuentaBancaria.setText(cliente.getCuentaBancaria());
    	curp.setText(cliente.getCurp());

    }
    private void asignarKeyListeners() {
    	asignarKeyListenerParaNumero(edad,3);
    	asignarKeyListenerParaNumero(numeroCelular,15);
    	asignarKeyListenerParaNumero(telefonoEmpleo,15);
    	asignarKeyListenerParaNumero(ingresoMensual,10);
    	asignarKeyListenerParaNumero(cuentaBancaria,32);
    	asignarKeyListenerParaTexto(nombres, 45);
    	asignarKeyListenerParaTexto(domicilio, 45);
    	asignarKeyListenerParaTexto(correo, 45);
    	asignarKeyListenerParaTexto(empleo, 45);
    	asignarKeyListenerParaTexto(domicilioEmpleo, 45);
    	asignarKeyListenerParaTexto(banco, 45);
    	asignarKeyListenerParaTexto(curp, 45);
    }
    private void asignarKeyListenerParaNumero(JTextField campoDeTexto, int extensión)
    {
    	campoDeTexto.addKeyListener(new KeyAdapter() 
         {
         	public void keyTyped(KeyEvent e)
         	{
         		
         			if(!Character.isSpaceChar(e.getKeyChar()))
         			{
         				if(!Character.isDigit(e.getKeyChar()) || Character.isAlphabetic(e.getKeyChar()))
         				{
         					e.consume();
         				}
         			}
         		
         		
         		if(campoDeTexto.getText().length() >= extensión)
         		{
         			e.consume();
         		}
         	}
 		});
    }
    private void asignarKeyListenerParaTexto(JTextField campoDeTexto, int extensión)
    {
    	campoDeTexto.addKeyListener(new KeyAdapter() 
         {
         	public void keyTyped(KeyEvent e)
         	{	
         		if(campoDeTexto.getText().length() >= extensión)
         		{
         			e.consume();
         		}
         	}
 		});
    }
    public void seleccionarImagen() {
    	JFileChooser selector=new  JFileChooser();
    	selector.setDialogTitle("Selecciona tu foto de perfil");
    	FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagenes", "jpg","jpeg","png");
    	selector.setFileFilter(filtro);
    	int opcion= selector.showOpenDialog(this);
    	if(opcion==JFileChooser.APPROVE_OPTION) {
    		File archivo=selector.getSelectedFile();
    		ineCliente.setIcon(escalarImagen(archivo.getAbsolutePath(), 200, 200));
    		ineIconoCliente.setDescription(archivo.getAbsolutePath());
    	}
    }
    public void seleccionarPDF() {
    	JFileChooser selector=new  JFileChooser();
    	selector.setDialogTitle("Selecciona el comprobante de domicillio");
    	FileNameExtensionFilter filtro = new FileNameExtensionFilter("documento de texto", "pdf");
    	selector.setFileFilter(filtro);
    	int opcion= selector.showOpenDialog(this);
    	if(opcion==JFileChooser.APPROVE_OPTION) {
    		File archivo=selector.getSelectedFile();
    		try {
				PDDocument documento = Loader.loadPDF(archivo);
				PDFRenderer render = new PDFRenderer(documento);
				BufferedImage imagen = render.renderImageWithDPI(0, 150);
				comprobanteCliente.setIcon(escalarBufferedImagen(imagen, archivo.getAbsolutePath(), 200, 200));
				comprobanteIconoCliente.setDescription(archivo.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    private JTextField crearTextField(String placeholder, String nombreCampo) 
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
        campo.setName(nombreCampo);
        
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
    private ImageIcon escalarBufferedImagen(BufferedImage imagen, String direccion,int x,int y) {
    	
        ImageIcon iconoOriginal = new ImageIcon(imagen);

       
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
    	
    	textField.setBackground(Colores.TEXTO_TABULADO_COLOR);
    	
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                }
                textField.setForeground(Color.BLACK);
                textField.setBackground(Color.WHITE);
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.BLACK);
                    textField.setBackground(Colores.FONDO_TEXTO_COLOR);
                } else {
                	textField.setForeground(Color.BLACK);
                    textField.setBackground(Colores.TEXTO_TABULADO_COLOR);
                }
            }
        });
    }
    
    private void crearBoton(JButton button, String ruta,String titulo, Color colorBoton, int ancho, int largo, Font font)
    {
        int buttonAncho = ancho;   
        int buttonAlto = largo;   
        
        button.setBackground(colorBoton);
        button.setForeground(Color.black);
        button.setToolTipText(titulo);
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(buttonAncho, buttonAlto));
        button.setPreferredSize(new Dimension(buttonAncho, buttonAlto));
        button.setMinimumSize(new Dimension(buttonAncho, buttonAlto));
        
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
    	lblErrorEdad.setText("");
    	lblErrorDomicilio.setText("");
    	lblErrorNumeroCelular.setText("");
    	lblErrorCorreo.setText("");
    	lblErrorEmpleo.setText("");
    	lblErrorTelefonoEmpleo.setText("");
    	lblErrorDomicilioEmpleo.setText("");
    	lblErrorIngresoMensual.setText("");
    	lblErrorBanco.setText("");
    	lblErrorCuentaBancaria.setText("");
    	lblErrorCurp.setText("");
    	lblErrorFoto.setText("");
    	lblErrorDocumento.setText("");
    }
    
    public void crearComponentesDeRegistro(JPanel panelComponentes)
    {
    	
    	nombres = crearTextField("Nombre", "nombres");
    	apellidos = crearTextField("Apellido", "apellidos");
    	edad = crearTextField("Edad", "edad");
    	domicilio = crearTextField("Direccion", "domicilio");
    	numeroCelular = crearTextField("Numero celular", "numeroCelular");
    	correo = crearTextField("correo electronico", "correo");
    	empleo = crearTextField("Empleo", "empleo");
    	telefonoEmpleo = crearTextField("Telefono empleo", "telefonoEmpleo");
    	domicilioEmpleo = crearTextField("Domicilio empleo", "domicilioEmpleo");
    	ingresoMensual = crearTextField("Ingreso mensual", "ingresoMensual");
    	banco = crearTextField("Nombre Banco", "banco");
    	cuentaBancaria = crearTextField("Cuenta bancaria", "cuentaBancaria");
    	curp = crearTextField("Curp", "curp");

        
        crearBoton(seleccionar,"..\\img\\icono.png" , "Seleccionar", Colores.BOTON_COLOR2, 105, 30, Fuentes.fuenteBotonChico);
        crearBoton(seleccionarComprobante,"..\\img\\icono.png" , "Seleccionar", Colores.BOTON_COLOR2, 105, 30, Fuentes.fuenteBotonChico);
        
        asignarFocusListenerConPlaceholder(nombres, "Nombre");
        asignarFocusListenerConPlaceholder(apellidos, "Apellido");
        asignarFocusListenerConPlaceholder(edad, "Edad");
        asignarFocusListenerConPlaceholder(domicilio, "Direccion");
        asignarFocusListenerConPlaceholder(numeroCelular, "Numero celular");
        asignarFocusListenerConPlaceholder(correo, "correo electronico");
        asignarFocusListenerConPlaceholder(empleo, "Empleo");
        asignarFocusListenerConPlaceholder(telefonoEmpleo, "Telefono empleo");
        asignarFocusListenerConPlaceholder(domicilioEmpleo, "Domicilio empleo");
        asignarFocusListenerConPlaceholder(ingresoMensual, "Ingreso mensual");
        asignarFocusListenerConPlaceholder(banco, "Nombre Banco");
        asignarFocusListenerConPlaceholder(cuentaBancaria, "Cuenta bancaria");
        asignarFocusListenerConPlaceholder(curp, "Curp");

        
        JLabel ine = new JLabel("Foto INE");
        ine.setOpaque(false);
        ine.setFont(Fuentes.fuenteTitulo);
        ine.setForeground(Colores.TEXTO_COLOR);
        ine.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelComponentes.add(ine);
        
        ineCliente.setIcon(ineIconoCliente);
        ineCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
        ineCliente.setPreferredSize(new Dimension(200,200));
        ineCliente.setVisible(true);
        panelComponentes.add(ineCliente);
        lblErrorFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelComponentes.add(lblErrorFoto);
        panelComponentes.add(seleccionar);
        
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,10)));
        
        JLabel comprobante = new JLabel("Comprobante Domicilio");
        comprobante.setOpaque(false);
        comprobante.setFont(Fuentes.fuenteTitulo);
        comprobante.setForeground(Colores.TEXTO_COLOR);
        comprobante.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelComponentes.add(comprobante);
        
        comprobanteCliente.setIcon(comprobanteIconoCliente);
        comprobanteCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
        comprobanteCliente.setPreferredSize(new Dimension(200,200));
        comprobanteCliente.setVisible(true);
        panelComponentes.add(comprobanteCliente);
        lblErrorDocumento.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelComponentes.add(lblErrorDocumento);
        panelComponentes.add(seleccionarComprobante);
        
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,10)));
        
        JPanel panelIneEnvoltorio = new JPanel();
        panelIneEnvoltorio.setLayout(new BoxLayout(panelIneEnvoltorio, BoxLayout.Y_AXIS));
        panelIneEnvoltorio.setOpaque(false);
        panelIneEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelIneEnvoltorio.setMaximumSize(new Dimension(400, 70));
        
        
     // Nombre
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

        // Apellido
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

        // Edad
        JPanel panelEdadEnvoltorio = new JPanel();
        panelEdadEnvoltorio.setLayout(new BoxLayout(panelEdadEnvoltorio, BoxLayout.Y_AXIS));
        panelEdadEnvoltorio.setOpaque(false);
        panelEdadEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelEdadEnvoltorio.setMaximumSize(new Dimension(400, 70));

        edad.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelEdadEnvoltorio.add(edad);
        panelEdadEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorEdad.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelEdadEnvoltorio.add(lblErrorEdad);

        panelComponentes.add(panelEdadEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // Domicilio
        JPanel panelDomicilioEnvoltorio = new JPanel();
        panelDomicilioEnvoltorio.setLayout(new BoxLayout(panelDomicilioEnvoltorio, BoxLayout.Y_AXIS));
        panelDomicilioEnvoltorio.setOpaque(false);
        panelDomicilioEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDomicilioEnvoltorio.setMaximumSize(new Dimension(400, 70));

        domicilio.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelDomicilioEnvoltorio.add(domicilio);
        panelDomicilioEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorDomicilio.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelDomicilioEnvoltorio.add(lblErrorDomicilio);

        panelComponentes.add(panelDomicilioEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // Número Celular
        JPanel panelNumeroCelularEnvoltorio = new JPanel();
        panelNumeroCelularEnvoltorio.setLayout(new BoxLayout(panelNumeroCelularEnvoltorio, BoxLayout.Y_AXIS));
        panelNumeroCelularEnvoltorio.setOpaque(false);
        panelNumeroCelularEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelNumeroCelularEnvoltorio.setMaximumSize(new Dimension(400, 70));

        numeroCelular.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelNumeroCelularEnvoltorio.add(numeroCelular);
        panelNumeroCelularEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorNumeroCelular.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelNumeroCelularEnvoltorio.add(lblErrorNumeroCelular);

        panelComponentes.add(panelNumeroCelularEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // Correo
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

        // Empleo
        JPanel panelEmpleoEnvoltorio = new JPanel();
        panelEmpleoEnvoltorio.setLayout(new BoxLayout(panelEmpleoEnvoltorio, BoxLayout.Y_AXIS));
        panelEmpleoEnvoltorio.setOpaque(false);
        panelEmpleoEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelEmpleoEnvoltorio.setMaximumSize(new Dimension(400, 70));

        empleo.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelEmpleoEnvoltorio.add(empleo);
        panelEmpleoEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorEmpleo.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelEmpleoEnvoltorio.add(lblErrorEmpleo);

        panelComponentes.add(panelEmpleoEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));
     // Teléfono Empleo
        JPanel panelTelefonoEmpleoEnvoltorio = new JPanel();
        panelTelefonoEmpleoEnvoltorio.setLayout(new BoxLayout(panelTelefonoEmpleoEnvoltorio, BoxLayout.Y_AXIS));
        panelTelefonoEmpleoEnvoltorio.setOpaque(false);
        panelTelefonoEmpleoEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTelefonoEmpleoEnvoltorio.setMaximumSize(new Dimension(400, 70));

        telefonoEmpleo.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelTelefonoEmpleoEnvoltorio.add(telefonoEmpleo);
        panelTelefonoEmpleoEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorTelefonoEmpleo.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelTelefonoEmpleoEnvoltorio.add(lblErrorTelefonoEmpleo);

        panelComponentes.add(panelTelefonoEmpleoEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // Domicilio Empleo
        JPanel panelDomicilioEmpleoEnvoltorio = new JPanel();
        panelDomicilioEmpleoEnvoltorio.setLayout(new BoxLayout(panelDomicilioEmpleoEnvoltorio, BoxLayout.Y_AXIS));
        panelDomicilioEmpleoEnvoltorio.setOpaque(false);
        panelDomicilioEmpleoEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDomicilioEmpleoEnvoltorio.setMaximumSize(new Dimension(400, 70));

        domicilioEmpleo.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelDomicilioEmpleoEnvoltorio.add(domicilioEmpleo);
        panelDomicilioEmpleoEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorDomicilioEmpleo.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelDomicilioEmpleoEnvoltorio.add(lblErrorDomicilioEmpleo);

        panelComponentes.add(panelDomicilioEmpleoEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // Ingreso Mensual
        JPanel panelIngresoMensualEnvoltorio = new JPanel();
        panelIngresoMensualEnvoltorio.setLayout(new BoxLayout(panelIngresoMensualEnvoltorio, BoxLayout.Y_AXIS));
        panelIngresoMensualEnvoltorio.setOpaque(false);
        panelIngresoMensualEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelIngresoMensualEnvoltorio.setMaximumSize(new Dimension(400, 70));

        ingresoMensual.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelIngresoMensualEnvoltorio.add(ingresoMensual);
        panelIngresoMensualEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorIngresoMensual.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelIngresoMensualEnvoltorio.add(lblErrorIngresoMensual);

        panelComponentes.add(panelIngresoMensualEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // Banco
        JPanel panelBancoEnvoltorio = new JPanel();
        panelBancoEnvoltorio.setLayout(new BoxLayout(panelBancoEnvoltorio, BoxLayout.Y_AXIS));
        panelBancoEnvoltorio.setOpaque(false);
        panelBancoEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBancoEnvoltorio.setMaximumSize(new Dimension(400, 70));

        banco.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelBancoEnvoltorio.add(banco);
        panelBancoEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorBanco.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelBancoEnvoltorio.add(lblErrorBanco);

        panelComponentes.add(panelBancoEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // Cuenta Bancaria
        JPanel panelCuentaBancariaEnvoltorio = new JPanel();
        panelCuentaBancariaEnvoltorio.setLayout(new BoxLayout(panelCuentaBancariaEnvoltorio, BoxLayout.Y_AXIS));
        panelCuentaBancariaEnvoltorio.setOpaque(false);
        panelCuentaBancariaEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCuentaBancariaEnvoltorio.setMaximumSize(new Dimension(400, 70));

        cuentaBancaria.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelCuentaBancariaEnvoltorio.add(cuentaBancaria);
        panelCuentaBancariaEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorCuentaBancaria.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelCuentaBancariaEnvoltorio.add(lblErrorCuentaBancaria);

        panelComponentes.add(panelCuentaBancariaEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // CURP
        JPanel panelCurpEnvoltorio = new JPanel();
        panelCurpEnvoltorio.setLayout(new BoxLayout(panelCurpEnvoltorio, BoxLayout.Y_AXIS));
        panelCurpEnvoltorio.setOpaque(false);
        panelCurpEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCurpEnvoltorio.setMaximumSize(new Dimension(400, 70));

        curp.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelCurpEnvoltorio.add(curp);
        panelCurpEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorCurp.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelCurpEnvoltorio.add(lblErrorCurp);

        panelComponentes.add(panelCurpEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        nombres.setBackground(Colores.FONDO_TEXTO_COLOR);
        apellidos.setBackground(Colores.FONDO_TEXTO_COLOR);
        edad.setBackground(Colores.FONDO_TEXTO_COLOR);
        domicilio.setBackground(Colores.FONDO_TEXTO_COLOR);
        numeroCelular.setBackground(Colores.FONDO_TEXTO_COLOR);
        correo.setBackground(Colores.FONDO_TEXTO_COLOR);
        empleo.setBackground(Colores.FONDO_TEXTO_COLOR);
        domicilioEmpleo.setBackground(Colores.FONDO_TEXTO_COLOR);
        telefonoEmpleo.setBackground(Colores.FONDO_TEXTO_COLOR);
        ingresoMensual.setBackground(Colores.FONDO_TEXTO_COLOR);
        banco.setBackground(Colores.FONDO_TEXTO_COLOR);
        cuentaBancaria.setBackground(Colores.FONDO_TEXTO_COLOR);
        curp.setBackground(Colores.FONDO_TEXTO_COLOR);

        
        
        
        
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
			usuarioRepository.guardar(usuario);
			System.out.println("Se Registro Usuario!");
			JOptionPane.showMessageDialog(this, "Usuario registrado");
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	public JLabel getINEUsuario() {
		return ineCliente;
	}
	public void setINEUsuario(JLabel iconoUsuario) {
		this.ineCliente = iconoUsuario;
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
	public String getIconoDescription() {
		return ineIconoCliente.getDescription();
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
		return usuarioRepository;
	}
	public void setUserRepository(UsuarioRepository userRepository) {
		this.usuarioRepository = userRepository;
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
	
	public ImageIcon getIconoUsuarioFinal() {
		return ineIconoCliente;
	}
	public void setIconoUsuarioFinal(ImageIcon iconoUsuarioFinal) {
		this.ineIconoCliente = iconoUsuarioFinal;
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
	public JTextField getEdad() {
		return edad;
	}
	public void setEdad(JTextField edad) {
		this.edad = edad;
	}
	public JTextField getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(JTextField domicilio) {
		this.domicilio = domicilio;
	}
	public JTextField getNumeroCelular() {
		return numeroCelular;
	}
	public void setNumeroCelular(JTextField numeroCelular) {
		this.numeroCelular = numeroCelular;
	}
	public JTextField getEmpleo() {
		return empleo;
	}
	public void setEmpleo(JTextField empleo) {
		this.empleo = empleo;
	}
	public JTextField getIngresoMensual() {
		return ingresoMensual;
	}
	public void setIngresoMensual(JTextField ingresoMensual) {
		this.ingresoMensual = ingresoMensual;
	}
	public JTextField getBanco() {
		return banco;
	}
	public void setBanco(JTextField banco) {
		this.banco = banco;
	}
	public JTextField getCuentaBancaria() {
		return cuentaBancaria;
	}
	public void setCuentaBancaria(JTextField cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}
	public JTextField getCurp() {
		return curp;
	}
	public void setCurp(JTextField curp) {
		this.curp = curp;
	}
	public JButton getSeleccionarComprobante() {
		return seleccionarComprobante;
	}
	public void setSeleccionarComprobante(JButton seleccionarComprobante) {
		this.seleccionarComprobante = seleccionarComprobante;
	}
	public JLabel getLblErrorEdad() {
		return lblErrorEdad;
	}
	public void setLblErrorEdad(JLabel lblErrorEdad) {
		this.lblErrorEdad = lblErrorEdad;
	}
	public JLabel getLblErrorDomicilio() {
		return lblErrorDomicilio;
	}
	public void setLblErrorDomicilio(JLabel lblErrorDomicilio) {
		this.lblErrorDomicilio = lblErrorDomicilio;
	}
	public JLabel getLblErrorNumeroCelular() {
		return lblErrorNumeroCelular;
	}
	public void setLblErrorNumeroCelular(JLabel lblErrorNumeroCelular) {
		this.lblErrorNumeroCelular = lblErrorNumeroCelular;
	}
	public JLabel getLblErrorEmpleo() {
		return lblErrorEmpleo;
	}
	public void setLblErrorEmpleo(JLabel lblErrorEmpleo) {
		this.lblErrorEmpleo = lblErrorEmpleo;
	}
	public JLabel getLblErrorIngresoMensual() {
		return lblErrorIngresoMensual;
	}
	public void setLblErrorIngresoMensual(JLabel lblErrorIngresoMensual) {
		this.lblErrorIngresoMensual = lblErrorIngresoMensual;
	}
	public JLabel getLblErrorBanco() {
		return lblErrorBanco;
	}
	public void setLblErrorBanco(JLabel lblErrorBanco) {
		this.lblErrorBanco = lblErrorBanco;
	}
	public JLabel getLblErrorCuentaBancaria() {
		return lblErrorCuentaBancaria;
	}
	public void setLblErrorCuentaBancaria(JLabel lblErrorCuentaBancaria) {
		this.lblErrorCuentaBancaria = lblErrorCuentaBancaria;
	}
	public JLabel getLblErrorCurp() {
		return lblErrorCurp;
	}
	public void setLblErrorCurp(JLabel lblErrorCurp) {
		this.lblErrorCurp = lblErrorCurp;
	}
	public ImageIcon getIneIconCliente() {
		return ineIconoCliente;
	}
	public void setIneIconCliente(ImageIcon ineIconClient) {
		this.ineIconoCliente = ineIconClient;
	}
	public JLabel getIneCliente() {
		return ineCliente;
	}
	public void setIneCliente(JLabel ineClient) {
		this.ineCliente = ineClient;
	}
	public ImageIcon getComprobanteIconClient() {
		return comprobanteIconoCliente;
	}
	public String getComprobanteIconClientDirection() {
		return comprobanteIconoCliente.getDescription();
	}
	public void setComprobanteIconClient(ImageIcon comprobanteIconClient) {
		this.comprobanteIconoCliente = comprobanteIconClient;
	}
	public JLabel getComprobanteClient() {
		return comprobanteCliente;
	}
	public void setComprobanteClient(JLabel comprobanteClient) {
		this.comprobanteCliente = comprobanteClient;
	}
	public JTextField getTelefonoEmpleo() {
		return telefonoEmpleo;
	}
	public void setTelefonoEmpleo(JTextField telefonoEmpleo) {
		this.telefonoEmpleo = telefonoEmpleo;
	}
	public JTextField getDomicilioEmpleo() {
		return domicilioEmpleo;
	}
	public void setDomicilioEmpleo(JTextField domicilioEmpleo) {
		this.domicilioEmpleo = domicilioEmpleo;
	}
	public JLabel getLblErrorTelefonoEmpleo() {
		return lblErrorTelefonoEmpleo;
	}
	public void setLblErrorTelefonoEmpleo(JLabel lblErrorTelefonoEmpleo) {
		this.lblErrorTelefonoEmpleo = lblErrorTelefonoEmpleo;
	}
	public JLabel getLblErrorDomicilioEmpleo() {
		return lblErrorDomicilioEmpleo;
	}
	public void setLblErrorDomicilioEmpleo(JLabel lblErrorDomicilioEmpleo) {
		this.lblErrorDomicilioEmpleo = lblErrorDomicilioEmpleo;
	}
	public JLabel getLblErrorFoto() {
		return lblErrorFoto;
	}
	public void setLblErrorFoto(JLabel lblErrorFoto) {
		this.lblErrorFoto = lblErrorFoto;
	}
	public JLabel getLblErrorDocumento() {
		return lblErrorDocumento;
	}
	public void setLblErrorDocumento(JLabel lblErrorDocumento) {
		this.lblErrorDocumento = lblErrorDocumento;
	}
	public boolean isEdit() {
		return edicion;
	}
	public void setEdit(boolean edit) {
		this.edicion = edit;
	}
	public Cliente getClient() {
		return cliente;
	}
	public void setClient(Cliente client) {
		this.cliente = client;
	}
	
	
    
}