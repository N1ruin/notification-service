package by.niruin.notification_service.model.event.technical_library.equipment;

import by.niruin.notification_service.model.event.MessageBrokerEvent;

import java.time.Instant;

public record EquipmentUpdatedEvent(String name,
                                    String index,
                                    String description,
                                    String type,
                                    Instant updatedDate) implements MessageBrokerEvent {
}
