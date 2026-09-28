package com.store.store_my_documents.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.store.store_my_documents.Dtos.DocumentDto;
import com.store.store_my_documents.entity.Document;
import com.store.store_my_documents.service.UsersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("user")
public class UsersController {
	private final UsersService userService;
	UsersController(UsersService userService){
		this.userService=userService;
	}
	@PostMapping("/upload")
	public ResponseEntity<String> uploadDocument(@Valid @ModelAttribute DocumentDto dto,Authentication auth)throws IOException{
		userService.saveDocument(dto,auth);
		return ResponseEntity.status(201).body("Document saved successfully");
	}
	@GetMapping("/documents")
	public ResponseEntity<List<Document>> getMyDocuments(
	        Authentication auth) {

	    return ResponseEntity.ok(
	            userService.getMyDocuments(auth)
	    );
	}
}
