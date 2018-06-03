package es.altair.hotelAltair.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.persistence.Convert;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.altair.hotelAltair.bean.Cliente;
import es.altair.hotelAltair.bean.Habitacion;
import es.altair.hotelAltair.bean.Reserva;
import es.altair.hotelAltair.dao.ClienteDAO;
import es.altair.hotelAltair.dao.HabitacionDAO;
import es.altair.hotelAltair.dao.ReservaDAO;
import es.altair.hotelAltair.dao.TrabajadorDAO;
import javassist.CodeConverter;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private ClienteDAO clienteDAO;
	
	@Autowired
	private HabitacionDAO habitacionDAO;
	
	@Autowired
	private TrabajadorDAO trabajadorDAO;
	
	@Autowired
	private ReservaDAO reservaDAO;
	
	
	
	

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Model model,HttpSession session, @RequestParam(value = "mensaje", required= false, defaultValue="") String mensaje) {
		
		model.addAttribute("mensaje", mensaje);
		model.addAttribute("listarH", habitacionDAO.listarHabitaciones());
		
		model.addAttribute("clienteLogin", (Cliente)session.getAttribute("clienteLogin"));
		
		return new ModelAndView("home", "cli", new Cliente());
	}
	
	@RequestMapping(value = "/homet", method = RequestMethod.GET)
	public String homet(Model model, @RequestParam(value = "mensaje", required= false, defaultValue="") String mensaje) {
		
		
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/entrar", method = RequestMethod.POST)
	public String entrar(@ModelAttribute Cliente clienteLogin, Model model, HttpSession session) {
		
		model.addAttribute("listarH", habitacionDAO.listarHabitaciones());
		clienteLogin.setPassword(clienteDAO.encriptarContraseña(clienteLogin.getPassword()));
		clienteLogin = clienteDAO.comprobarCliente(clienteLogin.getCorreo(), clienteLogin.getPassword());
		
		if(clienteLogin != null) {
			session.setAttribute("clienteLogin", clienteLogin);
			System.out.println("Sesion iniciada con exito");
			return "redirect:/?mensaje=Sesion Iniciada Con exito";
		}
		System.out.println("Sesion no Iniciada");
		return "redirect:/?mensaje=Error en email o Password Incorrecto";
		
	}
	
	@RequestMapping(value = "/misReservas", method = RequestMethod.GET)
	public String misReservas(Model model, HttpServletRequest request ,HttpSession session) {
		
		if(noLogueado(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/";
		}
		
		Cliente n = (Cliente)session.getAttribute("clienteLogin");	
		
		model.addAttribute("listarR",reservaDAO.listarPorCliente(n));
		
		return ("misReservas");
		
	}
	
	
	@RequestMapping(value="/registrar", method = RequestMethod.GET)
	public ModelAndView registrar() {
		
		
		return new ModelAndView("registrar", "reg", new Cliente());
	}
	
	@RequestMapping(value="/modPerfil", method = RequestMethod.GET)
	public ModelAndView modPerfil(Model model, HttpServletRequest request ,HttpSession session) {
		
		model.addAttribute("clienteLogin", (Cliente)session.getAttribute("clienteLogin"));
		
		
		return new ModelAndView("modPerfil");
	}
	
	
	@RequestMapping(value="/reserva", method = RequestMethod.GET)
	public String reserva(Model model, HttpServletRequest request, HttpSession session) {
		
		if(noLogueado(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/";
		}
		
		 int idHabitacion = Integer.parseInt(request.getParameter("idHabitacion"));
		 Habitacion habitacionReservar = habitacionDAO.obtenerHabitacionPorId(idHabitacion);
		 
		 model.addAttribute("habitacion", habitacionReservar);
		 model.addAttribute("clienteLogin", (Cliente)session.getAttribute("clienteLogin"));
		
		return "reserva";
	}
	
	@RequestMapping(value="/confirmarReserva", method = RequestMethod.POST)
	public String confirmarReserva(Model model, HttpServletRequest request, HttpSession session) {

		Cliente n = (Cliente)session.getAttribute("clienteLogin");	
		
		int idHabitacion = Integer.parseInt(request.getParameter("idHabitacion"));
		Habitacion habitacionReservar = habitacionDAO.obtenerHabitacionPorId(idHabitacion);
		String fechaEntrada = request.getParameter("fechaEntrada");
		String fechaSalida =  request.getParameter("fechaSalida");
		
		String tipoPago =request.getParameter("tipoPago");
		
		boolean fechaCorrecta = compruebaFechas(fechaEntrada, fechaSalida);
		
		if(!fechaCorrecta)
		return "redirect:/?mensaje=Fecha de Salida Incorrecta vuelva a intentarlo";
		
		double precioApagar = precioFecha(fechaEntrada, fechaSalida ,habitacionReservar.getTipoHabitacion());
		
		
		
		Reserva nuevaReserva = new Reserva(n, null ,habitacionReservar, fechaEntrada, fechaSalida, precioApagar, tipoPago);
		
		reservaDAO.insertarReserva(nuevaReserva);
		
		
		return "redirect:/?mensaje=Reserva Realizada Con exito";
	}
	
	@RequestMapping(value="/actualizarPerfil", method = RequestMethod.POST)
	public String actualizarPerfil(Model model, HttpServletRequest request, HttpSession session) {

		Cliente clienteLogueado = (Cliente) session.getAttribute("clienteLogin");
		
		String nombre = request.getParameter("nombre");
		String apellidos =  request.getParameter("apellidos");
		int edad = Integer.parseInt(request.getParameter("edad"));
		String password =  clienteDAO.encriptarContraseña(request.getParameter("password")); 
		
		clienteLogueado.setNombre(nombre);
		clienteLogueado.setApellidos(apellidos);
		clienteLogueado.setEdad(edad);
		clienteLogueado.setPassword(password);
		
		
		
		clienteDAO.ActualizarCliente(clienteLogueado);
		
		return "redirect:/?mensaje=Datos Actualizados con exito";
	}
	
	
	
	@RequestMapping(value="/cancelarReserva", method = RequestMethod.GET)
	public String cancelarReserva(Model model, HttpServletRequest request, HttpSession session) {
		
		int idReserva = Integer.parseInt(request.getParameter("idReserva"));
		
		reservaDAO.borrarReserva(idReserva);
		
		
		return "redirect:/misReservas";
	}
	
	@RequestMapping(value = "/registrarse", method = RequestMethod.POST)
	public String registrarse(@ModelAttribute Cliente clienteLogin,@RequestParam(value = "mensaje", required= false, defaultValue="") String mensaje) {
		

		if(clienteDAO.validarEmail(clienteLogin)) {
			
			clienteLogin.setPassword(clienteDAO.encriptarContraseña(clienteLogin.getPassword())); 
			clienteDAO.insertar(clienteLogin);
		}
		else {
			return "redirect:/?mensaje=Usuario YA Registrado";
		}
		
		return "redirect:/?mensaje=Usuario Registrado";
		
	}
	
	@RequestMapping(value="/cerrarSesion", method = RequestMethod.GET)
	public String cerrarSesion(HttpSession session) {
		
		session.setAttribute("clienteLogin", null);
		
		return "redirect:/?mensaje=Sesion Cerrada Con exito";
	}
	
	
	private boolean noLogueado(HttpSession session) {
		Cliente clienteLogueado = (Cliente) session.getAttribute("clienteLogin");
		if (clienteLogueado == null || session.isNew()) {
			return true;
		}
		return false;
	}
	
	
	private List<Habitacion> listaHabitacionesSoloUno(){
		List<Habitacion> todasHabitaciones = habitacionDAO.listarHabitaciones();
	
		List<Habitacion> habitacionSoloUnoPorTipo = new ArrayList<Habitacion>();
		boolean esPirata1 = false, esStarWar1 = false, esBatman1 = false, esPirata2 = false, esStarWar2 = false, esBatman2 = false, esPirata3 = false, esStarWar3 = false, esBatman3 = false;
		
		for (Habitacion habitacion : todasHabitaciones) {
			
			if(habitacion.getTipoHabitacion() == 1 && habitacion.getTematica() == "Star Wars" && !esStarWar1) {
				habitacionSoloUnoPorTipo.add(habitacion);
					esStarWar1 = true;
			} else
			
				if(habitacion.getTipoHabitacion() == 1 && habitacion.getTematica() == "Piratas del Caribe" && !esPirata1) {
					habitacionSoloUnoPorTipo.add(habitacion);
						esPirata1 = true;
				} else
					if(habitacion.getTipoHabitacion() == 1 && habitacion.getTematica() == "Batman" && !esBatman1) {
						habitacionSoloUnoPorTipo.add(habitacion);
							esBatman1 = true;
					}
					else 
						if(habitacion.getTipoHabitacion() == 2 && habitacion.getTematica() == "Star Wars" && !esStarWar2) {
							habitacionSoloUnoPorTipo.add(habitacion);
								esStarWar2 = true;
						} else
						
							if(habitacion.getTipoHabitacion() == 2 && habitacion.getTematica() == "Piratas del Caribe" && !esPirata2) {
								habitacionSoloUnoPorTipo.add(habitacion);
									esPirata2 = true;
							} else
								if(habitacion.getTipoHabitacion() == 2 && habitacion.getTematica() == "Batman" && !esBatman2) {
									habitacionSoloUnoPorTipo.add(habitacion);
										esBatman2 = true;
								} else 
									// hola
									if(habitacion.getTipoHabitacion() == 3 && habitacion.getTematica() == "Star Wars" && !esStarWar3) {
										habitacionSoloUnoPorTipo.add(habitacion);
											esStarWar3 = true;
									} else
									
										if(habitacion.getTipoHabitacion() == 3 && habitacion.getTematica() == "Piratas del Caribe" && !esPirata3) {
											habitacionSoloUnoPorTipo.add(habitacion);
												esPirata3 = true;
										} else
											if(habitacion.getTipoHabitacion() == 3 && habitacion.getTematica() == "Batman" && !esBatman3) {
												habitacionSoloUnoPorTipo.add(habitacion);
													esBatman3 = true;
											}
			
			
			
			
		}
		
		return habitacionSoloUnoPorTipo;
		
	}
	
	private boolean compruebaFechas(String fechaEntrada, String fechaSalida) {
		 
		boolean fechaCorrecta = true; 
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		
		Date fechaEntradaDate = null;
		Date fechaSalidaDate = null;
		
		try {	
		fechaEntradaDate = formatoDelTexto.parse(fechaEntrada);
		
		fechaSalidaDate = formatoDelTexto.parse(fechaSalida);
		
		System.out.println("FECHA ENTRADA: " +fechaEntradaDate);
		System.out.println("FECHA SALIDA: " +fechaSalidaDate);
		} 
		catch (ParseException ex) {
				ex.printStackTrace();
		}
		
		if(fechaSalidaDate.before(fechaEntradaDate))
			fechaCorrecta = false;
		
		
		return fechaCorrecta;
		
		
	}
	
	private double precioFecha(String fechaEntrada,String fechaSalida, int tipoHabitacion) {
		
		boolean fechaCorrecta = true; //"yyyy-MM-dd"
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		
		Date fechaEntradaDate = null;
		Date fechaSalidaDate = null;
		
		try {	
		fechaEntradaDate = formatoDelTexto.parse(fechaEntrada);
		fechaSalidaDate = formatoDelTexto.parse(fechaSalida);
		} 
		catch (ParseException ex) {
				ex.printStackTrace();
		}
		
		long diferencia = fechaEntradaDate.getTime() - fechaSalidaDate.getTime();
		float dias = (diferencia / (1000*60*60*24)) * -1;
		
		System.out.println(dias);
		
		int mes = fechaEntradaDate.getMonth();
		
		if(mes == 1 | mes == 2) {
			if(tipoHabitacion ==1)
				return 100 * dias;	
			else if (tipoHabitacion == 2)
				return 130 * dias;
			else if (tipoHabitacion == 3)
				return 320 * dias;
		}
		else if(mes == 3 | mes == 4) {
			if(tipoHabitacion ==1)
				return 220 * dias;	
			else if (tipoHabitacion == 2)
				return 250 * dias;
			else if (tipoHabitacion == 3)
				return 400 * dias;
		} 
		else if(mes == 5 | mes == 6) {
			if(tipoHabitacion ==1)
				return 170 * dias;	
			else if (tipoHabitacion == 2)
				return 200 * dias;
			else if (tipoHabitacion == 3)
				return 370 * dias;
		}
		else if(tipoHabitacion ==1)
			return 120 * dias;	
		else if (tipoHabitacion == 2)
			return 150 * dias;
		else if (tipoHabitacion == 3)
			return 340 * dias;
		
		
		return 0;
		
		
		
			
		
		
	}
	
	
}
