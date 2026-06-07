package modelos;

public class Prestamo {
    private int idPrestamo;
    private int idUsuario;
    private int idCliente;
    private String estado; // "activo", "concluso", "cancelado"
    private double monto;
    private int numeroQuincenas;
    private double montoQuincenal;
    private double montoTotal;
    private double interes;
    private double interes_retraso;
    private java.sql.Date fecha;
    private String nombre;
    private String apellido;
    private String ine;

    // Constructor con id_prestamo
    public Prestamo(int idPrestamo, int idUsuario, int idCliente, String estado,
                    double monto, int numeroQuincenas, double monto_quincenal,
                    double monto_total, double interes, double interesRetraso,
                    java.sql.Date fecha, String nombre, String apellido, String ine) {
        this.idPrestamo = idPrestamo;
        this.idUsuario = idUsuario;
        this.idCliente = idCliente;
        this.estado = estado;
        this.monto = monto;
        this.numeroQuincenas = numeroQuincenas;
        this.montoQuincenal = monto_quincenal;
        this.montoTotal = monto_total;
        this.interes = interes;
        this.interes_retraso = interesRetraso;
        this.fecha = fecha;
        this.nombre=nombre;
        this.apellido=apellido;
        this.ine=ine;
    }

    // Constructor sin id_prestamo (para inserts donde el ID lo genera la BD)
    public Prestamo(int idUsuario, int idCliente, String estado,
            	double monto, int numeroQuincenas, double monto_quincenal,
            	double monto_total, double interes, double interesRetraso,
            	java.sql.Date fecha) {
    	 this.idUsuario = idUsuario;
         this.idCliente = idCliente;
         this.estado = estado;
         this.monto = monto;
         this.numeroQuincenas = numeroQuincenas;
         this.montoQuincenal = monto_quincenal;
         this.montoTotal = monto_total;
         this.interes = interes;
         this.interes_retraso = interesRetraso;
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

	public int getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
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

	public int getNumeroQuincenas() {
		return numeroQuincenas;
	}

	public void setNumeroQuincenas(int numeroQuincenas) {
		this.numeroQuincenas = numeroQuincenas;
	}

	public double getMontoQuincenal() {
		return montoQuincenal;
	}

	public void setMontoQuincenal(double montoQuincenal) {
		this.montoQuincenal = montoQuincenal;
	}

	public double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public double getInteres() {
		return interes;
	}

	public void setInteres(double interes) {
		this.interes = interes;
	}

	public double getInteresRetraso() {
		return interes_retraso;
	}

	public void setInteresRetraso(double interesRetraso) {
		this.interes_retraso = interesRetraso;
	}

	public java.sql.Date getFecha() {
		return fecha;
	}

	public void setFecha(java.sql.Date fecha) {
		this.fecha = fecha;
	}

    
 }
