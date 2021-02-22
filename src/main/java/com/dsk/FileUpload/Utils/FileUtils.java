package com.dsk.FileUpload.Utils;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtils {
	
	private Path filePath;
	private String fileDownloadPath;
	
	public String getFileName(MultipartFile file) {
		return StringUtils.cleanPath(file.getOriginalFilename());	
	}
	
	public Path getFileUploadedPath(String fileName) {
		return Paths.get(filePath + "\\" + fileName);
	}
	
	public Path getFileDownloadPath(String fileName) {
		return Paths.get(fileDownloadPath).toAbsolutePath().resolve(fileName);
	}
	
	
	

}
