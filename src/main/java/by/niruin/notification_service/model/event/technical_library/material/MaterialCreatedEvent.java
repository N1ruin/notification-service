package by.niruin.notification_service.model.event.technical_library.material;

import by.niruin.notification_service.model.event.MessageBrokerEvent;

import java.time.Instant;

public record MaterialCreatedEvent(String name,
                                   String description,
                                   String standard,
                                   String supplierCode,
                                   Instant createdDate) implements MessageBrokerEvent {
}
