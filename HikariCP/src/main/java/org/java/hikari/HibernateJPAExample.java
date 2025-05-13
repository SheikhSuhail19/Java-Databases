package org.java.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class HibernateJPAExample {

	public static void main(String[] args) {
		// Create HikariCP DataSource
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/ipce");
		hikariConfig.setUsername("ipce");
		hikariConfig.setPassword("ipce");
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setMinimumIdle(5);

		HikariDataSource dataSource = new HikariDataSource(hikariConfig);

		// Set up Hibernate properties
		Map<String, Object> settings = new HashMap<>();
		settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		settings.put("hibernate.hbm2ddl.auto", "update");  // Auto-update schema
		settings.put("hibernate.show_sql", "true");
		settings.put("hibernate.format_sql", "true");
		settings.put("hibernate.connection.datasource", dataSource);

		// Create EntityManagerFactory
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-jpa-example", settings);

		// Create EntityManager
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		// Begin transaction, create a Customer, and save it
		try {
			entityManager.getTransaction().begin();

			// Create a new Customer instance
			Customer customer = new Customer( "John Doe", "john.doe@example.com");

			// Persist the customer
			entityManager.persist(customer);

			// Commit transaction
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
			entityManagerFactory.close();
			dataSource.close();  // Close HikariCP DataSource
		}
	}
}
