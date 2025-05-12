package org.java.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.java.jpa.entities.Address;
import org.java.jpa.entities.Department;
import org.java.jpa.entities.Employee;
import org.java.jpa.entities.Project;
import org.java.jpa.util.JPAUtil;

import java.util.List;


public class App {
	public static void main(String[] args) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		Department dep = new Department();
		dep.setName("Engineering");

		Project p1 = new Project();
		p1.setProjectName("Project Alpha");

		Project p2 = new Project();
		p2.setProjectName("Project Beta");

		Employee emp1 = new Employee();
		emp1.setName("Alice");
		emp1.setAddress(new Address("123 Main St", "Springfield", "12345"));
		emp1.setDepartment(dep);
		emp1.getProjects().add(p1);
		emp1.getProjects().add(p2);

		Employee emp2 = new Employee();
		emp2.setName("Bob");
		emp2.setAddress(new Address("456 Side St", "Metropolis", "67890"));
		emp2.setDepartment(dep);
		emp2.getProjects().add(p1);

		dep.getEmployees().add(emp1);
		dep.getEmployees().add(emp2);

		em.persist(dep); // cascade will save everything

		tx.commit();

		// JPQL query
		List<Employee> all = em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
		all.forEach(e -> System.out.println("Found: " + e.getName()));

		// Criteria query example
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
		Root<Employee> root = cq.from(Employee.class);
		cq.select(root).where(cb.equal(root.get("name"), "Alice"));
		List<Employee> found = em.createQuery(cq).getResultList();
		System.out.println("Criteria Found: " + found.size());

		em.close();
		JPAUtil.closeEntityManager();
	}
}

