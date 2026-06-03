package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import utils.Colores;
import utils.Fonts;

public class FilterView extends JPanel {
	private JCheckBox exceelente= new JCheckBox(" excelente");
	private JCheckBox buena= new JCheckBox(" buena");
	private JCheckBox regular= new JCheckBox(" regular");
	private JCheckBox mala= new JCheckBox(" mala");
	private JCheckBox noMedida= new JCheckBox(" no medida");
	
	private ButtonGroup opcionTipoPrestamos;
	private ButtonGroup opcionEstadoPrestamos;
	
	private JTextField minimo;
	private JTextField maximo;
	
	private JButton buscar = new JButton("Buscar");
	private JButton cancelar = new JButton("Cancelar");
	private String tipo=" ";
	private String estado=" ";
	private VentanaPrincipal ventana;
	public FilterView(VentanaPrincipal ventana) {
		this.ventana=ventana;
		crearBoton(buscar, "Buscar", Colores.BUTTON_COLOR1, 100, 50, Fonts.fontBoton);
		crearBoton(cancelar, "Cancelar", Colores.BUTTON_COLOR1, 150, 50, Fonts.fontBoton);
		
		opcionTipoPrestamos = new ButtonGroup();
		opcionEstadoPrestamos= new ButtonGroup();
		minimo=crearTextField("MIN", "minimo");
		maximo=crearTextField("MAX", "maximo");
		asignarKeyListener(maximo);
		asignarKeyListener(minimo);
		asignarFocusListenerConPlaceholder(maximo, "MAX");
		asignarFocusListenerConPlaceholder(minimo, "MIN");
		setLayout(new GridLayout(3, 2));
		
		
		JPanel reputacion = new JPanel();
		reputacion.setLayout(new BoxLayout(reputacion, BoxLayout.Y_AXIS));
		reputacion.add(exceelente);
		reputacion.add(buena);
		reputacion.add(regular);
		reputacion.add(mala);
		reputacion.add(noMedida);
		add(reputacion);
		
		
		JPanel tipoPrestamos = new JPanel();
		tipoPrestamos.setLayout(new BoxLayout(tipoPrestamos, BoxLayout.Y_AXIS));
		crearGrupoOpcionesTipoPrestamos(tipoPrestamos);
		add(tipoPrestamos);
		
		
		
		
		JPanel estadoPrestamos= new JPanel();
		estadoPrestamos.setLayout(new BoxLayout(estadoPrestamos, BoxLayout.Y_AXIS));
		crearGrupoOpcionesEstadoPrestamos(estadoPrestamos);
		add(estadoPrestamos);
		
		JPanel rangoPrestamo= new JPanel();
		rangoPrestamo.setLayout(new BoxLayout(rangoPrestamo, BoxLayout.Y_AXIS));
		JPanel rangos= new JPanel();
		rangos.setLayout(new BoxLayout(rangos, BoxLayout.X_AXIS));
		rangos.add(minimo);
		rangos.add(maximo);
		rangoPrestamo.add(rangos);
		add(rangoPrestamo);
		
		JPanel botones = new JPanel();
		botones.setLayout(new BoxLayout(botones, BoxLayout.X_AXIS));
		botones.add(buscar);
		botones.add(cancelar);
		add(botones);
		
		
	}
	 public void crearGrupoOpcionesTipoPrestamos(JPanel panel) {
	       
	        JRadioButton opcion1 = new JRadioButton("Activos");
	        JRadioButton opcion2 = new JRadioButton("Conclusos");
	        
	        opcion1.setActionCommand("activos");
	        opcion2.setActionCommand("conclusos");

	        opcionTipoPrestamos.add(opcion1);
	        opcionTipoPrestamos.add(opcion2);
	        

	        opcion1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					tipo="activos";
				}
	        });
	        opcion2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					tipo="conclusos";
					opcionEstadoPrestamos.clearSelection();
					estado=" ";
				}
	        });
	        
	        
	      
	        panel.add(opcion1);
	        panel.add(opcion2);
	        

	        
	    }
	 public void crearGrupoOpcionesEstadoPrestamos(JPanel panel) {
	        // Crear los radio buttons
	        JRadioButton opcion1 = new JRadioButton("Correcto");
	        JRadioButton opcion2 = new JRadioButton("Atrasado");
	        
	        opcion1.setActionCommand("correcto");
	        opcion2.setActionCommand("atrasado");

	        // Crear el grupo exclusivo
	        
	        opcionEstadoPrestamos.add(opcion1);
	        opcionEstadoPrestamos.add(opcion2);
	        
	        opcion1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					estado="correcto";
					if(tipo.equals("conclusos")) {
						opcionTipoPrestamos.clearSelection();
						tipo=" ";
					}
				}
	        });
	        opcion2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					estado="atrasado";
					if(tipo.equals("conclusos")) {
						opcionTipoPrestamos.clearSelection();
						tipo=" ";
					}
				}
	        });
	      
	        panel.add(opcion1);
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
	 private void asignarKeyListener(JTextField JtextField)
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
	         		
	         		
	         		if(JtextField.getText().length() >= 10)
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
	 private void crearBoton(JButton button, String titulo, Color colorBoton, int ancho, int largo, Font font)
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
	 
}
