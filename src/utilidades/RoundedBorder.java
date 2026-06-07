package utilidades;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.AbstractBorder;

public class RoundedBorder extends AbstractBorder {
    private Color borderColor;
    private int radius;
    private int thickness;
    
    public RoundedBorder(Color color, int radius, int thickness) {
        this.borderColor = color;
        this.radius = radius;
        this.thickness = thickness;
    }
    
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(borderColor);
        g2d.setStroke(new java.awt.BasicStroke(thickness));
        
        // Adjust for thickness to keep border inside bounds
        int adjustedX = x + thickness / 2;
        int adjustedY = y + thickness / 2;
        int adjustedWidth = width - thickness;
        int adjustedHeight = height - thickness;
        
        g2d.drawRoundRect(adjustedX, adjustedY, adjustedWidth, adjustedHeight, radius, radius);
        g2d.dispose();
    }
    
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness + 2, thickness + 2, thickness + 2, thickness + 2);
    }
    
    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = thickness + 2;
        insets.top = thickness + 2;
        insets.right = thickness + 2;
        insets.bottom = thickness + 2;
        return insets;
    }
}