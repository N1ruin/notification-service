package by.niruin.notification_service.model;

public record NotificationDto(
        Long id,
        String payload,
        String recipient,
        String recipientRole
) {
}
