package org.java.hibernate.mappings.onetomanymapping.bidirectional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AuthorBD {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<BookBD> books = new ArrayList<>();

	public AuthorBD() {
	}

	public AuthorBD(String name) {
		this.name = name;
	}

	public void addBook(BookBD book) {
		books.add(book);
		book.setAuthor(this); // Set the owning side
	}

	// Getters and setters (optional if using Lombok or for clarity)
}
