package org.java.hibernate.querying.nativesql;

import org.hibernate.Session;
import org.java.hibernate.querying.EmployeeTest;
import org.java.hibernate.util.SessionFactoryUtil;

import java.util.List;

public class NativeSQLDemo {

	public static void main(String[] args) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();

		// Insert using Hibernate (for test data)
		session.beginTransaction();
		session.persist(new EmployeeTest("Alice", "HR", 50000));
		session.persist(new EmployeeTest("Bob", "IT", 70000));
		session.persist(new EmployeeTest("Charlie", "Sales", 60000));
		session.getTransaction().commit();

		// 1. SELECT all
		List<Object[]> employees = session.createNativeQuery("SELECT * FROM h8.employeetest").list();
		for (Object[] row : employees) {
			System.out.println("Employee: ID=" + row[0] + ", Name=" + row[1]);
		}

		// 2. SELECT with mapping to entity
		List<EmployeeTest> mapped =
				session.createNativeQuery("SELECT * FROM h8.employeetest", EmployeeTest.class).list();
		mapped.forEach(e -> System.out.println("Mapped: " + e.getName() + ", " + e.getDepartment()));

		// 3. Parameterized query
		List<EmployeeTest> itEmployees = session.createNativeQuery(
						"SELECT * FROM h8.employeetest WHERE department = :dept", EmployeeTest.class)
				.setParameter("dept", "IT")
				.list();
		itEmployees.forEach(e -> System.out.println("IT Employee: " + e.getName()));

		// 4. UPDATE
		session.beginTransaction();
		int updated = session.createNativeQuery(
						"UPDATE h8.employeetest SET salary = salary + 1000 WHERE department = :dept")
				.setParameter("dept", "Sales")
				.executeUpdate();
		session.getTransaction().commit();
		System.out.println("Updated rows: " + updated);

		// 5. DELETE
		session.beginTransaction();
		int deleted = session.createNativeQuery("DELETE FROM h8.employeetest WHERE salary < 55000")
				.executeUpdate();
		session.getTransaction().commit();
		System.out.println("Deleted rows: " + deleted);

		session.close();
		SessionFactoryUtil.shutdown();
	}
}
