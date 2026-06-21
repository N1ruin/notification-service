package by.niruin.notification_service.model.event.technical_library.material;

import by.niruin.notification_service.model.event.MessageBrokerEvent;

import java.time.Instant;

public record MaterialUpdatedEvent(String name,
                                   String description,
                                   String standard,
                                   String supplierCode,
                                   Instant updatedDate) implements MessageBrokerEvent {
}
