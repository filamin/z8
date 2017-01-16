package org.zenframework.z8.server.exceptions;

import org.zenframework.z8.server.resources.Resources;
import org.zenframework.z8.server.types.exception;

public class AccessDeniedException extends exception {
	private static final long serialVersionUID = 2127190490820439197L;

	private static String message = Resources.get("Exception.accessDenied");

	public AccessDeniedException() {
		super(message);
	}
}
