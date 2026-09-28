package com.store.store_my_documents.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.store_my_documents.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}