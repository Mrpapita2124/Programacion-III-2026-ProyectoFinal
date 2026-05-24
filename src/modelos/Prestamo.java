package modelos;

public class Prestamo {
    private int id_prestamo;
    private int id_usuario;
    private int id_cliente;
    private String estado; // "activo", "concluso", "cancelado"
    private double monto;
    private int numero_quincenas;
    private double monto_quincenal;
    private double monto_total;
    private double interes;
    private double interes_retraso;
    private java.sql.Date fecha;
    private String nombre;
    private String apellido;
    private String ine;

    // Constructor con id_prestamo
    public Prestamo(int id_prestamo, int id_usuario, int id_cliente, String estado,
                    double monto, int numero_quincenas, double monto_quincenal,
                    double monto_total, double interes, double interes_retraso,
                    java.sql.Date fecha, String nombre, String apellido, String ine) {
        this.id_prestamo = id_prestamo;
        this.id_usuario = id_usuario;
        this.id_cliente = id_cliente;
        this.estado = estado;
        this.monto = monto;
        this.numero_quincenas = numero_quincenas;
        this.monto_quincenal = monto_quincenal;
        this.monto_total = monto_total;
        this.interes = interes;
        this.interes_retraso = interes_retraso;
        this.fecha = fecha;
        this.nombre=nombre;
        this.apellido=apellido;
        this.ine=ine;
    }

    // Constructor sin id_prestamo (para inserts donde el ID lo genera la BD)
    public Prestamo(int id_usuario, int id_cliente, String estado,
                    double monto, int numero_quincenas, double monto_quincenal,
                    double monto_total, double interes, double interes_retraso,
                    java.sql.Date fecha) {
        this.id_usuario = id_usuario;
        this.id_cliente = id_cliente;
        this.estado = estado;
        this.monto = monto;
        this.numero_quincenas = numero_quincenas;
        this.monto_quincenal = monto_quincenal;
        this.monto_total = monto_total;
        this.interes = interes;
        this.interes_retraso = interes_retraso;
        this.fecha = fecha;
        
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getIne() {
		return ine;
	}

	public void setIne(String ine) {
		this.ine = ine;
	}

	public int getId_prestamo() {
		return id_prestamo;
	}

	public void setId_prestamo(int id_prestamo) {
		this.id_prestamo = id_prestamo;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public int getNumero_quincenas() {
		return numero_quincenas;
	}

	public void setNumero_quincenas(int numero_quincenas) {
		this.numero_quincenas = numero_quincenas;
	}

	public double getMonto_quincenal() {
		return monto_quincenal;
	}

	public void setMonto_quincenal(double monto_quincenal) {
		this.monto_quincenal = monto_quincenal;
	}

	public double getMonto_total() {
		return monto_total;
	}

	public void setMonto_total(double monto_total) {
		this.monto_total = monto_total;
	}

	public double getInteres() {
		return interes;
	}

	public void setInteres(double interes) {
		this.interes = interes;
	}

	public double getInteres_retraso() {
		return interes_retraso;
	}

	public void setInteres_retraso(double interes_retraso) {
		this.interes_retraso = interes_retraso;
	}

	public java.sql.Date getFecha() {
		return fecha;
	}

	public void setFecha(java.sql.Date fecha) {
		this.fecha = fecha;
	}

    
 }
