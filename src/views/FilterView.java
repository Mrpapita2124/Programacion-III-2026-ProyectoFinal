package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import utils.Colores;
import utils.Fonts;
import utils.GradientBackground;

public class FilterView extends GradientBackground {
	
	private JCheckBox exceelente= new JCheckBox(" excelente");
	private JCheckBox buena= new JCheckBox(" buena");
	private JCheckBox regular= new JCheckBox(" regular");
	private JCheckBox mala= new JCheckBox(" mala");
	private JCheckBox noMedida= new JCheckBox(" no medida");
	
	private ButtonGroup opcionTipoPrestamos;
	private ButtonGroup opcionEstadoPrestamos;
	
	private JTextField minimo;
	private JTextField maximo;
	
	private JTextField edadMin;
	private JTextField edadMax;
	private JTextField ingresosMinimos;
	private JTextField ingresosMaximos;
	
	private JButton buscar = new JButton("Buscar");
	private JButton cancelar = new JButton("Cancelar");
	private String tipo=" ";
	private String estado=" ";
	private VentanaPrincipal ventana;
	
	
	private Font textoFont = Fonts.setFontSegoe(1, 35);
	private Color borderLineaColor = Colores.PRIMARY_HEADINGS;
	private int borderSize = 1;
	
	public FilterView(VentanaPrincipal ventana) 
	{
		this.ventana=ventana;
		
		setOpaque(true);
		setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		
		try 
		{
			crearBoton(buscar, "Buscar", Colores.BUTTON_COLOR1, 150, 50, Fonts.fontBoton, "src\\img\\buscar_Filter.png");
			crearBoton(cancelar, "Cancelar", Colores.BUTTON_COLOR1, 150, 50, Fonts.fontBoton, "src\\img\\cancelar_Filter.png");
		} 
		catch (Exception e) 
		{
			System.out.println("Iconos en Filter View no se pusieron!");
		}
		
		opcionTipoPrestamos = new ButtonGroup();
		opcionEstadoPrestamos= new ButtonGroup();
		minimo=crearTextField("MIN", "minimo");
		maximo=crearTextField("MAX", "maximo");
		edadMin=crearTextField("E.MIN", "e.min");
		edadMax=crearTextField("E.MAX", "e.max");
		ingresosMinimos=crearTextField("I.MIN", "i.min");
		ingresosMaximos=crearTextField("I.MAX", "i.max");
		asignarKeyListener(maximo,10);
		asignarKeyListener(minimo,10);
		asignarKeyListener(ingresosMinimos,10);
		asignarKeyListener(ingresosMaximos,10);
		asignarKeyListener(edadMin, 3);
		asignarKeyListener(edadMax, 3);
		asignarFocusListenerConPlaceholder(maximo, "MAX");
		asignarFocusListenerConPlaceholder(minimo, "MIN");
		asignarFocusListenerConPlaceholder(edadMax, "E.MAX");
		asignarFocusListenerConPlaceholder(edadMin, "E.MIN");
		asignarFocusListenerConPlaceholder(ingresosMaximos, "I.MAX");
		asignarFocusListenerConPlaceholder(ingresosMinimos, "I.MIN");
		setLayout(new GridLayout(4, 2));
		
		JPanel reputacion = new JPanel();
		reputacion.setOpaque(false);
		reputacion.setLayout(new BoxLayout(reputacion, BoxLayout.Y_AXIS));
		reputacion.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(borderLineaColor, borderSize),
			"Reputación",
			TitledBorder.DEFAULT_JUSTIFICATION,
			TitledBorder.DEFAULT_POSITION,
			textoFont,
			borderLineaColor
		));
		reputacion.add(Box.createVerticalStrut(15));
		reputacion.add(exceelente);
		reputacion.add(Box.createVerticalStrut(2));
		reputacion.add(buena);
		reputacion.add(Box.createVerticalStrut(2));
		reputacion.add(regular);
		reputacion.add(Box.createVerticalStrut(2));
		reputacion.add(mala);
		reputacion.add(Box.createVerticalStrut(2));
		reputacion.add(noMedida);
		add(reputacion);
		
		JPanel tipoPrestamos = new JPanel();
		tipoPrestamos.setOpaque(false);
		tipoPrestamos.setLayout(new BoxLayout(tipoPrestamos, BoxLayout.Y_AXIS));
		tipoPrestamos.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(borderLineaColor, borderSize),
			"Tipo de Préstamo",
			TitledBorder.DEFAULT_JUSTIFICATION,
			TitledBorder.DEFAULT_POSITION,
			textoFont,
			borderLineaColor
		));
		tipoPrestamos.add(Box.createVerticalStrut(20));
		crearGrupoOpcionesTipoPrestamos(tipoPrestamos);
		add(tipoPrestamos);
		
		JPanel estadoPrestamos= new JPanel();
		estadoPrestamos.setOpaque(false);
		estadoPrestamos.setLayout(new BoxLayout(estadoPrestamos, BoxLayout.Y_AXIS));
		estadoPrestamos.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(borderLineaColor, borderSize),
			"Estado del Préstamo",
			TitledBorder.DEFAULT_JUSTIFICATION,
			TitledBorder.DEFAULT_POSITION,
			textoFont,
			borderLineaColor
		));
		estadoPrestamos.add(Box.createVerticalStrut(20));
		crearGrupoOpcionesEstadoPrestamos(estadoPrestamos);
		add(estadoPrestamos);
		
		JPanel rangoPrestamo= new JPanel();
		rangoPrestamo.setOpaque(false);
		rangoPrestamo.setLayout(new BoxLayout(rangoPrestamo, BoxLayout.Y_AXIS));
		rangoPrestamo.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(borderLineaColor, borderSize),
			"Monto del Préstamo",
			TitledBorder.DEFAULT_JUSTIFICATION,
			TitledBorder.DEFAULT_POSITION,
			textoFont,
			borderLineaColor
		));
		JPanel rangos= new JPanel();
		rangos.setOpaque(false);
		rangos.setLayout(new BoxLayout(rangos, BoxLayout.X_AXIS));
		rangos.add(minimo);
		rangos.add(Box.createHorizontalStrut(20));
		rangos.add(maximo);
		rangoPrestamo.add(Box.createVerticalStrut(10));
		rangoPrestamo.add(rangos);
		add(rangoPrestamo);
		
		JPanel edad = new JPanel();
		edad.setOpaque(false);
		edad.setLayout(new BoxLayout(edad, BoxLayout.X_AXIS));
		edad.add(Box.createHorizontalGlue());
		edad.add(edadMin);
		edad.add(Box.createHorizontalStrut(20));
		edad.add(edadMax);
		edad.add(Box.createHorizontalGlue());
		add(edad);
		
		JPanel ingresos = new JPanel();
		ingresos.setOpaque(false);
		ingresos.setLayout(new BoxLayout(ingresos, BoxLayout.X_AXIS));
		ingresos.add(Box.createHorizontalGlue());
		ingresos.add(ingresosMinimos);
		ingresos.add(Box.createHorizontalStrut(20));
		ingresos.add(ingresosMaximos);
		ingresos.add(Box.createHorizontalGlue());
		add(ingresos);
		
		JPanel botones = new JPanel();
		botones.setOpaque(false);
		botones.setLayout(new BoxLayout(botones, BoxLayout.X_AXIS));
		botones.add(Box.createHorizontalGlue());
		botones.add(buscar);
		botones.add(Box.createHorizontalStrut(20));
		botones.add(cancelar);
		botones.add(Box.createHorizontalGlue());
		add(botones);
		
		
	}

	
	public void crearGrupoOpcionesTipoPrestamos(JPanel panel) {
	       
        JRadioButton opcion1 = new JRadioButton("Activos");
        JRadioButton opcion2 = new JRadioButton("Conclusos");
        
        opcion1.setActionCommand("activos");
        opcion2.setActionCommand("conclusos");

        opcionTipoPrestamos.add(opcion1);
        opcionTipoPrestamos.add(opcion2);
        
        opcion1.setOpaque(false);
        opcion2.setOpaque(false);

        opcion1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(tipo.equals("activos"))
				{
					opcionTipoPrestamos.clearSelection();
					tipo=" ";
				}
				else
				{
					tipo="activos";
				}
			}
        });
        
        opcion2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(tipo.equals("conclusos"))
				{
					opcionTipoPrestamos.clearSelection();
					tipo=" ";
				}
				else
				{
					tipo="conclusos";
					opcionEstadoPrestamos.clearSelection();
					estado=" ";
				}
				
			}
        });
        
        panel.add(opcion1);
        panel.add(Box.createVerticalStrut(2));
        panel.add(opcion2);
        
    }
	
	public void crearGrupoOpcionesEstadoPrestamos(JPanel panel) {
        JRadioButton opcion1 = new JRadioButton("Correcto");
        JRadioButton opcion2 = new JRadioButton("Atrasado");
        
        opcion1.setActionCommand("correcto");
        opcion2.setActionCommand("atrasado");
        
        opcion1.setOpaque(false);
        opcion2.setOpaque(false);
        
        opcionEstadoPrestamos.add(opcion1);
        opcionEstadoPrestamos.add(opcion2);
        
        opcion1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(estado.equals("correcto"))
				{
					opcionEstadoPrestamos.clearSelection();
					estado = " ";
				}
				else
				{
					estado="correcto";
				}
				
				if(tipo.equals("conclusos")) 
				{
					opcionTipoPrestamos.clearSelection();
					tipo=" ";
				}
			}
        });
        
        opcion2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(estado.equals("atrasado"))
				{
					opcionEstadoPrestamos.clearSelection();
					estado = " ";
				}
				else
				{
					estado="atrasado";
				}
				
				if(tipo.equals("conclusos")) 
				{
					opcionTipoPrestamos.clearSelection();
					tipo=" ";
				}
			}
        });
      
        panel.add(opcion1);
        panel.add(Box.createVerticalStrut(2));
        panel.add(opcion2);
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
	
	private JTextField crearTextField(String placeholder, String JTextFieldName) 
    {
        JTextField textField = new JTextField(placeholder);
        
        int fieldWidth = 70;
        int fieldHeight = 45;
        
        textField.setMaximumSize(new Dimension(fieldWidth, fieldHeight));
        textField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        textField.setMinimumSize(new Dimension(fieldWidth, fieldHeight));
        
        textField.setBackground(Colores.BACKGROUND);
        textField.setForeground(Color.BLACK);
        textField.setFont(Fonts.fontTextoCampo);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setName(JTextFieldName);
        
        return textField;
    }
	
	private void asignarKeyListener(JTextField JtextField,int largo)
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
         		
         		
         		if(JtextField.getText().length() >= largo)
         		{
         			e.consume();
         		}
         	}
 		});
    }
	
	public JCheckBox getExceelente() {
		 return exceelente;
	 }
	 
	 public String getTipo() {
		return tipo;
	}
	 
	 public VentanaPrincipal getVentana() {
		return ventana;
	}
	 public void setVentana(VentanaPrincipal ventana) {
		 this.ventana = ventana;
	 }
	 public void setTipo(String tipo) {
		 this.tipo = tipo;
	 }
	 
	 public String getEstado() {
		return estado;
	}
	 
	 public JTextField getEdadMin() {
		return edadMin;
	}


	 public void setEdadMin(JTextField edadMin) {
		 this.edadMin = edadMin;
	 }


	 public JTextField getEdadMax() {
		 return edadMax;
	 }


	 public void setEdadMax(JTextField edadMax) {
		 this.edadMax = edadMax;
	 }


	 public JTextField getIngresosMinimos() {
		 return ingresosMinimos;
	 }


	 public void setIngresosMinimos(JTextField ingresosMinimos) {
		 this.ingresosMinimos = ingresosMinimos;
	 }


	 public JTextField getIngresosMaximos() {
		 return ingresosMaximos;
	 }


	 public void setIngresosMaximos(JTextField ingresosMaximos) {
		 this.ingresosMaximos = ingresosMaximos;
	 }


	 public void setEstado(String estado) {
		 this.estado = estado;
	 }
	 public void setExceelente(JCheckBox exceelente) {
		 this.exceelente = exceelente;
	 }
	 public JCheckBox getBuena() {
		 return buena;
	 }
	 public void setBuena(JCheckBox buena) {
		 this.buena = buena;
	 }
	 public JCheckBox getRegular() {
		 return regular;
	 }
	 public void setRegular(JCheckBox regular) {
		 this.regular = regular;
	 }
	 public JCheckBox getMala() {
		 return mala;
	 }
	 public void setMala(JCheckBox mala) {
		 this.mala = mala;
	 }
	 public JCheckBox getNoMedida() {
		 return noMedida;
	 }
	 public void setNoMedida(JCheckBox noMedida) {
		 this.noMedida = noMedida;
	 }
	 public ButtonGroup getOpcionTipoPrestamos() {
		 return opcionTipoPrestamos;
	 }
	 public void setOpcionTipoPrestamos(ButtonGroup opcionTipoPrestamos) {
		 this.opcionTipoPrestamos = opcionTipoPrestamos;
	 }
	 public ButtonGroup getOpcionEstadoPrestamos() {
		 return opcionEstadoPrestamos;
	 }
	 public void setOpcionEstadoPrestamos(ButtonGroup opcionEstadoPrestamos) {
		 this.opcionEstadoPrestamos = opcionEstadoPrestamos;
	 }
	 public JTextField getMinimo() {
		 return minimo;
	 }
	 public void setMinimo(JTextField minimo) {
		 this.minimo = minimo;
	 }
	 public JTextField getMaximo() {
		 return maximo;
	 }
	 public void setMaximo(JTextField maximo) {
		 this.maximo = maximo;
	 }
	 
	private void crearBoton(JButton button, String titulo, Color colorBoton, int ancho, int largo, Font font, String iconoURL) throws Exception
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
	        
	        button.setIcon(escalarImagen(iconoURL, 32, 32));
	    }
	
	public JButton getBuscar() {
		 return buscar;
	 }
	 public void setBuscar(JButton buscar) {
		 this.buscar = buscar;
	 }
	 public JButton getCancelar() {
		 return cancelar;
	 }
	 public void setCancelar(JButton cancelar) {
		 this.cancelar = cancelar;
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
	 
	 
}