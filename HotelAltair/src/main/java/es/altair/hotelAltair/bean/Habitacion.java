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
	private int numeroHab;
	private String uuid;
	
	@OneToMany(mappedBy="habitacion", cascade=CascadeType.ALL)
	public Set<Reserva> reservas = new HashSet<Reserva>();
	
	public Habitacion() {
		
	}
	
	public Habitacion(int tipoHabitacion, String tematica, int numeroHab, String uuid) {
		this.tipoHabitacion= tipoHabitacion;
		this.tematica= tematica;
		this.numeroHab=numeroHab;
		this.uuid = uuid;
		
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public int getNumeroHab() {
		return numeroHab;
	}
	
	public void setNumeroHab(int numeroHab) {
		this.numeroHab = numeroHab;
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
				+ tematica + ", precio=" + "]";
	}
	
	
	
}
