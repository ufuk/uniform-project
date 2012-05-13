package uniform.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public abstract class AbstractDAO {
	
	private static SessionFactory sessionFactory;
	
	private ServiceRegistry serviceRegistry;
	
	protected Session session;
	
	protected Session openSession() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
			    configuration.configure();
			    serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
			    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory.openSession();
	}
	
}
