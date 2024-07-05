package fpt.lab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpt.lab.model.ApiResponse;
import fpt.lab.model.Contact;
import fpt.lab.service.ContactService;

@RestController
@RequestMapping("api/v1/contact")
public class ContactController {
    
    @Autowired
    private ContactService service;

    @GetMapping
    public ApiResponse<List<Contact>> getContacts(@RequestParam(required = false) String query) {
        List<Contact> contacts;
        if (query != null) {
            contacts = service.search(query);
        } else {
            contacts = service.findAll();
        }
        return new ApiResponse<>(HttpStatus.OK.value(), "Contact list fetched successfully.", contacts);
    }

    @PostMapping
    public ApiResponse<Contact> addContact(@RequestBody Contact contact) {
        service.save(contact);
        return new ApiResponse<>(HttpStatus.OK.value(), "Contact created successfully.", contact);
    }

    @PutMapping
    public ApiResponse<Contact> updateContact(@RequestBody Contact contact) {
        service.update(contact);
        return new ApiResponse<>(HttpStatus.OK.value(), "Contact updated successfully.", contact);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteContactById(@PathVariable String id) {
        service.deleteById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Contact deleted successfully.", null);
    }

    @GetMapping("/{id}")
    public ApiResponse<Contact> getContactById(@PathVariable String id) {
        Contact contact = service.findById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Contact fetched successfully.", contact);
    }
}
