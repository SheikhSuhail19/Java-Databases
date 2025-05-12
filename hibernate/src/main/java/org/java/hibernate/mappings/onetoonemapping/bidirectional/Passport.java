package org.java.hibernate.mappings.onetoonemapping.bidirectional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Passport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String number;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "person_id")
	private Person person;

	public Passport() {
	}

	public Passport(String number) {
		this.number = number;
	}

	// Getters and setters
	public void setPerson(Person person) {
		this.person = person;
	}

	public String getNumber() {
		return number;
	}
}
