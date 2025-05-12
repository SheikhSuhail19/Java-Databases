package org.java.hibernate.mappings.manytomanymapping.unidirectional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManyToManyMapping {
	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.addAnnotatedClass(Course.class)
				.buildSessionFactory();

		try (factory; Session session = factory.openSession()) {
			Student s1 = new Student();
			s1.setName("Charlie");

			Course c1 = new Course();
			c1.setTitle("Chemistry");

			Course c2 = new Course();
			c2.setTitle("Biology");

			s1.addCourse(c1);
			s1.addCourse(c2);

			session.beginTransaction();
			session.persist(s1); // Courses are cascaded and inserted
			session.getTransaction().commit();

			// more students
			Student s2 = new Student();
			s2.setName("Bob");

			Course c3 = new Course();
			c3.setTitle("Maths");

			s2.addCourse(c3);
			s2.addCourse(c1);
			try {
				session.beginTransaction();
				session.persist(s2); // Courses are cascaded and inserted
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
