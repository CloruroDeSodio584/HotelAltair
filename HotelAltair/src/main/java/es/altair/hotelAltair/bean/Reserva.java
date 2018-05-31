package es.altair.hotelAltair.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reserva")
public class Reserva implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReserva;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idCliente")
	private Cliente cliente;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idTrabajador")
	private Trabajador trabajador;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "idHabitacion")
	private Habitacion habitacion;

	String fechaEntrada;

	String fechaSalida;

	double precioActual;
	double precioAPagar;

	String tipoPago;

	public Reserva() {

	}

	public Reserva(Cliente cliente, Trabajador trabajador, Habitacion habitacion, String fechaEntrada, String fechaSalida,
			double precioActual, double precioAPagar, String tipoPago) {

		this.cliente = cliente;
		this.trabajador = trabajador;
		this.habitacion = habitacion;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.precioActual = precioActual;
		this.precioAPagar = precioAPagar;
		this.tipoPago = tipoPago;

	}
	

	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}



	public String getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(String fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public double getPrecioActual() {
		return precioActual;
	}

	public void setPrecioActual(double precioActual) {
		this.precioActual = precioActual;
	}

	public double getPrecioAPagar() {
		return precioAPagar;
	}

	public void setPrecioAPagar(double precioAPagar) {
		this.precioAPagar = precioAPagar;
	}

	public String getTipoPago() {
		return tipoPago;
	}
	
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	
	@Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", cliente=" + cliente + ", trabajador=" + trabajador
				+ ", habitacion=" + habitacion + ", sdf=" + ", fechaEntrada=" + fechaEntrada + ", fechaSalida="
				+ fechaSalida + ", precioActual=" + precioActual + ", precioAPagar=" + precioAPagar + "]";
	}

}
