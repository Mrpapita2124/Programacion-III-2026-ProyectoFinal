package modelos;

import java.sql.Date;

public class EstadoPrestamo {
    private int id_estado_prestamo;
    private int id_prestamo;
    private int quincenasRestantes;
    private double monto_restante;
    private Date fecha_proximo_pago;
    private double monto_proximo_pago;
    private String estado;
    private double dinero_atrasado;

    public EstadoPrestamo(int id_estado_prestamo, int id_prestamo,int quincenasRestantes, double monto_restante,
                          Date fecha_proximo_pago, double monto_proximo_pago,
                          String estado, double dinero_atrasado) {
        this.id_estado_prestamo = id_estado_prestamo;
        this.id_prestamo = id_prestamo;
        this.quincenasRestantes=quincenasRestantes;
        this.monto_restante = monto_restante;
        this.fecha_proximo_pago = fecha_proximo_pago;
        this.monto_proximo_pago = monto_proximo_pago;
        this.estado = estado;
        this.dinero_atrasado = dinero_atrasado;
    }

    public EstadoPrestamo(int id_prestamo,int quincenasRestantes, double monto_restante,
                          Date fecha_proximo_pago, double monto_proximo_pago,
                          String estado, double dinero_atrasado) {
        this.id_prestamo = id_prestamo;
        this.quincenasRestantes=quincenasRestantes;
        this.monto_restante = monto_restante;
        this.fecha_proximo_pago = fecha_proximo_pago;
        this.monto_proximo_pago = monto_proximo_pago;
        this.estado = estado;
        this.dinero_atrasado = dinero_atrasado;
    }

	public int getId_estado_prestamo() {
		return id_estado_prestamo;
	}

	public void setId_estado_prestamo(int id_estado_prestamo) {
		this.id_estado_prestamo = id_estado_prestamo;
	}

	public int getId_prestamo() {
		return id_prestamo;
	}

	public void setId_prestamo(int id_prestamo) {
		this.id_prestamo = id_prestamo;
	}

	public double getMonto_restante() {
		return monto_restante;
	}

	public int getQuincenasRestantes() {
		return quincenasRestantes;
	}

	public void setQuincenasRestantes(int quincenasRestantes) {
		this.quincenasRestantes = quincenasRestantes;
	}

	public void setMonto_restante(double monto_restante) {
		this.monto_restante = monto_restante;
	}

	public Date getFecha_proximo_pago() {
		return fecha_proximo_pago;
	}

	public void setFecha_proximo_pago(Date fecha_proximo_pago) {
		this.fecha_proximo_pago = fecha_proximo_pago;
	}

	public double getMonto_proximo_pago() {
		return monto_proximo_pago;
	}

	public void setMonto_proximo_pago(double monto_proximo_pago) {
		this.monto_proximo_pago = monto_proximo_pago;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getDinero_atrasado() {
		return dinero_atrasado;
	}

	public void setDinero_atrasado(double dinero_atrasado) {
		this.dinero_atrasado = dinero_atrasado;
	}
    
}
