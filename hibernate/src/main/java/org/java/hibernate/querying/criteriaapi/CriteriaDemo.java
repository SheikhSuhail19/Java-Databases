package org.java.hibernate.querying.criteriaapi;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.java.hibernate.querying.EmployeeTest;
import org.java.hibernate.util.SessionFactoryUtil;

import java.util.List;

public class CriteriaDemo {
	public static void main(String[] args) {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("your-persistence-unit");
//		EntityManager em = emf.createEntityManager();

		Session session = SessionFactoryUtil.getSessionFactory().openSession();

		// --- Criteria API starts here ---
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<EmployeeTest> cq = cb.createQuery(EmployeeTest.class);
		Root<EmployeeTest> root = cq.from(EmployeeTest.class);

		// WHERE e.department = :dept
		Predicate departmentPredicate = cb.equal(root.get("department"), "IT");
		cq.select(root).where(departmentPredicate);

		List<EmployeeTest> itEmployees = session.createQuery(cq).getResultList();
		itEmployees.forEach(e -> System.out.println("IT Employee: " + e.getName()));

		session.close();
		SessionFactoryUtil.shutdown();
	}
}