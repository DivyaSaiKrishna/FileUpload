package com.dsk.FileUpload.Exceptions;

import java.io.IOException;

public class FileException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	public FileException(String Message) {
		super(Message);
	}
	
	public FileException(String Message, IOException e) {
		super(Message,e);
	}
}
