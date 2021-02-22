package com.dsk.FileUpload.Exceptions;

import java.net.MalformedURLException;

public class FileNotFound extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FileNotFound(String Message) {
		super(Message);
	}

	public FileNotFound(String Message, MalformedURLException e) {
		super(Message,e);
	}
}
