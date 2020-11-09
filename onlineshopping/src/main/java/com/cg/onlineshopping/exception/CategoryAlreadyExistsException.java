package com.cg.onlineshopping.exception;

public class CategoryAlreadyExistsException extends Exception {
	/**
	 * category already exists exception
	 */
	private static final long serialVersionUID = 1L;

	public CategoryAlreadyExistsException() {
	}

	public CategoryAlreadyExistsException(String msg) {
		super(msg);
	}
}
