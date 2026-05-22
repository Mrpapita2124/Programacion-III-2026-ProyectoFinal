package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
	public ClientInfoView(Client client){
		this.client = client;
		PDDocument document;
		JLabel ineFoto;
		JLabel comprobanteClient=new JLabel();
		try {
			document = Loader.loadPDF(new File(client.getComprobanteDomicilio()));
			PDFRenderer render = new PDFRenderer(document);
			BufferedImage imagen = render.renderImageWithDPI(0, 150);
			comprobanteClient.setIcon(escalarBufferedImagen(imagen, client.getComprobanteDomicilio(), 230, 280));		
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ineFoto=new JLabel(escalarImagen(this.client.getIneDireccion(), 230, 280));
		} catch (Exception e) {
			// TODO: handle exception
			ineFoto=new JLabel(escalarImagenLocal("..\\img\\LicenseDefault.png", 280, 280));
		}
		ineFoto.setBounds(60, 60, 230, 280);
		comprobanteClient.setBounds(60, 360, 230, 280);
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		setLayout(null);
		setSize(750, 750);
		setResizable(false);
		setTitle("Información de "+ this.client.getNombre() + " " + this.client.getApellido());  // Nombre de la Ventana
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
		bg1.setBounds(50, 50, 625, 600);
		bg1.setBackground(Colores.LOGIN_PANEL);
		setValuess(client);
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
		System.out.println("nigga");
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
	private void setValuess(Client client) {
		// INICIALIZACIÓN Y ASIGNACIÓN DE VALORES
		clientFullInfoPanel=new JPanel();
		clientFullInfoPanel.setLayout(new BoxLayout(clientFullInfoPanel, BoxLayout.Y_AXIS));
		clientFullInfoPanel.setBounds(350, 50, 350, 600);
		clientFullInfoPanel.setOpaque(false);
		clientFullInfoPanel.setForeground(Color.BLACK);
		txtIdCliente = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>Id cliente: " + String.valueOf(client.getIdCliente()) + "</body></html>");

		txtIdUser = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>Id usuario: " + String.valueOf(client.getIdUser()) + "</body></html>");

		txtNombre = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>Nombre: " + client.getNombre() + "</body></html>");

		txtApellido = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>Apellido: " + client.getApellido() + "</body></html>");

		txtEdad = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>Edad: " + String.valueOf(client.getEdad()) + "</body></html>");

		txtDomicilio = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>Domicilio: " + client.getDomicilio() + "</body></html>");

		txtCelular = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>Celular: " + client.getCelular() + "</body></html>");

		txtCorreo = new JLabel("<html><body style='width: 200px; overflow-wrap: break-word;'>Correo electrónico: " + client.getCorreoElectronico() + "</body></html>");

		txtEmpleo = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>Empleo: " + client.getEmpleo() + "</body></html>");

		txtDomicilioEmpleo = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>Domicilio empleo: " + client.getDomicilioEmpleo() + "</body></html>");

		txtTelfEmpleo = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>Teléfono empleo: " + client.getTelfEmpleo() + "</body></html>");

		txtIngresos = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>Ingresos mensuales: " + String.valueOf(client.getIngresosMensuales()) + "</body></html>");

		txtBanco = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>Banco: " + client.getBanco() + "</body></html>");

		txtCuentaBancaria = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>Cuenta bancaria: " + client.getCuentaBancaria() + "</body></html>");

		txtCurp = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>CURP: " + client.getCurp() + "</body></html>");

		txtReputacion = new JLabel("<html><body style='width: 300px; overflow-wrap: break-word;'>Reputación: " + client.getReputacion() + "</body></html>");
		txtIdCliente.setForeground(Color.BLACK);
		txtIdUser.setForeground(Color.BLACK);
		txtNombre.setForeground(Color.BLACK);
		txtApellido.setForeground(Color.BLACK);
		txtEdad.setForeground(Color.BLACK);
	
		txtDomicilio.setForeground(Color.BLACK);
	
		txtCelular.setForeground(Color.BLACK);
		txtCorreo.setForeground(Color.BLACK);
		txtEmpleo.setForeground(Color.BLACK);
		txtDomicilioEmpleo.setForeground(Color.BLACK);
		txtTelfEmpleo.setForeground(Color.BLACK);
		txtIngresos.setForeground(Color.BLACK);
		txtBanco.setForeground(Color.BLACK);
		txtCuentaBancaria.setForeground(Color.BLACK);
		txtCurp.setForeground(Color.BLACK);
		txtReputacion.setForeground(Color.BLACK);
		
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
