package es.altair.hotelAltair.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class TrabajadorController {
	
	
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
	

	
	@RequestMapping(value = "/loginTrabajador", method = RequestMethod.GET)
	public ModelAndView loginTrabajador(Model model,HttpSession session, @RequestParam(value = "mensaje", required= false, defaultValue="") String mensaje) {
		
		model.addAttribute("mensaje", mensaje);
		
		
		model.addAttribute("trabajadorLogin", (Trabajador)session.getAttribute("trabajadorLogin"));
		
		return new ModelAndView("loginTrabajador", "tra", new Trabajador());
	}
	
	
	@RequestMapping(value = "/entrarTrabajador", method = RequestMethod.POST)
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
		
	}
	
	
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
	}
	
	@RequestMapping(value = "/eliminarCliente", method = RequestMethod.GET)
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
		
	}
	
	
	@RequestMapping(value = "/eliminarTrabajador", method = RequestMethod.GET)
	public String eliminarTrabajador(Model model, HttpSession session, HttpServletRequest request) {
		
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		
		String uuid = request.getParameter("uuid");
		
		Trabajador tra = trabajadorDAO.obtenerTrabajadorporUuid(uuid);
		
		if(tra.getTipoAcceso() == 2 && trabajadorDAO.listarAdministradores().size() == 1) {
			return "redirect:/datosTrabajadores?mensaje=**Error** Debe al menos haber un administrador";
		}
		
		
		
		tra.setTipoAcceso(0);
		
		
		trabajadorDAO.ActualizarTrabajador(tra);


		return "redirect:/datosTrabajadores?mensaje=Trabajador dado de baja Correctamente";
		
	}
	
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
	}
	
	@RequestMapping(value="/datosClientes", method = RequestMethod.GET)
	public String datosClientes(Model model, HttpServletRequest request, HttpSession session, @RequestParam(value = "mensaje", required= false, defaultValue="") String mensaje) {
		
		
		if(noLogueadoTrabajador(session)) {
			model.addAttribute("errorLogin","Inicie sesión para entrar");
			return "redirect:/loginTrabajador?mensaje=Debe iniciar Sesion para continuar";
		}
		
		model.addAttribute("mensaje", mensaje);
		model.addAttribute("listaClientes", clienteDAO.listarClientes());
		model.addAttribute("listaReservas", reservaDAO.listarTodos());
		
		
		return "datosClientes";
	}
	
	
	@RequestMapping(value="/datosTrabajadores", method = RequestMethod.GET)
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
	}
	
	@RequestMapping(value = "/reservasCliente", method = RequestMethod.GET)
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
	}
	
	@RequestMapping(value = "/contabilidad", method = RequestMethod.GET)
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
		
	}
	
	@RequestMapping(value="/confirmarReservaTrabajador", method = RequestMethod.POST)
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
	}
	
private void enviarMailReserva(Cliente c, Reserva r) throws MessagingException {
		
		MimeMessage message = mailsender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setSubject("Reserva hotel Altair");
		helper.setTo(c.getCorreo());
		helper.setText("Hola buenas!\n Entregue lo siguiente en recepcion a su llegada al hotel como resguardo de la reserva. Recuerde que sin él no se le garantiza su hospedaje:\nEUWar: " + r.getUuid() +"\n Precio total: " + r.getPrecioAPagar() + " Euros" + "\nFecha Entrada: " + r.getFechaEntrada() +" \n Fecha Salida: " + r.getFechaSalida()  +"\n\n  Recuerde leer nuestras condiciones al pie de la pagina de Inicio");
		
		
		mailsender.send(message);
		
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
	
	private float diasEntreDosFechas(Date fechaEntradaDate, Date fechaSalidaDate) {
		
		long diferencia = fechaEntradaDate.getTime() - fechaSalidaDate.getTime();
		float dias = (diferencia / (1000*60*60*24)) * -1;
		
		return dias;
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
	
	
	private boolean noLogueadoTrabajador(HttpSession session) {
		Trabajador trabajadorLogueado = (Trabajador) session.getAttribute("trabajadorLogin");
		if (trabajadorLogueado == null || session.isNew()) {
			return true;
		}
		return false;
	}
	
private String uuidAleatorio() {
		
		String uui = UUID.randomUUID().toString();
		
		return uui;
	}
	
	
	
}
