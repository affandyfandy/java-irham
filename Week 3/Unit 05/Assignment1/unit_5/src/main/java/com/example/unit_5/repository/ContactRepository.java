package com.example.unit_5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.unit_5.models.Contact;

@Repository
public interface ContactRepository extends  JpaRepository<Contact, String> {
    
}