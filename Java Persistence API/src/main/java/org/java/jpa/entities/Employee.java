package org.java.jpa.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;

import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
public class Employee extends BaseEntity {

	private String name;

	@Embedded
	private Address address;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Department department;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
			name = "employee_project",
			joinColumns = @JoinColumn(name = "emp_id"),
			inverseJoinColumns = @JoinColumn(name = "project_id")
	)
	private Set<Project> projects = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public void addProject(Project project) {
		this.projects.add(project);
		project.getEmployees().add(this);
	}

	public void removeProject(Project project) {
		this.projects.remove(project);
		project.getEmployees().remove(this);
	}

	public void removeProjects() {
		for (Project project : new HashSet<>(projects)) {
			removeProject(project);
		}
	}

	public void setDepartmentAndAddToEmployees(Department department) {
		if (this.department != null) {
			this.department.getEmployees().remove(this);
		}
		this.department = department;
		if (department != null) {
			department.getEmployees().add(this);
		}
	}

	public void removeDepartment() {
		if (this.department != null) {
			this.department.getEmployees().remove(this);
			this.department = null;
		}
	}

	public void removeDepartmentAndRemoveFromEmployees() {
		if (this.department != null) {
			this.department.getEmployees().remove(this);
			this.department = null;
		}
	}
}

