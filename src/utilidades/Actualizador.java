package utilidades;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelos.Cliente;
import modelos.EstadoPrestamo;
import modelos.Prestamo;
import repositorios.ClienteRepository;
import repositorios.EstadoPrestamoRepository;
import repositorios.PrestamoRepository;
import repositorios.UsuarioRepository;

public class Actualizador {
	EstadoPrestamoRepository estadoRepository;
	PrestamoRepository prestamoRepository;
	UsuarioRepository usuarioRepository;
	ClienteRepository clienteRepository;
	public Actualizador(){
		estadoRepository= new EstadoPrestamoRepository();
		prestamoRepository= new PrestamoRepository();
		usuarioRepository=new UsuarioRepository();
		clienteRepository=new ClienteRepository();
	}
	public void actualizarPrestamos() {
		
		LocalDate hoy = LocalDate.now(); 
		List<EstadoPrestamo> estados= estadoRepository.getTodosLosEstadoPrestamo();
		for(EstadoPrestamo estado : estados) {
			Prestamo prestamo = prestamoRepository.getPrestamoPorId(estado.getIdPrestamo());
			LocalDate fecha=estado.getFechaProximoPago().toLocalDate();
			if(fecha.isBefore(hoy)) {
				estado.setEstado("atrasado");
				do {
					if(estado.getQuincenasRestantes()==0) {
						break;
					}
					estado.setFechaProximoPago(java.sql.Date.valueOf(estado.getFechaProximoPago().toLocalDate().plusDays(15)));
					estado.setMontoRestante(estado.getMontoRestante()+estado.getMontoProximoPago()*(prestamo.getInteresRetraso()/100));
					estado.setDineroAtrasado(estado.getDineroAtrasado()+estado.getMontoProximoPago()+estado.getMontoProximoPago()*(prestamo.getInteresRetraso()/100));
					estado.setQuincenasRestantes(estado.getQuincenasRestantes()-1);
					prestamo.setMontoTotal(prestamo.getMontoTotal()+prestamo.getMontoQuincenal()*(prestamo.getInteresRetraso()/100));
					estadoRepository.actualizar(estado);
					prestamoRepository.actualizar(prestamo);
					fecha = fecha.plusDays(15);
					
				}while(fecha.isBefore(hoy));
			}
		}
	}
	public void actualizarReputacionClientes() {
		List<Cliente> clients = clienteRepository.getClients();
		
		if(clients.size() > 0) 
		{
			for(Cliente cliente : clients) 
			{
				List<Prestamo> prestamos=prestamoRepository.getPrestamosDeCliente(cliente);
				List<Integer> puntuaciones= new ArrayList<Integer>();
				double promedio=0;
				
				if(prestamos.size()>0) 
				{
					for(Prestamo prestamo : prestamos) 
					{
						double monto = prestamo.getMontoQuincenal()*prestamo.getNumeroQuincenas();
						double montoTotal=prestamo.getMontoTotal();
						double diferencia= montoTotal-monto;
						double diferenciaMaxima=(monto*(prestamo.getInteresRetraso()/100));
						
						if(diferencia==0) {
							puntuaciones.add(3);
						}else if(diferencia<diferenciaMaxima/3){
							puntuaciones.add(2);
						}else if(diferencia<diferenciaMaxima/2){
							puntuaciones.add(1);
						}else{
							puntuaciones.add(0);
						}
					}
					for(double puntuacion: puntuaciones) {
						promedio+=puntuacion;
					}
					promedio=promedio/puntuaciones.size();
					if(promedio<0.3) {
						cliente.setReputacion("mala");
					}else if(promedio<1.2) {
						cliente.setReputacion("regular");
					}else if(promedio<2.1) {
						cliente.setReputacion("buena");
					}else if(promedio<=3) {
						cliente.setReputacion("excelente");
					}
					clienteRepository.actualizar(cliente);
				}else {
					cliente.setReputacion("no medido");
					clienteRepository.actualizar(cliente);
				}
			}
				
		}
		
		
	}
}
