package org.java.hibernate.primarykeystrategies;

import org.hibernate.Session;
import org.java.hibernate.util.SessionFactoryUtil;

public class HibernatePrimaryKeyTest {
	public static void main(String[] args) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();

		session.beginTransaction();

		session.persist(new UserIdentity("Identity_User"));
		session.persist(new UserSequence("Sequence_User"));
		session.persist(new UserTable("Table_User"));
		session.persist(new UserAuto("Auto_User"));
		session.persist(new UserUUID("UUID_User"));

		session.getTransaction().commit();

		session.close();
		SessionFactoryUtil.shutdown();
	}
}
