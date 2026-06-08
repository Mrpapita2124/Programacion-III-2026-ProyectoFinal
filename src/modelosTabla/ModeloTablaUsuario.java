package modelosTabla;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelos.Usuario;


public class ModeloTablaUsuario extends AbstractTableModel{

	private List<Usuario> usuarios;
	
	private final String[] columns = {
		"Nombre",
		"Apellido",
		"Email",
		
	};
	
	public ModeloTablaUsuario(List<Usuario> usuarios) 
	{
		this.usuarios = usuarios;
	}
	
	
	public void eliminarFila(int fila) {
		usuarios.remove(fila);
		fireTableRowsDeleted(fila, fila);
	}
	
	public void aniadirFila(Usuario usuario) {
		int fila = usuarios.size();
		usuarios.add(usuario);
		fireTableRowsInserted(fila, fila);
	}
	
	public void actualizarFila(int fila, Usuario usuario) {
		usuarios.set(fila, usuario);
		fireTableRowsUpdated(fila, fila);
	}
	
	
	public void actualizarUsuario(Usuario usuarioActualizado) {
	    for (int i = 0; i < usuarios.size(); i++) {
	        if (usuarios.get(i).getId() == usuarioActualizado.getId()) {
	            usuarios.set(i, usuarioActualizado);
	            fireTableRowsUpdated(i, i);
	            return;
	        }
	    }
	}
	
	@Override
	public int getRowCount() 
	{
		return usuarios.size();
	}

	@Override
	public int getColumnCount() 
	{
		return columns.length;
	}
	
	@Override
	public String getColumnName(int column) 
	{
		return columns[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) 
	{
		
		Usuario user = usuarios.get(rowIndex);
		
		switch(columnIndex) {
		case 0:
			return user.getNombre();
		
		case 1:
			return user.getApellido();
			
		case 2:
			return user.getCorreo();
		case 3:
			return user.getFoto();
		case 4:
			return user.isGuardar();
		}
		return null;
		
	}
	public Usuario getUsuarioEn(int row) {
		return usuarios.get(row);
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios=usuarios;
		fireTableDataChanged();
	}
	public int getNumGuardar() {
		int guardados=0;
		for(int i=0;i<usuarios.size();i++) {
			if(usuarios.get(i).isGuardar()) {
				guardados++;
			}
		}
		return guardados;
	}
}