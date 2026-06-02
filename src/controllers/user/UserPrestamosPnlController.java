package controllers.user;

import views.user.UserPrestamosPanel;

public class UserPrestamosPnlController {
	
	private UserPrestamosPanel userPrestamosPanel;
	
	public UserPrestamosPnlController(UserPrestamosPanel userPrestamosPanel)
	{
		this.userPrestamosPanel = userPrestamosPanel;
		
		this.userPrestamosPanel.getBtnFilter().addActionListener(e -> 
		{
			
			// Prestamos filtatrrrr
			System.out.println("\nFILTAR PERO EN PRESTAMOS!");
		});
	}

	public UserPrestamosPanel getUserPrestamosPanel() {
		return userPrestamosPanel;
	}

	public void setUserPrestamosPanel(UserPrestamosPanel userPrestamosPanel) {
		this.userPrestamosPanel = userPrestamosPanel;
	}
	
	
}
