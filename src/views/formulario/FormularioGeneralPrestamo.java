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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

import modelos.Client;
import modelos.User;
import repository.UserRepository;
import utils.Colores;
import utils.PanelPersonalizable;
import views.VentanaPrincipal;

public class FormularioGeneralPrestamo extends JFrame{
    
	
	// Puro Fonts, ya depues hare otra clase de Fonts
    public Font fuente;
    private Font fontError = new Font("Times New Roman", Font.ITALIC, 12);
    private Font fontTextoCampo = new Font("Times New Roman", Font.ITALIC, 15);
    private Font fontBoton = new Font("Times New Roman", Font.BOLD, 20);
    private Font fontTitulo = new Font("Times New Roman", Font.BOLD, 35);
    private Font fontBotonChico = new Font("Times New Roman", Font.BOLD, 14);
    
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

    
    
    private JLabel showInteres;
    private Client client;
    

    
    private boolean edit;
    //Chckboxes
    private JCheckBox guardar= new JCheckBox("Guardar como usuario rapido");
    public FormularioGeneralPrestamo(VentanaPrincipal ventana, Client client)
    {
    	edit=false;
    	numQuincenas=0;
    	cantInteres=0;
        this.client=client;

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

        JLabel saludo = new JLabel("Registrar prestamo"); 
        saludo.setOpaque(false);
        saludo.setFont(fontTitulo);
        saludo.setForeground(Colores.TEXT_COLOR);
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

        crearBoton(registrar, "..\\img\\icono.png","Registrarse", Colores.BUTTON_COLOR1, 150, 35, fontBoton);
       
        
        
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

        setVisible(true);
    }
    public void actualizarQuincenaInteres(int quincenas, double interes) {
    	numQuincenas=quincenas;
    	cantInteres=interes;
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
				showInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        opcion2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(8, 45);
				showInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        opcion3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(10, 50);
				showInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        opcion4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(12, 57);
				showInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        opcion5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(14, 65);
				showInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        opcion6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(16, 77);
				showInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        opcion7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(18, 87);
				showInteres.setText("El interes serà de: "+ cantInteres);
			}
        });
        opcion8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actualizarQuincenaInteres(20, 88);
				showInteres.setText("El interes serà de: "+ cantInteres);
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
    	

    	lblErrorMonto.setText("");
    	lblErrorInteresAtrasado.setText("");
    
    }
    
    public void crearComponentesDeRegistro(JPanel panelComponentes)
    {
    	
    	monto = crearTextField("Monto", "monto");
    	interesAtrasado=crearTextField("Interes atrasado", "interes-atrasado");

        
        crearBoton(seleccionarComprobante,"..\\img\\icono.png" , "Seleccionar", Colores.BUTTON_COLOR2, 105, 30, fontBotonChico);

        asignarFocusListenerConPlaceholder(monto, "Monto");
        asignarFocusListenerConPlaceholder(interesAtrasado, "Interes atrasado");
  
        // Monto
        JPanel panelMontoWrapper = new JPanel();
        panelMontoWrapper.setLayout(new BoxLayout(panelMontoWrapper, BoxLayout.Y_AXIS));
        panelMontoWrapper.setOpaque(false);
        panelMontoWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMontoWrapper.setMaximumSize(new Dimension(400, 70));

        monto.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelMontoWrapper.add(monto);
        panelMontoWrapper.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorMonto.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelMontoWrapper.add(lblErrorMonto);

        panelComponentes.add(panelMontoWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

     // Interés Atrasado
        JPanel panelInteresAtrasadoWrapper = new JPanel();
        panelInteresAtrasadoWrapper.setLayout(new BoxLayout(panelInteresAtrasadoWrapper, BoxLayout.Y_AXIS));
        panelInteresAtrasadoWrapper.setOpaque(false);
        panelInteresAtrasadoWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInteresAtrasadoWrapper.setMaximumSize(new Dimension(400, 70));

        interesAtrasado.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelInteresAtrasadoWrapper.add(interesAtrasado);
        panelInteresAtrasadoWrapper.add(Box.createRigidArea(new Dimension(0, 5)));

        lblErrorInteresAtrasado.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panelInteresAtrasadoWrapper.add(lblErrorInteresAtrasado);

        panelComponentes.add(panelInteresAtrasadoWrapper);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));

        showInteres= new JLabel("Cantidad de quincenas"); 
        showInteres.setOpaque(false);
        showInteres.setFont(fontTitulo);
        showInteres.setForeground(Colores.TEXT_COLOR);
        showInteres.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelComponentes.add(showInteres);
        panelComponentes.add(Box.createRigidArea(new Dimension(0, 15)));
        crearGrupoOpciones(panelComponentes);

       


        monto.setBackground(Colores.BG_TEXT_COLOR);
       

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
	
	
	
	public int confirmExit() 
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
	
	public Font getFontBotonChico() {
		return fontBotonChico;
	}
	public void setFontBotonChico(Font fontBotonChico) {
		this.fontBotonChico = fontBotonChico;
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
		return showInteres;
	}
	public void setShowInteres(JLabel showInteres) {
		this.showInteres = showInteres;
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