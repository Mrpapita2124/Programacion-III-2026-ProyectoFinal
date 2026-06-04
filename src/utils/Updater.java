package utils;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelos.Client;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import repository.ClientRepository;
import repository.EstadoPrestamoRepository;
import repository.PrestamoRepository;
import repository.UserRepository;

public class Updater {
	EstadoPrestamoRepository estadoRepository;
	PrestamoRepository prestamoRepository;
	UserRepository userRepository;
	ClientRepository clientRepository;
	public Updater(){
		estadoRepository= new EstadoPrestamoRepository();
		prestamoRepository= new PrestamoRepository();
		userRepository=new UserRepository();
		clientRepository=new ClientRepository();
	}
	public void updateEverything() {
		System.out.println("aosdjhoiadsip");
		LocalDate hoy = LocalDate.now(); 
		List<EstadoPrestamo> estados= estadoRepository.getAllEstadoPrestamo();
		for(EstadoPrestamo estado : estados) {
			Prestamo prestamo = prestamoRepository.getPrestamoById(estado.getId_prestamo());
			LocalDate fecha=estado.getFecha_proximo_pago().toLocalDate();
			if(fecha.isBefore(hoy)) {
				estado.setEstado("atrasado");
				do {
					if(estado.getQuincenasRestantes()==0) {
						break;
					}
					estado.setFecha_proximo_pago(java.sql.Date.valueOf(estado.getFecha_proximo_pago().toLocalDate().plusDays(15)));
					estado.setMonto_restante(estado.getMonto_restante()+estado.getMonto_proximo_pago()*(prestamo.getInteres_retraso()/100));
					estado.setDinero_atrasado(estado.getDinero_atrasado()+estado.getMonto_proximo_pago()+estado.getMonto_proximo_pago()*(prestamo.getInteres_retraso()/100));
					estado.setQuincenasRestantes(estado.getQuincenasRestantes()-1);
					prestamo.setMonto_total(prestamo.getMonto_total()+prestamo.getMonto_quincenal()*(prestamo.getInteres_retraso()/100));
					estadoRepository.update(estado);
					prestamoRepository.update(prestamo);
					fecha = fecha.plusDays(15);
					
				}while(fecha.isBefore(hoy));
			}
		}
	}
	public void updateClientsEvaluation() {
		List<Client> clients = clientRepository.getClients();
		System.out.println("ayuda me quiero matar");
		System.out.println("clientes size "+ clients.size());
		if(clients.size() > 0) 
		{
			for(Client client : clients) 
			{
				List<Prestamo> prestamos=prestamoRepository.getClientPrestamos(client);
				System.out.println("preestamos "+ prestamos.size());
				List<Integer> scores= new ArrayList<Integer>();
				double promedio=0;
				
				if(prestamos.size()>0) 
				{
					for(Prestamo prestamo : prestamos) 
					{
						double monto = prestamo.getMonto_quincenal()*prestamo.getNumero_quincenas();
						double montoTotal=prestamo.getMonto_total();
						double diferencia= montoTotal-monto;
						double diferenciaMaxima=(monto*(prestamo.getInteres_retraso()/100));
						
						if(diferencia==0) {
							scores.add(3);
						}else if(diferencia<diferenciaMaxima/3){
							scores.add(2);
						}else if(diferencia<diferenciaMaxima/2){
							scores.add(1);
						}else{
							scores.add(0);
						}
					}
					for(double score: scores) {
						promedio+=score;
					}
					promedio=promedio/scores.size();
					System.out.println("promedio "+ promedio);
					if(promedio<0.3) {
						client.setReputacion("mala");
						System.out.println("1asdddadd");
					}else if(promedio<1.2) {
						client.setReputacion("regular");
						System.out.println("2asdasd");
					}else if(promedio<2.1) {
						client.setReputacion("buena");
						System.out.println("3asdasd");
					}else if(promedio<=3) {
						client.setReputacion("excelente");
						System.out.println("4asdasd");
					}
					clientRepository.update(client);
				}else {
					client.setReputacion("no medido");
					clientRepository.update(client);
				}
			}
				
		}
		
		
	}
}
