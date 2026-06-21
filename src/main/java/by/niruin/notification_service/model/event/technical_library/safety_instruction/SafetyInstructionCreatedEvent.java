package by.niruin.notification_service.model.event.technical_library.safety_instruction;

import by.niruin.notification_service.model.event.MessageBrokerEvent;

import java.time.Instant;

public record SafetyInstructionCreatedEvent(String number,
                                            String description,
                                            Instant createdDate) implements MessageBrokerEvent {
}
