package es.altair.hotelAltair.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.transaction.Transactional;

import es.altair.hotelAltair.bean.Habitacion;

public class HabitacionDAOImplHibernate implements HabitacionDAO {

private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory= sessionFactory;
	}
	
	@Transactional
	@Override
	public List<Habitacion> listarHabitaciones() {
		Session sesion = sessionFactory.getCurrentSession();
		return (List<Habitacion>) sesion.createQuery("From Habitacion").list();
	}

	@Transactional
	@Override
	public List<Habitacion> listarPorTipo(int tipoHabitacion) {
		Session sesion = sessionFactory.getCurrentSession();
		return (List<Habitacion>) sesion.createQuery("From Habitacion WHERE tipoHabitacion=:t")
				.setParameter("t", tipoHabitacion)
				.list();
	}

	@Transactional
	@Override
	public void suspenderHabitacion(int idHabitacion) {
		Session sesion = sessionFactory.getCurrentSession();
		sesion.delete(obtenerHabitacionPorId(idHabitacion));

	}

	@Transactional
	@Override
	public Habitacion obtenerHabitacionPorId(int idHabitacion) {
		Session sesion = sessionFactory.getCurrentSession();
		return (Habitacion) sesion.get(Habitacion.class, idHabitacion);
	}

	@Transactional
	@Override
	public void actualizarHabitacion(Habitacion habitacionActualizar) {
		Session sesion = sessionFactory.getCurrentSession();
		sesion.update(habitacionActualizar);

	}

}
