package by.niruin.notification_service.mapper;

import by.niruin.notification_service.domain.Notification;
import by.niruin.notification_service.domain.RecipientRole;
import by.niruin.notification_service.model.event.MessageBrokerEvent;
import by.niruin.notification_service.model.event.technical_library.equipment.EquipmentCreatedEvent;
import by.niruin.notification_service.model.event.technical_library.equipment.EquipmentDeletedEvent;
import by.niruin.notification_service.model.event.technical_library.equipment.EquipmentUpdatedEvent;
import by.niruin.notification_service.model.event.technical_library.material.MaterialCreatedEvent;
import by.niruin.notification_service.model.event.technical_library.material.MaterialDeletedEvent;
import by.niruin.notification_service.model.event.technical_library.material.MaterialUpdatedEvent;
import by.niruin.notification_service.model.event.technical_library.safety_instruction.SafetyInstructionCreatedEvent;
import by.niruin.notification_service.model.event.technical_library.safety_instruction.SafetyInstructionDeletedEvent;
import by.niruin.notification_service.model.event.technical_library.safety_instruction.SafetyInstructionUpdatedEvent;
import by.niruin.notification_service.model.event.technological_process.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Component
public class EventMapper {
    private final Map<String, Class<? extends MessageBrokerEvent>> eventClasses = new HashMap<>();
    private final ObjectMapper objectMapper;

    public EventMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Notification map(MessageBrokerEvent event) {
        var notification = new Notification();

        if (event instanceof TechnologicalProcessCreatedEvent e) {
            notification.setRecipientRole(RecipientRole.HEAD);
            notification.setPayload("Создан техпроцесс c номером %s. Номер узла: %s. Наименование узла: %s"
                    .formatted(e.fullNumber(), e.partNumber(), e.partName()));
        } else if (event instanceof TechnologicalProcessUpdatedEvent e) {
            notification.setRecipient(e.authorUsername());
            notification.setRecipientRole(RecipientRole.ENGINEER);
            notification.setPayload("Обновлен техпроцесс: ");
        } else if (event instanceof TechnologicalProcessCancelledEvent e) {
            notification.setRecipient(e.getDeveloperUsername());
            notification.setRecipientRole(RecipientRole.DEVELOPER);
            notification.setPayload("Отменен техпроцесс: " + e.getFullNumber());
        } else if (event instanceof TechnologicalProcessSentToReviewEvent e) {
            notification.setRecipient(e.getDeveloperUsername());
            notification.setRecipientRole(RecipientRole.DEVELOPER);
            notification.setPayload("Отправлен на проверку: " + e.getFullNumber());
        } else if (event instanceof TechnologicalProcessApprovedEvent e) {
            notification.setRecipient(e.getDeveloperUsername());
            notification.setRecipientRole(RecipientRole.DEVELOPER);
            notification.setPayload("Согласован техпроцесс: " + e.getFullNumber());
        } else if (event instanceof TechnologicalProcessReturnedAfterReviewEvent e) {
            notification.setRecipient(e.getDeveloperUsername());
            notification.setRecipientRole(RecipientRole.DEVELOPER);
            notification.setPayload("Возвращен на доработку: " + e.getFullNumber());
        } else if (event instanceof EquipmentCreatedEvent e) {
            notification.setRecipient("admin");
            notification.setRecipientRole(RecipientRole.ADMIN);
            notification.setPayload("Создано оборудование: " + e.getName());
        } else if (event instanceof EquipmentUpdatedEvent e) {
            notification.setRecipient("admin");
            notification.setRecipientRole(RecipientRole.ADMIN);
            notification.setPayload("Обновлено оборудование: " + e.getName());
        } else if (event instanceof EquipmentDeletedEvent e) {
            notification.setRecipient("admin");
            notification.setRecipientRole(RecipientRole.ADMIN);
            notification.setPayload("Удалено оборудование: " + e.getName());
        } else if (event instanceof MaterialCreatedEvent e) {
            notification.setRecipient("admin");
            notification.setRecipientRole(RecipientRole.ADMIN);
            notification.setPayload("Создан материал: " + e.getName());
        } else if (event instanceof MaterialUpdatedEvent e) {
            notification.setRecipient("admin");
            notification.setRecipientRole(RecipientRole.ADMIN);
            notification.setPayload("Обновлен материал: " + e.getName());
        } else if (event instanceof MaterialDeletedEvent e) {
            notification.setRecipient("admin");
            notification.setRecipientRole(RecipientRole.ADMIN);
            notification.setPayload("Удален материал: " + e.getName());
        } else if (event instanceof SafetyInstructionCreatedEvent e) {
            notification.setRecipient("admin");
            notification.setRecipientRole(RecipientRole.ADMIN);
            notification.setPayload("Создана инструкция: " + e.getName());
        } else if (event instanceof SafetyInstructionUpdatedEvent e) {
            notification.setRecipient("admin");
            notification.setRecipientRole(RecipientRole.ADMIN);
            notification.setPayload("Обновлена инструкция: " + e.getName());
        } else if (event instanceof SafetyInstructionDeletedEvent e) {
            notification.setRecipient("admin");
            notification.setRecipientRole(RecipientRole.ADMIN);
            notification.setPayload("Удалена инструкция: " + e.getName());
        } else {
            throw RuntimeException();
        }

        return notification;
    }

    public Class<? extends MessageBrokerEvent> getEventClass(String eventType) {
        return eventClasses.get(eventType);
    }

    @PostConstruct
    public void initEventClasses() {
        eventClasses.put("EQUIPMENT_CREATED", EquipmentCreatedEvent.class);
        eventClasses.put("EQUIPMENT_UPDATED", EquipmentUpdatedEvent.class);
        eventClasses.put("EQUIPMENT_DELETED", EquipmentDeletedEvent.class);

        eventClasses.put("MATERIAL_CREATED", MaterialCreatedEvent.class);
        eventClasses.put("MATERIAL_UPDATED", MaterialUpdatedEvent.class);
        eventClasses.put("MATERIAL_DELETED", MaterialDeletedEvent.class);

        eventClasses.put("SAFETY_INSTRUCTION_CREATED", SafetyInstructionCreatedEvent.class);
        eventClasses.put("SAFETY_INSTRUCTION_UPDATED", SafetyInstructionUpdatedEvent.class);
        eventClasses.put("SAFETY_INSTRUCTION_DELETED", SafetyInstructionDeletedEvent.class);

        eventClasses.put("TECHNOLOGICAL_PROCESS_CREATED", TechnologicalProcessCreatedEvent.class);
        eventClasses.put("TECHNOLOGICAL_PROCESS_UPDATED", TechnologicalProcessUpdatedEvent.class);
        eventClasses.put("TECHNOLOGICAL_PROCESS_CANCELLED", TechnologicalProcessCancelledEvent.class);
        eventClasses.put("TECHNOLOGICAL_PROCESS_SENT_TO_REVIEW", TechnologicalProcessSentToReviewEvent.class);
        eventClasses.put("TECHNOLOGICAL_PROCESS_APPROVED", TechnologicalProcessApprovedEvent.class);
        eventClasses.put("TECHNOLOGICAL_PROCESS_RETURNED_AFTER_REVIEW", TechnologicalProcessReturnedAfterReviewEvent.class);
    }
}
