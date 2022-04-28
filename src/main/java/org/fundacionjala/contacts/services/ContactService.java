package org.fundacionjala.contacts.services;

import lombok.Data;
import org.fundacionjala.contacts.db.entities.ContactData;
import org.fundacionjala.contacts.exceptions.ContactNotFoundException;
import org.fundacionjala.contacts.exceptions.DuplicatedContactException;
import org.fundacionjala.contacts.exceptions.RequiredFieldException;
import org.fundacionjala.contacts.models.Contact;
import org.fundacionjala.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class ContactService implements IContactService {

    private final ContactRepository contactRepository;

    @Override
    public List<Contact> findAll(String name) {
        if (name == null || name.isEmpty()) {
            return contactRepository
                    .findAll()
                    .stream()
                    .map(ContactData::toModel)
                    .collect(Collectors.toList());
        }

        return contactRepository
                .findByName(name)
                .stream()
                .map(ContactData::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Contact findById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Unable to find contact with Id: " + id))
                .toModel();
    }

    @Override
    public Contact save(Contact contact) throws RequiredFieldException {
        validateContactFields(contact);
        return contactRepository
                .save(contact.toEntity())
                .toModel();
    }

    @Override
    public Contact deleteById(Long id) {
        boolean exists = contactRepository.existsById(id);
        if(exists){
            throw new IllegalStateException(
                    "El contacto con id " + id + " no existe"
            );
        } contactRepository.deleteById(id);
        return null;
    }

    private void validateContactFields(Contact contact) throws RequiredFieldException {
        if (contact.getEmail() == null || contact.getEmail().isEmpty()) {
            throw new RequiredFieldException("email");
        }

        if (contact.getName() == null || contact.getName().isEmpty()) {
            throw new RequiredFieldException("name");
        }

        if (contact.getPhone() == null || contact.getPhone().isEmpty()) {
            throw new RequiredFieldException("phone");
        }

        if (contact.getPhone().length()<8){
            throw new RequiredFieldException("phone must be at least 8 digits");
        }

        Optional<ContactData> existingContact = contactRepository.findByEmail(contact.getEmail());

        if (existingContact.isPresent()) {
            throw new DuplicatedContactException("Unable to create contact due duplicated email: " + contact.getEmail());
        }
    }
}
