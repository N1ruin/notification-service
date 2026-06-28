package by.niruin.notification_service.repository;

import by.niruin.notification_service.domain.Notification;
import by.niruin.notification_service.domain.RecipientRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE " +
            "n.recipient = :username OR " +
            "(n.recipient IS NULL AND n.recipientRole = :role)")
    Page<Notification> findAllForUser(@Param("username") String username,
            @Param("role") RecipientRole role,
            Pageable pageable);
}
