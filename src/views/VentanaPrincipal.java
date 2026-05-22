package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.io.IOException;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.*;

import controllers.UserClientsPanelController;
import controllers.VentanaPrincipalController;
import modelos.User;
import repository.UserRepository;
import utils.Session;
import utils.ThemeManager;

public class VentanaPrincipal extends JFrame 
{
	public static final String HOME = "HOME";
	public static final String USERS = "USERS";
	
	private JMenuItem mItemExit;
	private JButton btnUsers;
	private JButton btnHome;
	private UsersView usersPanel;
	
	private CardLayout cardLayout;
	private JPanel container;
	JPanel homePanel;
	
	public VentanaPrincipal() 
	{
		setSize(800, 600);
		setTitle("Mi aplicación");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		
		// Esto solo deberia ir en el Main pero el modo oscuro hace que el login se vea raro entonces solo se aplicara ya al entrar al perfil.
    	ThemeManager.applySavedTheme();
    	

		Toolkit tk = Toolkit.getDefaultToolkit();
		ImageIcon cursorImage = new ImageIcon("src\\img\\pointer_b.png");
		Cursor myCursor = tk.createCustomCursor(cursorImage.getImage(), new Point(0,0), "Cursor");
		this.setCursor(myCursor);
		
		setMenu();
		
		createNavbar();
		createViews();

		setVisible(true);
	}
	public void reload() 
	{
		// Esto solo deberia ir en el Main pero el modo oscuro hace que el login se vea raro entonces solo se aplicara ya al entrar al perfil.
    	ThemeManager.applySavedTheme();
    	container.remove(homePanel);
    	createClientsView();
    	revalidate();
    	repaint();
    	showView(HOME);
    	
	}
	
	public void createNavbar() 
	{
		JPanel navbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		btnHome = new JButton("Inicio");
		btnUsers = new JButton("Usuarios");
		
		
		navbar.add(btnHome);
		navbar.add(btnUsers);
		add(navbar, BorderLayout.NORTH);
	}
	
	private void createViews() 
	{
		cardLayout = new CardLayout();
		container = new JPanel(cardLayout);
		UserClientsPanel panel=new UserClientsPanel(this);
		new UserClientsPanelController(panel);
		homePanel = panel;
		
		//homePanel.add(new JLabel("Bienvenido al Sistema"));
		
		usersPanel = new UsersView();
		
		container.add(homePanel, HOME);
		container.add(usersPanel, USERS);
		
		add(container, BorderLayout.CENTER);
		
	}
	private void createClientsView() 
	{
		
		UserClientsPanel panel=new UserClientsPanel(this);
		new UserClientsPanelController(panel);
		JPanel homePanel = panel;
		container.add(homePanel, HOME);
		
		add(container, BorderLayout.CENTER);
		
	}
	
	public void showView(String view) 
	{
		cardLayout.show(container, view);
	}

	public void setMenu() {

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

	    mItemExit = new JMenuItem("Exit");
	    mItemExit.setMnemonic(KeyEvent.VK_E);
	    menuFile.add(mItemExit);
	    
	    
	    JMenuItem theme = new JMenuItem("Cambiar modo");
	    theme.addActionListener(e -> {
	    	ThemeManager.toggle();
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
	
	public int confirmExit() 
	{
	    return JOptionPane.showConfirmDialog(
	        this,
	        "¿Seguro que deseas regresar? Se perderán todos los datos",
	        "¿Seguro?",
	        JOptionPane.YES_NO_OPTION
	    );
	}
	
	public void setWindowSize(int width, int height) {
		setSize(width, height);
	}
	
	public void setWindowLocation(int x, int y) {
		setLocation(x, y);
	}

	public JMenuItem getmItemExit() {
		return mItemExit;
	}

	public void setmItemExit(JMenuItem mItemExit) {
		this.mItemExit = mItemExit;
	}

	public JButton getBtnUsers() {
		return btnUsers;
	}

	public void setBtnUsers(JButton btnUsers) {
		this.btnUsers = btnUsers;
	}

	public JButton getBtnHome() {
		return btnHome;
	}

	public void setBtnHome(JButton btnHome) {
		this.btnHome = btnHome;
	}

	public UsersView getUsersPanel() {
		return usersPanel;
	}

	public void setUsersPanel(UsersView usersPanel) {
		this.usersPanel = usersPanel;
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}

	public void setCardLayout(CardLayout cardLayout) {
		this.cardLayout = cardLayout;
	}

	public JPanel getContainer() {
		return container;
	}

	public void setContainer(JPanel container) {
		this.container = container;
	}
	
}