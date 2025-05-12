package org.java.hibernate.mappings.manytomanymapping.bidirectional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManyToManyBidirectionalMapping {
	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(StudentBi.class)
				.addAnnotatedClass(CourseBi.class)
				.buildSessionFactory();

		try (factory; Session session = factory.openSession()) {
			StudentBi s1 = new StudentBi();
			s1.setName("Alice");

			StudentBi s2 = new StudentBi();
			s2.setName("Bob");

			CourseBi c1 = new CourseBi();
			c1.setTitle("Math");

			CourseBi c2 = new CourseBi();
			c2.setTitle("Physics");

			// Establish bidirectional links
			s1.addCourse(c1);
			s1.addCourse(c2);
			s2.addCourse(c1);

			session.beginTransaction();

			// Persist students — cascade takes care of courses
			session.persist(s1);
			session.persist(s2);

			session.getTransaction().commit();

			System.out.println("Saved students with courses.");

			session.beginTransaction();
			StudentBi retrievedStudent = session.get(StudentBi.class, s1.getId());

			// now save the course to save the student
			StudentBi retrievedStudent2 = session.get(StudentBi.class, 3L);
			retrievedStudent2.addCourse(c2);
			session.persist(c1);
			session.persist(c2);
			session.getTransaction().commit();
		}
	}
}
