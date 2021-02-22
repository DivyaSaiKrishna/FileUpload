package com.dsk.FileUpload.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class File {
	
	private String fileName;
	private String fileType;
	private String fileURL;

}
