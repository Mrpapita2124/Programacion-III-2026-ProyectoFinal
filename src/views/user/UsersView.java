package views.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import config.Config;
import tablemodels.UserTableModel;

public class UsersView extends JPanel{

	private JTable tabla;
	private JButton btnEditar;
	private JButton btnAgregar;
	private JButton btnEliminar;
	
	private JButton btnPdf;


	public UsersView() 
	{
		setLayout(new BorderLayout());
		tabla = new JTable();
		styleTable();

		add(new JScrollPane(tabla), BorderLayout.CENTER);
		
		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnPdf = new JButton("Exportar a PDF");

        panelButtons.add(btnAgregar);
        panelButtons.add(btnEditar);
        panelButtons.add(btnEliminar);

        panelButtons.add(btnPdf);
        
        add(panelButtons, BorderLayout.NORTH);

	}

	public void styleTable()
	{
		tabla.setRowHeight(35);
		tabla.setShowGrid(true);
		tabla.setGridColor(new Color(230, 230, 230));
		tabla.setBackground(Color.WHITE);
		tabla.setForeground(Color.BLACK);
		
		tabla.setSelectionBackground(new Color(52, 152, 219));
		tabla.setSelectionForeground(Color.WHITE);
		
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JTableHeader header = tabla.getTableHeader();
		header.setBackground(new Color(44, 62, 80));
		header.setForeground(Color.WHITE);
		//header.setFont(AppFont.bold());
		header.setPreferredSize(new Dimension(0, 40));
		header.setReorderingAllowed(false);
		
		tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                Component c = super.getTableCellRendererComponent(
                        table,
                        value,
                        isSelected,
                        hasFocus,
                        row,
                        column);
                
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(245, 245, 245));
                    }

                    c.setForeground(Color.BLACK);
                }
				
				if(column == 1) {
					//c.setFont(AppFont.bold());
					if(!isSelected) {
						c.setForeground(new Color(41, 128, 185));
					}
				} else {
					//c.setFont(AppFont.normal());
				}
			
				
				return c;
				
			}
			
		});
		
		
	}
	
	public File selectPdfFile()
	{
		String path = Config.get("users.export.pdf", System.getProperty("user.home"));
		JFileChooser chooser = new JFileChooser(path);
		
		chooser.setSelectedFile(new File("reporte-usuarios.pdf"));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		chooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Documentos PDF", "pdf");
		chooser.addChoosableFileFilter(filter);
		chooser.setFileFilter(filter);
		
		
		int opcion = chooser.showDialog(this, "Exportar PDF de usuarios");
		if(opcion != JFileChooser.APPROVE_OPTION)
		{
			return null;
		}
		
		
		File file = chooser.getSelectedFile();
		Config.set("users.export.pdf", file.getParent());
		
		if(!file.getName().toLowerCase().endsWith(".pdf"))
		{
			file = new File(file.getAbsolutePath() + ".pdf");
		}
		
		return file;
	}
	
	public void setTableModel(UserTableModel model) {
		tabla.setModel(model);
		
		if(tabla.getColumnCount() >= 1) {
			tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
		}
		
		if(tabla.getColumnCount() >= 2) {
			tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
		}
		
		if(tabla.getColumnCount() >= 3) {
			tabla.getColumnModel().getColumn(2).setPreferredWidth(50);
		}
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		
		if(tabla.getColumnCount() >= 1) {
			tabla.getColumnModel().getColumn(0).setCellRenderer(center);
		}
	}
	
	public JTable getTable() {
		return tabla;
	}
	
	public JButton getBtnAdd() {
        return btnAgregar;
    }

    public JButton getBtnEdit() {
        return btnEditar;
    }

    public JButton getBtnDelete() {
        return btnEliminar;
    }
	
    public int getSelectedRow() {
    	return tabla.getSelectedRow();
    }
    
    public JButton getBtnPdf() {
		return btnPdf;
	}

	public void setBtnPdf(JButton btnPdf) {
		this.btnPdf = btnPdf;
	}
	
	
	public int confirmExit() 
	{
	    return JOptionPane.showConfirmDialog(
	        this,
	        "¿Seguro que deseas eliminar el usuario? Se perderán todos los datos",
	        "¿Seguro?",
	        JOptionPane.YES_NO_OPTION
	    );
	}
	
	public Window getWindow() {
		return SwingUtilities.getWindowAncestor(this);
	}
}
