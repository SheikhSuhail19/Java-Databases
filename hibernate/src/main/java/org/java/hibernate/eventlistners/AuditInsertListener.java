package org.java.hibernate.eventlistners;

import org.hibernate.Session;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.java.hibernate.util.SessionFactoryUtil;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AuditInsertListener implements PreInsertEventListener {

	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		// Check if the entity is an instance of AuditLog
		// This prevents recursion
		if (event.getEntity() instanceof AuditLog) {
			return false; // Don't audit the audit log!
		}
		// Capture entity class and details
		String entityName = event.getEntity().getClass().getSimpleName();
		String action = "INSERT";
		String details = Arrays.stream(event.getState())
				.map(Object::toString)
				.collect(Collectors.joining(", "));
		String username = "system"; // This could be fetched from session or security context
		long timestamp = System.currentTimeMillis();

		// Save audit log to database (you can optimize this by using a separate thread or batch)
		saveAuditLog(entityName, action, details, username, timestamp);

		return false; // Continue with insert
	}

	private void saveAuditLog(String entityName, String action, String details, String username, long timestamp) {
		Session session = SessionFactoryUtil.getSessionFactory().openSession();
		session.beginTransaction();
		AuditLog auditLog = new AuditLog(entityName, action, details, username, timestamp);
		session.persist(auditLog);
		session.getTransaction().commit();
		session.close();
	}
}
