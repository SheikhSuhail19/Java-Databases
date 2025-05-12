package org.java.hibernate.mappings.onetoonemapping.unidirectional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String bio;

	public UserProfile() {
	}

	public UserProfile(String bio) {
		this.bio = bio;
	}

	// Getters and setters omitted for brevity
}
