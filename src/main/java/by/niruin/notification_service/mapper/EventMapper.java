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

import java.util.HashMap;
import java.util.Map;

@Component
public class EventMapper {
    private final Map<String, Class<? extends MessageBrokerEvent>> eventClasses = new HashMap<>();

    public Notification map(MessageBrokerEvent event) {
        var notification = new Notification();

        switch (event) {
            case TechnologicalProcessCreatedEvent e -> {
                notification.setRecipient(null);
                notification.setRecipientRole(RecipientRole.HEAD);
                notification.setPayload("Создан новый техпроцесс: %s (%s)".formatted(e.fullNumber(), e.partName()));
            }
            case TechnologicalProcessUpdatedEvent e -> {
                notification.setRecipient(null);
                notification.setRecipientRole(RecipientRole.HEAD);
                notification.setPayload("Техпроцесс %s (%s) был обновлен".formatted(e.fullNumber(), e.partName()));
            }
            case TechnologicalProcessCancelledEvent e -> {
                notification.setRecipient(null);
                notification.setRecipientRole(RecipientRole.HEAD);
                notification.setPayload("Техпроцесс %s (%s) был аннулирован".formatted(e.fullNumber(), e.partName()));
            }
            case TechnologicalProcessSentToReviewEvent e -> {
                notification.setRecipient(e.reviewerUsername());
                notification.setRecipientRole(RecipientRole.ENGINEER);
                notification.setPayload("Техпроцесс %s (%s) отправлен вам на проверку".formatted(e.fullNumber(), e.partName()));
            }
            case TechnologicalProcessApprovedEvent e -> {
                notification.setRecipient(e.authorUsername());
                notification.setRecipientRole(RecipientRole.ENGINEER);
                notification.setPayload("Техпроцесс %s (%s) был согласован проверяющим %s".formatted(e.fullNumber(), e.partName(), e.reviewerUsername()));
            }
            case TechnologicalProcessReturnedAfterReviewEvent e -> {
                notification.setRecipient(e.authorUsername());
                notification.setRecipientRole(RecipientRole.ENGINEER);
                notification.setPayload("Техпроцесс %s (%s) возвращен на доработку проверяющим %s".formatted(e.fullNumber(), e.partName(), e.reviewerUsername()));
            }
            case EquipmentCreatedEvent e -> {
                notification.setRecipient(null);
                notification.setRecipientRole(RecipientRole.ENGINEER);
                notification.setPayload("Создано новое оборудование: %s (индекс: %s, тип: %s)".formatted(e.name(), e.index(), e.type()));
            }
            case EquipmentUpdatedEvent e -> {
                notification.setRecipient(null);
                notification.setRecipientRole(RecipientRole.ENGINEER);
                notification.setPayload("Обновлено оборудование: %s (индекс: %s, тип: %s)".formatted(e.name(), e.index(), e.type()));
            }
            case EquipmentDeletedEvent e -> {
                notification.setRecipient(null);
                notification.setRecipientRole(RecipientRole.ENGINEER);
                notification.setPayload("Удалено оборудование: %s (индекс: %s)".formatted(e.name(), e.index()));
            }
            case MaterialCreatedEvent e -> {
                notification.setRecipient(null);
                notification.setRecipientRole(RecipientRole.ENGINEER);
                notification.setPayload("Создан новый материал: %s (стандарт: %s, поставщик: %s)".formatted(e.name(), e.standard(), e.supplierCode()));
            }
            case MaterialUpdatedEvent e -> {
                notification.setRecipient(null);
                notification.setRecipientRole(RecipientRole.ENGINEER);
                notification.setPayload("Обновлен материал: %s (стандарт: %s, поставщик: %s)".formatted(e.name(), e.standard(), e.supplierCode()));
            }
            case MaterialDeletedEvent e -> {
                notification.setRecipient(null);
                notification.setRecipientRole(RecipientRole.ENGINEER);
                notification.setPayload("Удален материал: %s (стандарт: %s)".formatted(e.name(), e.standard()));
            }
            case SafetyInstructionCreatedEvent e -> {
                notification.setRecipient(null);
                notification.setRecipientRole(RecipientRole.ENGINEER);
                notification.setPayload("Создана новая инструкция по безопасности: №%s".formatted(e.number()));
            }
            case SafetyInstructionUpdatedEvent e -> {
                notification.setRecipient(null);
                notification.setRecipientRole(RecipientRole.ENGINEER);
                notification.setPayload("Обновлена инструкция по безопасности: №%s".formatted(e.number()));
            }
            case SafetyInstructionDeletedEvent e -> {
                notification.setRecipient(null);
                notification.setRecipientRole(RecipientRole.ENGINEER);
                notification.setPayload("Удалена инструкция по безопасности: №%s".formatted(e.number()));
            }
            case null, default -> throw new RuntimeException("Event mapping error");
        }

        return notification;
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
