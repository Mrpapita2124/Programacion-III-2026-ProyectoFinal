package views.formulario;

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

import modelos.Client;
import modelos.User;
import repository.UserRepository;
import utils.Colores;
import utils.PanelPersonalizable;

public class FormularioGeneralCliente extends JFrame{
    
	
	// Puro Fonts, ya depues hare otra clase de Fonts
    public Font fuente;
    private Font fontError = new Font("Times New Roman", Font.ITALIC, 12);
    private Font fontTextoCampo = new Font("Times New Roman", Font.ITALIC, 15);
    private Font fontBoton = new Font("Times New Roman", Font.BOLD, 20);
    private Font fontTitulo = new Font("Times New Roman", Font.BOLD, 35);
    private Font fontBotonChico = new Font("Times New Roman", Font.BOLD, 14);
    
    
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
   
    private UserRepository userRepository;
    
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

    Client client;
   // public JLabel lblErrorFoto;
    private ImageIcon ineIconClient;
    private JLabel ineClient = new JLabel();
    
    private ImageIcon comprobanteIconClient;
    private JLabel comprobanteClient = new JLabel();
    
    private boolean edit;
    //Chckboxes
    private JCheckBox guardar= new JCheckBox("Guardar como usuario rapido");
    public FormularioGeneralCliente()
    {
    	edit=false;
    	userRepository = new UserRepository();
    	
        ineIconClient = escalarImagenLocal("/img/LicenseDefault.png",200,200);
        ineIconClient.setDescription("/img/LicenseDefault.png");
        
        comprobanteIconClient = escalarImagenLocal("/img/DocumentDefaultIcon.png",200,200);
        comprobanteIconClient.setDescription("/img/DocumentDefaultIcon.png");
        setSize(850,550);
        setLayout(null);
        setResizable(false);
        setTitle("Registro");
        setLocationRelativeTo(null);
        getContentPane().setBackground(Colores.BACKGROUND);
        
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
        fondo2.setBackground(Colores.SHADOW_COLOR);
        

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
        saludo.setFont(fontTitulo);
        saludo.setForeground(Colores.TEXT_COLOR);
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

        crearBoton(registrar, "..\\img\\icono.png","Registrarse", Colores.BUTTON_COLOR1, 150, 35, fontBoton);
       
        
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
		scrollPanel.getVerticalScrollBar().setBackground(Colores.BACKGROUND);
		scrollPanel.getVerticalScrollBar().setOpaque(false);
		scrollPanel.getViewport().setBackground(Colores.BACKGROUND);
		
		add(scrollPanel);
        add(fondo);
        add(fondo2);
        asignarKeyListeners();
        setVisible(true);
    }
    public FormularioGeneralCliente(Client client)
    {
    	this.client=client;
    	edit=true;
    	userRepository = new UserRepository();
    	
        ineIconClient = escalarImagenLocal("/img/LicenseDefault.png",200,200);
        ineIconClient.setDescription("/img/LicenseDefault.png");
        
        comprobanteIconClient = escalarImagenLocal("/img/DocumentDefaultIcon.png",200,200);
        comprobanteIconClient.setDescription("/img/DocumentDefaultIcon.png");
        setSize(850,550);
        setLayout(null);
        setResizable(false);
        setTitle("Registro");
        setLocationRelativeTo(null);
        getContentPane().setBackground(Colores.BACKGROUND);
        
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
        fondo2.setBackground(Colores.SHADOW_COLOR);
        

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
        saludo.setFont(fontTitulo);
        saludo.setForeground(Colores.TEXT_COLOR);
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

        crearBoton(registrar, "..\\img\\icono.png","Registrarse", Colores.BUTTON_COLOR1, 150, 35, fontBoton);
       
        
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
		scrollPanel.getVerticalScrollBar().setBackground(Colores.BACKGROUND);
		scrollPanel.getVerticalScrollBar().setOpaque(false);
		scrollPanel.getViewport().setBackground(Colores.BACKGROUND);
		addValues(client);
		add(scrollPanel);
        add(fondo);
        add(fondo2);
        asignarKeyListeners();
        setVisible(true);
    }
    public void addValues(Client client) {
    	ineClient.setIcon(escalarImagen(client.getIneDireccion(), 200, 200));
    	ineIconClient.setDescription(client.getIneDireccion());
    	
    	PDDocument document;
		try {
			document = Loader.loadPDF(new File(client.getComprobanteDomicilio()));
			PDFRenderer render = new PDFRenderer(document);
			BufferedImage imagen = render.renderImageWithDPI(0, 150);
			comprobanteClient.setIcon(escalarBufferedImagen(imagen, client.getComprobanteDomicilio(), 200, 200));
			comprobanteIconClient.setDescription(client.getComprobanteDomicilio());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    	nombres.setText(client.getNombre());
    	apellidos.setText(client.getApellido());
    	edad.setText(String.valueOf(client.getEdad()));
    	domicilio.setText(client.getDomicilio());
    	numeroCelular.setText(client.getCelular());
    	correo.setText(client.getCorreoElectronico());
    	empleo.setText(client.getEmpleo());
    	telefonoEmpleo.setText(client.getTelfEmpleo());
    	domicilioEmpleo.setText(client.getDomicilioEmpleo());
    	ingresoMensual.setText(String.valueOf(client.getIngresosMensuales()));
    	banco.setText(client.getBanco());
    	cuentaBancaria.setText(client.getCuentaBancaria());
    	curp.setText(client.getCurp());

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
    private void asignarKeyListenerParaNumero(JTextField JtextField, int extensión)
    {
    	JtextField.addKeyListener(new KeyAdapter() 
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
         		
         		
         		if(JtextField.getText().length() >= extensión)
         		{
         			e.consume();
         		}
         	}
 		});
    }
    private void asignarKeyListenerParaTexto(JTextField JtextField, int extensión)
    {
    	JtextField.addKeyListener(new KeyAdapter() 
         {
         	public void keyTyped(KeyEvent e)
         	{	
         		if(JtextField.getText().length() >= extensión)
         		{
         			e.consume();
         		}
         	}
 		});
    }
    public void chooseImage() {
    	JFileChooser chooser=new  JFileChooser();
    	chooser.setDialogTitle("Selecciona tu foto de perfil");
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagenes", "jpg","jpeg","png");
    	chooser.setFileFilter(filter);
    	int option= chooser.showOpenDialog(this);
    	if(option==JFileChooser.APPROVE_OPTION) {
    		File file=chooser.getSelectedFile();
    		ineClient.setIcon(escalarImagen(file.getAbsolutePath(), 200, 200));
    		ineIconClient.setDescription(file.getAbsolutePath());
    	}
    }
    public void choosePDF() {
    	JFileChooser chooser=new  JFileChooser();
    	chooser.setDialogTitle("Selecciona el comprobante de domicillio");
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("documento de texto", "pdf");
    	chooser.setFileFilter(filter);
    	int option= chooser.showOpenDialog(this);
    	if(option==JFileChooser.APPROVE_OPTION) {
    		File file=chooser.getSelectedFile();
    		try {
				PDDocument document = Loader.loadPDF(file);
				PDFRenderer render = new PDFRenderer(document);
				BufferedImage imagen = render.renderImageWithDPI(0, 150);
				comprobanteClient.setIcon(escalarBufferedImagen(imagen, file.getAbsolutePath(), 200, 200));
				comprobanteIconClient.setDescription(file.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
    	
    	textField.setBackground(Colores.TABBED_TEXT_COLOR);
    	
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

        
        crearBoton(seleccionar,"..\\img\\icono.png" , "Seleccionar", Colores.BUTTON_COLOR2, 105, 30, fontBotonChico);
        crearBoton(seleccionarComprobante,"..\\img\\icono.png" , "Seleccionar", Colores.BUTTON_COLOR2, 105, 30, fontBotonChico);
        
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
        ine.setFont(fontTitulo);
        ine.setForeground(Colores.TEXT_COLOR);
        ine.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelComponentes.add(ine);
        
        ineClient.setIcon(ineIconClient);
        ineClient.setAlignmentX(Component.CENTER_ALIGNMENT);
        ineClient.setPreferredSize(new Dimension(200,200));
        ineClient.setVisible(true);
        panelComponentes.add(ineClient);
        lblErrorFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelComponentes.add(lblErrorFoto);
        panelComponentes.add(seleccionar);
        
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,10)));
        
        JLabel comprobante = new JLabel("Comprobante Domicilio");
        comprobante.setOpaque(false);
        comprobante.setFont(fontTitulo);
        comprobante.setForeground(Colores.TEXT_COLOR);
        comprobante.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelComponentes.add(comprobante);
        
        comprobanteClient.setIcon(comprobanteIconClient);
        comprobanteClient.setAlignmentX(Component.CENTER_ALIGNMENT);
        comprobanteClient.setPreferredSize(new Dimension(200,200));
        comprobanteClient.setVisible(true);
        panelComponentes.add(comprobanteClient);
        lblErrorDocumento.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelComponentes.add(lblErrorDocumento);
        panelComponentes.add(seleccionarComprobante);
        
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,10)));
        
        JPanel panelIneWrapper = new JPanel();
        panelIneWrapper.setLayout(new BoxLayout(panelIneWrapper, BoxLayout.Y_AXIS));
        panelIneWrapper.setOpaque(false);
        panelIneWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelIneWrapper.setMaximumSize(new Dimension(400, 70));
        
        
     // Nombre
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

        // Apellido
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

        // Edad
        JPanel panelEdadWrapper = new JPanel();
        panelEdadWrapper.setLayout(new BoxLayout(panelEdadWrapper, BoxLayout.Y_AXIS));
        panelEdadWrapper.setOpaque(false);
        panelEdadWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelEdadWrapper.setMaximumSize(new Dimension(400, 70));

        edad.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelEdadWrapper.add(edad);
        panelEdadWrapper.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorEdad.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelEdadWrapper.add(lblErrorEdad);

        panelComponentes.add(panelEdadWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // Domicilio
        JPanel panelDomicilioWrapper = new JPanel();
        panelDomicilioWrapper.setLayout(new BoxLayout(panelDomicilioWrapper, BoxLayout.Y_AXIS));
        panelDomicilioWrapper.setOpaque(false);
        panelDomicilioWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDomicilioWrapper.setMaximumSize(new Dimension(400, 70));

        domicilio.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelDomicilioWrapper.add(domicilio);
        panelDomicilioWrapper.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorDomicilio.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelDomicilioWrapper.add(lblErrorDomicilio);

        panelComponentes.add(panelDomicilioWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // Número Celular
        JPanel panelNumeroCelularWrapper = new JPanel();
        panelNumeroCelularWrapper.setLayout(new BoxLayout(panelNumeroCelularWrapper, BoxLayout.Y_AXIS));
        panelNumeroCelularWrapper.setOpaque(false);
        panelNumeroCelularWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelNumeroCelularWrapper.setMaximumSize(new Dimension(400, 70));

        numeroCelular.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelNumeroCelularWrapper.add(numeroCelular);
        panelNumeroCelularWrapper.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorNumeroCelular.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelNumeroCelularWrapper.add(lblErrorNumeroCelular);

        panelComponentes.add(panelNumeroCelularWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // Correo
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

        // Empleo
        JPanel panelEmpleoWrapper = new JPanel();
        panelEmpleoWrapper.setLayout(new BoxLayout(panelEmpleoWrapper, BoxLayout.Y_AXIS));
        panelEmpleoWrapper.setOpaque(false);
        panelEmpleoWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelEmpleoWrapper.setMaximumSize(new Dimension(400, 70));

        empleo.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelEmpleoWrapper.add(empleo);
        panelEmpleoWrapper.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorEmpleo.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelEmpleoWrapper.add(lblErrorEmpleo);

        panelComponentes.add(panelEmpleoWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));
     // Teléfono Empleo
        JPanel panelTelefonoEmpleoWrapper = new JPanel();
        panelTelefonoEmpleoWrapper.setLayout(new BoxLayout(panelTelefonoEmpleoWrapper, BoxLayout.Y_AXIS));
        panelTelefonoEmpleoWrapper.setOpaque(false);
        panelTelefonoEmpleoWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTelefonoEmpleoWrapper.setMaximumSize(new Dimension(400, 70));

        telefonoEmpleo.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelTelefonoEmpleoWrapper.add(telefonoEmpleo);
        panelTelefonoEmpleoWrapper.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorTelefonoEmpleo.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelTelefonoEmpleoWrapper.add(lblErrorTelefonoEmpleo);

        panelComponentes.add(panelTelefonoEmpleoWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // Domicilio Empleo
        JPanel panelDomicilioEmpleoWrapper = new JPanel();
        panelDomicilioEmpleoWrapper.setLayout(new BoxLayout(panelDomicilioEmpleoWrapper, BoxLayout.Y_AXIS));
        panelDomicilioEmpleoWrapper.setOpaque(false);
        panelDomicilioEmpleoWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDomicilioEmpleoWrapper.setMaximumSize(new Dimension(400, 70));

        domicilioEmpleo.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelDomicilioEmpleoWrapper.add(domicilioEmpleo);
        panelDomicilioEmpleoWrapper.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorDomicilioEmpleo.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelDomicilioEmpleoWrapper.add(lblErrorDomicilioEmpleo);

        panelComponentes.add(panelDomicilioEmpleoWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // Ingreso Mensual
        JPanel panelIngresoMensualWrapper = new JPanel();
        panelIngresoMensualWrapper.setLayout(new BoxLayout(panelIngresoMensualWrapper, BoxLayout.Y_AXIS));
        panelIngresoMensualWrapper.setOpaque(false);
        panelIngresoMensualWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelIngresoMensualWrapper.setMaximumSize(new Dimension(400, 70));

        ingresoMensual.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelIngresoMensualWrapper.add(ingresoMensual);
        panelIngresoMensualWrapper.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorIngresoMensual.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelIngresoMensualWrapper.add(lblErrorIngresoMensual);

        panelComponentes.add(panelIngresoMensualWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // Banco
        JPanel panelBancoWrapper = new JPanel();
        panelBancoWrapper.setLayout(new BoxLayout(panelBancoWrapper, BoxLayout.Y_AXIS));
        panelBancoWrapper.setOpaque(false);
        panelBancoWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBancoWrapper.setMaximumSize(new Dimension(400, 70));

        banco.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelBancoWrapper.add(banco);
        panelBancoWrapper.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorBanco.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelBancoWrapper.add(lblErrorBanco);

        panelComponentes.add(panelBancoWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // Cuenta Bancaria
        JPanel panelCuentaBancariaWrapper = new JPanel();
        panelCuentaBancariaWrapper.setLayout(new BoxLayout(panelCuentaBancariaWrapper, BoxLayout.Y_AXIS));
        panelCuentaBancariaWrapper.setOpaque(false);
        panelCuentaBancariaWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCuentaBancariaWrapper.setMaximumSize(new Dimension(400, 70));

        cuentaBancaria.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelCuentaBancariaWrapper.add(cuentaBancaria);
        panelCuentaBancariaWrapper.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorCuentaBancaria.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelCuentaBancariaWrapper.add(lblErrorCuentaBancaria);

        panelComponentes.add(panelCuentaBancariaWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        // CURP
        JPanel panelCurpWrapper = new JPanel();
        panelCurpWrapper.setLayout(new BoxLayout(panelCurpWrapper, BoxLayout.Y_AXIS));
        panelCurpWrapper.setOpaque(false);
        panelCurpWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCurpWrapper.setMaximumSize(new Dimension(400, 70));

        curp.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelCurpWrapper.add(curp);
        panelCurpWrapper.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorCurp.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelCurpWrapper.add(lblErrorCurp);

        panelComponentes.add(panelCurpWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        nombres.setBackground(Colores.BG_TEXT_COLOR);
        apellidos.setBackground(Colores.BG_TEXT_COLOR);
        edad.setBackground(Colores.BG_TEXT_COLOR);
        domicilio.setBackground(Colores.BG_TEXT_COLOR);
        numeroCelular.setBackground(Colores.BG_TEXT_COLOR);
        correo.setBackground(Colores.BG_TEXT_COLOR);
        empleo.setBackground(Colores.BG_TEXT_COLOR);
        domicilioEmpleo.setBackground(Colores.BG_TEXT_COLOR);
        telefonoEmpleo.setBackground(Colores.BG_TEXT_COLOR);
        ingresoMensual.setBackground(Colores.BG_TEXT_COLOR);
        banco.setBackground(Colores.BG_TEXT_COLOR);
        cuentaBancaria.setBackground(Colores.BG_TEXT_COLOR);
        curp.setBackground(Colores.BG_TEXT_COLOR);

        
        
        
        
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
	public JLabel getINEUsuario() {
		return ineClient;
	}
	public void setINEUsuario(JLabel iconoUsuario) {
		this.ineClient = iconoUsuario;
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
	public String getIconDescription() {
		return ineIconClient.getDescription();
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
	
	public ImageIcon getIconoUsuarioFinal() {
		return ineIconClient;
	}
	public void setIconoUsuarioFinal(ImageIcon iconoUsuarioFinal) {
		this.ineIconClient = iconoUsuarioFinal;
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
	public Font getFontBotonChico() {
		return fontBotonChico;
	}
	public void setFontBotonChico(Font fontBotonChico) {
		this.fontBotonChico = fontBotonChico;
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
	public ImageIcon getIneIconClient() {
		return ineIconClient;
	}
	public void setIneIconClient(ImageIcon ineIconClient) {
		this.ineIconClient = ineIconClient;
	}
	public JLabel getIneClient() {
		return ineClient;
	}
	public void setIneClient(JLabel ineClient) {
		this.ineClient = ineClient;
	}
	public ImageIcon getComprobanteIconClient() {
		return comprobanteIconClient;
	}
	public String getComprobanteIconClientDirection() {
		return comprobanteIconClient.getDescription();
	}
	public void setComprobanteIconClient(ImageIcon comprobanteIconClient) {
		this.comprobanteIconClient = comprobanteIconClient;
	}
	public JLabel getComprobanteClient() {
		return comprobanteClient;
	}
	public void setComprobanteClient(JLabel comprobanteClient) {
		this.comprobanteClient = comprobanteClient;
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
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	
    
}