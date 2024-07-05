package fpt.lab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.lab.model.Contact;
import fpt.lab.repository.ContactRepository;

@Service
public class ContactService {
    
    @Autowired
    private ContactRepository repository;

    @Transactional(transactionManager = "contactTransactionManager")
    public int save(Contact contact) {
        return repository.save(contact);
    }

    @Transactional(transactionManager = "contactTransactionManager")
    public int update(Contact contact) {
        return repository.update(contact);
    }

    public Contact findById(String id) {
        return repository.findById(id);
    }

    @Transactional(transactionManager = "contactTransactionManager")
    public int deleteById(String id) {
        return repository.deleteById(id);
    }

    public List<Contact> findAll() {
        return repository.findAll();
    }

    public List<Contact> search(String query) {
        return repository.search(query);
    }
}
