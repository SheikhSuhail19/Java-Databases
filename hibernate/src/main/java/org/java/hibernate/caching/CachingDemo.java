package org.java.hibernate.caching;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.java.hibernate.common.Student;
import org.java.hibernate.util.SessionFactoryUtil;

public class CachingDemo {
	public static void main(String[] args) {
		// Insert the student
		try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
			Transaction tx = session.beginTransaction();
			Student student = new Student();
			student.setId(1);
			student.setName("John");
			session.persist(student);
			tx.commit();
		}

		// First-level cache test (same session)
		try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
			Student student1 = session.get(Student.class, 1);
			Student student2 = session.get(Student.class, 1);

			System.out.println("1st-Level Cache:");
			System.out.println("Are student1 and student2 same? " + (student1 == student2)); // true
		}

//		Student s1;
//		Student s2;
//		// Second-level cache test (different session)
//		try (Session session1 = SessionFactoryUtil.getSessionFactory().openSession()) {
//			s1 = session1.get(Student.class, 1); // DB or 2nd level
//			System.out.println("Session 1 - Student: " + s1.getName());
//		}
//
//		try (Session session2 = SessionFactoryUtil.getSessionFactory().openSession()) {
//			s2 = session2.get(Student.class, 1); // Should hit 2nd level cache
//			System.out.println("Session 2 - Student: " + s2.getName());
//		}
	}
}
