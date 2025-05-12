package org.java.hibernate.mappings.onetomanymapping.unidirectional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class OneToManyMapping {
	public static void main(String[] args) {
		// Create SessionFactory
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Author.class)
				.addAnnotatedClass(Book.class)
				.buildSessionFactory();

		// Create Session

		try (factory; Session session = factory.openSession()) {
			Book book1 = new Book("The Stand");
			Book book2 = new Book("The Shining");
			Book book3 = new Book("The Dark Tower");

			Author author  = new Author();
			author.setBooks(List.of(book1, book2, book3));

			// Start a transaction
			session.beginTransaction();

			// Save the user (this will also save the userProfile due to cascading)
			session.persist(author);

			// Commit the transaction
			session.getTransaction().commit();
		}
	}
}
