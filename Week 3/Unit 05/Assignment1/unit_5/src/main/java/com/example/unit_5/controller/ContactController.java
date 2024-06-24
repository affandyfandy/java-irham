package com.example.unit_5.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.unit_5.models.Contact;
import com.example.unit_5.repository.ContactRepository;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/v1/contact")
@AllArgsConstructor
public class ContactController {
    
    @Autowired
    private final ContactRepository repository;

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        List<Contact> response = repository.findAll();
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable("id") String id) {
        Optional<Contact> response = repository.findById(id);
        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        }
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact body) {
        Optional<Contact> request = repository.findById(body.getId());
        if (request.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repository.save(body));
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<Contact> updateContact(
        @PathVariable(value = "id") String id,
        @RequestBody Contact contactForm) {
        Optional<Contact> contactOpt = repository.findById(id);
        if(contactOpt.isPresent()) {
            Contact contact = contactOpt.get();
            if (contactForm.getName() != null) {
                contact.setName(contactForm.getName());
            }
            if (contactForm.getAge() != null) {
                contact.setAge(contactForm.getAge());
            }

            Contact updatedContact = repository.save(contact);
            return ResponseEntity.ok(updatedContact);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Contact> deleteContact(@PathVariable(value = "id") String id) {
        Optional<Contact> contactOpt = repository.findById(id);
        if(contactOpt.isPresent()) {
            repository.delete(contactOpt.get());
            return ResponseEntity.ok(contactOpt.get());

        }
        return ResponseEntity.notFound().build();
    }
}
