package by.niruin.notification_service.model.event.technological_process;

import by.niruin.notification_service.model.event.MessageBrokerEvent;

public record TechnologicalProcessCreatedEvent(String fullNumber,
                                               String partName,
                                               String partNumber) implements MessageBrokerEvent {
}
