package com.store.store_my_documents.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.store_my_documents.entity.Document;

public interface DocumentRepo extends JpaRepository<Document,Long>{
	List<Document> findByUsername(String username);
}
