package com.store.store_my_documents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store")
public class PagesController {
		@GetMapping("/register")
		public String register() {
			return "register";
		}
		@GetMapping("/MyDocuments")
		public String getDocuments() {
			return "document";
		}
		@GetMapping("/upload")
		public String uploadDoc() {
			return "uploadDoc";
		}
}
