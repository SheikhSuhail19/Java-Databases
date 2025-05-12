package org.java.hibernate.mappings.onetomanymapping.bidirectional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class OneToManyBidirectionalMapping {
	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(AuthorBD.class)
				.addAnnotatedClass(BookBD.class)
				.buildSessionFactory();

		try (factory; Session session = factory.openSession()) {
			AuthorBD author = new AuthorBD("Stephen King");

			// Add books (will set both sides)
			author.addBook(new BookBD("The Shining"));
			author.addBook(new BookBD("IT"));
			author.addBook(new BookBD("Misery"));

			session.beginTransaction();
			session.persist(author); // Cascades to books
			session.getTransaction().commit();

			System.out.println("Author and books saved!");

			session.beginTransaction();

			// Fetch the existing author
			AuthorBD existingAuthor = session.get(AuthorBD.class, 1L); // existing ID

			if (existingAuthor == null) {
				throw new RuntimeException("Author not found!");
			}

			// Create a new book and set the author
			BookBD book = new BookBD("Doctor Sleep");
			book.setAuthor(existingAuthor); // Setting the owning side

			// Save the book
			session.persist(book);

			session.getTransaction().commit();
			System.out.println("Book saved and linked to author!");
		}

	}
}
