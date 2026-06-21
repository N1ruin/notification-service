package by.niruin.notification_service.repository;

import by.niruin.notification_service.domain.Notification;
import by.niruin.notification_service.domain.RecipientRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    Page<Notification> findAllByRecipient(String recipient, Pageable pageable);

    Page<Notification> findAllByRecipientRole(RecipientRole recipientRole, Pageable pageable);
}
