package by.niruin.notification_service.kafka;

import by.niruin.notification_service.model.event.technological_process.*;
import by.niruin.notification_service.service.NotificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "technological-process-topic", groupId = "technological-process-group")
public class TechnologicalProcessEventListener {
    private static final Logger logger = LogManager.getLogger(TechnologicalProcessEventListener.class);
    private final NotificationService notificationService;

    public TechnologicalProcessEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaHandler
    public void handleEvent(TechnologicalProcessApprovedEvent event) {

    }

    @KafkaHandler
    public void handleEvent(TechnologicalProcessCancelledEvent event) {

    }

    @KafkaHandler
    public void handleEvent(TechnologicalProcessCreatedEvent event) {

    }

    @KafkaHandler
    public void handleEvent(TechnologicalProcessReturnedAfterReviewEvent event) {

    }

    @KafkaHandler
    public void handleEvent(TechnologicalProcessSentToReviewEvent event) {

    }

    @KafkaHandler
    public void handleEvent(TechnologicalProcessUpdatedEvent event) {

    }
}
