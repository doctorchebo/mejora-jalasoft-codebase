package org.fundacionjala.contacts.db.entities;

import lombok.*;
import org.fundacionjala.contacts.models.Contact;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "contact")
@Table(name = "contact")
public class ContactData {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @ManyToMany
    private Set<MessageData> messages;

    public Contact toModel() {
        Contact contact = new Contact(name, email, phone);
        contact.setId(id);
        contact.setUserId(userId);
        return contact;
    }


}
