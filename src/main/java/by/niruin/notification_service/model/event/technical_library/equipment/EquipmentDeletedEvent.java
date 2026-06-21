package by.niruin.notification_service.model.event.technical_library.equipment;

import by.niruin.notification_service.model.event.MessageBrokerEvent;

public record EquipmentDeletedEvent(String name,
                                    String index) implements MessageBrokerEvent {
}
