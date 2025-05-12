package org.java.hibernate.eventlistners.integrator;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.java.hibernate.eventlistners.AuditInsertListener;

public class AuditIntegrator implements Integrator {

	@Override
	public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
	                      SessionFactoryServiceRegistry serviceRegistry) {
		EventListenerRegistry registry = serviceRegistry.getService(EventListenerRegistry.class);
		registry.getEventListenerGroup(EventType.PRE_INSERT)
				.appendListener(new AuditInsertListener());
	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory,
	                         SessionFactoryServiceRegistry serviceRegistry) {
		// No-op
	}
}
