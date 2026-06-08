package controllers;



import java.util.ArrayList;
import java.util.List;

import modelos.Cliente;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import repositorios.ClienteRepository;
import repositorios.EstadoPrestamoRepository;
import repositorios.PrestamoRepository;
import utilidades.Sesion;
import vistas.otros.Filtros;
import vistas.otros.VentanaPrincipal;

public class FiltrarPrestamosController {
	
	Filtros vistaFiltro;
	ClienteRepository clienteRepositorio;
	PrestamoRepository prestamoRepositorio;
	EstadoPrestamoRepository estadoPrestamoRepositorio;
	
	public FiltrarPrestamosController(Filtros vistaFiltro) 
	{
		estadoPrestamoRepositorio= new EstadoPrestamoRepository();
		prestamoRepositorio= new PrestamoRepository();
		clienteRepositorio= new ClienteRepository();
		this.vistaFiltro=vistaFiltro;
		
		refrescarPrestamosFiltrados(false);
		
		this.vistaFiltro.getBuscar().addActionListener(e -> {
			
			List<Prestamo> prestamos = filtrar();
			
			//System.out.println("HI");
			
			if(prestamos.size() == 0)
			{
				System.out.println("* No hay prestamos o prestamos concluidos que cumpla con esos filtros!");
				Sesion.setPrestamosFiltrados(null);
				
				this.vistaFiltro.getVentana().recargarPrestamos(true);
				this.vistaFiltro.getVentana().mostrarVista(VentanaPrincipal.PRESTAMOS);
				
				return;
			}
			
			Sesion.setPrestamosFiltrados(prestamos);
			
			this.vistaFiltro.getVentana().recargarPrestamos(true);
			this.vistaFiltro.getVentana().mostrarVista(VentanaPrincipal.PRESTAMOS);
		});
		
		
		this.vistaFiltro.getCancelar().addActionListener(e -> {
			Sesion.setPrestamosFiltrados(prestamoRepositorio.getPrestamosDesdeUsuario());
			this.vistaFiltro.getVentana().recargarPrestamos(true);
			this.vistaFiltro.getVentana().mostrarVista(VentanaPrincipal.PRESTAMOS);
		});
	}
	
	public List<Prestamo> filtrar() 
	{
		List<Prestamo> prestamos= prestamoRepositorio.getPrestamosDesdeUsuario();
		//System.out.println("----------------------");
		
		prestamos = filtroReputacion(prestamos);
		prestamos = filtroTipoPrestamos(prestamos);
		
		prestamos = filtroEstadoPrestamos(prestamos);
		
		prestamos = filtroRangoPrestamos(prestamos);
		prestamos = filtroEdad(prestamos);
		prestamos = filtroIngresos(prestamos);
		
		return prestamos;
	}
	
	
	
	
	public List<Prestamo> filtroReputacion(List<Prestamo> prestamos){
		List<String> reputacion= new ArrayList<String>();
		
		if(this.vistaFiltro.getExcelente().isSelected()) {
			reputacion.add("excelente");
		}
		if(this.vistaFiltro.getBuena().isSelected()) {
			reputacion.add("buena");
		}
		if(this.vistaFiltro.getRegular().isSelected()) {
			reputacion.add("regular");
		}
		if(this.vistaFiltro.getMala().isSelected()) {
			reputacion.add("mala");
		}
		if(this.vistaFiltro.getNoMedido().isSelected()) {
			reputacion.add("no medido");
		}
		if(reputacion.size()==0) {
			return prestamos;
		}
		
		List<Prestamo> prestamosFiltrados=new ArrayList<Prestamo>();
		
		for(Prestamo prestamo: prestamos) {
			for(String tipo: reputacion){
				
				if(prestamoRepositorio.getPrestamoTieneReputacion(prestamo, tipo)) {
					
					prestamosFiltrados.add(prestamo);
				}
			}
			
		}
		
		return prestamosFiltrados;
	}
	public List<Prestamo> filtroTipoPrestamos(List<Prestamo> prestamos){
		List<Prestamo> prestamosFiltrados=new ArrayList<Prestamo>();
		
			if(this.vistaFiltro.getTipo().equals("activos")) {
				//System.out.println("activo");
				for(Prestamo prestamo: prestamos) {
					if(prestamo.getEstado().equals("activo")) {
						prestamosFiltrados.add(prestamo);
					}
				}
			}else if(this.vistaFiltro.getTipo().equals("conclusos")){
				for(Prestamo prestamo: prestamos) {
					if(prestamo.getEstado().equals("concluso")) {
						prestamosFiltrados.add(prestamo);
					}
				}
			}else {
				return prestamos;
			}
		
		return prestamosFiltrados;
	}
	public List<Prestamo> filtroEstadoPrestamos(List<Prestamo> prestamos){
		List<Prestamo> prestamosFiltrados=new ArrayList<Prestamo>();
		
		//System.out.println("\n\nESTADO: " + this.filterView.getEstado());	
		
			if(this.vistaFiltro.getEstado().equals("correcto")) {
				//System.out.println("correcto");
				for(Prestamo prestamo: prestamos) {
					EstadoPrestamo estadoPrestamo= estadoPrestamoRepositorio.getEstadoPrestamoDesdePrestamo(prestamo);
					try {
						if(estadoPrestamo.getEstado().equals("correcto")) {
							prestamosFiltrados.add(prestamo);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
			}else if(this.vistaFiltro.getEstado().equals("atrasado")){
				//System.out.println("atrasado");
				for(Prestamo prestamo: prestamos) {
					EstadoPrestamo estadoPrestamo= estadoPrestamoRepositorio.getEstadoPrestamoDesdePrestamo(prestamo);
					try {
						if(estadoPrestamo.getEstado().equals("atrasado")) {
							prestamosFiltrados.add(prestamo);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
			}else {
				return prestamos;
			}
		
		return prestamosFiltrados;
	}
	
	public List<Prestamo> filtroRangoPrestamos(List<Prestamo> prestamos){
		List<Prestamo> prestamosFiltrados=new ArrayList<Prestamo>();
		if((!this.vistaFiltro.getMinimo().getText().isEmpty() && !this.vistaFiltro.getMinimo().getText().equals("MIN"))&& (this.vistaFiltro.getMaximo().getText().isEmpty() || this.vistaFiltro.getMaximo().getText().equals("MAX")) ) {
			//System.out.println("minimo");
			for(Prestamo prestamo: prestamos) {
				if(prestamo.getMonto()>=Double.parseDouble(this.vistaFiltro.getMinimo().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}else if((this.vistaFiltro.getMinimo().getText().isEmpty() || this.vistaFiltro.getMinimo().getText().equals("MIN"))&& (!this.vistaFiltro.getMaximo().getText().isEmpty() && !this.vistaFiltro.getMaximo().getText().equals("MAX")) ){
			//System.out.println("maximo");
			for(Prestamo prestamo: prestamos) {
				if(prestamo.getMonto()<=Double.parseDouble(this.vistaFiltro.getMaximo().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}else if((!this.vistaFiltro.getMinimo().getText().isEmpty() && !this.vistaFiltro.getMinimo().getText().equals("MIN"))&& (!this.vistaFiltro.getMaximo().getText().isEmpty() && !this.vistaFiltro.getMaximo().getText().equals("MAX")) ){
			//System.out.println("rango");
			for(Prestamo prestamo: prestamos) {
				if(prestamo.getMonto()<=Double.parseDouble(this.vistaFiltro.getMaximo().getText())&&prestamo.getMonto()>=Double.parseDouble(this.vistaFiltro.getMinimo().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}else {
			//System.out.println("ninguno");
			return prestamos;
		}
		return prestamosFiltrados;
	}

	public List<Prestamo> filtroEdad(List<Prestamo> prestamos){
		List<Prestamo> prestamosFiltrados=new ArrayList<Prestamo>();
		if((!this.vistaFiltro.getEdadMin().getText().isEmpty() && !this.vistaFiltro.getEdadMin().getText().equals("E.MIN")) && (this.vistaFiltro.getEdadMax().getText().isEmpty() || this.vistaFiltro.getEdadMax().getText().equals("E.MAX")) ){
			//System.out.println("minimo");
			for(Prestamo prestamo: prestamos) {
				Cliente client = clienteRepositorio.getClienteDePrestamo(prestamo);
				if(client.getEdad()>= Double.parseDouble(this.vistaFiltro.getEdadMin().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}else if((!this.vistaFiltro.getEdadMax().getText().isEmpty() && !this.vistaFiltro.getEdadMax().getText().equals("E.MAX")) && (this.vistaFiltro.getEdadMin().getText().isEmpty() || this.vistaFiltro.getEdadMin().getText().equals("E.MIN"))){
			//System.out.println("maximo");
			for(Prestamo prestamo: prestamos) {
				Cliente client = clienteRepositorio.getClienteDePrestamo(prestamo);
				if(client.getEdad()<= Double.parseDouble(this.vistaFiltro.getEdadMax().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}else if(!this.vistaFiltro.getEdadMin().getText().isEmpty() && !this.vistaFiltro.getEdadMin().getText().equals("E.MIN")&& !this.vistaFiltro.getEdadMax().getText().isEmpty() && !this.vistaFiltro.getEdadMax().getText().equals("E.MAX") ) {
			//System.out.println("aeswiuljhkdfjvvvhbyfijkd eerodujiffwhcbnujiouhkbnerfrdw2huebjigodfw");
			for(Prestamo prestamo: prestamos) {
				Cliente client = clienteRepositorio.getClienteDePrestamo(prestamo);
				if(client.getEdad()<= Double.parseDouble(this.vistaFiltro.getEdadMax().getText())&& client.getEdad()>= Double.parseDouble(this.vistaFiltro.getEdadMin().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}
		else{
			
			return prestamos;
		}
		return prestamosFiltrados;
	}
	
	
	public List<Prestamo> filtroIngresos(List<Prestamo> prestamos){
		List<Prestamo> prestamosFiltrados=new ArrayList<Prestamo>();
		if((!this.vistaFiltro.getIngresosMinimos().getText().isEmpty() && !this.vistaFiltro.getIngresosMinimos().getText().equals("I.MIN")) && (this.vistaFiltro.getIngresosMaximos().getText().isEmpty() || this.vistaFiltro.getIngresosMaximos().getText().equals("I.MAX")) ){
			//System.out.println("minimo");
			for(Prestamo prestamo: prestamos) {
				Cliente client = clienteRepositorio.getClienteDePrestamo(prestamo);
				if(client.getIngresosMensuales()>= Double.parseDouble(this.vistaFiltro.getIngresosMinimos().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}else if((!this.vistaFiltro.getIngresosMaximos().getText().isEmpty() && !this.vistaFiltro.getIngresosMaximos().getText().equals("I.MAX")) && (this.vistaFiltro.getIngresosMinimos().getText().isEmpty() || this.vistaFiltro.getIngresosMinimos().getText().equals("I.MIN"))){
			//System.out.println("maximo");
			for(Prestamo prestamo: prestamos) {
				Cliente client = clienteRepositorio.getClienteDePrestamo(prestamo);
				if(client.getIngresosMensuales()<= Double.parseDouble(this.vistaFiltro.getIngresosMaximos().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}else if(!this.vistaFiltro.getIngresosMinimos().getText().isEmpty() && !this.vistaFiltro.getIngresosMinimos().getText().equals("I.MIN")&& !this.vistaFiltro.getEdadMax().getText().isEmpty() && !this.vistaFiltro.getEdadMax().getText().equals("I.MAX") ) {
			//System.out.println("aeswiuljhkdfjvvvhbyfijkd eerodujiffwhcbnujiouhkbnerfrdw2huebjigodfw");
			for(Prestamo prestamo: prestamos) {
				Cliente client = clienteRepositorio.getClienteDePrestamo(prestamo);
				if(client.getIngresosMensuales()<= Double.parseDouble(this.vistaFiltro.getIngresosMaximos().getText())&& client.getIngresosMensuales()> Double.parseDouble(this.vistaFiltro.getIngresosMinimos().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}
		else{
			
			return prestamos;
		}
		return prestamosFiltrados;
	}
	
	public void refrescarPrestamosFiltrados(boolean visitarHome)
	{
		Sesion.setPrestamosFiltrados(filtrar()); 
		
		this.vistaFiltro.getVentana().recargarPrestamos(true);
		
		if(visitarHome)
		{
			this.vistaFiltro.getVentana().mostrarVista(VentanaPrincipal.HOME);
		}
		else
		{			
			this.vistaFiltro.getVentana().mostrarVista(VentanaPrincipal.PRESTAMOS);
		}
	}
	

}
