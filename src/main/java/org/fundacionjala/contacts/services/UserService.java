package org.fundacionjala.contacts.services;

import org.fundacionjala.contacts.db.entities.ContactData;
import org.fundacionjala.contacts.db.entities.UserData;
import org.fundacionjala.contacts.models.Contact;
import org.fundacionjala.contacts.models.User;
import org.fundacionjala.contacts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
