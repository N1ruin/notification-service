package by.niruin.notification_service.service;

import by.niruin.notification_service.domain.Notification;
import by.niruin.notification_service.domain.RecipientRole;
import by.niruin.notification_service.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional(readOnly = true)
    public Page<Notification> findAllByRecipientRole(RecipientRole role, Pageable pageable) {
        return notificationRepository.findAllByRecipientRole(role, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Notification> findAllByRecipient(String recipient, Pageable pageable) {
        return notificationRepository.findAllByRecipient(recipient, pageable);
    }
}
