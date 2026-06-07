package main;

import utilidades.ManejadorTemas;
import vistas.otros.Ventana;


public class Main {
	
	public static void main(String[] args)
	{
		ManejadorTemas.aplicarTemaGuardado();
		
		Ventana ventanita = new Ventana();
	}
}
