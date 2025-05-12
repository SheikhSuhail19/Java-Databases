package org.java.hibernate.compositekey;

import org.hibernate.Session;
import org.java.hibernate.util.SessionFactoryUtil;

public class CompositeKeyDemo {
	public static void main(String[] args) {
		try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
			EnrollmentId enrollmentId = new EnrollmentId();
			enrollmentId.setStudentId(1L);
			enrollmentId.setCourseId(1L);

			Enrollment enrollment = new Enrollment();
			enrollment.setId(enrollmentId);
			enrollment.setSemester("5");

			session.beginTransaction();
			session.persist(enrollment);
			session.getTransaction().commit();
		}
	}
}
