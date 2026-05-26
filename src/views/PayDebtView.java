package views;

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
import utils.Colores;
import utils.Fonts;

public class PayDebtView extends JFrame {
	EstadoPrestamo estadoPrestamo;
	JLabel lblErrorMoney;
	JTextField money;
	JPanel components;
	JButton btnPay;
	Prestamo prestamo;
	VentanaPrincipal ventana;
	public PayDebtView(EstadoPrestamo estadoPrestamo, VentanaPrincipal ventana, Prestamo prestamo) 
	{
		this.ventana=ventana;
		this.prestamo=prestamo;
		this.estadoPrestamo=estadoPrestamo;
		setLayout(null);
		components = new JPanel();
		components.setLayout(new BoxLayout(components, BoxLayout.Y_AXIS));
		components.setBounds(0,0,450,210);
		components.setBackground(Colores.BACKGROUND);
		setSize(450, 210); // Hace lo mismo de setSize y setLocation.
		setResizable(false);  // No se puede cambiar de tamaño con el mouse.
		setTitle("Pay Debt");  // Nombre de la Ventana
		setLocationRelativeTo(null);  // Pone la ventana en el centro
		setVisible(true);
		JLabel debtText=new JLabel("Cantidad a pagar : " + String.valueOf(estadoPrestamo.getDinero_atrasado()));
		lblErrorMoney=crearErrorLabel();
		components.add(debtText);
		money= crearTextField("Dinero a pagar", "Money");
		asignarFocusListenerConPlaceholder(money, "Dinero a pagar");
		
		btnPay= new JButton("Pagar");
		crearBoton(btnPay,"..\\img\\icono.png" , "Pagar", Colores.BUTTON_COLOR2, 105, 30, Fonts.fontBoton);
		components.add(money);
		components.add(lblErrorMoney);
		components.add(btnPay);
		components.setVisible(true);
		add(components);
	}
	public void resetearErrorLabels() 
    {
    	

    	lblErrorMoney.setText("");
    	
    
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
	 public JButton getBtnPay() {
		return btnPay;
	}
	public void setBtnPay(JButton btnPay) {
		this.btnPay = btnPay;
	}
	 public EstadoPrestamo getEstadoPrestamo() {
		return estadoPrestamo;
	}
	public void setEstadoPrestamo(EstadoPrestamo estadoPrestamo) {
		this.estadoPrestamo = estadoPrestamo;
	}
	public JLabel getLblErrorMoney() {
		return lblErrorMoney;
	}
	public void setLblErrorMoney(JLabel lblErrorMoney) {
		this.lblErrorMoney = lblErrorMoney;
	}
	public JTextField getMoney() {
		return money;
	}
	public void setMoney(JTextField money) {
		this.money = money;
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
        label.setFont(Fonts.fontError);
        label.setForeground(Color.RED);
        label.setMaximumSize(new Dimension(400, 20));
        label.setPreferredSize(new Dimension(400, 20));
        label.setMinimumSize(new Dimension(400, 20));
        label.setAlignmentX(Component.LEFT_ALIGNMENT); // CAMBIADO: Alinear a la izquierda
        label.setHorizontalAlignment(SwingConstants.LEFT); // CAMBIADO: Texto alineado a la izquierda
        return label;
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
        textField.setFont(Fonts.fontTextoCampo);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setName(JTextFieldName);
        
        return textField;
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
}
