package com.dsk.FileUpload.Service;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileStore;
import org.springframework.beans.factory.annotation.Value;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dsk.FileUpload.Controller.FileUploadController;
import com.dsk.FileUpload.Exceptions.FileException;
import com.dsk.FileUpload.Exceptions.FileNotFound;
import com.dsk.FileUpload.Utils.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IFileService implements FileService {
	@Autowired
	private FileUtils fileUtils;
	
	private static final Logger logger = LoggerFactory.getLogger(IFileService.class);
	
	private String fileStorageLocation;
	
	private Path fileStoragePath;
	
	 public IFileService(@Value("${file.storage.location:temp}") String fileStorageLocation) {

	        this.fileStorageLocation = fileStorageLocation;
	        
	        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();

	        try {
	            Files.createDirectories(fileStoragePath);
	        } catch (IOException e) {
	            throw new RuntimeException("Issue in creating file directory");
	        }
	    }

	@Override
	public String upload(MultipartFile file) throws FileException {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		log.info("file name : "+fileName);
		
		log.info("file name : "+fileUtils.getFileName(file));
		Path filePath = Paths.get(fileStoragePath + "\\" + fileName);
		
		log.info("file path : "+filePath);
		log.info("file path : "+fileUtils.getFileUploadedPath(fileName));
		
		try {
			Files.copy(file.getInputStream(), filePath , StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new FileException("File is not Stored",e);
		}

		return fileName;
	}

	@Override
	public Resource download(String fileName) throws FileNotFound {
		
		Path path =  Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);
		
		Resource resource;
		
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			throw new FileNotFound("File Not Found",e);
		}

		if(resource.exists() && resource.isReadable()){
            return resource;
        }else{
            throw new FileNotFound("File Not Found");
        }
	}

}
