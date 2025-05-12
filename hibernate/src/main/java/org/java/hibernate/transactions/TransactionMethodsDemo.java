package org.java.hibernate.transactions;

import jakarta.transaction.Synchronization;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.java.hibernate.common.Student;
import org.java.hibernate.util.SessionFactoryUtil;

public class TransactionMethodsDemo {
	public static void main(String[] args) {
		try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {

			Transaction tx = session.getTransaction();

			tx.begin(); // begin the transaction
			System.out.println("Transaction started: " + tx.isActive());

			// Set timeout
			tx.setTimeout(5);
			System.out.println("Transaction timeout: " + tx.getTimeout() + " seconds");

			// Register synchronization callback
			tx.registerSynchronization(new Synchronization() {
				@Override
				public void beforeCompletion() {
					System.out.println("Before completion callback");
				}

				@Override
				public void afterCompletion(int status) {
					System.out.println("After completion callback. Status: " + status);
				}
			});

			// Persist something
			Student student = new Student();
			student.setId(101);
			student.setName("Morpheus");
			session.persist(student);

			// Check rollback-only flag
			if (!tx.getRollbackOnly()) {
				System.out.println("Transaction is not marked for rollback. Marking it...");
				tx.markRollbackOnly(); // Also can use setRollbackOnly()
			}

			// Check and roll back
			if (tx.getRollbackOnly()) {
				System.out.println("Transaction is marked for rollback. Rolling back...");
				tx.rollback();
			} else {
				tx.commit();
			}

			TransactionStatus status = tx.getStatus();
			System.out.println("Transaction status: " + status);

			if (status == TransactionStatus.COMMITTED) {
				System.out.println("Transaction was committed.");
			} else if (status == TransactionStatus.ROLLED_BACK) {
				System.out.println("Transaction was rolled back.");
			}
		}
	}
}
