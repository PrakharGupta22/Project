package com.cg.onlineshopping.exception;

public class CategoryNotFoundException extends Exception{
	/**
	 * category not found exception
	 */
	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException() {
	}

	public CategoryNotFoundException(String msg) {
		super(msg);
	}

}
