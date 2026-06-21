package by.niruin.notification_service.model.event.technical_library.safety_instruction;

import by.niruin.notification_service.model.event.MessageBrokerEvent;

import java.time.LocalDateTime;

public record SafetyInstructionUpdatedEvent(String number,
                                            String description,
                                            LocalDateTime updatedDate) implements MessageBrokerEvent {
}
