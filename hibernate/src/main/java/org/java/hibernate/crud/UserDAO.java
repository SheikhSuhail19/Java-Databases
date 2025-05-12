package org.java.hibernate;

import org.hibernate.Session;
import org.java.hibernate.crud.User;
import org.java.hibernate.util.SessionFactoryUtil;

public class UserDAO {

	public void save(User user) {
		try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
			session.beginTransaction();
			session.persist(user);
			session.getTransaction().commit();
		}
	}

	public User getById(Long id) {
		try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
			session.beginTransaction();
			User user = session.get(User.class, id);
			session.getTransaction().commit();
			return user;
		}
	}

	public void updateEmail(Long id, String newEmail) {
		try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
			session.beginTransaction();
			User user = session.get(User.class, id);
			if (user != null) {
				user.setEmail(newEmail);
				session.merge(user);
			}
			session.getTransaction().commit();
		}
	}

	public void delete(Long id) {
		try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
			session.beginTransaction();
			User user = session.get(User.class, id);
			if (user != null) {
				session.remove(user);
			}
			session.getTransaction().commit();
		}
	}
}
