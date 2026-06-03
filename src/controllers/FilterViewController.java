package controllers;



import java.util.ArrayList;
import java.util.List;

import modelos.Client;
import repository.ClientRepository;
import repository.EstadoPrestamoRepository;
import views.FilterView;
import views.VentanaPrincipal;

public class FilterViewController {
	FilterView filterView;
	ClientRepository clientRepository;
	public FilterViewController(FilterView filterView) {
		this.filterView=filterView;
		clientRepository= new ClientRepository();
		this.filterView.getBuscar().addActionListener(e -> {
			List<Client> clients=filtrar();
			// La lista es la de clientes lennycollinsmanzarias
		});
		this.filterView.getCancelar().addActionListener(e -> {
			this.filterView.getVentana().showView(VentanaPrincipal.HOME);
		});
	}
	public List<Client> filtrar() {
		List<Client> clients= clientRepository.getClients();
		System.out.println("-------------------------");
		clients=filtroReputacion(clients);
		System.out.println(clients.size());
		clients=filtroTipoPrestamos(clients);
		System.out.println(clients.size());
		clients=filtroEstadoPrestamos(clients);
		System.out.println(clients.size());
		clients=filtroRangoPrestamos(clients);
		System.out.println(clients.size());
		return clients;
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
			System.out.println("qerfoikjgfodfkasdjshgolkjifdadhgoiaddfhngojauñddefghnòikadlfhngo´ljadfqnbv");
			reputacion.add("no medido");
		}
		if(reputacion.size()==0) {
			return clients;
		}
		List<Client> clientesFiltrados=new ArrayList<Client>();
		for(Client client: clients) {
			System.out.println(client.getReputacion());
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
				System.out.println("activo");
				for(Client client: clients) {
					if(clientRepository.clientHasPrestamoActivo(client)) {
						clientesFiltrados.add(client);
					}
				}
			}else if(this.filterView.getTipo().equals("conclusos")){
				System.out.println("activo");
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
			System.out.println("minimo");
			for(Client client: clients) {
				if(clientRepository.clientHasPrestamoMinimo(client,Double.parseDouble(this.filterView.getMinimo().getText()))) {
					clientesFiltrados.add(client);
				}
			}
		}else if((this.filterView.getMinimo().getText().isEmpty() || this.filterView.getMinimo().getText().equals("MIN"))&& (!this.filterView.getMaximo().getText().isEmpty() && !this.filterView.getMaximo().getText().equals("MAX")) ){
			System.out.println("maximo");
			for(Client client: clients) {
				if(clientRepository.clientHasPrestamoMaximo(client,Double.parseDouble(this.filterView.getMaximo().getText()))) {
					clientesFiltrados.add(client);
				}
			}
		}else if((!this.filterView.getMinimo().getText().isEmpty() && !this.filterView.getMinimo().getText().equals("MIN"))&& (!this.filterView.getMaximo().getText().isEmpty() && !this.filterView.getMaximo().getText().equals("MAX")) ){
			System.out.println("rango");
			for(Client client: clients) {
				if(clientRepository.clientHasPrestamoInRange(client,Double.parseDouble(this.filterView.getMinimo().getText()),Double.parseDouble(this.filterView.getMaximo().getText()))) {
					clientesFiltrados.add(client);
				}
			}
		}else {
			System.out.println("ninguno");
			return clients;
		}
		return clientesFiltrados;
	}
}
