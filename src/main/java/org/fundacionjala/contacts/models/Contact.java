package org.fundacionjala.contacts.models;

import lombok.*;
import org.fundacionjala.contacts.db.entities.ContactData;

import java.util.HashSet;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Contact {

    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String phone;

    public Contact(String name, String email, String phone) {
    }

    public ContactData toEntity() {
        return new ContactData(id, 1L, name, email, phone, new HashSet<>());
    }
}
