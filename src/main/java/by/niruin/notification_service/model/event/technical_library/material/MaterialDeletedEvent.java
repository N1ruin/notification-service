package by.niruin.notification_service.model.event.technical_library.material;

import by.niruin.notification_service.model.event.MessageBrokerEvent;

public record MaterialDeletedEvent(String name, String standard) implements MessageBrokerEvent {
}
