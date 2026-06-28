package by.niruin.notification_service.mapper;

import by.niruin.notification_service.domain.Notification;
import by.niruin.notification_service.model.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "recipientRole", expression = "java(notification.getRecipientRole().name())")
    NotificationDto toDto(Notification notification);

    List<NotificationDto> toDto(List<Notification> notifications);
}
