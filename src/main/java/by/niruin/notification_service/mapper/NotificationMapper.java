package by.niruin.notification_service.mapper;

import by.niruin.notification_service.domain.Notification;
import by.niruin.notification_service.model.NotificationDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationDto toDto(Notification notification);
}
