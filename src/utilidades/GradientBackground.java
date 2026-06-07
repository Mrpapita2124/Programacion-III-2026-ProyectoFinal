package utilidades;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class GradientBackground extends JPanel {
 
	 @Override
	    protected void paintComponent(Graphics g) 
	 {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        
	        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	        
	        int ancho = getWidth();
	        int alto = getHeight();
	        
	        GradientPaint gradiente1 = new GradientPaint(
	            0, 0, Colores.COLOR_GRADIENTE1,              // Top
	            0, alto / 2, Colores.COLOR_GRADIENTE2      // Middle
	        );
	        
	        GradientPaint gradiente2 = new GradientPaint(
	            0, alto / 2, Colores.COLOR_GRADIENTE2,     // Middle
	            0, alto, Colores.COLOR_GRADIENTE3          // Bottom
	        );
	        
	        g2d.setPaint(gradiente1);
	        g2d.fillRect(0, 0, ancho, alto / 2);
	        
	        g2d.setPaint(gradiente2);
	        g2d.fillRect(0, alto / 2, ancho, alto);
	    }
}