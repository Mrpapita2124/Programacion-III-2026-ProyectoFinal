package vistas.otros;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.*;

import com.mysql.cj.Session;

import controllers.FiltrarPrestamosController;
import controllers.FiltrarClientesController;
import controllers.usuario.ClientesUsuarioController;
import controllers.usuario.UsuarioPrestamosPnlController;
import utilidades.GradientBackground;
import utilidades.ManejadorTemas;
import utilidades.Sesion;
import vistas.usuario.ClientesUsuarioPanel;
import vistas.usuario.UserPrestamosPanel;
import vistas.usuario.UsuariosTablaVista;

public class VentanaPrincipal extends JFrame 
{
	public static final String HOME = "HOME";
	public static final String USERS = "USERS";
	public static final String PRESTAMOS = "PRESTAMOS";
	public static final String FILTROS = "FILTROS";
	public static final String FILTROS_PRESTAMOS = "FILTROS_PRESTAMOS";
	
	private JMenuItem mItemSalida;
	private JButton btnUsuarios;
	private JButton btnInicio;
	private JButton btnPrestamo;
	private UsuariosTablaVista usuariosPanel;
	
	private CardLayout cardLayout;
	private JPanel contenedor;
	JPanel inicioPanel;
	JPanel prestamosPanel;
	JPanel filtroUsuariosPanel;
	JPanel filtroPrestamosPanel;
	
	private FiltrarClientesController filtroClientesController;
	private FiltrarPrestamosController filtroPrestamosController;
	
	public VentanaPrincipal() 
	{
		setSize(800, 600);
		setLayout(null);
		setTitle(Sesion.getusuarioActual().getNombre() + " " + Sesion.getusuarioActual().getApellido());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		// Esto solo deberia ir en el Main pero el modo oscuro hace que el login se vea raro entonces solo se aplicara ya al entrar al perfil.
    	ManejadorTemas.aplicarTemaGuardado();
    	

		Toolkit tk = Toolkit.getDefaultToolkit();
		ImageIcon imagenCursor = new ImageIcon("src\\img\\pointer_b.png");
		Cursor cursor = tk.createCustomCursor(imagenCursor.getImage(), new Point(0,0), "Cursor");
		this.setCursor(cursor);
		
		montarGradienteFondo();
		
		hacerMenu();
		
		crearBarraNavegacion();
		crearVistas();

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		
		setVisible(true);
	}
	
	private void montarGradienteFondo() 
	{
		GradientBackground gradientePanel = new GradientBackground();
		gradientePanel.setLayout(new BorderLayout());
		
		setContentPane(gradientePanel);
	}
	
	public void reload() 
	{
		//System.out.println("RELOAD");
		
		ManejadorTemas.aplicarTemaGuardado();
    	contenedor.remove(inicioPanel);
    	crearVistaClientes();
    	revalidate();
    	repaint();
    	mostrarVista(HOME);
	}
	
	public void recargarPrestamos(boolean cambiarVista) 
	{
		// Esto solo deberia ir en el Main pero el modo oscuro hace que el login se vea raro entonces solo se aplicara ya al entrar al perfil.
    	ManejadorTemas.aplicarTemaGuardado();
    	contenedor.remove(prestamosPanel);
    	createPrestamosView();
    	revalidate();
    	repaint();
    	if(cambiarVista)mostrarVista(PRESTAMOS);
	}
	
	public void crearBarraNavegacion() 
	{
		JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT));
		barra.setOpaque(true);
		
		btnInicio = new JButton("Inicio");
		if(Sesion.getusuarioActual().getRol().equals("admin")) {
			btnUsuarios = new JButton("Usuarios");
		}else {
			btnUsuarios = new JButton("Editar");
		}
		
		//btnPrestamo= new JButton("Prestamos");
		
		barra.add(btnInicio);
		//navbar.add(btnPrestamo);
		barra.add(btnUsuarios);
		add(barra, BorderLayout.NORTH);
	}
	
	private void crearVistas() 
	{
		cardLayout = new CardLayout();
		contenedor = new JPanel(cardLayout);
		contenedor.setOpaque(false);
		
		ClientesUsuarioPanel panel=new ClientesUsuarioPanel(this);
		new ClientesUsuarioController(panel);
		inicioPanel = panel;

		UserPrestamosPanel prestamosCartas = new UserPrestamosPanel(this);
	    new UsuarioPrestamosPnlController(prestamosCartas);
	    prestamosPanel = prestamosCartas;
	    
		usuariosPanel = new UsuariosTablaVista();
		
		Filtros filtros= new Filtros(this);
		filtroClientesController =new FiltrarClientesController(filtros);
		filtroUsuariosPanel=filtros;
		
		
		Filtros filtrosPrestamo= new Filtros(this);
		filtroPrestamosController = new FiltrarPrestamosController(filtrosPrestamo);
		filtroPrestamosPanel=filtrosPrestamo;
		
		contenedor.add(inicioPanel, HOME);
		contenedor.add(usuariosPanel, USERS);
		contenedor.add(prestamosPanel,PRESTAMOS);
		contenedor.add(filtroUsuariosPanel, FILTROS);
		contenedor.add(filtroPrestamosPanel, FILTROS_PRESTAMOS);
		
		add(contenedor, BorderLayout.CENTER);
		
		filtroClientesController.refrescarClientesFiltrados();
		
	}
	
	public void createPrestamosView() 
	{
		UserPrestamosPanel prestamosCards = new UserPrestamosPanel(this);
		UsuarioPrestamosPnlController usuarioPrestamosPnlController = new UsuarioPrestamosPnlController(prestamosCards);
		
		prestamosPanel = prestamosCards;  
		contenedor.add(prestamosPanel, PRESTAMOS);
		add(contenedor, BorderLayout.CENTER);
		
	}
	
	private void crearVistaClientes() 
	{
		
		ClientesUsuarioPanel panel=new ClientesUsuarioPanel(this);
		new ClientesUsuarioController(panel);
		JPanel inicioPanel = panel;
		contenedor.add(inicioPanel, HOME);
		
		add(contenedor, BorderLayout.CENTER);
		
	}
	
	public void mostrarVista(String view) 
	{
		cardLayout.show(contenedor, view);
	}

	public void hacerMenu() {

	    JMenuBar mb = new JMenuBar();
	    setJMenuBar(mb);

	    JMenu menuFile = new JMenu("File");
	    menuFile.setMnemonic(KeyEvent.VK_F);
	    mb.add(menuFile);

	    JMenuItem mItemOpen = new JMenuItem("Open");
	    mItemOpen.setMnemonic(KeyEvent.VK_O);
	    menuFile.add(mItemOpen);

	    JMenuItem mItemSave = new JMenuItem("Save");
	    mItemSave.setMnemonic(KeyEvent.VK_S);
	    menuFile.add(mItemSave);

	    menuFile.addSeparator();

	    mItemSalida = new JMenuItem("Exit");
	    mItemSalida.setMnemonic(KeyEvent.VK_E);
	    menuFile.add(mItemSalida);
	    
	    
	    JMenuItem theme = new JMenuItem("Cambiar modo");
	    theme.addActionListener(e -> {
	    	ManejadorTemas.cambiar();
	    });
	    mb.add(theme);
	    

	    JMenu menuOtherOption = new JMenu("Other Option");
	    menuOtherOption.setMnemonic(KeyEvent.VK_O);
	    mb.add(menuOtherOption);

	    JMenu menuOption1 = new JMenu("Option 1");
	    menuOtherOption.add(menuOption1);

	    JMenuItem mItemOption3 = new JMenuItem("Option 3");
	    menuOption1.add(mItemOption3);

	    JMenuItem mItemOption2 = new JMenuItem("Option 2");
	    menuOtherOption.add(mItemOption2);

	}
	
	public int confirmarSalir() 
	{
	    return JOptionPane.showConfirmDialog(
	        this,
	        "¿Seguro que deseas regresar? Se perderán todos los datos",
	        "¿Seguro?",
	        JOptionPane.YES_NO_OPTION
	    );
	}
	
	
	
	public void setTamanioVentana(int ancho, int alto) {
		setSize(ancho, alto);
	}
	
	public void setUbicacionVentana(int x, int y) {
		setLocation(x, y);
	}

	public JMenuItem getmItemSalida() {
		return mItemSalida;
	}

	public void setmItemSalida(JMenuItem mItemSalida) {
		this.mItemSalida = mItemSalida;
	}

	public JButton getBtnUsuarios() {
		return btnUsuarios;
	}

	public void setBtnUsuarios(JButton btnUsuarios) {
		this.btnUsuarios = btnUsuarios;
	}

	public JButton getBtnPrestamo() {
		return btnPrestamo;
	}
	public void setBtnPrestamo(JButton btnPrestamo) {
		this.btnPrestamo = btnPrestamo;
	}
	public JButton getBtnInicio() {
		return btnInicio;
	}

	public FiltrarClientesController getFiltroClientesControlador() {
		return filtroClientesController;
	}

	public void setFiltroClientesControlador(FiltrarClientesController filtroUsuarioControlador) {
		this.filtroClientesController = filtroUsuarioControlador;
	}

	public FiltrarPrestamosController getFilterPrestamoViewController() {
		return filtroPrestamosController;
	}

	public void setFiltroPrestamosController(FiltrarPrestamosController filtroPrestamoController) {
		this.filtroPrestamosController = filtroPrestamoController;
	}

	public void setBtnInicio(JButton btnInicio) {
		this.btnInicio = btnInicio;
	}

	public UsuariosTablaVista getUsuariosPanel() {
		return usuariosPanel;
	}

	public void setUsuariosPanel(UsuariosTablaVista usuariosPanel) {
		this.usuariosPanel = usuariosPanel;
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}

	public void setCardLayout(CardLayout cardLayout) {
		this.cardLayout = cardLayout;
	}

	public JPanel getContenedor() {
		return contenedor;
	}

	public void setContainer(JPanel contenedor) {
		this.contenedor = contenedor;
	}
	
}