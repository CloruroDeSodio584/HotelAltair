package es.altair.hotelAltair.dao;

import java.util.List;

import es.altair.hotelAltair.bean.Habitacion;

public interface HabitacionDAO {

	List<Habitacion> listarHabitaciones();
	
	List<Habitacion> listarPorTipo(int tipoHabitacion);
	
	void suspenderHabitacion(int idHabitacion);
	
	Habitacion obtenerHabitacionPorId(int idHabitacion);
	
	Habitacion obtenerHabitacionPorUuid(String uuid);
	
	void actualizarHabitacion(Habitacion habitacionActualizar);
	
}
