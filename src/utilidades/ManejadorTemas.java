package utilidades;

import java.awt.Window;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import config.Config;

public class ManejadorTemas {

	public static void aplicarTemaGuardado() {
		String tema = Config.get("ui.theme", "light");
		aplicar(tema);
	}
	
	public static void aplicar(String tema) {
		try {
			if(tema.equalsIgnoreCase("dark")) {
				FlatDarkLaf.setup();
			}else {
				FlatLightLaf.setup();
			}
			
			Config.set("ui.theme", tema);
			
			for(Window w: Window.getWindows()) {
				SwingUtilities.updateComponentTreeUI(w);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void cambiar() {
		
		String current = Config.get("ui.theme", "light");
		
		if(current.equalsIgnoreCase("light")) {
			aplicar("dark");
		}else {
			aplicar("light");
		}
		
	}
	
}









