package es.altair.hotelAltair.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

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
import es.altair.hotelAltair.dao.ClienteDAO;
import es.altair.hotelAltair.dao.HabitacionDAO;
import es.altair.hotelAltair.dao.ReservaDAO;
import es.altair.hotelAltair.dao.TrabajadorDAO;

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
	public ModelAndView home(Model model, @RequestParam(value = "mensaje", required= false, defaultValue="") String mensaje) {
		
		model.addAttribute("mensaje", mensaje);
		model.addAttribute("listarH", habitacionDAO.listarHabitaciones());
		
		
		return new ModelAndView("home", "cli", new Cliente());
	}
	
	@RequestMapping(value = "/entrar", method = RequestMethod.POST)
	public String entrar(@ModelAttribute Cliente clienteLogin, Model model, HttpSession session) {
		
		model.addAttribute("listarH", habitacionDAO.listarHabitaciones());
		clienteLogin.setPassword(clienteDAO.encriptarContraseña(clienteLogin.getPassword()));
		clienteLogin = clienteDAO.comprobarCliente(clienteLogin.getCorreo(), clienteLogin.getPassword());
		
		if(clienteLogin != null) {
			session.setAttribute("clienteLogin", clienteLogin);
			System.out.println("Sesion iniciada con esito");
			return "redirect:/?mensaje=Sesion Iniciada Con exito";
		}
		System.out.println("Sesion no Iniciada");
		return "redirect:/?mensaje=Error en email o Password Incorrecto";
		
	}
	
	@RequestMapping(value="/registrar", method = RequestMethod.GET)
	public ModelAndView registrar() {
		
		
		return new ModelAndView("registrar", "reg", new Cliente());
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
	
	@RequestMapping(value="/confirmarReserva", method = RequestMethod.GET)
	public String confirmarReserva(Model model, HttpServletRequest request) {
		
		
		
		return "redirect:/";
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
	
	private boolean noLogueado(HttpSession session) {
		Cliente clienteLogueado = (Cliente) session.getAttribute("clienteLogin");
		if (clienteLogueado == null || session.isNew()) {
			return true;
		}
		return false;
	}
	
	
	
}
