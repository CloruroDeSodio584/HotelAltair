package es.altair.hotelAltair.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionProvider {

	private static Session session;
	private static Configuration configuration;
	
	public SessionProvider() {		
	}
	
	static {
		configuration = new Configuration()
				.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
				.setProperty("hibernate.connection.driver.class", "com.mysql.jdbc.Driver")
				.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/bdhotelaltair")
				.setProperty("hibernate.connection.username", "root")
				.setProperty("hibernate.connection.password", "altair")
				.setProperty("hibernate.show_sql", "true")
				.addAnnotatedClass(es.altair.hotelAltair.bean.Cliente.class)
				.addAnnotatedClass(es.altair.hotelAltair.bean.Habitacion.class)
				.addAnnotatedClass(es.altair.hotelAltair.bean.Trabajador.class)
				.addAnnotatedClass(es.altair.hotelAltair.bean.Reserva.class);
			
	}
	
	public static Session getSession() {
		SessionFactory sf = configuration.configure().buildSessionFactory();
		session = sf.openSession();
		return session;
	}
	
	
}



  