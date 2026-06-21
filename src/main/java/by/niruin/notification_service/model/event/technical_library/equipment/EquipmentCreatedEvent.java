package by.niruin.notification_service.model.event.technical_library.equipment;

import by.niruin.notification_service.model.event.MessageBrokerEvent;

public record EquipmentCreatedEvent(String name,
                                    String index,
                                    String description,
                                    String type) implements MessageBrokerEvent {
}
