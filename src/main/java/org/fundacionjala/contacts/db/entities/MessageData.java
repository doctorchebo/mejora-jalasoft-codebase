package org.fundacionjala.contacts.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.fundacionjala.contacts.models.Message;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "message")
@Table(name = "messages")
@ToString(of = {"id", "content"})
public class MessageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserData user;

    @Column(nullable = false)
    private String content;

//    @OneToMany(
//            fetch = FetchType.EAGER,
//            mappedBy = "message",
//            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
//            orphanRemoval = true
//    )
    @ManyToMany(mappedBy = "messages")
    private Set<ContactData> contacts;

    public MessageData(Long id, UserData user, String content) {
        this.id = id;
        this.user = user;
        this.content = content;
    }

    public Message toModel() {
        Set<Long> contactIds = contacts.stream()
                .map(ContactData::toModel)
                .map((c) -> { return c.getId(); })
                .collect(Collectors.toSet());
        return new Message(id, user.getId(), content, contactIds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MessageData that = (MessageData) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
