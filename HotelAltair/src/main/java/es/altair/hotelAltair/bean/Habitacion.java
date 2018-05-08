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
@Table(name="habitacion")
public class Habitacion implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idHabitacion;
	private int tipoHabitacion;
	private String tematica;
	private double precio;
	
	@OneToMany(mappedBy="habitacion", cascade=CascadeType.ALL)
	public Set<Reserva> reservas = new HashSet<Reserva>();
	
	public Habitacion() {
		
	}
	
	public Habitacion(int tipoHabitacion, String tematica, double precio) {
		this.tipoHabitacion= tipoHabitacion;
		this.tematica= tematica;
		this.precio= precio;
	}

	public int getIdHabitacion() {
		return idHabitacion;
	}

	public void setIdHabitacion(int idHabitacion) {
		this.idHabitacion = idHabitacion;
	}

	public int getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(int tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	public String getTematica() {
		return tematica;
	}

	public void setTematica(String tematica) {
		this.tematica = tematica;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Set<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Set<Reserva> reservas) {
		this.reservas = reservas;
	}

	@Override
	public String toString() {
		return "Habitacion [idHabitacion=" + idHabitacion + ", tipoHabitacion=" + tipoHabitacion + ", tematica="
				+ tematica + ", precio=" + precio + "]";
	}
	
	
	
}
