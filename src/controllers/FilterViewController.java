package controllers;



import java.util.ArrayList;
import java.util.List;

import modelos.Client;
import repository.ClientRepository;
import repository.EstadoPrestamoRepository;
import utils.Session;
import views.FilterView;
import views.VentanaPrincipal;

public class FilterViewController {
	
	FilterView filterView;
	ClientRepository clientRepository;
	
	public FilterViewController(FilterView filterView) 
	{
		clientRepository= new ClientRepository();
		this.filterView=filterView;
		
		refreshFilteredClientes();
		
		this.filterView.getBuscar().addActionListener(e -> {
			
			List<Client> clients = filtrar();

			if(clients.size() == 0)
			{
				System.out.println("* No hay clientes que cumpla con esos filtros!");
				Session.setClientesFiltrados(null);
				this.filterView.getVentana().reload();
				this.filterView.getVentana().showView(VentanaPrincipal.HOME);
				return;
			}
			
			Session.setClientesFiltrados(clients);
			
			this.filterView.getVentana().reload();
			this.filterView.getVentana().showView(VentanaPrincipal.HOME);
		});
		
		
		this.filterView.getCancelar().addActionListener(e -> {
			
			this.filterView.getVentana().reload();
			this.filterView.getVentana().showView(VentanaPrincipal.HOME);
		});
	}
	
	public List<Client> filtrar() 
	{
		List<Client> clientesFromRepo = clientRepository.getClients();
		System.out.println("-------------------------");
		
		clientesFromRepo = filtroReputacion(clientesFromRepo);
		clientesFromRepo = filtroTipoPrestamos(clientesFromRepo);
		clientesFromRepo = filtroEstadoPrestamos(clientesFromRepo);
		clientesFromRepo = filtroRangoPrestamos(clientesFromRepo);
		
		return clientesFromRepo;
	}
	public List<Client> filtroReputacion(List<Client> clients){
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
			//System.out.println("qerfoikjgfodfkasdjshgolkjifdadhgoiaddfhngojauñddefghnòikadlfhngo´ljadfqnbv");
			reputacion.add("no medido");
		}
		if(reputacion.size()==0) {
			return clients;
		}
		
		List<Client> clientesFiltrados=new ArrayList<Client>();
		
		for(Client client: clients) {
			//System.out.println(client.getReputacion());
			for(String tipo: reputacion){
				
				if(tipo.equals(client.getReputacion())) {
					
					clientesFiltrados.add(client);
				}
			}
			
		}
		
		return clientesFiltrados;
	}
	public List<Client> filtroTipoPrestamos(List<Client> clients){
		List<Client> clientesFiltrados=new ArrayList<Client>();
		
			if(this.filterView.getTipo().equals("activos")) {
				//System.out.println("activo");
				for(Client client: clients) {
					if(clientRepository.clientHasPrestamoActivo(client)) {
						clientesFiltrados.add(client);
					}
				}
			}else if(this.filterView.getTipo().equals("conclusos")){
				//System.out.println("conlcusos");
				for(Client client: clients) {
					if(!clientRepository.clientHasPrestamoActivo(client)) {
						clientesFiltrados.add(client);
					}
				}
			}else {
				return clients;
			}
		
			
			
		
		
		return clientesFiltrados;
	}
	public List<Client> filtroEstadoPrestamos(List<Client> clients){
		List<Client> clientesFiltrados=new ArrayList<Client>();
		
			
			if(this.filterView.getEstado().equals("correcto")) {
				for(Client client: clients) {
					if(clientRepository.clientHasPrestamoCorrecto(client)) {
						clientesFiltrados.add(client);
					}
				}
			}else if(this.filterView.getEstado().equals("atrasado")){
				for(Client client: clients) {
					if(!clientRepository.clientHasPrestamoCorrecto(client)) {
						clientesFiltrados.add(client);
					}
				}
			}else {
				return clients;
			}
		
		return clientesFiltrados;
	}
	
	public List<Client> filtroRangoPrestamos(List<Client> clients){
		List<Client> clientesFiltrados=new ArrayList<Client>();
		if((!this.filterView.getMinimo().getText().isEmpty() && !this.filterView.getMinimo().getText().equals("MIN"))&& (this.filterView.getMaximo().getText().isEmpty() || this.filterView.getMaximo().getText().equals("MAX")) ) {
			//System.out.println("minimo");
			for(Client client: clients) {
				if(clientRepository.clientHasPrestamoMinimo(client,Double.parseDouble(this.filterView.getMinimo().getText()))) {
					clientesFiltrados.add(client);
				}
			}
		}else if((this.filterView.getMinimo().getText().isEmpty() || this.filterView.getMinimo().getText().equals("MIN"))&& (!this.filterView.getMaximo().getText().isEmpty() && !this.filterView.getMaximo().getText().equals("MAX")) ){
			//System.out.println("maximo");
			for(Client client: clients) {
				if(clientRepository.clientHasPrestamoMaximo(client,Double.parseDouble(this.filterView.getMaximo().getText()))) {
					clientesFiltrados.add(client);
				}
			}
		}else if((!this.filterView.getMinimo().getText().isEmpty() && !this.filterView.getMinimo().getText().equals("MIN"))&& (!this.filterView.getMaximo().getText().isEmpty() && !this.filterView.getMaximo().getText().equals("MAX")) ){
			//System.out.println("rango");
			for(Client client: clients) {
				if(clientRepository.clientHasPrestamoInRange(client,Double.parseDouble(this.filterView.getMinimo().getText()),Double.parseDouble(this.filterView.getMaximo().getText()))) {
					clientesFiltrados.add(client);
				}
			}
		}else {
			//System.out.println("ninguno");
			return clients;
		}
		return clientesFiltrados;
	}
	
	
	public void refreshFilteredClientes()
	{
		// Esto se hace antes de entrar a ventana principal para asegurar que salgan todos los clientes pq no hay filters
		Session.setClientesFiltrados(filtrar()); 
		this.filterView.getVentana().reload();
		this.filterView.getVentana().showView(VentanaPrincipal.HOME);
	}
	

}
