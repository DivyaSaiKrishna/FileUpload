package com.dsk.FileUpload.Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dsk.FileUpload.Model.File;
import com.dsk.FileUpload.Service.IFileService;
import com.dsk.FileUpload.Utils.FileUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
public class FileUploadController {

	@Autowired
	IFileService fileService;
	
	@Autowired
	private FileUtils fileUtils;
	
	//private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@PostMapping("/single")
	public File uploadSingleFile(@RequestParam("file") MultipartFile file) {
		Path pathToFile = Paths.get(fileUtils.getFileName(file));
	    System.out.println(pathToFile.toAbsolutePath());
		String fileName = fileService.upload(file);
		
		String url = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/download/")
				.path(fileName)
				.toUriString();
		
		String fileContentType = file.getContentType();
		
		File fileupload = new File(fileName, fileContentType, url);
		
		return fileupload;
		
	}
	
	@GetMapping("/download/{fileName}")
	public  ResponseEntity<Resource> downloadSingleFile(@PathVariable String fileName, HttpServletRequest request){
		
		Resource resource = fileService.download(fileName);
		
		String returndownload;
		try {
			returndownload = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		}catch (IOException e) {
			returndownload  = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		}
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(returndownload))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
				.body(resource);
	}
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
}
