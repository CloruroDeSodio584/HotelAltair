package es.altair.hotelAltair.dao;

import java.util.List;

import es.altair.hotelAltair.bean.Trabajador;

public interface TrabajadorDAO {

	Trabajador comprobarTrabajador(String correo, String password);
	
	void anadirTrabajador(Trabajador trabajador);
	
	boolean validarEmail(Trabajador trabajador);
	
	List<Trabajador> listarTrabajadores();
	
	List<Trabajador> listarTrabajadoresAdmin();
	
	List<Trabajador> listarAdministradores();
	
	void borrarTrabajador(int idTrabajador);
	
	Trabajador obtenerTrabajadorporId(int idTrabajador);
	
	void ActualizarTrabajador(Trabajador trabajador);
	
}
