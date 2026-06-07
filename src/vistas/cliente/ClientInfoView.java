package vistas.cliente;

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

import modelos.Cliente;
import utilidades.Colores;
import utilidades.Fuentes;
import utilidades.GradientBackground;
import utilidades.PanelPersonalizable;
import utilidades.RoundedBorder;

public class ClientInfoView extends JFrame {
	
	private Font fuenteTexto = Fuentes.setFontSegoe(2, 21);
	private Font fuenteTextoValores = Fuentes.setFontSegoe(1, 25);
	private Color fuenteTextoColor = Colores.ENCABEZADOS_SECUNDARIOS;
	private Color fuenteTextoVlrColor = Colores.ENCABEZADOS_PRIMARIOS;
	
	private Cliente cliente;
	private JPanel clienteInfoPanelCompleto;
	
	static int imgInfoX;
	static int imgInfoY;
	static int imgInfoWidth;
	static int imgInfoHeight;
	
	static int clienteInfoX;
	static int clienteInfoY;
	static int clienteInfoWidth;
	static int clienteInfoHeight;
	
	public ClientInfoView(Cliente cliente)
	{
	    this.cliente = cliente;
	    PDDocument documento;
	    JLabel ineFoto;
	    JLabel comprobanteClient = new JLabel();
	
	    int anchoIne = 650;
	    int altoIne = 550;

	    int anchoComprobante = 640;
	    int altoComprobante = 800;
	    
	    try {
	        documento = Loader.loadPDF(new File(cliente.getComprobanteDomicilio()));
	        PDFRenderer render = new PDFRenderer(documento);
	        BufferedImage imagen = render.renderImageWithDPI(0, 150);
	        comprobanteClient.setIcon(escalarBufferedImagen(imagen, cliente.getComprobanteDomicilio(), anchoComprobante, altoComprobante));        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    try {
	        ineFoto = new JLabel(escalarImagen(this.cliente.getIneDireccion(), anchoIne, altoIne));
	    } catch (Exception e) {
	        ineFoto = new JLabel(escalarImagenLocal("..\\img\\LicenseDefault.png", anchoIne, altoIne));
	    }
	 
	    Toolkit tk = Toolkit.getDefaultToolkit();
	    
	    setLayout(null);
	    setSize(1250, 750);
	    setResizable(false);
	    setTitle("Información de "+ this.cliente.getNombre() + " " + this.cliente.getApellido()); 
	    setLocationRelativeTo(null);
	    getContentPane().setBackground(Colores.FONDO);
	    Image icono = tk.getImage("src\\img\\icono.png");
	    setIconImage(icono);	
	    setVisible(true);
	    
	    addWindowListener(new WindowListener() {
	        @Override
	        public void windowActivated(WindowEvent e) {
	            getContentPane().setBackground(Colores.FONDO);
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
	    
	    ImageIcon imagenCursor = new ImageIcon("src\\img\\pointer_b.png");
	    Cursor cursor = tk.createCustomCursor(imagenCursor.getImage(), new Point(0,0), "Cursor");
	    this.setCursor(cursor);
	    
	    imgInfoX = 35;
	    imgInfoY = 50;
	    imgInfoWidth = 685;
	    imgInfoHeight = 600;
	    
	    PanelPersonalizable fondo1 = new PanelPersonalizable();
	    fondo1.setBounds(imgInfoX, imgInfoY, imgInfoWidth, imgInfoHeight);
	    fondo1.setBackground(Colores.CARTA_FONDO);
	    fondo1.setBorder(new RoundedBorder(Colores.BORDE_POR_DEFECTO, 20, 2));
	    
	    
	    clienteInfoX = 750;
	    clienteInfoY = 50;
	    clienteInfoWidth = 450;
	    clienteInfoHeight = 600;
	    
	    PanelPersonalizable fondo2 = new PanelPersonalizable();
	    fondo2.setBounds(clienteInfoX, clienteInfoY, clienteInfoWidth, clienteInfoHeight);
	    fondo2.setBackground(Colores.CARTA_FONDO);
	    fondo2.setBorder(new RoundedBorder(Colores.BORDE_POR_DEFECTO, 20, 2));
	    
	    GradientBackground gradientePanel = new GradientBackground();
	    gradientePanel.setLayout(null);  
	    setContentPane(gradientePanel);
	    
	    
	    JPanel imagenesPanel = crearImagenesJPanel(ineFoto, comprobanteClient);
	    JScrollPane imagenesScrollPane = crearScrollPanelImagenes(imagenesPanel);
	    
	    
	    asignarValores(cliente);
	    add(clienteInfoPanelCompleto);
	    add(imagenesScrollPane);  
	    add(fondo1);
	    add(crearScrollPanelInfo(clienteInfoPanelCompleto));
	    add(fondo2);
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
	
	private void asignarValores(Cliente client) 
	{
	    clienteInfoPanelCompleto = new JPanel();
	    clienteInfoPanelCompleto.setLayout(new BoxLayout(clienteInfoPanelCompleto, BoxLayout.Y_AXIS));
	    clienteInfoPanelCompleto.setBounds((clienteInfoX+20), (clienteInfoY+10), clienteInfoWidth, clienteInfoHeight);
	    clienteInfoPanelCompleto.setOpaque(false);
	    clienteInfoPanelCompleto.setForeground(Color.BLACK);
	    
	    String txtIdClienteValue = String.valueOf(client.getIdCliente());
	    String txtIdUserValue = String.valueOf(client.getIdUsuario());
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
	    clienteInfoPanelCompleto.add(idClientePanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // ID USUARIO
	    JPanel idUserPanel = crearFieldLabelVertical("Id usuario:", txtIdUserValue);
	    clienteInfoPanelCompleto.add(idUserPanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // NOMBRE
	    JPanel nombrePanel = crearFieldLabelVertical("Nombre:", txtNombreValue);
	    clienteInfoPanelCompleto.add(nombrePanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // APELLIDO
	    JPanel apellidoPanel = crearFieldLabelVertical("Apellido:", txtApellidoValue);
	    clienteInfoPanelCompleto.add(apellidoPanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // EDAD
	    JPanel edadPanel = crearFieldLabelVertical("Edad:", txtEdadValue);
	    clienteInfoPanelCompleto.add(edadPanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // DOMICILIO
	    JPanel domicilioPanel = crearFieldLabelVertical("Domicilio:", txtDomicilioValue);
	    clienteInfoPanelCompleto.add(domicilioPanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // CELULAR
	    JPanel celularPanel = crearFieldLabelVertical("Celular:", txtCelularValue);
	    clienteInfoPanelCompleto.add(celularPanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // CORREO ELECTRÓNICO
	    JPanel correoPanel = crearFieldLabelVertical("Correo electrónico:", txtCorreoValue);
	    clienteInfoPanelCompleto.add(correoPanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // EMPLEO
	    JPanel empleoPanel = crearFieldLabelVertical("Empleo:", txtEmpleoValue);
	    clienteInfoPanelCompleto.add(empleoPanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // DOMICILIO EMPLEO
	    JPanel domicilioEmpleoPanel = crearFieldLabelVertical("Domicilio empleo:", txtDomicilioEmpleoValue);
	    clienteInfoPanelCompleto.add(domicilioEmpleoPanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // TELÉFONO EMPLEO
	    JPanel telfEmpleoPanel = crearFieldLabelVertical("Teléfono empleo:", txtTelfEmpleoValue);
	    clienteInfoPanelCompleto.add(telfEmpleoPanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // INGRESOS MENSUALES
	    JPanel ingresosPanel = crearFieldLabelVertical("Ingresos mensuales:", txtIngresosValue);
	    clienteInfoPanelCompleto.add(ingresosPanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // BANCO
	    JPanel bancoPanel = crearFieldLabelVertical("Banco:", txtBancoValue);
	    clienteInfoPanelCompleto.add(bancoPanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // CUENTA BANCARIA
	    JPanel cuentaBancariaPanel = crearFieldLabelVertical("Cuenta bancaria:", txtCuentaBancariaValue);
	    clienteInfoPanelCompleto.add(cuentaBancariaPanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // CURP
	    JPanel curpPanel = crearFieldLabelVertical("CURP:", txtCurpValue);
	    clienteInfoPanelCompleto.add(curpPanel);
	    clienteInfoPanelCompleto.add(Box.createVerticalStrut(spacingBetweenLabels));
	    
	    // REPUTACIÓN
	    JPanel reputacionPanel = crearFieldLabelVertical("Reputación:", txtReputacionValue);
	    clienteInfoPanelCompleto.add(reputacionPanel);
	}

	private JPanel crearFieldLabelVertical(String labelText, String valueText) 
	{
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    panel.setAlignmentX(LEFT_ALIGNMENT);
	    
	    // Label
	    JLabel label = new JLabel(labelText);
	    label.setFont(fuenteTexto);
	    label.setForeground(fuenteTextoColor);
	    
	    // Este wrapper es para que no sea tan larga la palabra y lo ponga abajo
	    String textoEnvuelto = "<html><body style='width: 325px; word-wrap: break-word;'>" + valueText + "</body></html>";
	    
	    // Valor
	    JLabel valor = new JLabel(textoEnvuelto);
	    valor.setFont(fuenteTextoValores);
	    valor.setForeground(fuenteTextoVlrColor);;
	    
	    int spacingBetweenFieldName = 1;
	    
	    panel.add(label);
	    panel.add(Box.createVerticalStrut(spacingBetweenFieldName)); 
	    panel.add(valor);
	    
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
	    ineLabel.setFont(Fuentes.setFontSegoe(1, 30));
	    ineLabel.setForeground(Colores.ENCABEZADOS_PRIMARIOS);
	    ineLabel.setAlignmentX(LEFT_ALIGNMENT);
	    imagenesPanel.add(Box.createVerticalStrut(15));
	    imagenesPanel.add(ineLabel);
	    imagenesPanel.add(Box.createVerticalStrut(10));
	    imagenesPanel.add(ineFoto);
	    imagenesPanel.add(Box.createVerticalStrut(20));
	    
	    JLabel comprobanteLabel = new JLabel("Comprobante de domicilio:");
	    comprobanteLabel.setFont(Fuentes.setFontSegoe(1, 30));
	    comprobanteLabel.setForeground(Colores.ENCABEZADOS_PRIMARIOS);
	    comprobanteLabel.setAlignmentX(LEFT_ALIGNMENT);
	    imagenesPanel.add(Box.createVerticalStrut(15));
	    imagenesPanel.add(comprobanteLabel);
	    imagenesPanel.add(Box.createVerticalStrut(10));
	    imagenesPanel.add(comprobanteClient);
	    
	    return imagenesPanel;
	}
	
	private JScrollPane crearScrollPanelImagenes(JPanel imagesPanel)
	{
		JScrollPane imagenesScrollPane = new JScrollPane(imagesPanel);
		
	    imagenesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    imagenesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    imagenesScrollPane.setBounds((imgInfoX+10), (imgInfoY+10), (imgInfoWidth-10), (imgInfoHeight-20));  
	    imagenesScrollPane.setOpaque(false);
	    imagenesScrollPane.getViewport().setOpaque(false);
	    imagenesScrollPane.getVerticalScrollBar().setUnitIncrement(30);
	    imagenesScrollPane.setBorder(null);
	    
	    return imagenesScrollPane;
	}
}
