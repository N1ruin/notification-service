package by.niruin.notification_service.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.ZonedDateTime;

@Entity
@Table(name = "notification", schema = "notification_service")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String payload;
    private String recipient;
    @Column(name = "recipient_role")
    @Enumerated(EnumType.STRING)
    private RecipientRole recipientRole;
    @CreatedDate
    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Long getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public RecipientRole getRecipientRole() {
        return recipientRole;
    }

    public void setRecipientRole(RecipientRole recipientRole) {
        this.recipientRole = recipientRole;
    }
}
