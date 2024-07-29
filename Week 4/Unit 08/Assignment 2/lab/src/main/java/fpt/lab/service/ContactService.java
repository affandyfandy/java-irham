package fpt.lab.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.lab.model.Contact;
import fpt.lab.repository.ContactRepository;

@Service
public class ContactService {
    
    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    @Transactional(transactionManager = "contactTransactionManager")
    public int save(Contact contact) {
        try {
            return repository.save(contact);
        } catch (DataAccessException e) {
            throw e;
        }
    }

    @Transactional(transactionManager = "contactTransactionManager")
    public int update(Contact contact) {
        try {
            return repository.update(contact);
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public Contact findById(String id) {
        try {
            return repository.findById(id);
        } catch (DataAccessException e) {
            throw e;
        }
    }

    @Transactional(transactionManager = "contactTransactionManager")
    public int deleteById(String id) {
        try {
            return repository.deleteById(id);
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public List<Contact> findAll() {
        try {
            return repository.findAll();
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public List<Contact> search(String query) {
        try {
            return repository.search(query);
        } catch (DataAccessException e) {
            throw e;
        }
    }
}
