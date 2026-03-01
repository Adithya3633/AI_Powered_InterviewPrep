package com.Portal.AI_Interview_Portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Portal.AI_Interview_Portal.entity.user;

public interface UserRepository extends JpaRepository<user, Long> {
    user findByEmail(String email);
}

