package org.java.hibernate.interceptor;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;

public class LoggingInterceptor extends EmptyInterceptor {

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
	                      String[] propertyNames, Type[] types) {
		System.out.println("[LOG] Saving entity: " + entity.getClass().getSimpleName());
		return super.onSave(entity, id, state, propertyNames, types);
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
	                            Object[] previousState, String[] propertyNames, Type[] types) {
		System.out.println("[LOG] Updating entity: " + entity.getClass().getSimpleName());
		return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
	}

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state,
	                     String[] propertyNames, Type[] types) {
		System.out.println("[LOG] Deleting entity: " + entity.getClass().getSimpleName());
		super.onDelete(entity, id, state, propertyNames, types);
	}
}
