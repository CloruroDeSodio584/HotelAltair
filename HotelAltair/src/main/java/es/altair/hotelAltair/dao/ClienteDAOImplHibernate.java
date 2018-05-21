package es.altair.hotelAltair.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


import es.altair.hotelAltair.bean.Cliente;
import es.altair.hotelAltair.util.SessionProvider;

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
		
		/*client = (Cliente) sesion.createQuery("SELECT cl FROM Cliente cl WHERE correo =:c AND password=AES_ENCRYPT(:p, :passphrase)")
				.setParameter("c", correo)
				.setParameter("p", password)
				.setParameter("passphrase", pass)
				.uniqueResult();*/
		
		client = (Cliente) sesion.createQuery("SELECT cl FROM Cliente cl WHERE correo =:c AND password =:p)")
				.setParameter("c", correo)
				.setParameter("p", password)
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
		
	
		
		if( (Cliente) sesion.createQuery("FROM Cliente WHERE correo=:c")
				.setParameter("c", client.getCorreo())
				.uniqueResult() != null)
			correcto = false;
		
		//sesion.getTransaction().commit();
		
		
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
	
	@Override
	public String encriptarContrase�a(String contrase�a) {
		
		String contrase�aEncriptada;
		
		char array[] = contrase�a.toCharArray();
		
		for (int i = 0; i < array.length; i++) {
			
			array[i] = (char)(array[i] + (char)5);
		}
		
		contrase�aEncriptada = String.valueOf(array);
		System.out.println(contrase�aEncriptada);
		return contrase�aEncriptada;
		
	}

}