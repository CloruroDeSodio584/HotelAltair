package es.altair.hotelAltair.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import es.altair.hotelAltair.bean.Cliente;

public class ClienteDAOImplHibernate implements ClienteDAO {
		String pass = "altair123$%";	
	
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory= sessionFactory;
	}
	
	@Transactional
	@Override
	public Cliente comprobarCliente(String correo, String password) {
		
		Cliente client = null;
		
		Session sesion = sessionFactory.getCurrentSession();
		
		client = (Cliente) sesion.createQuery("SELECT cl FROM Cliente cl WHERE correo =:c AND password=AES_ENCRYPT(:p, :passphrase)")
				.setParameter("c", correo)
				.setParameter("p", password)
				.setParameter("passphrase", pass)
				.uniqueResult();
		
		
		return client;
	}

	@Transactional
	@Override
	public void insertar(Cliente client) {
		Session sesion = sessionFactory.getCurrentSession();
		sesion.persist(client);
	}

	@Transactional
	@Override
	public boolean validarEmail(Cliente client) {
		Session sesion = sessionFactory.getCurrentSession();
		boolean correcto = true;
		
		sesion.beginTransaction();
		
		if( (Cliente) sesion.createQuery("FROM Cliente WHERE correo=:c")
				.setParameter("c", client.getCorreo())
				.uniqueResult() != null)
			correcto = false;
		
		sesion.getTransaction().commit();
		
		
		return correcto;
	}

	@Transactional
	@Override
	public List<Cliente> listarClientes() {
		Session sesion = sessionFactory.getCurrentSession();
		return (List<Cliente>) sesion.createQuery("FROM Cliente").list();
	}

	@Transactional
	@Override
	public void borrarCliente(int idCliente) {
		Session sesion = sessionFactory.getCurrentSession();
		sesion.delete(obtenerClienteporId(idCliente));

	}

	@Transactional
	@Override
	public Cliente obtenerClienteporId(int idCliente) {
		Session sesion = sessionFactory.getCurrentSession();
		return (Cliente)sesion.get(Cliente.class, idCliente);
	}

	@Transactional
	@Override
	public void ActualizarCliente(Cliente client) {
		Session sesion = sessionFactory.getCurrentSession();
		sesion.update(client);

	}

}
