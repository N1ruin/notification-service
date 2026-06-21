package by.niruin.notification_service.model.event.technological_process;

import by.niruin.notification_service.model.event.MessageBrokerEvent;

public record TechnologicalProcessSentToReviewEvent(String fullNumber,
                                                    String partName,
                                                    String partNumber,
                                                    String reviewerUsername) implements MessageBrokerEvent {
}
