package org.java.hibernate.querying.namedquery;

import org.hibernate.Session;
import org.java.hibernate.querying.EmployeeTest;
import org.java.hibernate.util.SessionFactoryUtil;

import java.util.List;

public class NamedQueryDemo {
	public static void main(String[] args) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();

		// Insert some data
		session.beginTransaction();
		session.persist(new EmployeeTest("Alice", "HR", 50000));
		session.persist(new EmployeeTest("Bob", "IT", 70000));
		session.persist(new EmployeeTest("Charlie", "IT", 80000));
		session.persist(new EmployeeTest("Daisy", "Sales", 60000));
		session.getTransaction().commit();

		// ========== Named Query Example ==========

		// Using the named query defined in the entity
		List<EmployeeTest> itEmployees = session.createNamedQuery("EmployeeTest.findByDepartment", EmployeeTest.class)
				.setParameter("dept", "IT")
				.getResultList();

		// Print the results
		itEmployees.forEach(e -> System.out.println("EmployeeTest: " + e.getName() + " from " + e.getDepartment()));

		session.close();
		SessionFactoryUtil.shutdown();
	}
}
