package utils;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class GradientBackground extends JPanel {
 
	 @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        
	        // Enable anti-aliasing for smoother gradient
	        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	        
	        int width = getWidth();
	        int height = getHeight();
	        
	        // First gradient: top to middle (color1 → color2)
	        GradientPaint gradient1 = new GradientPaint(
	            0, 0, Colores.COLOR_GRADIENT1,              // Top
	            0, height / 2, Colores.COLOR_GRADIENT2      // Middle
	        );
	        
	        // Second gradient: middle to bottom (color2 → color3)
	        GradientPaint gradient2 = new GradientPaint(
	            0, height / 2, Colores.COLOR_GRADIENT2,     // Middle
	            0, height, Colores.COLOR_GRADIENT3          // Bottom
	        );
	        
	        // Paint top half
	        g2d.setPaint(gradient1);
	        g2d.fillRect(0, 0, width, height / 2);
	        
	        // Paint bottom half
	        g2d.setPaint(gradient2);
	        g2d.fillRect(0, height / 2, width, height);
	    }
}