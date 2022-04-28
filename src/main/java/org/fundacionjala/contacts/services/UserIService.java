package org.fundacionjala.contacts.services;

import org.fundacionjala.contacts.exceptions.RequiredFieldException;
import org.fundacionjala.contacts.models.Contact;
import org.fundacionjala.contacts.models.Message;
import org.fundacionjala.contacts.models.User;

import java.util.List;

public interface UserIService {

    List<User> findAll();

    User findById(User user);

    User save(User User);
}
