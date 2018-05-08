package es.altair.hotelAltair.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import es.altair.hotelAltair.bean.Cliente;
import es.altair.hotelAltair.bean.Habitacion;
import es.altair.hotelAltair.bean.Reserva;

public class ReservaDAOImplHibernate implements ReservaDAO {

private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory= sessionFactory;
	}
	
	@Transactional
	@Override
	public List<Reserva> listarPorCliente(Cliente client) {
		Session sesion = sessionFactory.getCurrentSession();
		return (List<Reserva>) sesion.createQuery("From Reserva WHERE idCliente=:i")
		.setParameter("i", client.getIdCliente())
		.list();
	}

	@Transactional
	@Override
	public List<Reserva> listarTodos() {
		Session sesion = sessionFactory.getCurrentSession();
		return (List<Reserva>) sesion.createQuery("From Reserva").list();
	}

	@Transactional
	@Override
	public void insertarReserva(Reserva reserva) {
		Session sesion = sessionFactory.getCurrentSession();
		sesion.persist(reserva);
	}

	@Transactional
	@Override
	public void borrarReserva(int idReserva) {
		Session sesion = sessionFactory.getCurrentSession();
		sesion.delete(obtenerReservaPorId(idReserva));

	}

	@Transactional
	@Override
	public Reserva obtenerReservaPorId(int idReserva) {
		Session sesion = sessionFactory.getCurrentSession();
		return (Reserva) sesion.get(Reserva.class, idReserva);
	}

}
