package org.sevensource.support.jpa.exception;

public class EntityNotFoundException extends EntityException {
	private static final long serialVersionUID = 7992904489502842099L;

	public EntityNotFoundException(String message) {
		super(message);
	}
}
