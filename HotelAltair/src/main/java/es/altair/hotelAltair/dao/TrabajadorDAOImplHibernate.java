package es.altair.hotelAltair.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.transaction.Transactional;

import es.altair.hotelAltair.bean.Cliente;
import es.altair.hotelAltair.bean.Trabajador;

public class TrabajadorDAOImplHibernate implements TrabajadorDAO {
	String pass = "altair123$%";	

private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory= sessionFactory;
	}
	
	@Transactional
	@Override
	public Trabajador comprobarTrabajador(String correo, String password) {
		Trabajador trab = null;
		
		Session sesion = sessionFactory.getCurrentSession();
				
		trab = (Trabajador) sesion.createQuery("SELECT tr FROM Trabajador tr WHERE correo =:c AND password=:p")
				.setParameter("c", correo)
				.setParameter("p", password)
				.uniqueResult();
		
		System.out.println("");
		
		return trab;
	}

	@Transactional
	@Override
	public void anadirTrabajador(Trabajador trabajador) {
		Session sesion = sessionFactory.getCurrentSession();
		sesion.persist(trabajador);

	}

	@Transactional
	@Override
	public boolean validarEmail(Trabajador trabajador) {
		Session sesion = sessionFactory.getCurrentSession();
		boolean correcto = true;
		
		sesion.beginTransaction();
		
		if( (Trabajador) sesion.createQuery("FROM Trabajador WHERE correo=:c")
				.setParameter("c", trabajador.getCorreo())
				.uniqueResult() != null)
			correcto = false;
		
		sesion.getTransaction().commit();
		
		
		return correcto;
	}

	@Transactional
	@Override
	public List<Trabajador> listarTrabajadores() {
		Session sesion = sessionFactory.getCurrentSession();
		return (List<Trabajador>) sesion.createQuery("FROM Trabajador").list();
	}

	@Transactional
	@Override
	public void borrarTrabajador(int idTrabajador) {
		Session sesion = sessionFactory.getCurrentSession();
		sesion.delete(obtenerTrabajadorporId(idTrabajador));

	}

	@Transactional
	@Override
	public Trabajador obtenerTrabajadorporId(int idTrabajador) {
		Session sesion = sessionFactory.getCurrentSession();
		return (Trabajador)sesion.get(Trabajador.class, idTrabajador);
	}

	@Transactional
	@Override
	public void ActualizarTrabajador(Trabajador trabajador) {
		Session sesion = sessionFactory.getCurrentSession();
		sesion.update(trabajador);

	}

}
