package es.altair.hotelAltair.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="cliente")
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCliente;
	private String nombre;
	private String apellidos;
	private int edad;
	private String correo;
	private String password;
	private String uuid;
	private int tipoAcceso;
	
	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL)
	public Set<Reserva> reservas = new HashSet<Reserva>();
	
	public Cliente() {
		
	}
	
	public Cliente(String uuid) {
		this.uuid = uuid;
	}
	
	public Cliente(String nombre, String apellidos, int edad, String correo, String password,String uuid, int tipoAcceso) {
		super();
		
		this.nombre= nombre;
		this.apellidos= apellidos;
		this.edad= edad;
		this.correo= correo;
		this.password= password;
		this.uuid = uuid;
		this.tipoAcceso = tipoAcceso;
		
	}
	
	public int getTipoAcceso() {
		return tipoAcceso;
	}
	
	public void setTipoAcceso(int tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Set<Reserva> reservas) {
		this.reservas = reservas;
	}

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", nombre=" + nombre + ", apellidos=" + apellidos + ", edad=" + edad
				+ ", correo=" + correo + ", password=" + password + "]";
	}
	
	
	
}
