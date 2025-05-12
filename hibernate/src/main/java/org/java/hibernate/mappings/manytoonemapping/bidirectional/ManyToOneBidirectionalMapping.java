package org.java.hibernate.mappings.manytoonemapping.bidirectional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManyToOneBidirectionalMapping {
	public static void main(String[] args) {

		try (SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(CustomerBi.class)
				.addAnnotatedClass(OrderBi.class)
				.buildSessionFactory(); Session session = factory.openSession()) {
			CustomerBi customerBi = new CustomerBi();
			customerBi.setName("Alice");

			OrderBi order1 = new OrderBi();
			order1.setProduct("Tablet");
			order1.setCustomerBi(customerBi); // Link child to parent

			OrderBi order2 = new OrderBi();
			order2.setProduct("Smartwatch");
			order2.setCustomerBi(customerBi);

			// Link parent to children
			customerBi.getOrders().add(order1);
			customerBi.getOrders().add(order2);

			session.beginTransaction();

			// Save parent (cascades to orders)
			session.persist(customerBi);

			session.getTransaction().commit();

			System.out.println("Customer and orders saved!");

			session.beginTransaction();

			// Fetch existing customer
			CustomerBi existingCustomer = session.get(CustomerBi.class, 1L); // Existing ID
			if (existingCustomer == null) {
				throw new RuntimeException("Customer not found!");
			}

			System.out.println("Customer: " + existingCustomer);

			// now save order to propagate to customer
			OrderBi order3 = new OrderBi();
			order3.setProduct("Laptop");
			order3.setCustomerBi(existingCustomer); // Link child to parent
			session.persist(order3);

			session.getTransaction().commit();
		}
	}
}
