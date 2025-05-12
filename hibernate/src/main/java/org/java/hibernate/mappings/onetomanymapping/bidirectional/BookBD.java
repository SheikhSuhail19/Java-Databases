package org.java.hibernate.mappings.onetomanymapping.bidirectional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BookBD {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@ManyToOne
	@JoinColumn(name = "author_id") // This is the owning side
	private AuthorBD author;

	public BookBD() {
	}

	public BookBD(String title) {
		this.title = title;
	}

	public void setAuthor(AuthorBD author) {
		this.author = author;
	}

	// Getters and setters (optional)
}
