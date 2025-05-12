package org.java.hibernate.mappings.manytomanymapping.bidirectional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CourseBi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;

	@ManyToMany(mappedBy = "courses")
	private List<StudentBi> students = new ArrayList<>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<StudentBi> getStudents() {
		return students;
	}

	public void setStudents(List<StudentBi> students) {
		this.students = students;
	}
}
