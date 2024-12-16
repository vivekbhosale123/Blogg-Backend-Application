package org.vdb.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.vdb.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file)throws IOException {
		
		// file name
		String name= file.getOriginalFilename();
		// vivek.png
		
		// random name generate file
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(name.substring(name.indexOf(".")));
		
		// Full Path
		String filePath =path+ File.separator +fileName1 ; 
		
		
		// create folder if not created
		File f=new File(path);
		
		if(!f.exists())
		{
			f.mkdir();
		}
		
		// file copy
		Files.copy(file.getInputStream(),Paths.get(filePath));
		
		return name;
	}

	@Override
	public InputStream getsource(String path, String fileName) throws FileNotFoundException{
	
		String fullpath =path +File.separator + fileName;
		
		InputStream is=new FileInputStream(fullpath);
		
		// db logic to return inputstream
		return is;
	}

}
