package com.store.store_my_documents.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
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
	 @Value("${file.upload-dir}")
	    private String uploadDir;
	public void saveDocument(DocumentDto dto,Authentication auth) throws IOException{
		MultipartFile file=dto.getFile();
		String name=dto.getName();
	    if (file == null || file.isEmpty()) {
	        throw new DocumentException("File is required");
	    }
	    String username = auth.getName();
	    Path folder = Paths.get(uploadDir)
                .toAbsolutePath()
                .normalize();

        Files.createDirectories(folder);
		String filename=UUID.randomUUID().toString()+"-"+file.getOriginalFilename();
		
		
		
			 Path destination = folder.resolve(filename);
		    file.transferTo(destination);
		    Document document = new Document();

		    document.setName(dto.getName());
		    document.setFileName(filename);
		    document.setFilePath(destination.toString());
		    document.setUsername(username);

		    documentRepo.save(document);
		
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
}
