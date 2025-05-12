package org.java.hibernate.mappings.manytoonemapping.unidirectional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManyToOneMapping {
	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Customer.class)
				.addAnnotatedClass(Order.class)
				.buildSessionFactory();

		try (Session session = factory.openSession()) {
			Customer customer = new Customer("Bob");

			Order order1 = new Order();
			order1.setProduct("Laptop");
			order1.setCustomer(customer); // Set parent

			Order order2 = new Order();
			order2.setProduct("Phone");
			order2.setCustomer(customer); // Set parent

			session.beginTransaction();

			// Save orders — customer will also be saved if cascade is set properly
			session.persist(order1);
			session.persist(order2);

			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}
}
