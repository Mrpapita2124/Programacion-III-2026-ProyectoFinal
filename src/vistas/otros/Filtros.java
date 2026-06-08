package vistas.otros;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import utilidades.Colores;
import utilidades.Fuentes;
import utilidades.GradientBackground;

public class Filtros extends GradientBackground {
    
    private JCheckBox excelente = new JCheckBox("Excelente");
    private JCheckBox buena = new JCheckBox("Buena");
    private JCheckBox regular = new JCheckBox("Regular");
    private JCheckBox mala = new JCheckBox("Mala");
    private JCheckBox noMedido = new JCheckBox("No medido");
    
    private ButtonGroup grupoTipoPrestamo;
    private ButtonGroup grupoEstadoPrestamo;
    
    private JTextField minimo;
    private JTextField maximo;
    
    private JTextField edadMinima;
    private JTextField edadMaxima;
    private JTextField ingresosMinimos;
    private JTextField ingresosMaximos;
    
    private JButton botonBuscar = new JButton("Buscar");
    private JButton botonCancelar = new JButton("Restablecer");
    private String tipo = " ";
    private String estado = " ";
    private VentanaPrincipal ventana;
    
    private Font fuenteTitulo = Fuentes.setFontSegoe(1, 18);
    private Font fuenteEtiqueta = Fuentes.setFontSegoe(0, 14);
    
    public Filtros(VentanaPrincipal ventana) {
        this.ventana = ventana;
        
        setOpaque(true);
        setBorder(new EmptyBorder(30, 40, 40, 40));
        
        excelente.setOpaque(false);
        excelente.setForeground(Color.WHITE);
        excelente.setFont(fuenteEtiqueta);
        excelente.setFocusPainted(false);
        
        buena.setOpaque(false);
        buena.setForeground(Color.WHITE);
        buena.setFont(fuenteEtiqueta);
        buena.setFocusPainted(false);
        
        regular.setOpaque(false);
        regular.setForeground(Color.WHITE);
        regular.setFont(fuenteEtiqueta);
        regular.setFocusPainted(false);
        
        mala.setOpaque(false);
        mala.setForeground(Color.WHITE);
        mala.setFont(fuenteEtiqueta);
        mala.setFocusPainted(false);
        
        noMedido.setOpaque(false);
        noMedido.setForeground(Color.WHITE);
        noMedido.setFont(fuenteEtiqueta);
        noMedido.setFocusPainted(false);
        
        
        grupoTipoPrestamo = new ButtonGroup();
        grupoEstadoPrestamo = new ButtonGroup();
        
        /*
        minimo = crearTextField("MIN", "minimo");
        maximo = crearTextField("MAX", "maximo");
        edadMinima = crearTextField("E.MIN", "e.min");
        edadMaxima = crearTextField("E.MAX", "e.max");
        ingresosMinimos = crearTextField("I.MIN", "i.min");
        ingresosMaximos = crearTextField("I.MAX", "i.max");
        */
        
        minimo = crearTextField("", "");
        maximo = crearTextField("", "");
        edadMinima = crearTextField("", "");
        edadMaxima = crearTextField("", "");
        ingresosMinimos = crearTextField("", "");
        ingresosMaximos = crearTextField("", "");
        
        crearBoton(botonBuscar, Colores.BOTON_COLOR1);
        crearBoton(botonCancelar, new Color(100, 100, 100));
        
        try {
            botonBuscar.setIcon(escalarImagen("src\\img\\buscar_Filter.png", 20, 20));
            botonCancelar.setIcon(escalarImagen("src\\img\\cancelar_Filter.png", 20, 20));
        } catch (Exception e) {
        }
        
        setLayout(new GridBagLayout());
        GridBagConstraints restriccionesGridBag = new GridBagConstraints();
        restriccionesGridBag.fill = GridBagConstraints.BOTH;
        restriccionesGridBag.weightx = 1.0;
        restriccionesGridBag.insets = new Insets(10, 15, 10, 15);
        
        restriccionesGridBag.gridx = 0;
        restriccionesGridBag.gridy = 0;
        restriccionesGridBag.weighty = 1.0;
        add(crearPanelReputacion(), restriccionesGridBag);
        
        restriccionesGridBag.gridx = 1;
        restriccionesGridBag.gridy = 0;
        add(crearPanelTipoPrestamo(), restriccionesGridBag);
        
        restriccionesGridBag.gridx = 0;
        restriccionesGridBag.gridy = 1;
        add(crearPanelEstadoPrestamo(), restriccionesGridBag);
        
        restriccionesGridBag.gridx = 1;
        restriccionesGridBag.gridy = 1;
        add(crearPanelMontoPrestamo(), restriccionesGridBag);
        
        restriccionesGridBag.gridx = 0;
        restriccionesGridBag.gridy = 2;
        add(crearPanelEdad(), restriccionesGridBag);
        
        restriccionesGridBag.gridx = 1;
        restriccionesGridBag.gridy = 2;
        add(crearPanelIngresos(), restriccionesGridBag);
        
        restriccionesGridBag.gridx = 0;
        restriccionesGridBag.gridy = 3;
        restriccionesGridBag.gridwidth = 2;
        restriccionesGridBag.weighty = 0.5;
        restriccionesGridBag.insets = new Insets(30, 15, 10, 15);
        add(crearPanelBotones(), restriccionesGridBag);
    }
    
    private JPanel crearPanelReputacion() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(crearBordeModerno("Reputación"));
        
        panel.add(Box.createVerticalStrut(15));
        panel.add(excelente);
        panel.add(Box.createVerticalStrut(8));
        panel.add(buena);
        panel.add(Box.createVerticalStrut(8));
        panel.add(regular);
        panel.add(Box.createVerticalStrut(8));
        panel.add(mala);
        panel.add(Box.createVerticalStrut(8));
        panel.add(noMedido);
        panel.add(Box.createVerticalStrut(15));
        
        return panel;
    }
    
    private JPanel crearPanelTipoPrestamo() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(crearBordeModerno("Tipo de Préstamo"));
        
        JRadioButton activos = new JRadioButton("Activos");
        JRadioButton conclusos = new JRadioButton("Conclusos");
        
        activos.setOpaque(false);
        activos.setForeground(Color.WHITE);
        activos.setFont(fuenteEtiqueta);
        activos.setFocusPainted(false);
        
        conclusos.setOpaque(false);
        conclusos.setForeground(Color.WHITE);
        conclusos.setFont(fuenteEtiqueta);
        conclusos.setFocusPainted(false);
        
        activos.setActionCommand("activos");
        conclusos.setActionCommand("conclusos");
        
        grupoTipoPrestamo.add(activos);
        grupoTipoPrestamo.add(conclusos);
        
        activos.addActionListener(e -> {
            if (tipo.equals("activos")) 
            {
                grupoTipoPrestamo.clearSelection();
                tipo = " ";
            } 
            else 
            {
                tipo = "activos";
            }
        });
        
        conclusos.addActionListener(e -> {
            if (tipo.equals("conclusos")) 
            {
                grupoTipoPrestamo.clearSelection();
                tipo = " ";
            } 
            else 
            {
                tipo = "conclusos";
                grupoEstadoPrestamo.clearSelection();
                estado = " ";
            }
        });
        
        panel.add(Box.createVerticalStrut(25));
        panel.add(activos);
        panel.add(Box.createVerticalStrut(15));
        panel.add(conclusos);
        panel.add(Box.createVerticalStrut(25));
        
        return panel;
    }
    
    private JPanel crearPanelEstadoPrestamo() 
    {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(crearBordeModerno("Estado del Préstamo"));
        
        JRadioButton correcto = new JRadioButton("Correcto");
        JRadioButton atrasado = new JRadioButton("Atrasado");
        
        correcto.setOpaque(false);
        correcto.setForeground(Color.WHITE);
        correcto.setFont(fuenteEtiqueta);
        correcto.setFocusPainted(false);
        
        atrasado.setOpaque(false);
        atrasado.setForeground(Color.WHITE);
        atrasado.setFont(fuenteEtiqueta);
        atrasado.setFocusPainted(false);
        
        correcto.setActionCommand("correcto");
        atrasado.setActionCommand("atrasado");
        
        grupoEstadoPrestamo.add(correcto);
        grupoEstadoPrestamo.add(atrasado);
        
        correcto.addActionListener(e -> {
            if (estado.equals("correcto")) 
            {
                grupoEstadoPrestamo.clearSelection();
                estado = " ";
            } 
            else 
            {
                estado = "correcto";
            }
            
            if (tipo.equals("conclusos")) 
            {
                grupoTipoPrestamo.clearSelection();
                tipo = " ";
            }
        });
        
        atrasado.addActionListener(e -> {
            if (estado.equals("atrasado")) 
            {
                grupoEstadoPrestamo.clearSelection();
                estado = " ";
            } 
            else 
            {
                estado = "atrasado";
            }
            if (tipo.equals("conclusos")) 
            {
                grupoTipoPrestamo.clearSelection();
                tipo = " ";
            }
        });
        
        panel.add(Box.createVerticalStrut(25));
        panel.add(correcto);
        panel.add(Box.createVerticalStrut(15));
        panel.add(atrasado);
        panel.add(Box.createVerticalStrut(25));
        
        return panel;
    }
    
    private JPanel crearPanelMontoPrestamo() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(crearBordeModerno("Monto del Préstamo"));
        
        JPanel panelRango = new JPanel();
        panelRango.setOpaque(false);
        panelRango.setLayout(new BoxLayout(panelRango, BoxLayout.X_AXIS));
        
        JLabel etiquetaDesde = new JLabel("Desde:");
        JLabel etiquetaHasta = new JLabel("Hasta:");
        etiquetaDesde.setFont(fuenteEtiqueta);
        etiquetaHasta.setFont(fuenteEtiqueta);
        etiquetaDesde.setForeground(Color.WHITE);
        etiquetaHasta.setForeground(Color.WHITE);
        
        panelRango.add(etiquetaDesde);
        panelRango.add(Box.createHorizontalStrut(10));
        panelRango.add(minimo);
        panelRango.add(Box.createHorizontalStrut(20));
        panelRango.add(etiquetaHasta);
        panelRango.add(Box.createHorizontalStrut(10));
        panelRango.add(maximo);
        
        panel.add(Box.createVerticalStrut(20));
        panel.add(panelRango);
        panel.add(Box.createVerticalStrut(20));
        
        return panel;
    }
    
    private JPanel crearPanelEdad() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(crearBordeModerno("Búsqueda de Edad"));
        
        JPanel panelRango = new JPanel();
        panelRango.setOpaque(false);
        panelRango.setLayout(new BoxLayout(panelRango, BoxLayout.X_AXIS));
        
        JLabel etiquetaMin = new JLabel("Mín:");
        JLabel etiquetaMax = new JLabel("Máx:");
        etiquetaMin.setFont(fuenteEtiqueta);
        etiquetaMax.setFont(fuenteEtiqueta);
        etiquetaMin.setForeground(Color.WHITE);
        etiquetaMax.setForeground(Color.WHITE);
        
        panelRango.add(etiquetaMin);
        panelRango.add(Box.createHorizontalStrut(10));
        panelRango.add(edadMinima);
        panelRango.add(Box.createHorizontalStrut(20));
        panelRango.add(etiquetaMax);
        panelRango.add(Box.createHorizontalStrut(10));
        panelRango.add(edadMaxima);
        
        panel.add(Box.createVerticalStrut(20));
        panel.add(panelRango);
        panel.add(Box.createVerticalStrut(20));
        
        return panel;
    }
    
    private JPanel crearPanelIngresos() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(crearBordeModerno("Búsqueda de Ingresos"));
        
        JPanel panelRango = new JPanel();
        panelRango.setOpaque(false);
        panelRango.setLayout(new BoxLayout(panelRango, BoxLayout.X_AXIS));
        
        JLabel etiquetaMin = new JLabel("Mín:");
        JLabel etiquetaMax = new JLabel("Máx:");
        etiquetaMin.setFont(fuenteEtiqueta);
        etiquetaMax.setFont(fuenteEtiqueta);
        etiquetaMin.setForeground(Color.WHITE);
        etiquetaMax.setForeground(Color.WHITE);
        
        panelRango.add(etiquetaMin);
        panelRango.add(Box.createHorizontalStrut(10));
        panelRango.add(ingresosMinimos);
        panelRango.add(Box.createHorizontalStrut(20));
        panelRango.add(etiquetaMax);
        panelRango.add(Box.createHorizontalStrut(10));
        panelRango.add(ingresosMaximos);
        
        panel.add(Box.createVerticalStrut(20));
        panel.add(panelRango);
        panel.add(Box.createVerticalStrut(20));
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        
        panel.add(Box.createHorizontalGlue());
        panel.add(botonBuscar);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(botonCancelar);
        panel.add(Box.createHorizontalGlue());
        
        return panel;
    }
    
    private Border crearBordeModerno(String titulo) {
        Border bordeLinea = BorderFactory.createLineBorder(new Color(50, 70, 90), 2);
        Border bordeTitulo = BorderFactory.createTitledBorder(
            bordeLinea,
            titulo,
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.TOP,
            Fuentes.setFontSegoe(1, 16),
            new Color(100, 200, 255)
        );
        return BorderFactory.createCompoundBorder(bordeTitulo, new EmptyBorder(5, 10, 5, 10));
    }
    
    private void crearBoton(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(Fuentes.setFontSegoe(1, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
    
    private JTextField crearTextField(String marcador, String nombre) {
        JTextField campoTexto = new JTextField(marcador, 8);
        campoTexto.setFont(Fuentes.setFontSegoe(0, 14));
        campoTexto.setForeground(Color.WHITE);
        campoTexto.setBackground(new Color(30, 50, 70));
        campoTexto.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 80, 100), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        campoTexto.setHorizontalAlignment(SwingConstants.CENTER);
        campoTexto.setName(nombre);
        
        campoTexto.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campoTexto.getText().equals(marcador)) {
                    campoTexto.setText("");
                    campoTexto.setForeground(Color.WHITE);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campoTexto.getText().isEmpty()) {
                    campoTexto.setText(marcador);
                    campoTexto.setForeground(Color.GRAY);
                }
            }
        });
        
        campoTexto.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char caracter = e.getKeyChar();
                if (!Character.isDigit(caracter) && caracter != '.') {
                    e.consume();
                }
            }
        });
        
        return campoTexto;
    }
    
    private ImageIcon escalarImagen(String direccion, int ancho, int alto) throws Exception {
        ImageIcon iconoOriginal = new ImageIcon(direccion);
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
    
    public JCheckBox getExcelente() { 
        return excelente; 
    }

    public JCheckBox getBuena() { 
        return buena; 
    }

    public JCheckBox getRegular() { 
        return regular; 
    }

    public JCheckBox getMala() { 
        return mala; 
    }

    public JCheckBox getNoMedido() { 
        return noMedido; 
    }

    public String getTipo() { 
        return tipo; 
    }

    public String getEstado() { 
        return estado; 
    }

    public JTextField getMinimo() { 
        return minimo; 
    }

    public JTextField getMaximo() { 
        return maximo; 
    }

    public JTextField getEdadMin() { 
        return edadMinima; 
    }

    public JTextField getEdadMax() { 
        return edadMaxima; 
    }

    public JTextField getIngresosMinimos() { 
        return ingresosMinimos; 
    }

    public JTextField getIngresosMaximos() { 
        return ingresosMaximos; 
    }

    public JButton getBuscar() { 
        return botonBuscar; 
    }

    public JButton getCancelar() { 
        return botonCancelar; 
    }

    public VentanaPrincipal getVentana() { 
        return ventana; 
    }

    public void setTipo(String tipo) { 
        this.tipo = tipo; 
    }

    public void setEstado(String estado) { 
        this.estado = estado; 
    }

    public void setVentana(VentanaPrincipal ventana) { 
        this.ventana = ventana; 
    }
}