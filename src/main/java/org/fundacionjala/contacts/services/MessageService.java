package org.fundacionjala.contacts.services;

import org.fundacionjala.contacts.db.entities.ContactData;
import org.fundacionjala.contacts.db.entities.MessageData;
import org.fundacionjala.contacts.exceptions.RequiredFieldException;
import org.fundacionjala.contacts.models.Contact;
import org.fundacionjala.contacts.models.Message;
import org.fundacionjala.contacts.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService implements IMessageService {


    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @Override
    public List<Message> findAllMessages() {
        return messageRepository
                .findAll()
                .stream()
                .map(MessageData::toModel)
                .collect(Collectors.toList());
    }


    @Override
    public Message saveMessage(Message message) {

        return messageRepository.save(message.toEntity()).toModel();
    }
}
