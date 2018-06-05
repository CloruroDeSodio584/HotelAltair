package es.altair.hotelAltair.dao;

import java.util.List;

import es.altair.hotelAltair.bean.Cliente;

public interface ClienteDAO {
	
	Cliente comprobarCliente(String correo, String password);
	
	void insertar(Cliente client);
	
	boolean validarEmail(Cliente client);
	
	List<Cliente> listarClientes();
	
	void borrarCliente(int idCliente);
	
	Cliente obtenerClienteporId(int idCliente);
	
	Cliente obtenerClienteporCorreo(String correo);
	
	void ActualizarCliente(Cliente client);
	
	public String encriptarContraseña(String contraseña);
	
}
