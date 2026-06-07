package vistas.otros;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modelos.EstadoPrestamo;
import modelos.Prestamo;
import utilidades.Colores;
import utilidades.Fuentes;

public class PagoDeuda extends JFrame {
	EstadoPrestamo estadoPrestamo;
	JLabel lblErrorDinero;
	JTextField dinero;
	JPanel componentes;
	JButton btnPagar;
	Prestamo prestamo;
	VentanaPrincipal ventana;
	public PagoDeuda(EstadoPrestamo estadoPrestamo, VentanaPrincipal ventana, Prestamo prestamo) 
	{
		this.ventana=ventana;
		this.prestamo=prestamo;
		this.estadoPrestamo=estadoPrestamo;
		setLayout(null);
		componentes = new JPanel();
		componentes.setLayout(new BoxLayout(componentes, BoxLayout.Y_AXIS));
		componentes.setBounds(0,0,450,210);
		componentes.setBackground(Colores.FONDO);
		setSize(450, 210); // Hace lo mismo de setSize y setLocation.
		setResizable(false);  // No se puede cambiar de tamaño con el mouse.
		setTitle("Pay Debt");  // Nombre de la Ventana
		setLocationRelativeTo(null);  // Pone la ventana en el centro
		setVisible(true);
		JLabel debtText=new JLabel("Cantidad a pagar : " + String.valueOf(estadoPrestamo.getDineroAtrasado()));
		lblErrorDinero=crearErrorLabel();
		componentes.add(debtText);
		dinero= crearCampoTexto("Dinero a pagar", "Money");
		asignarFocusListenerConPlaceholder(dinero, "Dinero a pagar");
		
		btnPagar= new JButton("Pagar");
		crearBoton(btnPagar,"..\\img\\icono.png" , "Pagar", Colores.BOTON_COLOR2, 105, 30, Fuentes.fuenteBoton);
		componentes.add(dinero);
		componentes.add(lblErrorDinero);
		componentes.add(btnPagar);
		componentes.setVisible(true);
		add(componentes);
	}
	public void reiniciarErrorLabels() 
    {
    	

    	lblErrorDinero.setText("");
    	
    
    }
	
	 public Prestamo getPrestamo() {
		return prestamo;
	}
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}
	public VentanaPrincipal getVentana() {
		return ventana;
	}
	public void setVentana(VentanaPrincipal ventana) {
		this.ventana = ventana;
	}
	 public JButton getBtnPagar() {
		return btnPagar;
	}
	public void setBtnPagar(JButton btnPay) {
		this.btnPagar = btnPay;
	}
	 public EstadoPrestamo getEstadoPrestamo() {
		return estadoPrestamo;
	}
	public void setEstadoPrestamo(EstadoPrestamo estadoPrestamo) {
		this.estadoPrestamo = estadoPrestamo;
	}
	public JLabel getLblErrorDinero() {
		return lblErrorDinero;
	}
	public void setLblErrorDinero(JLabel lblErrorMoney) {
		this.lblErrorDinero = lblErrorMoney;
	}
	public JTextField getDinero() {
		return dinero;
	}
	public void setDinero(JTextField money) {
		this.dinero = money;
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
        label.setFont(Fuentes.fuenteError);
        label.setForeground(Color.RED);
        label.setMaximumSize(new Dimension(400, 20));
        label.setPreferredSize(new Dimension(400, 20));
        label.setMinimumSize(new Dimension(400, 20));
        label.setAlignmentX(Component.LEFT_ALIGNMENT); // CAMBIADO: Alinear a la izquierda
        label.setHorizontalAlignment(SwingConstants.LEFT); // CAMBIADO: Texto alineado a la izquierda
        return label;
    }
	private JTextField crearCampoTexto(String placeholder, String nombreCampoTexto) 
    {
        JTextField campoTexto = new JTextField(placeholder);
        
        int ancho = 400;
        int alto = 45;
        
        campoTexto.setMaximumSize(new Dimension(ancho, alto));
        campoTexto.setPreferredSize(new Dimension(ancho, alto));
        campoTexto.setMinimumSize(new Dimension(ancho, alto));
        
        campoTexto.setBackground(Colores.FONDO);
        campoTexto.setForeground(Color.BLACK);
        campoTexto.setFont(Fuentes.fuenteTextoCampo);
        campoTexto.setAlignmentX(Component.CENTER_ALIGNMENT);
        campoTexto.setName(nombreCampoTexto);
        
        return campoTexto;
    }
	private void asignarFocusListenerConPlaceholder(JTextField campoTexto, String placeholder) {
    	
    	campoTexto.setBackground(Colores.TEXTO_TABULADO_COLOR);
    	
        campoTexto.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campoTexto.getText().equals(placeholder)) {
                    campoTexto.setText("");
                }
                campoTexto.setForeground(Color.BLACK);
                campoTexto.setBackground(Color.WHITE);
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (campoTexto.getText().isEmpty()) {
                    campoTexto.setText(placeholder);
                    campoTexto.setForeground(Color.BLACK);
                    campoTexto.setBackground(Colores.FONDO_TEXTO_COLOR);
                } else {
                	campoTexto.setForeground(Color.BLACK);
                    campoTexto.setBackground(Colores.TEXTO_TABULADO_COLOR);
                }
            }
        });
    }
}
