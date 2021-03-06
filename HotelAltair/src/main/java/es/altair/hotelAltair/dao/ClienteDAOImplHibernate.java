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
	public Cliente obtenerClienteporCorreo(String correo) {
		Session sesion = sessionFactory.getCurrentSession();
		
		Cliente client = null;
		
		client = (Cliente) sesion.createQuery("FROM Cliente WHERE correo=:c")
				.setParameter("c", correo)
				.uniqueResult();
		
		
		return client;
	}
	
	@Transactional
	@Override
	public Cliente obtenerClienteporUuid(String uuid) {
		Session sesion = sessionFactory.getCurrentSession();
		
		Cliente client = null;
		
		client = (Cliente) sesion.createQuery("FROM Cliente WHERE uuid=:c")
				.setParameter("c", uuid)
				.uniqueResult();
		
		
		return client;
	}
	

	@Transactional
	@Override
	public List<Cliente> listarClientes() {
		Session sesion = sessionFactory.getCurrentSession();
		return (List<Cliente>) sesion.createQuery("FROM Cliente where tipoAcceso = 1").list();
	}

	@Transactional
	@Override
	public void borrarCliente(String uuid) {
		Session sesion = sessionFactory.getCurrentSession();
		sesion.delete(obtenerClienteporUuid(uuid));

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
	public String encriptarContraseña(String contraseña) {
		
		String contraseñaEncriptada;
		
		char array[] = contraseña.toCharArray();
		
		for (int i = 0; i < array.length; i++) {
			
			array[i] = (char)(array[i] + (char)5);
		}
		
		contraseñaEncriptada = String.valueOf(array);
		System.out.println(contraseñaEncriptada);
		return contraseñaEncriptada;
		
	}

	

}
