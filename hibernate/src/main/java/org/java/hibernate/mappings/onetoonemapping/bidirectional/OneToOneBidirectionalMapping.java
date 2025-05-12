package org.java.hibernate.mappings.onetoonemapping.bidirectional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class OneToOneBidirectionalMapping {
	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Person.class)
				.addAnnotatedClass(Passport.class)
				.buildSessionFactory();

		Session session = factory.openSession();

		try {
			Person person = new Person("John Doe");
			Passport passport = new Passport("X123456");

			// Set both sides of the relationship
			person.setPassport(passport);
			passport.setPerson(person);

			session.beginTransaction();
			session.persist(passport); // or person — both sides are linked
			session.getTransaction().commit();

			System.out.println("Saved person with passport.");
		} finally {
			session.close();
			factory.close();
		}
	}
}
