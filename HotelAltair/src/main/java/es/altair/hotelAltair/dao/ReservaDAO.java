package es.altair.hotelAltair.dao;

import java.util.List;

import es.altair.hotelAltair.bean.Cliente;
import es.altair.hotelAltair.bean.Habitacion;
import es.altair.hotelAltair.bean.Reserva;

public interface ReservaDAO {

	List<Reserva> listarPorCliente(Cliente client);
	List<Reserva> listarTodos();
	void insertarReserva(Reserva reserva);
	void borrarReserva(int idReserva);
	Reserva obtenerReservaPorId(int idReserva);
    List<Reserva> listarReservaPorTipoHabitacion(int tipoHabitacion);
	
	
}
