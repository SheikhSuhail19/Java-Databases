package org.java.hibernate.querying.hql;

import org.hibernate.Session;
import org.java.hibernate.querying.EmployeeTest;
import org.java.hibernate.util.SessionFactoryUtil;

import java.util.List;

public class HQLDemo {

	public static void main(String[] args) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();

		// Insert Data
		session.beginTransaction();
		session.persist(new EmployeeTest("Alice", "HR", 50000));
		session.persist(new EmployeeTest("Bob", "IT", 70000));
		session.persist(new EmployeeTest("Charlie", "IT", 80000));
		session.persist(new EmployeeTest("Daisy", "Sales", 60000));
		session.getTransaction().commit();

		// ========== HQL Examples ==========

		// 1. SELECT all
		List<EmployeeTest> all = session.createQuery("from EmployeeTest", EmployeeTest.class).list();
		all.forEach(e -> System.out.println("EmployeeTest: " + e.getName()));

		// 2. SELECT with WHERE
		List<EmployeeTest> itEmployeeTests = session.createQuery(
						"from EmployeeTest where department = :dept", EmployeeTest.class)
				.setParameter("dept", "IT")
				.list();

		// 3. Projection (select name only)
		List<String> names = session.createQuery("select name from EmployeeTest", String.class).list();

		// 4. Aggregate + GROUP BY
		List<Object[]> groupByDept = session.createQuery(
				"select department, avg(salary) from EmployeeTest group by department").list();
		for (Object[] row : groupByDept)
			System.out.println(row[0] + " avg salary: " + row[1]);

		// 5. ORDER BY
		List<EmployeeTest> sorted = session.createQuery("from EmployeeTest order by salary desc", EmployeeTest.class).list();

		// 6. UPDATE
		session.beginTransaction();
		int updated = session.createQuery(
						"update EmployeeTest set salary = salary + 1000 where department = :dept")
				.setParameter("dept", "Sales")
				.executeUpdate();
		session.getTransaction().commit();
		System.out.println("Updated rows: " + updated);

		// 7. DELETE
		session.beginTransaction();
		int deleted = session.createQuery(
						"delete from EmployeeTest where salary < 55000")
				.executeUpdate();
		session.getTransaction().commit();
		System.out.println("Deleted rows: " + deleted);

		// 8. Named query (optional)
		// Define in @NamedQuery if needed

		session.close();
		SessionFactoryUtil.shutdown();
	}
}
