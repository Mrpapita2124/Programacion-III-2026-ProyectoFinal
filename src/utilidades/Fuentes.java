package utilidades;

import java.awt.Font;

public class Fuentes {
	
	public final static Font FONT_SEGOE_BOLD = new Font("Segoe UI", Font.BOLD, 16);
	public final static Font FONT_SEGOE = new Font("Segoe UI", Font.PLAIN, 12);
	public final static Font fuenteError = new Font("Times New Roman", Font.ITALIC, 12);
	public final static Font fuenteTextoCampo = new Font("Times New Roman", Font.ITALIC, 15);
	public final static Font fuenteBoton = new Font("Times New Roman", Font.BOLD, 20);
	public final static Font fuenteTitulo = new Font("Times New Roman", Font.BOLD, 35);
	public final static Font fuenteBotonChico = new Font("Times New Roman", Font.BOLD, 14);
	public final static Font fontTexto = new Font("Times New Roman", Font.BOLD, 35);
	public final static Font setFontSegoe(int fontStyle, int fontSize)
	{
		return new Font("Segoe UI", fontStyle, fontSize);
	}
}
