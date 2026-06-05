package views.prestamo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controllers.prestamo.PrestamoPanelController;
import modelos.Client;
import modelos.Prestamo;
import repository.EstadoPrestamoRepository;
import repository.PrestamoRepository;
import utils.Colores;
import utils.Fonts;
import utils.PanelPersonalizable;
import utils.RoundedBorder;
import utils.Session;
import views.PrestamoInfoPanel;
import views.VentanaPrincipal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;


public class PrestamoCards extends PanelPersonalizable 
{
	private Font fontTitulo = Fonts.setFontSegoe(1,12);
	int alto;
	
	public PrestamoCards(VentanaPrincipal ventana) 
	{
		ArrayList<JPanel> prestamoCards = new ArrayList<JPanel>();
		PrestamoRepository prestamoRepo = new PrestamoRepository();
		
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
		
		
		List<Prestamo> prestamos = ordenarListaPrestamos(); // Nuevo metodo abajo 
		
		
		
		if (prestamos == null || prestamos.isEmpty()) 
		{
			String message = prestamos == null ? "No se encontraron préstamos con Filtro " : "No tienes préstamos activos";
			JLabel voidMessage = new JLabel(message); 
			
			voidMessage.setOpaque(false);
			voidMessage.setFont(fontTitulo);
			voidMessage.setForeground(Color.WHITE);
			voidMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
			alto = 300;
			
			add(Box.createRigidArea(new Dimension(0, 85)));
			add(voidMessage);
			
		} else {
			alto = 0;
			// define alto
			if(prestamos.size() % 2 == 0) {
				alto = (prestamos.size() / 2) * 300;
			} else {
				alto = ((prestamos.size() / 2) + 1) * 300;
			}
			
			for(int i = 0; i < prestamos.size(); i++) {
				PanelPersonalizable card = new PanelPersonalizable();
				card.setBackground(Colores.CLIENT_CARD_BG);
				card.setBorder(new RoundedBorder(Colores.DEFAULT_BORDER, 20, 2));
				card.setOpaque(true);
				
				PrestamoInfoPanel prestamoPanel = new PrestamoInfoPanel(prestamos.get(i));
				new PrestamoPanelController(prestamoPanel, ventana);
				prestamoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				
				card.add(prestamoPanel);
				prestamoCards.add(card);
			}
			
			setLayout(new GridLayout(0, 2, 15, 15));
			
			for(JPanel card : prestamoCards) {
				add(card);
			}
			
			if(prestamos.size() % 2 != 0) {
				JPanel relleno = new JPanel();
				relleno.setBackground(new Color(0, 0, 0, 0));
				relleno.setOpaque(false);
				add(relleno);
			}
		}
		
		setVisible(true);
	}
	
	
	// Metodo para poder ordenar los prestamos en orden: ACTIVOS -> ATRASADOS -> CONCLUIDOS -> CANCELADOS
	public List<Prestamo> ordenarListaPrestamos()
	{
		List<Prestamo> prestamosFiltrados = Session.getPrestamosFiltrados();
		
		if (prestamosFiltrados == null) 
		{
			//System.out.println("No tienes Prestamos activos");
	        return Session.getPrestamosFiltrados(); 
	    }
		
		
		List<Prestamo> prestamosOrdenandos = new ArrayList<Prestamo>();
		
		EstadoPrestamoRepository estadoPrestamoRepo = new EstadoPrestamoRepository();
		
		for (Prestamo prestamo : prestamosFiltrados) 
		{
			String estado = estadoPrestamoRepo.getClientPrestamosEstado(prestamo);
			
			if(estado != null && prestamo.getEstado().equals("activo") && !estado.equals("atrasado"))
			{
				prestamosOrdenandos.add(prestamo);
			}
		}
		
		for (Prestamo prestamo : prestamosFiltrados) {
			
			String estado = estadoPrestamoRepo.getClientPrestamosEstado(prestamo);
			
			if (estado != null && estado.equals("atrasado")) 
			{
				prestamosOrdenandos.add(prestamo);
			} 
		}
		
		
		for (Prestamo prestamo : prestamosFiltrados) {
			if(prestamo.getEstado().equals("concluso"))
			{
				prestamosOrdenandos.add(prestamo);
			}
		}
		
		for (Prestamo prestamo : prestamosFiltrados) {
			if(prestamo.getEstado().equals("cancelado"))
			{
				prestamosOrdenandos.add(prestamo);
			}
		}
		
		
		return prestamosOrdenandos;
	}
	
	
	public int getAlto() {
		return alto;
	}
	
	public void setAlto(int alto) {
		this.alto = alto;
	}
}