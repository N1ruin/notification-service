package by.niruin.notification_service.model.notification;

public record NotificationDto(
        Long id,
        String payload,
        String recipient,
        String recipientRole
) {
}
