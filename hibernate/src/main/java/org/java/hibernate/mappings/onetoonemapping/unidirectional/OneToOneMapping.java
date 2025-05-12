package org.java.hibernate.mappings.onetoonemapping.unidirectional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class OneToOneMapping {
	public static void main(String[] args) {
		// Create SessionFactory
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(FBUser.class)
				.addAnnotatedClass(UserProfile.class)
				.buildSessionFactory();

		// Create Session

		try (factory; Session session = factory.openSession()) {
			// Create a new UserProfile
			UserProfile userProfile = new UserProfile("This is a bio");

			// Create a new User with the profile
			FBUser newUser = new FBUser("John Doe", userProfile);

			// Start a transaction
			session.beginTransaction();

			// Save the user (this will also save the userProfile due to cascading)
			session.persist(newUser);

			// Commit the transaction
			session.getTransaction().commit();

			// Lazy Loading
			session.beginTransaction();
			FBUser user = session.get(FBUser.class, 1L);
			System.out.println("Student name: " + user.getName());

			System.out.println("Now accessing courses...");
			System.out.println("Courses: " + user.getUserProfile());  // triggers lazy load
		}
	}
}
