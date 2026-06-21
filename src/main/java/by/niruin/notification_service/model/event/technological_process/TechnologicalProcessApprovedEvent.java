package by.niruin.notification_service.model.event.technological_process;

import by.niruin.notification_service.model.event.MessageBrokerEvent;

public record TechnologicalProcessApprovedEvent(String fullNumber,
                                                String partName,
                                                String partNumber,
                                                String reviewerUsername,
                                                String authorUsername) implements MessageBrokerEvent {
}
