package vistas.otros;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controllers.usuario.UsuarioCartaController;
import modelos.Usuario;
import modelosTabla.ModeloTablaUsuario;
import repositorios.UsuarioRepository;
import utilidades.Colores;
import vistas.usuario.UsuarioCarta;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Usuarios extends JPanel {
	
	public Usuarios() {
		ArrayList<UsuarioCarta> usuariosCartas = new ArrayList<UsuarioCarta>();
		UsuarioRepository usuarioRepository = new UsuarioRepository();
		try {
			List<Usuario> usuarios = usuarioRepository.getUsuariosComunes(); 
			
			ModeloTablaUsuario tablaUsuarios = new ModeloTablaUsuario(usuarios);
			
			for(int i = 0; i < tablaUsuarios.getRowCount(); i++) {
				if(tablaUsuarios.getValueAt(i, 4).toString().equals("true")) {
					UsuarioCarta usuarioPanel = new UsuarioCarta(tablaUsuarios.getUsuarioEn(i));
					new UsuarioCartaController(usuarioPanel);
					usuariosCartas.add(usuarioPanel);
				}
			}
			
			
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setBackground(new Color(0, 0, 0, 0)); 
			setOpaque(false); 
			setBorder(new EmptyBorder(10, 20, 10, 20));
			
			JLabel titulo = new JLabel("CUENTAS GUARDADAS");
			titulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
			titulo.setForeground(Colores.ENCABEZADOS_SECUNDARIOS);
			titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(titulo);
			add(Box.createRigidArea(new Dimension(0, 5)));
			
			JLabel subTitulo = new JLabel("Inicia Sesion");
			subTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
			subTitulo.setForeground(Colores.ENCABEZADOS_PRIMARIOS);
			subTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(subTitulo);
			add(Box.createRigidArea(new Dimension(0, 20)));
			
			
			// Aqui se ponen todas los cuardos o cartas, como quieras llamarlo
			for(int i = 0; i < usuariosCartas.size(); i++) 
			{
				UsuarioCarta usuario = usuariosCartas.get(i);
				usuario.setAlignmentX(Component.CENTER_ALIGNMENT); 
				add(usuario);
				add(Box.createRigidArea(new Dimension(0, 12))); 
			}
			
			setVisible(true);
			
		} 
		catch (IOException ex) 
		{
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
	}
}