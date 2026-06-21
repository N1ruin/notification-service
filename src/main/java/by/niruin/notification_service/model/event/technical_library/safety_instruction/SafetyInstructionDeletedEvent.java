package by.niruin.notification_service.model.event.technical_library.safety_instruction;

import by.niruin.notification_service.model.event.MessageBrokerEvent;

public record SafetyInstructionDeletedEvent(String number) implements MessageBrokerEvent {
}
