package org.fundacionjala.contacts.controllers;

import org.fundacionjala.contacts.exceptions.RequiredFieldException;
import org.fundacionjala.contacts.models.Message;
import org.fundacionjala.contacts.services.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final IMessageService service;

    @Autowired
    public MessageController(IMessageService service) {

        this.service = service;
    }

    @GetMapping()
    public List<Message> retrieveAllMessages() {

        return service.findAllMessages();
    }

    @PostMapping()
    public Message saveMessage(@RequestBody Message message) throws RequiredFieldException {
        validateMessageFields(message);
        return service.saveMessage(message);
    }

    private void validateMessageFields(Message message) throws RequiredFieldException {
        if (message.getContent() == null || message.getContent().isEmpty()) {
            throw new RequiredFieldException("Content of the message is required.");
        }

        if (message.getContacts().isEmpty()) {
            throw new RequiredFieldException("At least one contact should be added to the message");
        }
    }
}
