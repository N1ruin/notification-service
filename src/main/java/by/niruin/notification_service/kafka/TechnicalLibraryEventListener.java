package by.niruin.notification_service.kafka;

import by.niruin.notification_service.model.event.technical_library.equipment.EquipmentCreatedEvent;
import by.niruin.notification_service.model.event.technical_library.equipment.EquipmentDeletedEvent;
import by.niruin.notification_service.model.event.technical_library.equipment.EquipmentUpdatedEvent;
import by.niruin.notification_service.model.event.technical_library.material.MaterialCreatedEvent;
import by.niruin.notification_service.model.event.technical_library.material.MaterialDeletedEvent;
import by.niruin.notification_service.model.event.technical_library.material.MaterialUpdatedEvent;
import by.niruin.notification_service.model.event.technical_library.safety_instruction.SafetyInstructionCreatedEvent;
import by.niruin.notification_service.model.event.technical_library.safety_instruction.SafetyInstructionDeletedEvent;
import by.niruin.notification_service.model.event.technical_library.safety_instruction.SafetyInstructionUpdatedEvent;
import by.niruin.notification_service.service.NotificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "technical-library-topic", groupId = "technical-library-topic")
public class TechnicalLibraryEventListener {
    private static final Logger logger = LogManager.getLogger(TechnicalLibraryEventListener.class);
    private final NotificationService notificationService;

    public TechnicalLibraryEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaHandler
    public void handleEvent(EquipmentCreatedEvent event) {

    }

    @KafkaHandler
    public void handleEvent(EquipmentDeletedEvent event) {

    }

    @KafkaHandler
    public void handleEvent(EquipmentUpdatedEvent event) {

    }

    @KafkaHandler
    public void handleEvent(MaterialCreatedEvent event) {

    }

    @KafkaHandler
    public void handleEvent(MaterialDeletedEvent event) {

    }

    @KafkaHandler
    public void handleEvent(MaterialUpdatedEvent event) {

    }

    @KafkaHandler
    public void handleEvent(SafetyInstructionCreatedEvent event) {

    }

    @KafkaHandler
    public void handleEvent(SafetyInstructionDeletedEvent event) {

    }

    @KafkaHandler
    public void handleEvent(SafetyInstructionUpdatedEvent event) {

    }
}
