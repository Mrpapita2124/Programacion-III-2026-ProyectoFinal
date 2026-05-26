package utils;

import java.awt.Font;

public class Fonts {
	
	public final static Font FONT_SEGOE_BOLD = new Font("Segoe UI", Font.BOLD, 16);
	public final static Font FONT_SEGOE = new Font("Segoe UI", Font.PLAIN, 12);
	public final static Font fontError = new Font("Times New Roman", Font.ITALIC, 12);
	public final static Font fontTextoCampo = new Font("Times New Roman", Font.ITALIC, 15);
	public final static Font fontBoton = new Font("Times New Roman", Font.BOLD, 20);
	public final static Font fontTitulo = new Font("Times New Roman", Font.BOLD, 35);
	public final static Font fontBotonChico = new Font("Times New Roman", Font.BOLD, 14);
	// fontStyle ->  BOLD = 1, PLAIN = 0
	public final static Font setFontSegoe(int fontStyle, int fontSize)
	{
		return new Font("Segoe UI", fontStyle, fontSize);
	}
}
