package es.altair.hotelAltair.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.Convert;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
import es.altair.hotelAltair.bean.Trabajador;
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
	
	@Autowired
	private JavaMailSender mailsender;
	
	

	
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
	
	    
		if(((Trabajador)session.getAttribute("trabajadorLogin")) != null)
	    	return "redirect:/?mensaje=Debe cerrar sesion como Trabajador";
		
		
		model.addAttribute("listarH", habitacionDAO.listarHabitaciones());
		clienteLogin.setPassword(clienteDAO.encriptarContraseña(clienteLogin.getPassword()));
		clienteLogin = clienteDAO.comprobarCliente(clienteLogin.getCorreo(), clienteLogin.getPassword());
		
		if(clienteLogin == null) {
			return "redirect:/?mensaje=Error en email o Password Incorrecto";
		}	
		if(clienteLogin.getTipoAcceso() == 0)
			return "redirect:/?mensaje=Revise su email para confirmacion de cuenta";
		
		if(clienteLogin != null) {
			session.setAttribute("clienteLogin", clienteLogin);
			System.out.println("Sesion iniciada con exito");
			return "redirect:/?mensaje=Sesion Iniciada Con exito";
		}
		
		return "redirect:/?mensaje=Error en email o Password Incorrecto";
		
	}
	
	@RequestMapping(value = "/misReservas", method = RequestMethod.GET)
	public String misReservas(Model model, HttpServletRequest request ,HttpSession session) {
		
		
		if(noLogueado(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/?mensaje=Debe iniciar Sesion para continuar";
		}
		
		Cliente n = (Cliente)session.getAttribute("clienteLogin");	
		
		model.addAttribute("listarR",reservaDAO.listarPorCliente(n));
		
		return ("misReservas");
		
	}
	
	
	@RequestMapping(value="/registrar", method = RequestMethod.GET )
	public ModelAndView registrar(Model model,HttpSession session) {
		
		model.addAttribute("trabajadorLogin", (Trabajador)session.getAttribute("trabajadorLogin"));
		
		
		
		
		return new ModelAndView("registrar", "reg", new Cliente(uuidAleatorio()));
	}
	
	@RequestMapping(value="/anadirTrabajador", method = RequestMethod.GET )
	public String anadirTrabajador(Model model,HttpSession session) {
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		
		Trabajador t = (Trabajador)session.getAttribute("trabajadorLogin");
	
		if(t.getTipoAcceso() != 2)
			return "redirect:/datosTrabajadores?mensaje=No tiene los permisos necesarios";
		
		
		
		return "anadirTrabajador";
	}
	
	@RequestMapping(value="/modPerfil", method = RequestMethod.GET)
	public String modPerfil(Model model, HttpServletRequest request ,HttpSession session) {
		
		
		if(noLogueado(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/?mensaje=Debe iniciar Sesion para continuar";
		}
		
		
		model.addAttribute("clienteLogin", (Cliente)session.getAttribute("clienteLogin"));
		
		
		return "modPerfil";
	}
	
	@RequestMapping(value="/modPerfilTrabajador", method = RequestMethod.GET)
	public String modPerfilTrabajador(Model model, HttpServletRequest request ,HttpSession session) {
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		
		 String uuid = request.getParameter("uuid");
		
		
		model.addAttribute("clienteLogin", clienteDAO.obtenerClienteporUuid(uuid));
		model.addAttribute("trabajadorLogin", (Trabajador)session.getAttribute("trabajadorLogin"));
		
		return "modPerfil";
	}
	
	@RequestMapping(value="/modPerfilTrabajadorUsu", method = RequestMethod.GET)
	public String modPerfilTrabajadorUsu(Model model, HttpServletRequest request ,HttpSession session) {
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		
		 String uuid = request.getParameter("uuid");
		
		
		model.addAttribute("trabajadorCambiar", trabajadorDAO.obtenerTrabajadorporUuid(uuid));
		model.addAttribute("trabajadorLogin", (Trabajador)session.getAttribute("trabajadorLogin"));
		
		return "modPerfilUsu";
	}
	
	
	
	@RequestMapping(value="/reserva", method = RequestMethod.GET)
	public String reserva(Model model, HttpServletRequest request, HttpSession session) {
		
		if(noLogueado(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/?mensaje=Debe iniciar Sesion para continuar";
		}
		
		 String uuid =request.getParameter("uuid");
		 Habitacion habitacionReservar = habitacionDAO.obtenerHabitacionPorUuid(uuid);
		 
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
		
		
		
		//INICIO COMPROBACIONES DE FECHA
		
		boolean fechaCorrecta = compruebaFechas(fechaEntrada, fechaSalida);
		
		if(!compruebaFechas(fechaEntrada, fechaSalida)) {
			return "redirect:/?mensaje=Fecha Introducida Incorrecta vuelva a intentarlo";
		}
		
		if (!comrpuebaHabitacionLibre(fechaEntrada, fechaSalida, habitacionReservar.getIdHabitacion(), habitacionReservar.getNumeroHab())) {
			 return "redirect:/?mensaje=Todas las habitaciones estan ocupadas por al menos un dia en concreto, vuelva a intentarlo";
		}
		
		
		//FIN COMPROBACIONES DE FECHA
		
		double precioApagar = precioFecha(fechaEntrada, fechaSalida ,habitacionReservar.getTipoHabitacion());
		
		
		
		Reserva nuevaReserva = new Reserva(n, null ,habitacionReservar, fechaEntrada, fechaSalida, precioApagar, tipoPago, uuidAleatorio());
		
		reservaDAO.insertarReserva(nuevaReserva);
		
		try {
			enviarMailReserva(n, nuevaReserva);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "redirect:/?mensaje=Reserva Realizada Con exito, no olvide revisar el correo";
	}
	
	@RequestMapping(value="/actualizarPerfil", method = RequestMethod.POST)
	public String actualizarPerfil(Model model, HttpServletRequest request, HttpSession session) {
		boolean esTrabajador = false;
		
		if(((Trabajador)session.getAttribute("trabajadorLogin")) != null)
	    	esTrabajador = true;
		
		
		String nombre = request.getParameter("nombre");
		String apellidos =  request.getParameter("apellidos");
		int edad = Integer.parseInt(request.getParameter("edad"));
		int idCliente = Integer.parseInt(request.getParameter("idCliente"));
		
		Cliente clienteLogueado = clienteDAO.obtenerClienteporId(idCliente);
		
		clienteLogueado.setNombre(nombre);
		clienteLogueado.setApellidos(apellidos);
		clienteLogueado.setEdad(edad);
		
		if(!esTrabajador) {
			String password =  clienteDAO.encriptarContraseña(request.getParameter("password"));
			clienteLogueado.setPassword(password);
			}
		
		
		
		clienteDAO.ActualizarCliente(clienteLogueado);
		
		if(!esTrabajador)
			return "redirect:/?mensaje=Datos Actualizados con exito";
		else
			 return "redirect:/datosClientes?mensaje=Cliente actualizado";
		
	}
	
	@RequestMapping(value="/actualizarPerfilUsu", method = RequestMethod.POST)
	public String actualizarPerfilUsu(Model model, HttpServletRequest request, HttpSession session) {
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		
		int tipoAcceso = 0;
		
		String nombre = request.getParameter("nombre");
		String apellidos =  request.getParameter("apellidos");
		String idioma =  request.getParameter("idioma");
		int edad = Integer.parseInt(request.getParameter("edad"));
		int idTrabajador = Integer.parseInt(request.getParameter("idTrabajador"));
	
		
		Trabajador trabajadorLogin = (Trabajador)session.getAttribute("trabajadorLogin");
		
		
		
		
		Trabajador trabajadorCambiar = trabajadorDAO.obtenerTrabajadorporId(idTrabajador);
		
		
		
		trabajadorCambiar.setNombre(nombre);
		trabajadorCambiar.setApellidos(apellidos);
		trabajadorCambiar.setEdad(edad);
		
		trabajadorCambiar.setIdioma(idioma);
		
		if(trabajadorLogin.getTipoAcceso() == 2 ) {
			 tipoAcceso = Integer.parseInt(request.getParameter("tipoAcceso"));
			 
			 if(trabajadorCambiar.getTipoAcceso() == 2 && trabajadorDAO.listarAdministradores().size() == 1 && tipoAcceso != 2 ) {
				 return "redirect:/datosTrabajadores?mensaje=**Error** Debe al menos haber un administrador";
				}
			 
			 
			trabajadorCambiar.setTipoAcceso(tipoAcceso);
			
			
			
		}
		
		if (trabajadorLogin.getIdTrabajador() == idTrabajador) {
			String password =  request.getParameter("password");
			trabajadorCambiar.setPassword(clienteDAO.encriptarContraseña(password));
		}
		
		
	
		
		trabajadorDAO.ActualizarTrabajador(trabajadorCambiar);
		
		
		 return "redirect:/datosTrabajadores?mensaje=Trabajador actualizado";
		
	}
	
	
	
	@RequestMapping(value="/cancelarReserva", method = RequestMethod.GET)
	public String cancelarReserva(Model model, HttpServletRequest request, HttpSession session) {
		
		String idReserva = request.getParameter("idReserva");
		String idCliente = request.getParameter("idCliente");
		
		System.out.println();
		
		Reserva nReserva = reservaDAO.obtenerReservaPorUuid(idReserva);
		
		reservaDAO.borrarReserva(nReserva.getIdReserva());
			
		try {
			enviarMailCancelarReserva(clienteDAO.obtenerClienteporUuid(idCliente), nReserva );
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		if(((Trabajador)session.getAttribute("trabajadorLogin")) != null)
			return "redirect:/datosClientes?mensaje=Reserva Borrada Correctamente";
			
		
		return "redirect:/misReservas";
	}
	
	@RequestMapping(value = "/registrarse", method = RequestMethod.POST)
	public String registrarse(@ModelAttribute Cliente clienteLogin,@RequestParam(value = "mensaje", required= false, defaultValue="") String mensaje, HttpSession session) {
		boolean esTrabajador = false;
	    
		clienteLogin.setUuid(uuidAleatorio());
		clienteLogin.setTipoAcceso(0);
		
		if(((Trabajador)session.getAttribute("trabajadorLogin")) != null)
	    	esTrabajador = true;

		if(clienteDAO.validarEmail(clienteLogin)) {
			
			clienteLogin.setPassword(clienteDAO.encriptarContraseña(clienteLogin.getPassword())); 
			clienteDAO.insertar(clienteLogin);
		}
		else {
			
			if (esTrabajador) {
			
				return "redirect:/homeTrabajador?mensaje=Email YA Registrado";
			
			}else
			return "redirect:/?mensaje=Email YA Registrado";
		}
		
		if(esTrabajador) {
			
			try {
				enviarMail(clienteLogin);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "redirect:/homeTrabajador?mensaje=Esperando a confirmacion de cuenta";
		}
		else{
			try {
				enviarMail(clienteLogin);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "redirect:/?mensaje=Revise su Email para confirmacion de cuenta";
		}
		
		
	}
	
	@RequestMapping(value = "/anadirTrabajadorCofirmar", method = RequestMethod.POST)
	public String anadirTrabajadorConfirmar(HttpServletRequest request,HttpSession session) {
		
		String nombre = request.getParameter("nombre");
		String apellidos =  request.getParameter("apellidos");
		String correo =  request.getParameter("correo");
		String idioma =  request.getParameter("idioma");
		int edad = Integer.parseInt(request.getParameter("edad"));
		int tipoAcceso = Integer.parseInt(request.getParameter("tipoAcceso"));
		String password =  request.getParameter("password");
		
		Trabajador nuevoTrabajador = new Trabajador(nombre, apellidos, tipoAcceso, edad, idioma, correo, password, uuidAleatorio());
		
		
		if(trabajadorDAO.validarEmail(nuevoTrabajador)) {
			nuevoTrabajador.setPassword(clienteDAO.encriptarContraseña(password));
			trabajadorDAO.anadirTrabajador(nuevoTrabajador);
		}
		else 
			return "redirect:/datosTrabajadores?mensaje=Email YA Registrado";
		

		return "redirect:/datosTrabajadores?mensaje=Trabajador Registrado Correctamente";
		
	}
	
	// HOME TRABAJADOR
	
	
	
	
	@RequestMapping(value = "/verificarEmail", method = RequestMethod.GET)
	public String emailverificado(Model model, HttpServletRequest request ,HttpSession session) {
	
		String uuid = request.getParameter("uuid");
		
		Cliente cl = clienteDAO.obtenerClienteporUuid(uuid);
		
		cl.setTipoAcceso(1);
		
		clienteDAO.ActualizarCliente(cl);
		
		return "redirect:/?mensaje=Email verificado";
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value="/cerrarSesion", method = RequestMethod.GET)
	public String cerrarSesion(HttpSession session) {
		session.setAttribute("clienteLogin", null);
		
		return "redirect:/?mensaje=Sesion Cerrada Con exito";
	}
	
	@RequestMapping(value="/cerrarSesionTrabajador", method = RequestMethod.GET)
	public String cerrarSesionTrabajador(HttpSession session) {
		session.setAttribute("trabajadorLogin", null);
		
		return "redirect:/loginTrabajador?mensaje=Sesion Cerrada Con exito";
	}
	
	private boolean noLogueado(HttpSession session) {
		Cliente clienteLogueado = (Cliente) session.getAttribute("clienteLogin");
		if (clienteLogueado == null || session.isNew()) {
			return true;
		}
		return false;
	}
	
	private boolean noLogueadoTrabajador(HttpSession session) {
		Trabajador trabajadorLogueado = (Trabajador) session.getAttribute("trabajadorLogin");
		if (trabajadorLogueado == null || session.isNew()) {
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
		
		Date fechaEntradaDate = convertirStringaDate(fechaEntrada);
		Date fechaSalidaDate = convertirStringaDate(fechaSalida);
		
		Date dateobj = new Date();
		dateobj = convertirStringaDate(formatoDelTexto.format(dateobj));
		
		if(fechaSalidaDate.before(dateobj) || fechaEntradaDate.before(dateobj))
			fechaCorrecta = false;
		
		
		if(fechaSalidaDate.before(fechaEntradaDate) | fechaSalidaDate.equals(fechaEntradaDate))
			fechaCorrecta = false;
		
		
		return fechaCorrecta;
		
		
	}
	
	
	private boolean comrpuebaHabitacionLibre(String fechaEntrada, String fechaSalida, int idHabitacion , int numHabitaciones) {
		boolean estaLibre = true;
	
		Date fechaEntradaDate = convertirStringaDate(fechaEntrada);
		Date fechaSalidaDate = convertirStringaDate(fechaSalida);
		
		//Obtengo todas las reservas de ese tipo de habitacion
		List<Reserva> reservasPorTipo = reservaDAO.listarReservaPorTipoHabitacion(idHabitacion);
		

		//OBTENGO TODAS LAS FECHAS ENTRE LA ENTRADA Y SALIDA
		List<Date> fechas = obtenerFechasEntreDates(fechaEntradaDate, fechaSalidaDate);
		
		System.out.println("");
		
		int habitacionOcupada;
		for (Date date : fechas) {
			habitacionOcupada = 0;
			for (Reserva r : reservasPorTipo) {
				
				if( (date.after(convertirStringaDate(r.getFechaEntrada())) && date.before(convertirStringaDate(r.getFechaSalida()))) | (date.equals(convertirStringaDate(r.getFechaEntrada())) | date.equals(convertirStringaDate(r.getFechaSalida())))   );
				 habitacionOcupada ++;
			}
			
			if(habitacionOcupada >= numHabitaciones) {
				estaLibre = false;
				break;
			}
		}
		/*
		 	PARA VER LOS DIAS
		 for (Date date : fechas) {
			String formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd").format(date);
			
			System.out.println("s");
		}*/
		
		
		
		System.out.println("eh");
		return estaLibre;
	}	
	
	private List<Date> obtenerFechasEntreDates(Date fechaEntradaDate, Date fechaSalidaDate) {
		
		List<Date> fechas = new ArrayList<Date>();
		
		while(!fechaEntradaDate.after(fechaSalidaDate)) {
			fechas.add(fechaEntradaDate);
			
			Calendar c = Calendar.getInstance(); 
			c.setTime(fechaEntradaDate); 
			c.add(Calendar.DATE, 1);
			fechaEntradaDate = c.getTime();		
		}
		
		return fechas;
	}
	
	
	private double precioFecha(String fechaEntrada,String fechaSalida, int tipoHabitacion) {
		
		boolean fechaCorrecta = true;
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		
		Date fechaEntradaDate = convertirStringaDate(fechaEntrada);
		Date fechaSalidaDate = convertirStringaDate(fechaSalida);
	
		float dias = diasEntreDosFechas(fechaEntradaDate, fechaSalidaDate);
		
	
		
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
	
	private float diasEntreDosFechas(Date fechaEntradaDate, Date fechaSalidaDate) {
		
		long diferencia = fechaEntradaDate.getTime() - fechaSalidaDate.getTime();
		float dias = (diferencia / (1000*60*60*24)) * -1;
		
		return dias;
	}
	
	private Date convertirStringaDate(String fecha) {
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");	
		Date fecha1 = null;
		
		try {
			fecha1 = formatoDelTexto.parse(fecha);
		}
		catch (ParseException ex) {
			ex.printStackTrace();
		}
		
		return fecha1;
	}
	
	
	private void enviarMail(Cliente c) throws MessagingException {
		
		MimeMessage message = mailsender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setSubject("Verificacion Cuenta");
		helper.setTo(c.getCorreo());
		helper.setText("Hola buenas!\n Gracias por registrarse en Hotel Altair \n Para completar el registro solo tiene que pulsar en el siguiente enlace: http://hotelaltairproyecto.com/HotelAltair/verificarEmail?uuid="+c.getUuid() + "\n\n  Recuerde leer nuestras condiciones al pie de la pagina de Inicio");
		
		mailsender.send(message);
		
	}
	
	private void enviarMailReserva(Cliente c, Reserva r) throws MessagingException {
		
		MimeMessage message = mailsender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setSubject("Reserva hotel Altair");
		helper.setTo(c.getCorreo());
		helper.setText("Hola buenas!\n Entregue lo siguiente en recepcion a su llegada al hotel como resguardo de la reserva. Recuerde que sin él no se le garantiza su hospedaje:\nEUWar: " + r.getUuid() +"\n Precio total: " + r.getPrecioAPagar() + " Euros" + "\nFecha Entrada: " + r.getFechaEntrada() +" \n Fecha Salida: " + r.getFechaSalida()  +"\n\n  Recuerde leer nuestras condiciones al pie de la pagina de Inicio");
		
		
		mailsender.send(message);
		
	}
	
	private void enviarMailCancelarReserva(Cliente c, Reserva r) throws MessagingException {
		
		MimeMessage message = mailsender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setSubject("Cancelacion Reserva hotel Altair");
		helper.setTo(c.getCorreo());
		helper.setText("Hola buenas!\n Su reserva con los siguientes datos ha sido cancelada\n Fecha Entrada: " + r.getFechaEntrada() +" \n Fecha Salida: " + r.getFechaSalida() + "\n Precio Total: " + r.getPrecioAPagar() + " Euros"  +"\n\n  Recuerde leer nuestras condiciones al pie de la pagina de Inicio");
		
		
		mailsender.send(message);
		
	}
	
	
	
	
	private String uuidAleatorio() {
		
		String uui = UUID.randomUUID().toString();
		
		return uui;
	}
	
	
	/*@RequestMapping(value = "/loginTrabajador", method = RequestMethod.GET)
	public ModelAndView loginTrabajador(Model model,HttpSession session, @RequestParam(value = "mensaje", required= false, defaultValue="") String mensaje) {
		
		model.addAttribute("mensaje", mensaje);
		
		
		model.addAttribute("trabajadorLogin", (Trabajador)session.getAttribute("trabajadorLogin"));
		
		return new ModelAndView("loginTrabajador", "tra", new Trabajador());
	}*/
	
	
	/*@RequestMapping(value = "/entrarTrabajador", method = RequestMethod.POST)
	public String entrarTrabajador(@ModelAttribute Trabajador trabajadorLogin, Model model, HttpSession session) {
		
		
		if(((Cliente)session.getAttribute("clienteLogin")) != null)
	    	return "redirect:/loginTrabajador?mensaje=Debe cerrar sesion como Cliente para continuar";
		
		//HASTA QUE EL ADMINISTRADOR NO PUEDA CREAR TRABAJADORES
		trabajadorLogin.setPassword(clienteDAO.encriptarContraseña(trabajadorLogin.getPassword()));
		trabajadorLogin = trabajadorDAO.comprobarTrabajador(trabajadorLogin.getCorreo(), trabajadorLogin.getPassword());
		
		if(trabajadorLogin != null) {
			if(trabajadorLogin.getTipoAcceso() == 0)
				return "redirect:/loginTrabajador?mensaje=Trabajador dado de baja";
			
			
			session.setAttribute("trabajadorLogin", trabajadorLogin);
			
			return "redirect:/homeTrabajador?mensaje=Sesion Iniciada Con exito";
		}
		System.out.println("Sesion no Iniciada");
		return "redirect:/loginTrabajador?mensaje=Error en email o Password Incorrecto";
		
	}*/
	
	/*
	@RequestMapping(value = "/homeTrabajador", method = RequestMethod.GET)
	public String homeTrabajador(Model model,HttpSession session, @RequestParam(value = "mensaje", required= false, defaultValue="") String mensaje) {
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		
		model.addAttribute("mensaje", mensaje);
		model.addAttribute("listarH", habitacionDAO.listarHabitaciones());
		
		model.addAttribute("trabajadorLogin", (Trabajador)session.getAttribute("trabajadorLogin"));
		
		return "homeTrabajador";
	} */
	
/*	@RequestMapping(value = "/eliminarCliente", method = RequestMethod.GET)
	public String eliminarCliente(Model model, HttpSession session, HttpServletRequest request) {
		
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		
		String uuid = request.getParameter("uuid");
		
		
		
		List<Reserva> reservasEliminar = reservaDAO.listarPorCliente(clienteDAO.obtenerClienteporUuid(uuid));
		
		System.out.println("");
		
		if(reservasEliminar != null) {
			
			for (Reserva reserva : reservasEliminar) {
				reservaDAO.borrarReserva(reserva.getIdReserva());
			}
			
		}
		
		clienteDAO.borrarCliente(uuid);

		return "redirect:/datosClientes?mensaje=Cliente Borrado Correctamente";
		
	}*/
	/*
	@RequestMapping(value = "/eliminarTrabajador", method = RequestMethod.GET)
	public String eliminarTrabajador(Model model, HttpSession session, HttpServletRequest request) {
		
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		
		int idTrabajador = Integer.parseInt(request.getParameter("idTrabajador"));
		
		Trabajador tra = trabajadorDAO.obtenerTrabajadorporId(idTrabajador);
		
		if(tra.getTipoAcceso() == 2 && trabajadorDAO.listarAdministradores().size() == 1) {
			return "redirect:/datosTrabajadores?mensaje=**Error** Debe al menos haber un administrador";
		}
		
		
		
		tra.setTipoAcceso(0);
		
		
		trabajadorDAO.ActualizarTrabajador(tra);


		return "redirect:/datosTrabajadores?mensaje=Trabajador dado de baja Correctamente";
		
	}*/
	
	/*
	@RequestMapping(value="/reservaTrabajador", method = RequestMethod.GET)
	public String reservaTrabajador(Model model, HttpServletRequest request, HttpSession session, @RequestParam(value = "mensaje", required= false, defaultValue="") String mensaje) {
		
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		
		 model.addAttribute("mensaje", mensaje);
		
		 String uuid = request.getParameter("uuid");
		 Habitacion habitacionReservar = habitacionDAO.obtenerHabitacionPorUuid(uuid);
		 
		 model.addAttribute("habitacion", habitacionReservar);
		 model.addAttribute("trabajadorLogin", (Trabajador)session.getAttribute("trabajadorLogin"));
		
		return "reservaTrabajador";
	} */
	
	/*@RequestMapping(value="/datosClientes", method = RequestMethod.GET)
	public String datosClientes(Model model, HttpServletRequest request, HttpSession session, @RequestParam(value = "mensaje", required= false, defaultValue="") String mensaje) {
		
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		
		model.addAttribute("mensaje", mensaje);
		model.addAttribute("listaClientes", clienteDAO.listarClientes());
		model.addAttribute("listaReservas", reservaDAO.listarTodos());
		
		
		return "datosClientes";
	}*/
	
	/*@RequestMapping(value="/datosTrabajadores", method = RequestMethod.GET)
	public String datosTrabajadores(Model model, HttpServletRequest request, HttpSession session, @RequestParam(value = "mensaje", required= false, defaultValue="") String mensaje) {
		
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		
		model.addAttribute("mensaje", mensaje);
		
		Trabajador trabajadorLogin = (Trabajador)session.getAttribute("trabajadorLogin");
		
		if(trabajadorLogin.getTipoAcceso() == 2)
			model.addAttribute("listaTrabajadores", trabajadorDAO.listarTrabajadoresAdmin());
		else
		model.addAttribute("listaTrabajadores", trabajadorDAO.listarTrabajadores());
		
		model.addAttribute("tipoT", trabajadorLogin);
		
		
		return "datosTrabajadores";
	} */
	
	/*@RequestMapping(value = "/reservasCliente", method = RequestMethod.GET)
	public String reservasCliente(Model model, HttpServletRequest request ,HttpSession session) {
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		
		
		String uuid = request.getParameter("uuid");
		
		Cliente n = clienteDAO.obtenerClienteporUuid(uuid);
		
		List<Reserva> listaRservas = reservaDAO.listarPorCliente(n);
		
		System.out.println("");
		
		if(listaRservas.size() == 0 )
			return "redirect:/datosClientes?mensaje=El cliente no tiene ninguna reserva";
		
		model.addAttribute("listarR", listaRservas);
		model.addAttribute("trabajadorLogin", (Trabajador)session.getAttribute("trabajadorLogin"));
		
		
		
		
		return ("misReservas");
		
	}*/
	
	
	/*@RequestMapping(value = "/contabilidad", method = RequestMethod.GET)
	public String contabilidad(Model model, HttpServletRequest request ,HttpSession session) {
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		
		Trabajador trabajador = (Trabajador)session.getAttribute("trabajadorLogin");
		
		if(trabajador.getTipoAcceso() != 2)
		 return "redirect:/homeTrabajador?mensaje=No tiene el tipo de autorizacion para entrar aqui";
		
		List<Reserva> nReserva = reservaDAO.listarTodos();
		
		double total = 0;
		
		for (Reserva reserva : nReserva) {
			total = total + reserva.getPrecioAPagar();
		}
		
		model.addAttribute("listaReservas", nReserva );
		model.addAttribute("total", total);
				
		return ("contabilidad");
		
	}*/
	
	
/*	@RequestMapping(value="/confirmarReservaTrabajador", method = RequestMethod.POST)
	public String confirmarReservaTrabajador(Model model, HttpServletRequest request, HttpSession session) {

		
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		String correoCliente = request.getParameter("correo");
		
		Cliente cliente = clienteDAO.obtenerClienteporCorreo(correoCliente);
		

		if(cliente == null) {
			return "redirect:/homeTrabajador?mensaje=Correo de Cliente Erroneo";
		}	
		
		if(cliente.getTipoAcceso() == 0)
			return "redirect:/homeTrabajador?mensaje=El cliente no ha verificado su cuenta aun";
			
		Trabajador trabajador = (Trabajador)session.getAttribute("trabajadorLogin");	
		
		int idHabitacion = Integer.parseInt(request.getParameter("idHabitacion"));
		Habitacion habitacionReservar = habitacionDAO.obtenerHabitacionPorId(idHabitacion);
		String fechaEntrada = request.getParameter("fechaEntrada");
		String fechaSalida =  request.getParameter("fechaSalida");
		
		String tipoPago =request.getParameter("tipoPago");
		
		//INICIO COMPROBACIONES DE FECHA
		
		boolean fechaCorrecta = compruebaFechas(fechaEntrada, fechaSalida);
		
		if(!compruebaFechas(fechaEntrada, fechaSalida)) {
			return "redirect:/homeTrabajador?mensaje=Fecha de Salida Incorrecta vuelva a intentarlo";
		}
		
		if (!comrpuebaHabitacionLibre(fechaEntrada, fechaSalida, habitacionReservar.getIdHabitacion(), habitacionReservar.getNumeroHab())) {
			 return "redirect:/homeTrabajador?mensaje=Todas las habitaciones estan ocupadas por al menos un dia en concreto, vuelva a intentarlo";
		}
		
		
		//FIN COMPROBACIONES DE FECHA
		
		double precioApagar = precioFecha(fechaEntrada, fechaSalida ,habitacionReservar.getTipoHabitacion());
		
		
		
		Reserva nuevaReserva = new Reserva(cliente, trabajador, habitacionReservar, fechaEntrada, fechaSalida, precioApagar, tipoPago, uuidAleatorio());
		
		reservaDAO.insertarReserva(nuevaReserva);
		
		try {
			enviarMailReserva(cliente, nuevaReserva);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/homeTrabajador?mensaje=Reserva Realizada Con exito, no olvide revisar el correo";
	}*/
	
	
	
	
}
