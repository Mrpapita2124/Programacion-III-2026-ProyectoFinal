package modelos;

import java.sql.Date;

public class EstadoPrestamo {
    private int idEstadoPrestamo;
    private int idPrestamo;
    private int quincenasRestantes;
    private double montoRestante;
    private Date fechaProximoPago;
    private double montoProximoPago;
    private String estado;
    private double dineroAtrasado;

    public EstadoPrestamo(int idEstadoPrestamo, int idPrestamo,int quincenasRestantes, double montoRestante,
                          Date fechaProximoPago, double montoProximoPago,
                          String estado, double dineroAtrasado) {
        this.idEstadoPrestamo = idEstadoPrestamo;
        this.idPrestamo = idPrestamo;
        this.quincenasRestantes=quincenasRestantes;
        this.montoRestante = montoRestante;
        this.fechaProximoPago = fechaProximoPago;
        this.montoProximoPago = montoProximoPago;
        this.estado = estado;
        this.dineroAtrasado = dineroAtrasado;
    }

    public EstadoPrestamo(int idPrestamo,int quincenasRestantes, double montoRestante,
            Date fechaProximoPago, double montoProximoPago,
            String estado, double dineroAtrasado) {
    	this.idPrestamo = idPrestamo;
        this.quincenasRestantes=quincenasRestantes;
        this.montoRestante = montoRestante;
        this.fechaProximoPago = fechaProximoPago;
        this.montoProximoPago = montoProximoPago;
        this.estado = estado;
        this.dineroAtrasado = dineroAtrasado;
    }

	public int getIdEstadoPrestamo() {
		return idEstadoPrestamo;
	}

	public void setIdEstadoPrestamo(int idEstadoPrestamo) {
		this.idEstadoPrestamo = idEstadoPrestamo;
	}

	public int getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public double getMontoRestante() {
		return montoRestante;
	}

	public int getQuincenasRestantes() {
		return quincenasRestantes;
	}

	public void setQuincenasRestantes(int quincenasRestantes) {
		this.quincenasRestantes = quincenasRestantes;
	}

	public void setMontoRestante(double montoRestante) {
		this.montoRestante = montoRestante;
	}

	public Date getFechaProximoPago() {
		return fechaProximoPago;
	}

	public void setFechaProximoPago(Date fechaProximoPago) {
		this.fechaProximoPago = fechaProximoPago;
	}

	public double getMontoProximoPago() {
		return montoProximoPago;
	}

	public void setMontoProximoPago(double montoProximoPago) {
		this.montoProximoPago = montoProximoPago;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getDineroAtrasado() {
		return dineroAtrasado;
	}

	public void setDineroAtrasado(double dineroAtrasado) {
		this.dineroAtrasado = dineroAtrasado;
	}
    
}
