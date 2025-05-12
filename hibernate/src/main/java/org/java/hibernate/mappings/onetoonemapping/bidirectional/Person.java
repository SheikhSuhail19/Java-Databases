package org.java.hibernate.mappings.onetoonemapping.bidirectional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
	private Passport passport;

	public Person() {
	}

	public Person(String name) {
		this.name = name;
	}

	// Getters and setters
	public void setPassport(Passport passport) {
		this.passport = passport;
	}

	public String getName() {
		return name;
	}
}



