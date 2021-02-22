package com.dsk.FileUpload.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.dsk.FileUpload.Exceptions.FileException;
import com.dsk.FileUpload.Exceptions.FileNotFound;

public interface FileService {

	String upload(MultipartFile file) throws FileException;
	
	Resource download(String fileName) throws FileNotFound;
	
}
