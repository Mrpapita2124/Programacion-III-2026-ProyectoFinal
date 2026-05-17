package tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import modelos.User;


public class UserTableModel extends AbstractTableModel{

	private List<User> users;
	
	private final String[] columns = {
		"Nombre",
		"Apellido",
		"Email",
		
	};
	
	public UserTableModel(List<User> users) 
	{
		this.users = users;
	}
	
	
	public void removeRow(int row) {
		users.remove(row);
		fireTableRowsDeleted(row, row);
	}
	
	public void addRow(User user) {
		int row = users.size();
		users.add(user);
		fireTableRowsInserted(row, row);
	}
	
	public void updateRow(int row, User user) {
		users.set(row, user);
		fireTableRowsUpdated(row, row);
	}
	
	
	public void updateUser(User updatedUser) {
	    for (int i = 0; i < users.size(); i++) {
	        if (users.get(i).getId() == updatedUser.getId()) {
	            users.set(i, updatedUser);
	            fireTableRowsUpdated(i, i);
	            return;
	        }
	    }
	}
	
	@Override
	public int getRowCount() 
	{
		return users.size();
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
		
		User user = users.get(rowIndex);
		
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
	public User getUserAt(int row) {
		return users.get(row);
	}
	public void setUsers(List<User> users) {
		this.users=users;
		fireTableDataChanged();
	}
	public int getNumGuardar() {
		int guardados=0;
		for(int i=0;i<users.size();i++) {
			if(users.get(i).isGuardar()) {
				guardados++;
			}
		}
		return guardados;
	}
}