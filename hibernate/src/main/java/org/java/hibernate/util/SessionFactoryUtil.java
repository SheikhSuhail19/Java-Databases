package org.java.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.java.hibernate.crud.User;
import org.java.hibernate.interceptor.LoggingInterceptor;

public class SessionFactoryUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration()
					.configure("hibernate.cfg.xml")  // Read from hibernate.cfg.xml
					.addAnnotatedClass(User.class)
					.setInterceptor(new LoggingInterceptor())
					.buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed: " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		getSessionFactory().close();
	}
}
