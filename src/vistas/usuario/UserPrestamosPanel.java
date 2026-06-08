package vistas.usuario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

import repositorios.ClienteRepository;
import repositorios.PrestamoRepository;
import utilidades.Colores;
import utilidades.Fuentes;
import vistas.otros.VentanaPrincipal;
import vistas.prestamo.PrestamoCartas;
import vistas.prestamo.PrestamosConclusosCartas;

public class UserPrestamosPanel extends JPanel {

	private Font fontTitulo = Fuentes.setFontSegoe(1, 35);
	private Font fontBotones = Fuentes.setFontSegoe(0, 14);
	
	private JButton btnClientes;
	private JButton btnFiltro;
	PrestamoCartas prestamos;
	PrestamosConclusosCartas prestamosConlcuidos;
	JScrollPane scrollPrestamos;
	VentanaPrincipal ancestro;
	
	public UserPrestamosPanel(VentanaPrincipal ancestro) {
		this.ancestro = ancestro;
		
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(Box.createRigidArea(new Dimension(0, 25)));
		
		JPanel panelTitulo = new JPanel(new BorderLayout());
		panelTitulo.setOpaque(false);
		panelTitulo.setMaximumSize(new Dimension(1200, 50));
		panelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel izqPanel = new JPanel();
		izqPanel.setOpaque(false);
		izqPanel.setPreferredSize(new Dimension(80, 30));
		
		JLabel tituloLbl = new JLabel("Mis Préstamos"); 
		tituloLbl.setOpaque(false);
		tituloLbl.setFont(fontTitulo);
		tituloLbl.setForeground(Colores.ENCABEZADOS_PRIMARIOS);
		
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
		btnPanel.setOpaque(false);
		
		btnClientes = new JButton("Clientes");
		btnClientes.setFont(fontBotones);
		btnClientes.setBackground(Colores.BOTON_COLOR3);
		btnClientes.setForeground(Color.BLACK);
		btnClientes.setFocusPainted(false);
		btnClientes.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
		btnClientes.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnFiltro = new JButton("Filtrar");
		btnFiltro.setFont(fontBotones);
		btnFiltro.setBackground(Colores.BOTON_COLOR4);
		btnFiltro.setForeground(Color.WHITE);
		btnFiltro.setFocusPainted(false);
		btnFiltro.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
		btnFiltro.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		int iconSize = 20;
		
		try 
		{
			btnFiltro.setIcon(escalarImagen("src\\img\\cliente_filtrar.png", iconSize, iconSize));
			btnClientes.setIcon(escalarImagen("src\\img\\prestamo_regresar_cliente.png", iconSize, iconSize));
		} 
		catch (Exception e) 
		{
			System.out.println("No se cargo lo iconos de filtar cliente!");
		}
		
		btnPanel.add(btnClientes);
		
		PrestamoRepository prestamoRepo = new PrestamoRepository();
		if(prestamoRepo.getTotalNumeroDePrestamos() != 0)
		{
			btnPanel.add(btnFiltro);
		}
		
		panelTitulo.add(izqPanel, BorderLayout.WEST);
		panelTitulo.add(tituloLbl, BorderLayout.CENTER);
		panelTitulo.add(btnPanel, BorderLayout.EAST);
		
		panelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		add(panelTitulo);
		
		
		
		add(Box.createRigidArea(new Dimension(0, 50)));
		
		JPanel allPrestamos = new JPanel();
		allPrestamos.setLayout(new BoxLayout(allPrestamos, BoxLayout.Y_AXIS));
		allPrestamos.setOpaque(false);
		
		prestamos = new PrestamoCartas(ancestro);
		prestamos.setAlignmentX(Component.CENTER_ALIGNMENT);
		allPrestamos.add(prestamos);
		
		
		scrollPrestamos = new JScrollPane(allPrestamos);
		scrollPrestamos.setMaximumSize(new Dimension(1400, 400));
		scrollPrestamos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPrestamos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPrestamos.setOpaque(false);
		scrollPrestamos.getViewport().setOpaque(false);
		scrollPrestamos.setBorder(null);
		scrollPrestamos.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		scrollPrestamos.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Colores.BOTON_COLOR1;       // color del "pulgar" (la parte que arrastras)
		        this.trackColor = Color.LIGHT_GRAY;
			}
			@Override
			protected JButton createDecreaseButton(int orientation) {
				return createZeroButton();
			}
			@Override
			protected JButton createIncreaseButton(int orientation) {
				return createZeroButton();
			}
			private JButton createZeroButton() {
				JButton button = new JButton();
				button.setPreferredSize(new Dimension(0, 0));
				return button;
			}
		});
		scrollPrestamos.getVerticalScrollBar().setUnitIncrement(20);
		scrollPrestamos.getVerticalScrollBar().setOpaque(false);
		
		add(scrollPrestamos);
	}
	
	public JButton getBtnFiltro() {
		return btnFiltro;
	}
	
	public void setBtnFiltro(JButton btnFiltro) {
		this.btnFiltro = btnFiltro;
	}
	
	public PrestamoCartas getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(PrestamoCartas prestamos) {
		this.prestamos = prestamos;
	}
	
	public PrestamosConclusosCartas getConcludedPrestamos() {
		return prestamosConlcuidos;
	}

	public void setPrestamosConcluidos(PrestamosConclusosCartas prestamosConcluidos) {
		this.prestamosConlcuidos = prestamosConcluidos;
	}

	public JScrollPane getScrollPrestamos() {
		return scrollPrestamos;
	}
	
	public void setScrollPrestamos(JScrollPane scrollPrestamos) {
		this.scrollPrestamos = scrollPrestamos;
	}
	
	public VentanaPrincipal getAncestro() {
		return ancestro;
	}
	
	public void setAncestro(VentanaPrincipal ancestro) {
		this.ancestro = ancestro;
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

	public JButton getBtnClientes() {
		return btnClientes;
	}

	public void setBtnClientes(JButton btnClientes) {
		this.btnClientes = btnClientes;
	}
}