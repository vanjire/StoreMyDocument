package com.store.store_my_documents.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.store.store_my_documents.Dtos.DocumentDto;
import com.store.store_my_documents.Dtos.RegisterDto;
import com.store.store_my_documents.entity.Document;
import com.store.store_my_documents.entity.User;
import com.store.store_my_documents.exceptions.DocumentException;
import com.store.store_my_documents.exceptions.UserAlreadyExistsException;
import com.store.store_my_documents.repository.DocumentRepo;
import com.store.store_my_documents.repository.UserRepo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
@Service
public class UsersService {
	 private final AuthenticationManager authenticationManager;
	    private final UserRepo userRepo;
	    private final PasswordEncoder passwordEncoder;
	    private DocumentRepo documentRepo;
	    public UsersService(AuthenticationManager authenticationManager,UserRepo userRepo,PasswordEncoder passwordEncoder,DocumentRepo documentRepo) {
	        this.authenticationManager = authenticationManager;
	        this.userRepo=userRepo;
	        this.passwordEncoder = passwordEncoder;
	        this.documentRepo=documentRepo;
	    }
	 
	    private String uploadDir= "uploads/";
	public void saveDocument(DocumentDto dto,Authentication auth) throws IOException{
		
		MultipartFile file=dto.getFile();
		if(file==null||file.isEmpty())throw new DocumentException("file required");
		String name=dto.getName();
		 try {

	            File directory = new File(uploadDir);

	            if (!directory.exists()) {
	                directory.mkdirs();
	            }
	           
	            String fileName = file.getOriginalFilename();
	            String storedFileName = UUID.randomUUID() + "_"+fileName;
	            Path path = Paths.get(uploadDir + storedFileName);
	            System.out.println("entered3");
	            Files.copy(
	                file.getInputStream(),
	                path
	              //  StandardCopyOption.REPLACE_EXISTING
	            );
	           
	            Document d=new Document();
	            d.setName(name);
	            d.setFilePath(path.toString());
	            d.setUsername(auth.getName());
	            documentRepo.save(d);
	          
	            return;

	        } catch (IOException e) {
	        	
	            throw new RuntimeException("File upload failed");
	        }
	}
	public void saveUser(RegisterDto dto){

        if (userRepo.findByUsername(dto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "Username already exists");
        }

        User user = new User();
        user.setRole("USER");
        user.setUsername(dto.getUsername());
        user.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );
        

        userRepo.save(user);
    }
	public List<Document> getMyDocuments(Authentication auth) {

	    String username = auth.getName();

	    return documentRepo.findByUsername(username);
	}
	public ResponseEntity<Resource> viewDocument(
	        Long id,
	        Authentication auth) throws IOException {

	    Document document = documentRepo
	            .findByIdAndUsername(id, auth.getName())
	            .orElseThrow(() ->
	                    new RuntimeException("Document not found"));

	    Path path = Paths.get(document.getFilePath());

	    Resource resource = new UrlResource(path.toUri());

	    String contentType = Files.probeContentType(path);

	    if (contentType == null) {
	        contentType = "application/octet-stream";
	    }

	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType(contentType))
	            .header(
	                HttpHeaders.CONTENT_DISPOSITION,
	                "inline; filename=\"" + document.getName() + "\""
	            )
	            .body(resource);
	}
}
