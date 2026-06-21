package by.niruin.notification_service.kafka;

import by.niruin.notification_service.mapper.EventMapper;
import by.niruin.notification_service.model.event.MessageBrokerEvent;
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
    private final EventMapper eventMapper;

    public TechnologicalProcessEventListener(NotificationService notificationService, EventMapper eventMapper) {
        this.notificationService = notificationService;
        this.eventMapper = eventMapper;
    }

    @KafkaHandler
    public void handleEvent(TechnologicalProcessApprovedEvent event) {
        processEvent(event);
    }

    @KafkaHandler
    public void handleEvent(TechnologicalProcessCancelledEvent event) {
        processEvent(event);
    }

    @KafkaHandler
    public void handleEvent(TechnologicalProcessCreatedEvent event) {
        processEvent(event);
    }

    @KafkaHandler
    public void handleEvent(TechnologicalProcessReturnedAfterReviewEvent event) {
        processEvent(event);
    }

    @KafkaHandler
    public void handleEvent(TechnologicalProcessSentToReviewEvent event) {
        processEvent(event);
    }

    @KafkaHandler
    public void handleEvent(TechnologicalProcessUpdatedEvent event) {
        processEvent(event);
    }

    private void processEvent(MessageBrokerEvent event) {
        logger.info("Received event: {}", event.getClass().getSimpleName());
        var notification = eventMapper.map(event);

        if (notification != null) {
            notificationService.save(notification);
        }
    }
}
