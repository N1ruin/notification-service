package by.niruin.notification_service.model.event.technological_process;

import by.niruin.notification_service.model.event.MessageBrokerEvent;

public record TechnologicalProcessReturnedAfterReviewEvent(String fullNumber,
                                                           String partName,
                                                           String partNumber,
                                                           String authorUsername,
                                                           String reviewerUsername) implements MessageBrokerEvent {
}
