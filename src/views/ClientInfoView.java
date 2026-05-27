package views;

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

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import modelos.Client;

import utils.Colores;
import utils.GradientBackground;
import utils.PanelPersonalizable;

public class ClientInfoView extends JFrame {
	private Font fontTexto = new Font("Times New Roman", Font.BOLD, 25);
	private Client client;
	private JLabel txtIdCliente;
	private JLabel txtIdUser;
	private JLabel txtNombre;
	private JLabel txtApellido;
	private JLabel txtEdad;

	private JLabel txtDomicilio;
	private JLabel txtCelular;
	private JLabel txtCorreo;
	private JLabel txtEmpleo;
	private JLabel txtDomicilioEmpleo;
	private JLabel txtTelfEmpleo;
	private JLabel txtIngresos;
	private JLabel txtBanco;
	private JLabel txtCuentaBancaria;
	private JLabel txtCurp;
	private JLabel txtReputacion;
	private JPanel clientFullInfoPanel;
	
	
	public ClientInfoView(Client client)
	{
		this.client = client;
		PDDocument document;
		JLabel ineFoto;
		JLabel comprobanteClient=new JLabel();

		int sizeXComprobante = 280;
		int sizeYComprobante = 330;
		
		int sizeXIne = 360;
		int sizeYIne = 230;
		
		try {
			
			document = Loader.loadPDF(new File(client.getComprobanteDomicilio()));
			PDFRenderer render = new PDFRenderer(document);
			BufferedImage imagen = render.renderImageWithDPI(0, 150);
			
			
			comprobanteClient.setIcon(escalarBufferedImagen(imagen, client.getComprobanteDomicilio(), sizeXComprobante, sizeYComprobante));		
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			ineFoto=new JLabel(escalarImagen(this.client.getIneDireccion(), 360, 230));
		} catch (Exception e) {
			// TODO: handle exception
			ineFoto=new JLabel(escalarImagenLocal("..\\img\\LicenseDefault.png", 360, 230));
		}
		
		
		ineFoto.setBounds(60, 60, sizeXIne, sizeYIne);
		comprobanteClient.setBounds(100, 310, sizeXComprobante, sizeYComprobante);
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
				// TODO Auto-generated method stub
				getContentPane().setBackground(Colores.BACKGROUND);
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				getContentPane().setBackground(Color.GRAY);
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
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
			
		});
		
		
		ImageIcon cursorImage = new ImageIcon("src\\img\\pointer_b.png");
		Cursor myCursor = tk.createCustomCursor(cursorImage.getImage(), new Point(0,0), "Cursor");
		this.setCursor(myCursor);
		PanelPersonalizable bg1 = new PanelPersonalizable();
		bg1.setBounds(50, 50, 830, 600);
		bg1.setBackground(Colores.LOGIN_PANEL);
		
		GradientBackground gradientPanel = new GradientBackground();
		gradientPanel.setLayout(null);  
		setContentPane(gradientPanel);
		
		
		setValues(client);
		add(clientFullInfoPanel);
		add(ineFoto);
		add(comprobanteClient);
		add(bg1);
		
		
		add(txtIdCliente);
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
	
	private void setValues(Client client) {
		// INICIALIZACIÓN Y ASIGNACIÓN DE VALORES
		clientFullInfoPanel=new JPanel();
		clientFullInfoPanel.setLayout(new BoxLayout(clientFullInfoPanel, BoxLayout.Y_AXIS));
		clientFullInfoPanel.setBounds(500, 100, 350, 600);
		clientFullInfoPanel.setOpaque(false);
		clientFullInfoPanel.setForeground(Color.BLACK);
		
		
		
		txtIdCliente = new JLabel("Id cliente: " + String.valueOf(client.getIdCliente()));
		txtIdUser = new JLabel("Id usuario: " + String.valueOf(client.getIdUser()));
		txtNombre = new JLabel("Nombre: " + client.getNombre());
		txtApellido = new JLabel("Apellido: " + client.getApellido());
		txtEdad = new JLabel("Edad: " + String.valueOf(client.getEdad()));
		txtDomicilio = new JLabel("Domicilio: " + client.getDomicilio());
		txtCelular = new JLabel("Celular: " + client.getCelular());
		txtCorreo = new JLabel("Correo electrónico: " + client.getCorreoElectronico());
		txtEmpleo = new JLabel("Empleo: " + client.getEmpleo());
		txtDomicilioEmpleo = new JLabel("Domicilio empleo: " + client.getDomicilioEmpleo());
		txtTelfEmpleo = new JLabel("Teléfono empleo: " + client.getTelfEmpleo());
		txtIngresos = new JLabel("Ingresos mensuales: " + String.valueOf(client.getIngresosMensuales()));
		txtBanco = new JLabel("Banco: " + client.getBanco());
		txtCuentaBancaria = new JLabel("Cuenta bancaria: " + client.getCuentaBancaria());
		txtCurp = new JLabel("CURP: " + client.getCurp());
		txtReputacion = new JLabel("Reputación: " + client.getReputacion());
		
		
		Color txtColor = Colores.PRIMARY_HEADINGS;
		
		txtIdCliente.setForeground(txtColor);
		txtIdUser.setForeground(txtColor);
		txtNombre.setForeground(txtColor);
		txtApellido.setForeground(txtColor);
		txtEdad.setForeground(txtColor);
		txtDomicilio.setForeground(txtColor);
		txtCelular.setForeground(txtColor);
		txtCorreo.setForeground(txtColor);
		txtEmpleo.setForeground(txtColor);
		txtDomicilioEmpleo.setForeground(txtColor);
		txtTelfEmpleo.setForeground(txtColor);
		txtIngresos.setForeground(txtColor);
		txtBanco.setForeground(txtColor);
		txtCuentaBancaria.setForeground(txtColor);
		txtCurp.setForeground(txtColor);
		txtReputacion.setForeground(txtColor);
		
		txtIdCliente.setFont(fontTexto);
		txtIdUser.setFont(fontTexto);
		txtNombre.setFont(fontTexto);
		txtApellido.setFont(fontTexto);
		txtEdad.setFont(fontTexto);
		txtDomicilio.setFont(fontTexto);
		txtCelular.setFont(fontTexto);
		txtCorreo.setFont(fontTexto);
		txtEmpleo.setFont(fontTexto);
		txtDomicilioEmpleo.setFont(fontTexto);
		txtTelfEmpleo.setFont(fontTexto);
		txtIngresos.setFont(fontTexto);
		txtBanco.setFont(fontTexto);
		txtCuentaBancaria.setFont(fontTexto);
		txtCurp.setFont(fontTexto);
		txtReputacion.setFont(fontTexto);
		
		clientFullInfoPanel.add(txtIdCliente);
		clientFullInfoPanel.add(txtIdUser);
		clientFullInfoPanel.add(txtNombre);
		clientFullInfoPanel.add(txtApellido);
		clientFullInfoPanel.add(txtEdad);

		clientFullInfoPanel.add(txtDomicilio);

		clientFullInfoPanel.add(txtCelular);
		clientFullInfoPanel.add(txtCorreo);
		clientFullInfoPanel.add(txtEmpleo);
		clientFullInfoPanel.add(txtDomicilioEmpleo);
		clientFullInfoPanel.add(txtTelfEmpleo);
		clientFullInfoPanel.add(txtIngresos);
		clientFullInfoPanel.add(txtBanco);
		clientFullInfoPanel.add(txtCuentaBancaria);
		clientFullInfoPanel.add(txtCurp);
		clientFullInfoPanel.add(txtReputacion);
	}
	
}
