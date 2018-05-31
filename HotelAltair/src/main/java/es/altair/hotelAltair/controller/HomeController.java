package es.altair.hotelAltair.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

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
		double precioActual = Double.parseDouble((request.getParameter("precioApagar")));

		double precioApagar = precioActual;
		
		String tipoPago =request.getParameter("tipoPago");
		
		Reserva nuevaReserva = new Reserva(n, null ,habitacionReservar, fechaEntrada, fechaSalida, precioActual, precioApagar, tipoPago);
		
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
	
	
	
}
