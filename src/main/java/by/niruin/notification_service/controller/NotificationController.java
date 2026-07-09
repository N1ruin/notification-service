package by.niruin.notification_service.controller;

import by.niruin.notification_service.domain.RecipientRole;
import by.niruin.notification_service.mapper.NotificationMapper;
import by.niruin.notification_service.model.notification.NotificationDto;
import by.niruin.notification_service.service.NotificationService;
import by.niruin.notification_service.util.JwtUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PreAuthorize("hasAnyRole('ENGINEER', 'HEAD')")
    public ResponseEntity<Page<NotificationDto>> getNotifications(
            @AuthenticationPrincipal Jwt jwt,
            @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        var username = JwtUtils.extractUsername(jwt);
        var role = JwtUtils.extractRole(jwt);

        var notificationsPage = notificationService.findAllForUser(username, role, pageable);

        return ResponseEntity.ok(notificationsPage.map(notificationMapper::toDto));
    }
}
