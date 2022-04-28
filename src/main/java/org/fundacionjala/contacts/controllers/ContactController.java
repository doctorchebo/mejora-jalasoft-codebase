package org.fundacionjala.contacts.controllers;

import lombok.Data;
import org.fundacionjala.contacts.exceptions.ContactNotFoundException;
import org.fundacionjala.contacts.exceptions.RequiredFieldException;
import org.fundacionjala.contacts.models.Contact;
import org.fundacionjala.contacts.repository.ContactRepository;
import org.fundacionjala.contacts.services.ContactService;
import org.fundacionjala.contacts.services.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@Data
public class ContactController {

    private final ContactRepository contactRepository;

    private final ContactService contactService;

    @GetMapping()
    public List<Contact> findAll(@RequestParam(required = false) String name) {
        return contactService.findAll(name);
    }

    @PostMapping()
    public Contact save(@RequestBody Contact contact) throws RequiredFieldException {
        return contactService.save(contact);
    }

    @GetMapping("/{id}")
    public Contact findById(@PathVariable Long id) throws ContactNotFoundException {
        return contactService.findById(id);
    }

    @DeleteMapping("/{id}")
    public Contact deleteById(@PathVariable Long id) throws ContactNotFoundException {
        return contactService.deleteById(id);
    }
}
