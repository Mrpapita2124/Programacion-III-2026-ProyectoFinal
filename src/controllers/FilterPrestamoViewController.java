package controllers;



import java.util.ArrayList;
import java.util.List;

import modelos.Client;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import repository.ClientRepository;
import repository.EstadoPrestamoRepository;
import repository.PrestamoRepository;
import utils.Session;
import views.FilterView;
import views.VentanaPrincipal;

public class FilterPrestamoViewController {
	
	FilterView filterView;
	ClientRepository clientRepository;
	PrestamoRepository prestamoRepository;
	EstadoPrestamoRepository estadoPrestamoRepository;
	
	public FilterPrestamoViewController(FilterView filterView) 
	{
		estadoPrestamoRepository= new EstadoPrestamoRepository();
		prestamoRepository= new PrestamoRepository();
		clientRepository= new ClientRepository();
		this.filterView=filterView;
		
		refreshFilteredPrestamos(false);
		
		this.filterView.getBuscar().addActionListener(e -> {
			
			List<Prestamo> prestamos = filtrar();
			
			//System.out.println("HI");
			
			if(prestamos.size() == 0)
			{
				System.out.println("* No hay prestamos o prestamos concluidos que cumpla con esos filtros!");
				Session.setPrestamosFiltrados(null);
				
				this.filterView.getVentana().reloadPrestamos(true);
				this.filterView.getVentana().showView(VentanaPrincipal.PRESTAMOS);
				
				return;
			}
			
			Session.setPrestamosFiltrados(prestamos);
			
			this.filterView.getVentana().reloadPrestamos(true);
			this.filterView.getVentana().showView(VentanaPrincipal.PRESTAMOS);
		});
		
		
		this.filterView.getCancelar().addActionListener(e -> {
			Session.setPrestamosFiltrados(prestamoRepository.getAllPrestamosFromUser());
			this.filterView.getVentana().reloadPrestamos(true);
			this.filterView.getVentana().showView(VentanaPrincipal.PRESTAMOS);
		});
	}
	
	public List<Prestamo> filtrar() 
	{
		List<Prestamo> prestamos= prestamoRepository.getAllPrestamosFromUser();
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
		
		if(this.filterView.getExceelente().isSelected()) {
			reputacion.add("excelente");
		}
		if(this.filterView.getBuena().isSelected()) {
			reputacion.add("buena");
		}
		if(this.filterView.getRegular().isSelected()) {
			reputacion.add("regular");
		}
		if(this.filterView.getMala().isSelected()) {
			reputacion.add("mala");
		}
		if(this.filterView.getNoMedida().isSelected()) {
			reputacion.add("no medido");
		}
		if(reputacion.size()==0) {
			return prestamos;
		}
		
		List<Prestamo> prestamosFiltrados=new ArrayList<Prestamo>();
		
		for(Prestamo prestamo: prestamos) {
			for(String tipo: reputacion){
				
				if(prestamoRepository.getPrestamoTieneReputacion(prestamo, tipo)) {
					
					prestamosFiltrados.add(prestamo);
				}
			}
			
		}
		
		return prestamosFiltrados;
	}
	public List<Prestamo> filtroTipoPrestamos(List<Prestamo> prestamos){
		List<Prestamo> prestamosFiltrados=new ArrayList<Prestamo>();
		
			if(this.filterView.getTipo().equals("activos")) {
				//System.out.println("activo");
				for(Prestamo prestamo: prestamos) {
					if(prestamo.getEstado().equals("activo")) {
						prestamosFiltrados.add(prestamo);
					}
				}
			}else if(this.filterView.getTipo().equals("conclusos")){
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
		
			if(this.filterView.getEstado().equals("correcto")) {
				//System.out.println("correcto");
				for(Prestamo prestamo: prestamos) {
					EstadoPrestamo estadoPrestamo= estadoPrestamoRepository.getEstadoPrestamoFromPrestamo(prestamo);
					try {
						if(estadoPrestamo.getEstado().equals("correcto")) {
							prestamosFiltrados.add(prestamo);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
			}else if(this.filterView.getEstado().equals("atrasado")){
				//System.out.println("atrasado");
				for(Prestamo prestamo: prestamos) {
					EstadoPrestamo estadoPrestamo= estadoPrestamoRepository.getEstadoPrestamoFromPrestamo(prestamo);
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
		if((!this.filterView.getMinimo().getText().isEmpty() && !this.filterView.getMinimo().getText().equals("MIN"))&& (this.filterView.getMaximo().getText().isEmpty() || this.filterView.getMaximo().getText().equals("MAX")) ) {
			//System.out.println("minimo");
			for(Prestamo prestamo: prestamos) {
				if(prestamo.getMonto()>=Double.parseDouble(this.filterView.getMinimo().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}else if((this.filterView.getMinimo().getText().isEmpty() || this.filterView.getMinimo().getText().equals("MIN"))&& (!this.filterView.getMaximo().getText().isEmpty() && !this.filterView.getMaximo().getText().equals("MAX")) ){
			//System.out.println("maximo");
			for(Prestamo prestamo: prestamos) {
				if(prestamo.getMonto()<=Double.parseDouble(this.filterView.getMaximo().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}else if((!this.filterView.getMinimo().getText().isEmpty() && !this.filterView.getMinimo().getText().equals("MIN"))&& (!this.filterView.getMaximo().getText().isEmpty() && !this.filterView.getMaximo().getText().equals("MAX")) ){
			//System.out.println("rango");
			for(Prestamo prestamo: prestamos) {
				if(prestamo.getMonto()<=Double.parseDouble(this.filterView.getMaximo().getText())&&prestamo.getMonto()>=Double.parseDouble(this.filterView.getMinimo().getText())) {
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
		if((!this.filterView.getEdadMin().getText().isEmpty() && !this.filterView.getEdadMin().getText().equals("E.MIN")) && (this.filterView.getEdadMax().getText().isEmpty() || this.filterView.getEdadMax().getText().equals("E.MAX")) ){
			//System.out.println("minimo");
			for(Prestamo prestamo: prestamos) {
				Client client = clientRepository.getClientFromPrestamo(prestamo);
				if(client.getEdad()>= Double.parseDouble(this.filterView.getEdadMin().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}else if((!this.filterView.getEdadMax().getText().isEmpty() && !this.filterView.getEdadMax().getText().equals("E.MAX")) && (this.filterView.getEdadMin().getText().isEmpty() || this.filterView.getEdadMin().getText().equals("E.MIN"))){
			//System.out.println("maximo");
			for(Prestamo prestamo: prestamos) {
				Client client = clientRepository.getClientFromPrestamo(prestamo);
				if(client.getEdad()<= Double.parseDouble(this.filterView.getEdadMax().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}else if(!this.filterView.getEdadMin().getText().isEmpty() && !this.filterView.getEdadMin().getText().equals("E.MIN")&& !this.filterView.getEdadMax().getText().isEmpty() && !this.filterView.getEdadMax().getText().equals("E.MAX") ) {
			//System.out.println("aeswiuljhkdfjvvvhbyfijkd eerodujiffwhcbnujiouhkbnerfrdw2huebjigodfw");
			for(Prestamo prestamo: prestamos) {
				Client client = clientRepository.getClientFromPrestamo(prestamo);
				if(client.getEdad()<= Double.parseDouble(this.filterView.getEdadMax().getText())&& client.getEdad()>= Double.parseDouble(this.filterView.getEdadMin().getText())) {
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
		if((!this.filterView.getIngresosMinimos().getText().isEmpty() && !this.filterView.getIngresosMinimos().getText().equals("I.MIN")) && (this.filterView.getIngresosMaximos().getText().isEmpty() || this.filterView.getIngresosMaximos().getText().equals("I.MAX")) ){
			//System.out.println("minimo");
			for(Prestamo prestamo: prestamos) {
				Client client = clientRepository.getClientFromPrestamo(prestamo);
				if(client.getIngresosMensuales()>= Double.parseDouble(this.filterView.getIngresosMinimos().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}else if((!this.filterView.getIngresosMaximos().getText().isEmpty() && !this.filterView.getIngresosMaximos().getText().equals("I.MAX")) && (this.filterView.getIngresosMinimos().getText().isEmpty() || this.filterView.getIngresosMinimos().getText().equals("I.MIN"))){
			//System.out.println("maximo");
			for(Prestamo prestamo: prestamos) {
				Client client = clientRepository.getClientFromPrestamo(prestamo);
				if(client.getIngresosMensuales()<= Double.parseDouble(this.filterView.getIngresosMaximos().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}else if(!this.filterView.getIngresosMinimos().getText().isEmpty() && !this.filterView.getIngresosMinimos().getText().equals("I.MIN")&& !this.filterView.getEdadMax().getText().isEmpty() && !this.filterView.getEdadMax().getText().equals("I.MAX") ) {
			//System.out.println("aeswiuljhkdfjvvvhbyfijkd eerodujiffwhcbnujiouhkbnerfrdw2huebjigodfw");
			for(Prestamo prestamo: prestamos) {
				Client client = clientRepository.getClientFromPrestamo(prestamo);
				if(client.getIngresosMensuales()<= Double.parseDouble(this.filterView.getIngresosMaximos().getText())&& client.getIngresosMensuales()> Double.parseDouble(this.filterView.getIngresosMinimos().getText())) {
					prestamosFiltrados.add(prestamo);
				}
			}
		}
		else{
			
			return prestamos;
		}
		return prestamosFiltrados;
	}
	
	public void refreshFilteredPrestamos(boolean visitarHome)
	{
		Session.setPrestamosFiltrados(filtrar()); 
		
		this.filterView.getVentana().reloadPrestamos(true);
		
		if(visitarHome)
		{
			this.filterView.getVentana().showView(VentanaPrincipal.HOME);
		}
		else
		{			
			this.filterView.getVentana().showView(VentanaPrincipal.PRESTAMOS);
		}
	}
	

}
