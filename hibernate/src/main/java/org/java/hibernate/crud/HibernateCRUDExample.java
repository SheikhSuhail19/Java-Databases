package org.java.hibernate.crud;

import org.java.hibernate.util.SessionFactoryUtil;

public class HibernateCRUDExample {
	public static void main(String[] args) {
		org.java.hibernate.UserDAO userDAO = new org.java.hibernate.UserDAO();

		User newUser = new User("Alice", "alice@example.com");
		newUser.setAddress(new Address("123 Main St", "City", "State", "12345"));

		userDAO.save(newUser);

		User retrieved = userDAO.getById(newUser.getId());
		System.out.println("Retrieved BFUser: " + retrieved.getName());

		userDAO.updateEmail(retrieved.getId(), "alice.new@example.com");

		userDAO.delete(retrieved.getId());

		SessionFactoryUtil.shutdown();
	}
}
