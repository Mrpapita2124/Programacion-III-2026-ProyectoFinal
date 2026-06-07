package vistas.usuario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import controllers.VentanaPrincipalController;
import controllers.usuario.UserDialogController;
import modelos.Usuario;
import repositorios.UsuarioRepository;
import utilidades.Colores;
import utilidades.ManejadorTemas;

public class FormularioUsuarioDialog extends JDialog{

	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtCorreo;
	private JPasswordField txtContraseña;
	private JLabel txtErrNombre = new JLabel(" ");
	private JLabel txtErrApellido = new JLabel(" ");
	private JLabel txtErrCorreo = new JLabel(" ");
	private JLabel txtErrContraseña = new JLabel(" ");

	private JButton btnGuardar;
	private JButton btnCancelar;
    private JButton seleccionar;
    private Usuario user;
    private boolean guardado = false;
    
    private UsuarioRepository usuarioRepository = new UsuarioRepository();
	private JLabel iconoUsuario;
	private ImageIcon iconoUsuarioFinal;
	private JCheckBox guardar= new JCheckBox("Guardar como usuario rapido");
    		
    public FormularioUsuarioDialog(JFrame parent, Usuario user) 
    {
    	
    	super(parent, true);
    	iconoUsuario=new JLabel();
    	iconoUsuarioFinal = escalarImagenLocal("/img/icono.png",200,200);
        iconoUsuarioFinal.setDescription("/img/icono.png");
    	setTitle(user == null ? "Agregar usuario" : "Editar usuario");
    	
    	txtErrNombre.setForeground(Color.RED);
    	txtErrApellido.setForeground(Color.RED);
    	txtErrCorreo.setForeground(Color.RED);
    	txtErrContraseña.setForeground(Color.RED);

    	setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        add(crearPanelTitulo(), BorderLayout.NORTH);
        add(crearPanelFormulario());
        
        
        add(crearPanelBotones(), BorderLayout.SOUTH);
        
    }
    
    private JPanel crearPanelTitulo() 
    {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Formulario de Usuario"));
        return panel;
    }
    
    private JPanel crearPanelBotones() {

        JPanel panel = new JPanel();

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        panel.add(btnGuardar);
        panel.add(btnCancelar);
        
        btnCancelar.addActionListener(e -> dispose());
        
        return panel;
    }

    private JScrollPane crearPanelFormulario() {

    	JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

		JScrollPane scroll = new JScrollPane(panel);
		scroll.setBorder(null);
		scroll.setHorizontalScrollBar(null);
		scroll.getVerticalScrollBar().setUnitIncrement(14);

		txtNombre = new JTextField();
		txtApellido = new JTextField();
		txtCorreo = new JTextField();
		txtContraseña=new JPasswordField();
		
        
		JLabel labelFoto = new JLabel("Selecciona una foto");
		labelFoto.setMaximumSize(new Dimension(Integer.MAX_VALUE, labelFoto.getPreferredSize().height));
		labelFoto.setHorizontalAlignment(SwingConstants.LEFT);
		labelFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        
		
        iconoUsuario.setIcon(iconoUsuarioFinal);
        iconoUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconoUsuario.setPreferredSize(new Dimension(200,200));
        iconoUsuario.setVisible(true);
        
        seleccionar=new JButton("Seleeccionar foto");
        
        panel.add(labelFoto);
        panel.add(iconoUsuario);
        panel.add(seleccionar);
		panel.add(crearbloqueTexto("Nombre:", txtNombre, txtErrNombre));
		panel.add(crearbloqueTexto("Apellido:", txtApellido, txtErrApellido));
		panel.add(crearbloqueTexto("Correo:", txtCorreo, txtErrCorreo));
		panel.add(crearbloqueTexto("Contraseña:", txtContraseña, txtErrContraseña));
		panel.add(guardar);
		
		

		return scroll;
    }
    		
    private JPanel crearbloqueTexto(String labelTexto, Component campoTexto, JLabel errorLabel) {

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel label = new JLabel(labelTexto);
		label.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getPreferredSize().height));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		errorLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getPreferredSize().height));
		errorLabel.setHorizontalAlignment(SwingConstants.LEFT);
		errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(label);
		panel.add(errorLabel);
		panel.add(campoTexto);

		return panel;
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
    		
    
	private ImageIcon escalarImagen(String direccion,int x,int y) {
	    	
	        ImageIcon iconoOriginal = new ImageIcon(direccion);
	
	       
	        Image imagenEscalada = iconoOriginal.getImage()
	                .getScaledInstance(x, y, Image.SCALE_SMOOTH);
	
	        
	        ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
	        iconoFinal.setDescription(direccion);
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
	public void seleccionarImagen() {
    	JFileChooser selector=new  JFileChooser();
    	selector.setDialogTitle("Selecciona tu foto de perfil");
    	FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagenes", "jpg","jpeg","png");
    	selector.setFileFilter(filtro);
    	int opcion= selector.showOpenDialog(this);
    	if(opcion==JFileChooser.APPROVE_OPTION) {
    		File archivo=selector.getSelectedFile();
    		iconoUsuario.setIcon(escalarImagen(archivo.getAbsolutePath(), 200, 200));
    		iconoUsuarioFinal.setDescription(archivo.getAbsolutePath());
    	}
    }
    
    // Getter y Setters

	public JTextField getTxtNombre() {
		return txtNombre;
	}
	public void setIconDescription(String descripcion) {
		iconoUsuarioFinal.setDescription(descripcion);
	}
	public String getIconDescription() {
		return iconoUsuarioFinal.getDescription();
	}

	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}

	public JTextField getTxtApellido() {
		return txtApellido;
	}

	public void setTxtApellido(JTextField txtApellido) {
		this.txtApellido = txtApellido;
	}

	public JTextField getTxtCorreo() {
		return txtCorreo;
	}

	public void setTxtCorreo(JTextField txtCorreo) {
		this.txtCorreo = txtCorreo;
	}

	

	public JLabel getIconoUsuario() {
		return iconoUsuario;
	}

	public void setIconoUsuario(JLabel iconoUsuario) {
		this.iconoUsuario = iconoUsuario;
	}

	public JPasswordField getTxtContraseña() {
		return txtContraseña;
	}

	public JButton getSeleccionar() {
		return seleccionar;
	}

	public void setSeleccionar(JButton seleccionar) {
		this.seleccionar = seleccionar;
	}

	public ImageIcon getIconoUsuarioFinal() {
		return iconoUsuarioFinal;
	}

	public void setIconoUsuarioFinal(ImageIcon iconoUsuarioFinal) {
		this.iconoUsuarioFinal = iconoUsuarioFinal;
	}

	public void setTxtContraseña(JPasswordField txtContraseña) {
		this.txtContraseña = txtContraseña;
	}

	public JLabel getTxtErrNombre() {
		return txtErrNombre;
	}

	public void setTxtErrNombre(JLabel txtErrNombre) {
		this.txtErrNombre = txtErrNombre;
	}

	public JLabel getTxtErrApellido() {
		return txtErrApellido;
	}

	public void setTxtErrApellido(JLabel txtErrApellido) {
		this.txtErrApellido = txtErrApellido;
	}

	public JLabel getTxtErrCorreo() {
		return txtErrCorreo;
	}

	public void setTxtErrCorreo(JLabel txtErrCorreo) {
		this.txtErrCorreo = txtErrCorreo;
	}

	public JLabel getTxtErrContraseña() {
		return txtErrContraseña;
	}

	public void setTxtErrContraseña(JLabel txtErrContraseña) {
		this.txtErrContraseña = txtErrContraseña;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(JButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public boolean isGuardado() {
		return guardado;
	}

	public void setGuardado(boolean guardado) {
		this.guardado = guardado;
	}

	public UsuarioRepository getUsuarioRepository() {
		return usuarioRepository;
	}

	public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public JCheckBox getGuardar() {
		return guardar;
	}

	public void setGuardar(JCheckBox guardar) {
		this.guardar = guardar;
	}
    	
	 
}
