package controllers;



import java.util.ArrayList;
import java.util.List;

import modelos.Cliente;
import repositorios.ClienteRepository;
import repositorios.EstadoPrestamoRepository;
import utilidades.Sesion;
import vistas.otros.Filtros;
import vistas.otros.VentanaPrincipal;

public class FiltrarUsuariosController {
	
	Filtros vistaFiltros;
	ClienteRepository clientRepository;
	
	public FiltrarUsuariosController(Filtros vistaFiltros) 
	{
		clientRepository= new ClienteRepository();
		this.vistaFiltros=vistaFiltros;
		
		refrescarClientesFiltrados();
		
		this.vistaFiltros.getBuscar().addActionListener(e -> {
			
			List<Cliente> clientes = filtrar();

			if(clientes.size() == 0)
			{
				System.out.println("* No hay clientes que cumpla con esos filtros!");
				Sesion.setClientesFiltrados(null);
				this.vistaFiltros.getVentana().reload();
				this.vistaFiltros.getVentana().mostrarVista(VentanaPrincipal.HOME);
				return;
			}
			
			Sesion.setClientesFiltrados(clientes);
			
			this.vistaFiltros.getVentana().reload();
			this.vistaFiltros.getVentana().mostrarVista(VentanaPrincipal.HOME);
		});
		
		
		this.vistaFiltros.getCancelar().addActionListener(e -> {
			refrescarClientesFiltrados();
			this.vistaFiltros.getVentana().reload();
			this.vistaFiltros.getVentana().mostrarVista(VentanaPrincipal.HOME);
		});
	}
	
	public List<Cliente> filtrar() 
	{
		List<Cliente> clientesDeRepo = clientRepository.getClients();
		
		
		//System.out.println("-------------------------");
		//System.out.println(clientesFromRepo.size());
		clientesDeRepo = filtroReputacion(clientesDeRepo);
		//System.out.println(clientesFromRepo.size());
		clientesDeRepo = filtroTipoPrestamos(clientesDeRepo);
		//System.out.println(clientesFromRepo.size());
		clientesDeRepo = filtroEstadoPrestamos(clientesDeRepo);
		//System.out.println(clientesFromRepo.size());
		clientesDeRepo = filtroRangoPrestamos(clientesDeRepo);
		//System.out.println(clientesFromRepo.size());
		clientesDeRepo = filtroEdad(clientesDeRepo);
		//System.out.println(clientesFromRepo.size());
		clientesDeRepo = filtroIngresos(clientesDeRepo);
		//System.out.println(clientesFromRepo.size());
		
		return clientesDeRepo;
	}
	public List<Cliente> filtroReputacion(List<Cliente> clientes){
		List<String> reputacion= new ArrayList<String>();
		
		if(this.vistaFiltros.getExceelente().isSelected()) {
			reputacion.add("excelente");
		}
		if(this.vistaFiltros.getBuena().isSelected()) {
			reputacion.add("buena");
		}
		if(this.vistaFiltros.getRegular().isSelected()) {
			reputacion.add("regular");
		}
		if(this.vistaFiltros.getMala().isSelected()) {
			reputacion.add("mala");
		}
		if(this.vistaFiltros.getNoMedida().isSelected()) {
			//System.out.println("qerfoikjgfodfkasdjshgolkjifdadhgoiaddfhngojauñddefghnòikadlfhngo´ljadfqnbv");
			reputacion.add("no medido");
		}
		if(reputacion.size()==0) {
			return clientes;
		}
		
		List<Cliente> clientesFiltrados=new ArrayList<Cliente>();
		
		for(Cliente client: clientes) {
			//System.out.println(client.getReputacion());
			for(String tipo: reputacion){
				
				if(tipo.equals(client.getReputacion())) {
					
					clientesFiltrados.add(client);
				}
			}
			
		}
		
		return clientesFiltrados;
	}
	public List<Cliente> filtroTipoPrestamos(List<Cliente> clientes){
		List<Cliente> clientesFiltrados=new ArrayList<Cliente>();
		
			if(this.vistaFiltros.getTipo().equals("activos")) {
				//System.out.println("activo");
				for(Cliente cliente: clientes) {
					if(clientRepository.clienteTienePrestamoActivo(cliente)) {
						clientesFiltrados.add(cliente);
					}
				}
			}else if(this.vistaFiltros.getTipo().equals("conclusos")){
				//System.out.println("conlcusos");
				for(Cliente client: clientes) {
					if(!clientRepository.clienteTienePrestamoActivo(client)) {
						clientesFiltrados.add(client);
					}
				}
			}else {
				return clientes;
			}
		
			
			
		
		
		return clientesFiltrados;
	}
	public List<Cliente> filtroEstadoPrestamos(List<Cliente> clientes){
		List<Cliente> clientesFiltrados=new ArrayList<Cliente>();
		
			
			if(this.vistaFiltros.getEstado().equals("correcto")) {
				//System.out.println("correcto");
				for(Cliente client: clientes) {
					if(clientRepository.clienteTienePrestamoCorrecto(client)) {
						clientesFiltrados.add(client);
					}
				}
			}else if(this.vistaFiltros.getEstado().equals("atrasado")){
				System.out.println("atrasado");
				for(Cliente client: clientes) {
					if(clientRepository.clienteTienePrestamoAtrasado(client)) {
						clientesFiltrados.add(client);
					}
				}
			}else {
				return clientes;
			}
		
		return clientesFiltrados;
	}
	
	public List<Cliente> filtroRangoPrestamos(List<Cliente> clientes){
		List<Cliente> clientesFiltrados=new ArrayList<Cliente>();
		if((!this.vistaFiltros.getMinimo().getText().isEmpty() && !this.vistaFiltros.getMinimo().getText().equals("MIN"))&& (this.vistaFiltros.getMaximo().getText().isEmpty() || this.vistaFiltros.getMaximo().getText().equals("MAX")) ) {
			//System.out.println("minimo");
			for(Cliente client: clientes) {
				if(clientRepository.clienteTienePrestamoMinimo(client,Double.parseDouble(this.vistaFiltros.getMinimo().getText()))) {
					clientesFiltrados.add(client);
				}
			}
		}else if((this.vistaFiltros.getMinimo().getText().isEmpty() || this.vistaFiltros.getMinimo().getText().equals("MIN"))&& (!this.vistaFiltros.getMaximo().getText().isEmpty() && !this.vistaFiltros.getMaximo().getText().equals("MAX")) ){
			//System.out.println("maximo");
			for(Cliente client: clientes) {
				if(clientRepository.clienteTienePrestamoMaximo(client,Double.parseDouble(this.vistaFiltros.getMaximo().getText()))) {
					clientesFiltrados.add(client);
				}
			}
		}else if((!this.vistaFiltros.getMinimo().getText().isEmpty() && !this.vistaFiltros.getMinimo().getText().equals("MIN"))&& (!this.vistaFiltros.getMaximo().getText().isEmpty() && !this.vistaFiltros.getMaximo().getText().equals("MAX")) ){
			//System.out.println("rango");
			for(Cliente client: clientes) {
				if(clientRepository.clienteTienePrestamoEnRango(client,Double.parseDouble(this.vistaFiltros.getMinimo().getText()),Double.parseDouble(this.vistaFiltros.getMaximo().getText()))) {
					clientesFiltrados.add(client);
				}
			}
		}else {
			//System.out.println("ninguno");
			return clientes;
		}
		return clientesFiltrados;
	}
	
	public List<Cliente> filtroEdad(List<Cliente> clientes){
		List<Cliente> clientesFiltrados=new ArrayList<Cliente>();
		if((!this.vistaFiltros.getEdadMin().getText().isEmpty() && !this.vistaFiltros.getEdadMin().getText().equals("E.MIN")) && (this.vistaFiltros.getEdadMax().getText().isEmpty() || this.vistaFiltros.getEdadMax().getText().equals("E.MAX")) ){
			//System.out.println("minimo");
			for(Cliente client: clientes) {
				if(client.getEdad()>= Double.parseDouble(this.vistaFiltros.getEdadMin().getText())) {
					clientesFiltrados.add(client);
				}
			}
		}else if((!this.vistaFiltros.getEdadMax().getText().isEmpty() && !this.vistaFiltros.getEdadMax().getText().equals("E.MAX")) && (this.vistaFiltros.getEdadMin().getText().isEmpty() || this.vistaFiltros.getEdadMin().getText().equals("E.MIN"))){
			//System.out.println("maximo");
			for(Cliente client: clientes) {
				if(client.getEdad()<= Double.parseDouble(this.vistaFiltros.getEdadMax().getText())) {
					clientesFiltrados.add(client);
				}
			}
		}else if(!this.vistaFiltros.getEdadMin().getText().isEmpty() && !this.vistaFiltros.getEdadMin().getText().equals("E.MIN")&& !this.vistaFiltros.getEdadMax().getText().isEmpty() && !this.vistaFiltros.getEdadMax().getText().equals("E.MAX") ) {
			//System.out.println("aeswiuljhkdfjvvvhbyfijkd eerodujiffwhcbnujiouhkbnerfrdw2huebjigodfw");
			for(Cliente client: clientes) {
				if(client.getEdad()<= Double.parseDouble(this.vistaFiltros.getEdadMax().getText())&& client.getEdad()>= Double.parseDouble(this.vistaFiltros.getEdadMin().getText())) {
					clientesFiltrados.add(client);
				}
			}
		}
		else{
			
			return clientes;
		}
		return clientesFiltrados;
	}
	
	
	public List<Cliente> filtroIngresos(List<Cliente> clientes){
		List<Cliente> clientesFiltrados=new ArrayList<Cliente>();
		if((!this.vistaFiltros.getIngresosMinimos().getText().isEmpty() && !this.vistaFiltros.getIngresosMinimos().getText().equals("I.MIN")) && (this.vistaFiltros.getIngresosMaximos().getText().isEmpty() || this.vistaFiltros.getIngresosMaximos().getText().equals("I.MAX")) ){
			//System.out.println("minimo");
			for(Cliente client: clientes) {
				if(client.getIngresosMensuales() >= Double.parseDouble(this.vistaFiltros.getIngresosMinimos().getText())) {
					clientesFiltrados.add(client);
				}
			}
		}else if((!this.vistaFiltros.getIngresosMaximos().getText().isEmpty() && !this.vistaFiltros.getIngresosMaximos().getText().equals("I.MAX")) && (this.vistaFiltros.getIngresosMinimos().getText().isEmpty() || this.vistaFiltros.getIngresosMinimos().getText().equals("I.MIN"))){
			//System.out.println("maximo");
			for(Cliente client: clientes) {
				if(client.getIngresosMensuales()<= Double.parseDouble(this.vistaFiltros.getIngresosMaximos().getText())) {
					clientesFiltrados.add(client);
				}
			}
		}else if(!this.vistaFiltros.getIngresosMinimos().getText().isEmpty() && !this.vistaFiltros.getIngresosMinimos().getText().equals("I.MIN")&& !this.vistaFiltros.getEdadMax().getText().isEmpty() && !this.vistaFiltros.getEdadMax().getText().equals("I.MAX") ) {
			//System.out.println("aeswiuljhkdfjvvvhbyfijkd eerodujiffwhcbnujiouhkbnerfrdw2huebjigodfw");
			for(Cliente client: clientes) {
				if(client.getIngresosMensuales()<= Double.parseDouble(this.vistaFiltros.getIngresosMaximos().getText())&& client.getIngresosMensuales()>= Double.parseDouble(this.vistaFiltros.getIngresosMinimos().getText())) {
					clientesFiltrados.add(client);
				}
			}
		}
		else{
			
			return clientes;
		}
		return clientesFiltrados;
	}
	
	
	
	public void refrescarClientesFiltrados()
	{
		Sesion.setClientesFiltrados(clientRepository.getClients()); 
		this.vistaFiltros.getVentana().reload();
		this.vistaFiltros.getVentana().mostrarVista(VentanaPrincipal.HOME);
	}
	

}
