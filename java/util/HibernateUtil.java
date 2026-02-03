package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();
	
    private static SessionFactory buildSessionFactory() {
        try {
        	Configuration config = new Configuration();
        	
        	config.configure("hibernate.cfg.xml");

        	return config.buildSessionFactory();
            
        } catch (Exception e) {
            System.err.println("Initial SessionFactory creation failed.");
            throw new RuntimeException("There was an error building the factory session: ".concat(e.toString()));
        }
    }
    
    public static Session openSession() {
        return sessionFactory.openSession();
    }

    public static void shutdown() {
        sessionFactory.close();
    }
}
