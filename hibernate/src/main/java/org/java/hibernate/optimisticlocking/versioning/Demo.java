package org.java.hibernate.optimisticlocking.versioning;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.java.hibernate.util.SessionFactoryUtil;

public class Demo {
	public static void main(String[] args) throws InterruptedException {
		// Insert employee
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Employee emp = new Employee("John", "IT");
		session.persist(emp);
		tx.commit();
		session.close();

		Long empId = emp.getId();

		// Thread 1
		Thread t1 = new Thread(() -> updateEmployee(SessionFactoryUtil.getSessionFactory(), empId, "John Dev"));
		// Thread 2
		Thread t2 = new Thread(() -> updateEmployee(SessionFactoryUtil.getSessionFactory(), empId, "John QA"));

		t1.start();
		Thread.sleep(500); // Ensure overlap
		t2.start();

		t1.join();
		t2.join();

		SessionFactoryUtil.getSessionFactory().close();
	}

	public static void updateEmployee(SessionFactory sf, Long id, String newName) {

		Transaction tx = null;
		try (Session session = sf.openSession()) {
			tx = session.beginTransaction();
			Employee emp = session.get(Employee.class, id);
			System.out.println(Thread.currentThread().getName() + " read version: " + emp.getVersion());

			emp.setName(newName);

			Thread.sleep(2000); // Simulate delay

			session.merge(emp);
			tx.commit();
			System.out.println(Thread.currentThread().getName() + " updated to version: " + emp.getVersion());

		} catch (Exception e) {
			if (tx != null) tx.rollback();
			System.out.println(Thread.currentThread().getName() + " failed: " + e.getClass().getSimpleName());
			e.printStackTrace();
		}
	}
}