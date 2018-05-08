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
@Table(name="trabajador")
public class Trabajador implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTrabajador;
	private String nombre;
	private String apellidos;
	private int tipoAcceso;
	private int edad;
	private String idioma;
	private String correo;
	private String password;
	
	@OneToMany(mappedBy="trabajador", cascade=CascadeType.ALL)
	public Set<Reserva> reservas = new HashSet<Reserva>();
	
	public Trabajador() {
		
	}
	
	public Trabajador(String nombre, String apellidos, int tipoAcceso, int edad, String idioma, String correo, String password) {
		
		this.nombre= nombre;
		this.apellidos= apellidos;
		this.tipoAcceso= tipoAcceso;
		this.edad= edad;
		this.idioma= idioma;
		this.correo= correo;
		this.password= password;
	}

	public int getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(int idTrabajador) {
		this.idTrabajador = idTrabajador;
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

	public int getTipoAcceso() {
		return tipoAcceso;
	}

	public void setTipoAcceso(int tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
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
		return "Trabajador [idTrabajador=" + idTrabajador + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", tipoAcceso=" + tipoAcceso + ", edad=" + edad + ", idioma=" + idioma + ", correo=" + correo
				+ ", password=" + password + "]";
	}
	
}
