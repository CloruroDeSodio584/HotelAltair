package es.altair.hotelAltair.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.transaction.Transactional;

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
	public List<Reserva> listarReservaPorTipoHabitacion(int idHabitacion) {
		Session sesion = sessionFactory.getCurrentSession();
		System.out.println("");
		return (List<Reserva>) sesion.createQuery("From Reserva WHERE idHabitacion=:i")
				.setParameter("i", idHabitacion)
				.list();
		
		/*return (List<Reserva>) sesion.createSQLQuery("SELECT reserva.idReserva, reserva.idCliente, reserva.idTrabajador, reserva.idHabitacion, habitacion.numeroHab, habitacion.tematica, habitacion.tipoHabitacion , reserva.fechaEntrada, reserva.fechaSalida, reserva.precioAPagar, reserva.tipoPago  FROM reserva JOIN habitacion WHERE tipoHabitacion=:t")
				.addEntity(Habitacion.class)
				.setParameter("t", tipoHabitacion)
				.list();
		
		/* 
		 
		  Session sesion = sessionFactory.getCurrentSession();
	
		return (List<Juego>) sesion.createSQLQuery("SELECT juegos.titulo, juegos.numJugadores, juegos.descripcion, juegos.pegi FROM juegoteca JOIN juegos WHERE idUsuario=:i")
				.addEntity(Juego.class)
				.setParameter("i", u.getIdUsuario()).list();
		   
		   */
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
