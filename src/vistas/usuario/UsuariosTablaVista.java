package vistas.usuario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Window;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import modelosTabla.ModeloTablaUsuario;
import utilidades.Colores;
import utilidades.Fuentes;

public class UsuariosTablaVista extends JPanel{

    private JTable tabla;
    private JButton btnEditar;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnPdf;

    public UsuariosTablaVista() 
    {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBackground(Colores.FONDO);
        
        tabla = new JTable();
        styleTable();

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelButtons.setOpaque(false);
        panelButtons.setBackground(Colores.CARTA_FONDO);
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnPdf = new JButton("Exportar a PDF");

        crearBoton(btnAgregar, Colores.BOTON_COLOR1, "/img/enter.png");
        crearBoton(btnEditar, Colores.BOTON_COLOR1, "/img/edit.png");
        crearBoton(btnEliminar, Colores.BOTON_COLOR1, "/img/delete.png");
        crearBoton(btnPdf, Colores.BOTON_COLOR1, "/img/exportPDF.png");

        panelButtons.add(btnAgregar);
        panelButtons.add(btnEditar);
        panelButtons.add(btnEliminar);
        panelButtons.add(btnPdf);
        
        add(panelButtons, BorderLayout.NORTH);
    }

    private void crearBoton(JButton boton, Color colorFondo, String ruta) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.BLACK);
        boton.setFont(Fuentes.setFontSegoe(1, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        try 
		{
			Image icono = ImageIO.read(getClass().getResource(ruta));
			icono = icono.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			boton.setIcon(new ImageIcon(icono));
		}
		catch (Exception ex)
		{
			System.out.println("No se pudo poner el icono");
		}
    }

    public void styleTable()
    {
        tabla.setRowHeight(40);
        tabla.setShowGrid(true);
        tabla.setGridColor(Color.WHITE);
        tabla.setBackground(Colores.CARTA_FONDO);
        tabla.setForeground(Colores.ENCABEZADOS_PRIMARIOS);
        tabla.setFont(Fuentes.setFontSegoe(0, 14));
        
        tabla.setSelectionBackground(Colores.AVATAR_GRADIENTE1);
        tabla.setSelectionForeground(Color.WHITE);
        
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Hacer que las líneas interceldas sean más gruesas y visibles
        tabla.setIntercellSpacing(new Dimension(1, 1));
        
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(Colores.LOGIN_PANEL);
        header.setForeground(Color.WHITE);
        header.setFont(Fuentes.setFontSegoe(1, 18));
        header.setPreferredSize(new Dimension(0, 45));
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
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
                
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Colores.CARTA_FONDO);
                    } else {
                        c.setBackground(new Color(25, 55, 85));
                    }
                    c.setForeground(Colores.ENCABEZADOS_PRIMARIOS);
                } 
                else 
                {
                    c.setBackground(Colores.AVATAR_GRADIENTE1);
                    c.setForeground(Color.WHITE);
                }
                
                if (column == 1) {
                    if (!isSelected) {
                        c.setForeground(Colores.ENCABEZADOS_PRIMARIOS);
                    }
                    c.setFont(Fuentes.setFontSegoe(0, 13));
                } else {
                    c.setFont(Fuentes.setFontSegoe(0, 13));
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
    
    public void setTableModel(ModeloTablaUsuario model) {
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