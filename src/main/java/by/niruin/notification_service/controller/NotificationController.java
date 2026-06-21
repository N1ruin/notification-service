package by.niruin.notification_service.controller;

import by.niruin.notification_service.domain.Notification;
import by.niruin.notification_service.domain.RecipientRole;
import by.niruin.notification_service.mapper.NotificationMapper;
import by.niruin.notification_service.model.NotificationDto;
import by.niruin.notification_service.service.NotificationService;
import by.niruin.notification_service.util.JwtUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification-service")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    public NotificationController(NotificationService notificationService, NotificationMapper notificationMapper) {
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
    }

    @GetMapping("/notifications")
    public ResponseEntity<Page<NotificationDto>> getNotifications(
            @AuthenticationPrincipal Jwt jwt,
            @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        var username = jwt.getSubject();
        var role = JwtUtils.extractRole(jwt);

        Page<Notification> notificationsPage;
        if (role == RecipientRole.HEAD) {
            notificationsPage = notificationService.findAllByRecipientRole(role, pageable);
        } else {
            notificationsPage = notificationService.findAllByRecipient(username, pageable);
        }

        Page<NotificationDto> notificationDtos = notificationsPage.map(notificationMapper::toDto);

        return ResponseEntity.ok(notificationDtos);
    }
}
