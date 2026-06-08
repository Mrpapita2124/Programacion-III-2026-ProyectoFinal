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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
import vistas.otros.VentanaPrincipal;

public class FormularioGeneralPrestamo extends JFrame{
    
	
	// Puro Fonts, ya depues hare otra clase de Fonts
    public Font fuente;
    
    
    private int numQuincenas;
    private double cantInteres;
    // Fields de Texto para Usuario
    private JTextField monto;
    private JTextField interesAtrasado;
    private ButtonGroup quincenas;
    // JBotones para Regsitro Controller
    private JButton seleccionarComprobante = new JButton(" Seleccionar");
    private JButton registrar = new JButton(" Registrar");
   
    // Labels de Error
    private JLabel lblErrorMonto;
    private JLabel lblErrorInteresAtrasado;

    
    
    private JLabel mostrarInteres;
    private Cliente client;
    

    
    private boolean editado;
    //Chckboxes
    private JCheckBox guardar= new JCheckBox("Guardar como usuario rapido");
    public FormularioGeneralPrestamo(VentanaPrincipal ventana, Cliente client)
    {
    	editado=false;
    	numQuincenas=0;
    	cantInteres=0;
        this.client=client;

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

        JLabel saludo = new JLabel("Registrar prestamo"); 
        saludo.setOpaque(false);
        saludo.setFont(Fuentes.fuenteTitulo);
        saludo.setForeground(Colores.TEXTO_COLOR);
        saludo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        
        panelComponentes.add(saludo);
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,80)));


        lblErrorMonto = crearErrorLabel();
        lblErrorInteresAtrasado=crearErrorLabel();
        
        
        crearComponentesDeRegistro(panelComponentes);
        
        panelComponentes.add(Box.createRigidArea(new Dimension(0,25)));
        
        
       
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new BoxLayout(panelBoton, BoxLayout.Y_AXIS));
        panelBoton.setOpaque(false);
        panelBoton.setAlignmentX(Component.CENTER_ALIGNMENT);

        crearBoton(registrar, "..\\img\\icono.png","Registrarse", Colores.BOTON_COLOR1, 150, 35, Fuentes.fuenteBoton);
       
        
        
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

        setVisible(true);
    }
    public void actualizarQuincenaInteres(int quincenas, double interes) {
    	numQuincenas=quincenas;
    	cantInteres=interes;
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
    
    private JTextField crearTextField(String placeholder, String campoNombre) 
    {
        JTextField textField = new JTextField(placeholder);
        
        int fieldWidth = 400;
        int fieldHeight = 45;
        
        textField.setMaximumSize(new Dimension(fieldWidth, fieldHeight));
        textField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        textField.setMinimumSize(new Dimension(fieldWidth, fieldHeight));
        
        textField.setBackground(Colores.FONDO);
        textField.setForeground(Color.BLACK);
        textField.setFont(Fuentes.fuenteTextoCampo);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setName(campoNombre);
        
        return textField;
    }
    public void crearGrupoOpciones(JPanel panel) {
        // Crear los radio buttons
        JRadioButton opcion1 = new JRadioButton("6Q");
        JRadioButton opcion2 = new JRadioButton("8Q");
        JRadioButton opcion3 = new JRadioButton("10Q");
        JRadioButton opcion4 = new JRadioButton("12Q");
        JRadioButton opcion5 = new JRadioButton("14Q");
        JRadioButton opcion6 = new JRadioButton("16Q");
        JRadioButton opcion7 = new JRadioButton("18Q");
        JRadioButton opcion8 = new JRadioButton("20Q");

        // Crear el grupo exclusivo
        quincenas = new ButtonGroup();
        quincenas.add(opcion1);
        quincenas.add(opcion2);
        quincenas.add(opcion3);
        quincenas.add(opcion4);
        quincenas.add(opcion5);
        quincenas.add(opcion6);
        quincenas.add(opcion7);
        quincenas.add(opcion8);
        
        
        opcion1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(6, 38);
				mostrarInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        opcion2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(8, 45);
				mostrarInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        opcion3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(10, 50);
				mostrarInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        opcion4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(12, 57);
				mostrarInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        opcion5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(14, 65);
				mostrarInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        opcion6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(16, 77);
				mostrarInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        opcion7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(18, 87);
				mostrarInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        opcion8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(20, 88);
				mostrarInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        panel.add(opcion1);
        panel.add(opcion2);
        panel.add(opcion3);
        panel.add(opcion4);
        panel.add(opcion5);
        panel.add(opcion6);
        panel.add(opcion7);
        panel.add(opcion8);
        
    }
    
    
    private void asignarFocusListenerConPlaceholder(JTextField campoDeTexto, String placeholder) {
    	
    	campoDeTexto.setBackground(Colores.TEXTO_TABULADO_COLOR);
    	
        campoDeTexto.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campoDeTexto.getText().equals(placeholder)) {
                    campoDeTexto.setText("");
                }
                campoDeTexto.setForeground(Color.BLACK);
                campoDeTexto.setBackground(Color.WHITE);
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
    	

    	lblErrorMonto.setText("");
    	lblErrorInteresAtrasado.setText("");
    
    }
    
    public void crearComponentesDeRegistro(JPanel panelComponentes)
    {
    	
    	monto = crearTextField("Monto", "monto");
    	interesAtrasado=crearTextField("Interes atrasado", "interes-atrasado");
    	asignarKeyListenerParaNumero(interesAtrasado, 2);
    	asignarKeyListenerParaNumero(monto, 9);
        
        crearBoton(seleccionarComprobante,"..\\img\\icono.png" , "Seleccionar", Colores.BOTON_COLOR2, 105, 30, Fuentes.fuenteBotonChico);

        asignarFocusListenerConPlaceholder(monto, "Monto");
        asignarFocusListenerConPlaceholder(interesAtrasado, "Interes atrasado");
  
        // Monto
        JPanel panelMontoEnvoltorio = new JPanel();
        panelMontoEnvoltorio.setLayout(new BoxLayout(panelMontoEnvoltorio, BoxLayout.Y_AXIS));
        panelMontoEnvoltorio.setOpaque(false);
        panelMontoEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMontoEnvoltorio.setMaximumSize(new Dimension(400, 70));

        monto.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelMontoEnvoltorio.add(monto);
        panelMontoEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorMonto.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelMontoEnvoltorio.add(lblErrorMonto);

        panelComponentes.add(panelMontoEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

     // Interés Atrasado
        JPanel panelInteresAtrasadoEnvoltorio = new JPanel();
        panelInteresAtrasadoEnvoltorio.setLayout(new BoxLayout(panelInteresAtrasadoEnvoltorio, BoxLayout.Y_AXIS));
        panelInteresAtrasadoEnvoltorio.setOpaque(false);
        panelInteresAtrasadoEnvoltorio.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInteresAtrasadoEnvoltorio.setMaximumSize(new Dimension(400, 70));

        interesAtrasado.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelInteresAtrasadoEnvoltorio.add(interesAtrasado);
        panelInteresAtrasadoEnvoltorio.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorInteresAtrasado.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelInteresAtrasadoEnvoltorio.add(lblErrorInteresAtrasado);

        panelComponentes.add(panelInteresAtrasadoEnvoltorio);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        mostrarInteres= new JLabel("Cantidad de quincenas"); 
        mostrarInteres.setOpaque(false);
        mostrarInteres.setFont(Fuentes.fuenteTitulo);
        mostrarInteres.setForeground(Colores.TEXTO_COLOR);
        mostrarInteres.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelComponentes.add(mostrarInteres);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));
        crearGrupoOpciones(panelComponentes);

       


        monto.setBackground(Colores.FONDO_TEXTO_COLOR);
       

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
	
	
	
	public int confirmarCierre() 
	{
	    return JOptionPane.showConfirmDialog(
	        this,
	        "¿Seguro que deseas regresar? Se perderán todos los datos",
	        "¿Seguro?",
	        JOptionPane.YES_NO_OPTION
	    );
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
	
	public Font getFuente() {
		return fuente;
	}
	public void setFuente(Font fuente) {
		this.fuente = fuente;
	}

	
	public JButton getRegistrar() {
		return registrar;
	}
	public void setRegistrar(JButton registrar) {
		this.registrar = registrar;
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
	public JTextField getMonto() {
		return monto;
	}
	public void setMonto(JTextField edad) {
		this.monto = edad;
	}
	
	
	public JButton getSeleccionarComprobante() {
		return seleccionarComprobante;
	}
	public void setSeleccionarComprobante(JButton seleccionarComprobante) {
		this.seleccionarComprobante = seleccionarComprobante;
	}
	public JLabel getLblErrorEdad() {
		return lblErrorMonto;
	}
	public void setLblErrorEdad(JLabel lblErrorEdad) {
		this.lblErrorMonto = lblErrorEdad;
	}
	
	
	public int getNumQuincenas() {
		return numQuincenas;
	}
	public void setNumQuincenas(int numQuincenas) {
		this.numQuincenas = numQuincenas;
	}
	public double getCantInteres() {
		return cantInteres;
	}
	public void setCantInteres(double cantInteres) {
		this.cantInteres = cantInteres;
	}
	public JTextField getInteresAtrasado() {
		return interesAtrasado;
	}
	public void setInteresAtrasado(JTextField interesAtrasado) {
		this.interesAtrasado = interesAtrasado;
	}
	public ButtonGroup getQuincenas() {
		return quincenas;
	}
	public void setQuincenas(ButtonGroup quincenas) {
		this.quincenas = quincenas;
	}
	public JLabel getLblErrorMonto() {
		return lblErrorMonto;
	}
	public void setLblErrorMonto(JLabel lblErrorMonto) {
		this.lblErrorMonto = lblErrorMonto;
	}
	public JLabel getLblErrorInteresAtrasado() {
		return lblErrorInteresAtrasado;
	}
	public void setLblErrorInteresAtrasado(JLabel lblErrorInteresAtrasado) {
		this.lblErrorInteresAtrasado = lblErrorInteresAtrasado;
	}
	public JLabel getShowInteres() {
		return mostrarInteres;
	}
	public void setShowInteres(JLabel showInteres) {
		this.mostrarInteres = showInteres;
	}
	public boolean isEditado() {
		return editado;
	}
	public void setEditado(boolean edit) {
		this.editado = edit;
	}
	public Cliente getCliente() {
		return client;
	}
	public void setCliente(Cliente cliente) {
		this.client = cliente;
	}
	
	
	
    
}