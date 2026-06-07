package modelos;

public class Cliente {
	private int idCliente;
	private int idUsuario;
	private String nombre;
	private String apellido;
	private int edad;
	private String ineDireccion;
	private String domicilio;
	
	private String comprobanteDomicilio;
	private String celular;
	private String correoElectronico;
	private String empleo;
	private String domicilioEmpleo;
	private String telfEmpleo;
	private double ingresosMensuales;
	private String banco;
	private String cuentaBancaria;
	private String curp;
	private String reputacion;
	
	
	

	

	
	public Cliente(int idCliente, int idUsuario, String nombre, String apellido, int edad, String ineDireccion,
			String domimcilio, String comprobanteDomimcilio, String celular, String correoElectronico, String empleo,
			String domicilioEmpleo, String telfEmpleo, double ingresosMensuales, String banco, String cuentaBancaria,
			String curp, String reputacion) {
		super();
		this.idCliente = idCliente;
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.ineDireccion = ineDireccion;
		this.domicilio = domimcilio;
		this.comprobanteDomicilio = comprobanteDomimcilio;
		this.celular = celular;
		this.correoElectronico = correoElectronico;
		this.empleo = empleo;
		this.domicilioEmpleo = domicilioEmpleo;
		this.telfEmpleo = telfEmpleo;
		this.ingresosMensuales = ingresosMensuales;
		this.banco = banco;
		this.cuentaBancaria = cuentaBancaria;
		this.curp = curp;
		this.reputacion = reputacion;
	}
	public Cliente(int idCliente, int idUsuario, String nombre, String apellido, int edad, String ineDireccion,
			String domimcilio, String comprobanteDomimcilio, String celular, String correoElectronico, String empleo,
			String domicilioEmpleo, String telfEmpleo, double ingresosMensuales, String banco, String cuentaBancaria,
			String curp) {
		super();
		this.idCliente = idCliente;
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.ineDireccion = ineDireccion;
		this.domicilio = domimcilio;
		this.comprobanteDomicilio = comprobanteDomimcilio;
		this.celular = celular;
		this.correoElectronico = correoElectronico;
		this.empleo = empleo;
		this.domicilioEmpleo = domicilioEmpleo;
		this.telfEmpleo = telfEmpleo;
		this.ingresosMensuales = ingresosMensuales;
		this.banco = banco;
		this.cuentaBancaria = cuentaBancaria;
		this.curp = curp;
		
	}
	
	public Cliente(int idUsuario, String nombre, String apellido, int edad, String ineDireccion, String domimcilio,
			String comprobanteDomimcilio, String celular, String correoElectronico, String empleo,
			String domicilioEmpleo, String telfEmpleo, double ingresosMensuales, String banco, String cuenta_bancaria,
			String curp, String reputacion) {
		
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.ineDireccion = ineDireccion;
		this.domicilio = domimcilio;
		this.comprobanteDomicilio = comprobanteDomimcilio;
		this.celular = celular;
		this.correoElectronico = correoElectronico;
		this.empleo = empleo;
		this.domicilioEmpleo = domicilioEmpleo;
		this.telfEmpleo = telfEmpleo;
		this.ingresosMensuales = ingresosMensuales;
		this.banco = banco;
		this.cuentaBancaria = cuenta_bancaria;
		this.curp = curp;
		this.reputacion = reputacion;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getTelfEmpleo() {
		return telfEmpleo;
	}
	public void setTelfEmpleo(String telfEmpleo) {
		this.telfEmpleo = telfEmpleo;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUser) {
		this.idUsuario = idUser;
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
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domimcilio) {
		this.domicilio = domimcilio;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public String getEmpleo() {
		return empleo;
	}
	public void setEmpleo(String empleo) {
		this.empleo = empleo;
	}
	public String getDomicilioEmpleo() {
		return domicilioEmpleo;
	}
	public void setDomicilioEmpleo(String domicilioEmpleo) {
		this.domicilioEmpleo = domicilioEmpleo;
	}
	public double getIngresosMensuales() {
		return ingresosMensuales;
	}
	public void setIngresosMensuales(double ingresosMensualess) {
		this.ingresosMensuales = ingresosMensualess;
	}
	public String getCuentaBancaria() {
		return cuentaBancaria;
	}
	public void setCuentaBancaria(String cuenta_bancaria) {
		this.cuentaBancaria = cuenta_bancaria;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getReputacion() {
		return reputacion;
	}
	public void setReputacion(String reputacion) {
		this.reputacion = reputacion;
	}

	public String getIneDireccion() {
		return ineDireccion;
	}

	public void setIneDireccion(String ineDireccion) {
		this.ineDireccion = ineDireccion;
	}

	public String getComprobanteDomicilio() {
		return comprobanteDomicilio;
	}

	public void setComprobanteDomicilio(String comprobanteDomimcilio) {
		this.comprobanteDomicilio = comprobanteDomimcilio;
	}
	
	
}
