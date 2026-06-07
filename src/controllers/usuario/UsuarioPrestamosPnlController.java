package controllers.usuario;

import vistas.otros.VentanaPrincipal;
import vistas.usuario.UserPrestamosPanel;

public class UsuarioPrestamosPnlController {
	
	private UserPrestamosPanel usuarioPrestamosPanel;
	
	public UsuarioPrestamosPnlController(UserPrestamosPanel usuarioPrestamosPanel )
	{
		
		this.usuarioPrestamosPanel = usuarioPrestamosPanel;
		
		this.usuarioPrestamosPanel.getBtnFiltro().addActionListener(e -> 
		{
			this.usuarioPrestamosPanel.getAncestro().mostrarVista(VentanaPrincipal.FILTROS_PRESTAMOS);
			// Prestamos filtatrrrr
			//System.out.println("\nFILTAR PERO EN PRESTAMOS!");
		});
	}

	public UserPrestamosPanel getUserPrestamosPanel() {
		return usuarioPrestamosPanel;
	}

	public void setUserPrestamosPanel(UserPrestamosPanel userPrestamosPanel) {
		this.usuarioPrestamosPanel = userPrestamosPanel;
	}
	
	
}
