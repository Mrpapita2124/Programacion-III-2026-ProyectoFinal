package views.client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import modelos.Client;

import utils.Colores;
import utils.Fonts;
import utils.GradientBackground;
import utils.PanelPersonalizable;
import utils.RoundedBorder;

public class ClientInfoView extends JFrame {
	
	private Font fontTexto = Fonts.setFontSegoe(2, 21);
	private Font fontTextoValores = Fonts.setFontSegoe(1, 25);
	private Color fontTextoColor = Colores.SECONDARY_HEADINGS;
	private Color fontTextoVlrColor = Colores.PRIMARY_HEADINGS;
	
	private Client client;
	private JPanel clientFullInfoPanel;
	
	static int imgInfoX;
	static int imgInfoY;
	static int imgInfoWidth;
	static int imgInfoHeight;
	
	static int clienteInfoX;
	static int clienteInfoY;
	static int clienteInfoWidth;
	static int clienteInfoHeight;
	
	public ClientInfoView(Client client)
	{
	    this.client = client;
	    PDDocument document;
	    JLabel ineFoto;
	    JLabel comprobanteClient = new JLabel();
	
	    int sizeXIne = 650;
	    int sizeYIne = 550;

	    int sizeXComprobante = 640;
	    int sizeYComprobante = 800;
	    
	    try {
	        document = Loader.loadPDF(new File(client.getComprobanteDomicilio()));
	        PDFRenderer render = new PDFRenderer(document);
	        BufferedImage imagen = render.renderImageWithDPI(0, 150);
	        comprobanteClient.setIcon(escalarBufferedImagen(imagen, client.getComprobanteDomicilio(), sizeXComprobante, sizeYComprobante));        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    try {
	        ineFoto = new JLabel(escalarImagen(this.client.getIneDireccion(), sizeXIne, sizeYIne));
	    } catch (Exception e) {
	        ineFoto = new JLabel(escalarImagenLocal("..\\img\\LicenseDefault.png", sizeXIne, sizeYIne));
	    }
	 
	    Toolkit tk = Toolkit.getDefaultToolkit();
	    
	    setLayout(null);
	    setSize(1250, 750);
	    setResizable(false);
	    setTitle("Información de "+ this.client.getNombre() + " " + this.client.getApellido()); 
	    setLocationRelativeTo(null);
	    getContentPane().setBackground(Colores.BACKGROUND);
	    Image icono = tk.getImage("src\\img\\icono.png");
	    setIconImage(icono);	
	    setVisible(true);
	    
	    addWindowListener(new WindowListener() {
	        @Override
	        public void windowActivated(WindowEvent e) {
	            getContentPane().setBackground(Colores.BACKGROUND);
	        }
	        
	        @Override
	        public void windowDeactivated(WindowEvent e) {
	            getContentPane().setBackground(Color.GRAY);
	        }
	
	        @Override
	        public void windowOpened(WindowEvent e) {}
	        @Override
	        public void windowClosing(WindowEvent e) {}
	        @Override
	        public void windowClosed(WindowEvent e) {}
	        @Override
	        public void windowIconified(WindowEvent e) {}
	        @Override
	        public void windowDeiconified(WindowEvent e) {}
	    });
	    
	    ImageIcon cursorImage = new ImageIcon("src\\img\\pointer_b.png");
	    Cursor myCursor = tk.createCustomCursor(cursorImage.getImage(), new Point(0,0), "Cursor");
	    this.setCursor(myCursor);
	    
	    imgInfoX = 35;
	    imgInfoY = 50;
	    imgInfoWidth = 685;
	    imgInfoHeight = 600;
	    
	    PanelPersonalizable bg1 = new PanelPersonalizable();
	    bg1.setBounds(imgInfoX, imgInfoY, imgInfoWidth, imgInfoHeight);
	    bg1.setBackground(Colores.CARD_BG);
	    bg1.setBorder(new RoundedBorder(Colores.DEFAULT_BORDER, 20, 2));
	    
	    
	    clienteInfoX = 750;
	    clienteInfoY = 50;
	    clienteInfoWidth = 450;
	    clienteInfoHeight = 600;
	    
	    PanelPersonalizable bg2 = new PanelPersonalizable();
	    bg2.setBounds(clienteInfoX, clienteInfoY, clienteInfoWidth, clienteInfoHeight);
	    bg2.setBackground(Colores.CARD_BG);
	    bg2.setBorder(new RoundedBorder(Colores.DEFAULT_BORDER, 20, 2));
	    
	    GradientBackground gradientPanel = new GradientBackground();
	    gradientPanel.setLayout(null);  
	    setContentPane(gradientPanel);
	    
	    
	    JPanel imagenesPanel = crearImagenesJPanel(ineFoto, comprobanteClient);
	    JScrollPane imagesScrollPane = crearScrollPanelImagenes(imagenesPanel);
	    
	    
	    asignarValores(client);
	    add(clientFullInfoPanel);
	    add(imagesScrollPane);  
	    add(bg1);
	    add(crearScrollPanelInfo(clientFullInfoPanel));
	    add(bg2);
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
	    ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(direccion));
	
	   
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
	
	private void asignarValores(Client client) 
	{
	    clientFullInfoPanel = new JPanel();
	    clientFullInfoPanel.setLayout(new BoxLayout(clientFullInfoPanel, BoxLayout.Y_AXIS));
	    clientFullInfoPanel.setBounds((clienteInfoX+20), (clienteInfoY+10), clienteInfoWidth, clienteInfoHeight);
	    clientFullInfoPanel.setOpaque(false);
	    clientFullInfoPanel.setForeground(Color.BLACK);
	    
	    String txtIdClienteValue = String.valueOf(client.getIdCliente());
	    String txtIdUserValue = String.valueOf(client.getIdUser());
	    String txtNombreValue = client.getNombre();
	    String txtApellidoValue = client.getApellido();
	    String txtEdadValue = String.valueOf(client.getEdad());
	    String txtDomicilioValue = client.getDomicilio();
	    String txtCelularValue = client.getCelular();
	    String txtCorreoValue = client.getCorreoElectronico();
	    String txtEmpleoValue = client.getEmpleo();
	    String txtDomicilioEmpleoValue = client.getDomicilioEmpleo();
	    String txtTelfEmpleoValue = client.getTelfEmpleo();
	    String txtIngresosValue = String.valueOf(client.getIngresosMensuales());
	    String txtBancoValue = client.getBanco();
	    String txtCuentaBancariaValue = client.getCuentaBancaria();
	    String txtCurpValue = client.getCurp();
	    String txtReputacionValue = client.getReputacion();
	    
	    int spacingBetweenLabels = 25;
	    
	    // ID CLIENTE
	    JPanel idClientePanel = crearFieldLabelVertical("Id cliente:", txtIdClienteValue);
	    clientFullInfoPanel.add(idClientePanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // ID USUARIO
	    JPanel idUserPanel = crearFieldLabelVertical("Id usuario:", txtIdUserValue);
	    clientFullInfoPanel.add(idUserPanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // NOMBRE
	    JPanel nombrePanel = crearFieldLabelVertical("Nombre:", txtNombreValue);
	    clientFullInfoPanel.add(nombrePanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // APELLIDO
	    JPanel apellidoPanel = crearFieldLabelVertical("Apellido:", txtApellidoValue);
	    clientFullInfoPanel.add(apellidoPanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // EDAD
	    JPanel edadPanel = crearFieldLabelVertical("Edad:", txtEdadValue);
	    clientFullInfoPanel.add(edadPanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // DOMICILIO
	    JPanel domicilioPanel = crearFieldLabelVertical("Domicilio:", txtDomicilioValue);
	    clientFullInfoPanel.add(domicilioPanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // CELULAR
	    JPanel celularPanel = crearFieldLabelVertical("Celular:", txtCelularValue);
	    clientFullInfoPanel.add(celularPanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // CORREO ELECTRÓNICO
	    JPanel correoPanel = crearFieldLabelVertical("Correo electrónico:", txtCorreoValue);
	    clientFullInfoPanel.add(correoPanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // EMPLEO
	    JPanel empleoPanel = crearFieldLabelVertical("Empleo:", txtEmpleoValue);
	    clientFullInfoPanel.add(empleoPanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // DOMICILIO EMPLEO
	    JPanel domicilioEmpleoPanel = crearFieldLabelVertical("Domicilio empleo:", txtDomicilioEmpleoValue);
	    clientFullInfoPanel.add(domicilioEmpleoPanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // TELÉFONO EMPLEO
	    JPanel telfEmpleoPanel = crearFieldLabelVertical("Teléfono empleo:", txtTelfEmpleoValue);
	    clientFullInfoPanel.add(telfEmpleoPanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // INGRESOS MENSUALES
	    JPanel ingresosPanel = crearFieldLabelVertical("Ingresos mensuales:", txtIngresosValue);
	    clientFullInfoPanel.add(ingresosPanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // BANCO
	    JPanel bancoPanel = crearFieldLabelVertical("Banco:", txtBancoValue);
	    clientFullInfoPanel.add(bancoPanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // CUENTA BANCARIA
	    JPanel cuentaBancariaPanel = crearFieldLabelVertical("Cuenta bancaria:", txtCuentaBancariaValue);
	    clientFullInfoPanel.add(cuentaBancariaPanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // CURP
	    JPanel curpPanel = crearFieldLabelVertical("CURP:", txtCurpValue);
	    clientFullInfoPanel.add(curpPanel);
	    clientFullInfoPanel.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // REPUTACIÓN
	    JPanel reputacionPanel = crearFieldLabelVertical("Reputación:", txtReputacionValue);
	    clientFullInfoPanel.add(reputacionPanel);
	}

	private JPanel crearFieldLabelVertical(String labelText, String valueText) 
	{
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    panel.setAlignmentX(LEFT_ALIGNMENT);
	    
	    // Label
	    JLabel label = new JLabel(labelText);
	    label.setFont(fontTexto);
	    label.setForeground(fontTextoColor);
	    
	    // Este wrapper es para que no sea tan larga la palabra y lo ponga abajo
	    String wrappedText = "<html><body style='width: 325px; word-wrap: break-word;'>" + valueText + "</body></html>";
	    
	    // Valor
	    JLabel value = new JLabel(wrappedText);
	    value.setFont(fontTextoValores);
	    value.setForeground(fontTextoVlrColor);;
	    
	    int spacingBetweenFieldName = 1;
	    
	    panel.add(label);
	    panel.add(Box.createVerticalStrut(spacingBetweenFieldName)); 
	    panel.add(value);
	    
	    return panel;
	}
	
	private JScrollPane crearScrollPanelInfo(JPanel clientInfo)
	{
		 // Scroll Pane
        JScrollPane scrollPanel= new JScrollPane(clientInfo);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setBounds((clienteInfoX+20), (clienteInfoY+10), (clienteInfoWidth-20), (clienteInfoHeight-30));

		
	    scrollPanel.setOpaque(false);
	    scrollPanel.getViewport().setOpaque(false);
	    
	    scrollPanel.getVerticalScrollBar().setOpaque(false);
	    scrollPanel.getVerticalScrollBar().setUnitIncrement(20);
	    scrollPanel.setBorder(null);
	    
		return scrollPanel;
	}
	
	private JPanel crearImagenesJPanel(JLabel ineFoto, JLabel comprobanteClient)
	{
		JPanel imagenesPanel = new JPanel();
	    imagenesPanel.setLayout(new BoxLayout(imagenesPanel, BoxLayout.Y_AXIS));
	    imagenesPanel.setOpaque(false);
		
	    JLabel ineLabel = new JLabel("INE / Identificación:");
	    ineLabel.setFont(Fonts.setFontSegoe(1, 30));
	    ineLabel.setForeground(Colores.PRIMARY_HEADINGS);
	    ineLabel.setAlignmentX(LEFT_ALIGNMENT);
	    imagenesPanel.add(Box.createVerticalStrut(15));
	    imagenesPanel.add(ineLabel);
	    imagenesPanel.add(Box.createVerticalStrut(10));
	    imagenesPanel.add(ineFoto);
	    imagenesPanel.add(Box.createVerticalStrut(20));
	    
	    JLabel comprobanteLabel = new JLabel("Comprobante de domicilio:");
	    comprobanteLabel.setFont(Fonts.setFontSegoe(1, 30));
	    comprobanteLabel.setForeground(Colores.PRIMARY_HEADINGS);
	    comprobanteLabel.setAlignmentX(LEFT_ALIGNMENT);
	    imagenesPanel.add(Box.createVerticalStrut(15));
	    imagenesPanel.add(comprobanteLabel);
	    imagenesPanel.add(Box.createVerticalStrut(10));
	    imagenesPanel.add(comprobanteClient);
	    
	    return imagenesPanel;
	}
	
	private JScrollPane crearScrollPanelImagenes(JPanel imagesPanel)
	{
		JScrollPane imagesScrollPane = new JScrollPane(imagesPanel);
		
	    imagesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    imagesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    imagesScrollPane.setBounds((imgInfoX+10), (imgInfoY+10), (imgInfoWidth-10), (imgInfoHeight-20));  
	    imagesScrollPane.setOpaque(false);
	    imagesScrollPane.getViewport().setOpaque(false);
	    imagesScrollPane.getVerticalScrollBar().setUnitIncrement(30);
	    imagesScrollPane.setBorder(null);
	    
	    return imagesScrollPane;
	}
}
