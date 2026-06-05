package main;

import utils.ThemeManager;
import views.Ventana;


public class Main {
	
	public static void main(String[] args)
	{
		ThemeManager.applySavedTheme();
		
		Ventana ventanita = new Ventana();
	}
}
