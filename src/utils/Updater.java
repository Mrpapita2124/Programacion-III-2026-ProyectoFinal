package utils;



import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
					estado.setDinero_atrasado(estado.getDinero_atrasado()+estado.getMonto_proximo_pago()*(prestamo.getInteres_retraso()/100));
					estado.setQuincenasRestantes(estado.getQuincenasRestantes()-1);
					prestamo.setMonto_total(prestamo.getMonto_total()+prestamo.getMonto_quincenal()*(prestamo.getInteres_retraso()/100));
					estadoRepository.update(estado);
					prestamoRepository.update(prestamo);
					fecha = fecha.plusDays(15);
					
				}while(fecha.isBefore(hoy));
			}
		}
	}
}
