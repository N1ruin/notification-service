package by.niruin.notification_service.integration;

import by.niruin.notification_service.domain.Notification;
import by.niruin.notification_service.domain.RecipientRole;
import by.niruin.notification_service.service.NotificationService;
import by.niruin.notification_service.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class NotificationServiceIT extends BaseIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeEach
    void cleanDatabase() {
        notificationRepository.deleteAll();
    }

    @Test
    void getNotifications_shouldReturnUserNotifications() throws Exception {
        var userNotifications = List.of(
                createTestNotification("elagun", RecipientRole.ENGINEER),
                createTestNotification("elagun", RecipientRole.ENGINEER));
        var roleNotifications = List.of(
                createTestNotification(null, RecipientRole.ENGINEER),
                createTestNotification(null, RecipientRole.ENGINEER));
        var otherNotifications = List.of(
                createTestNotification(null, RecipientRole.HEAD),
                createTestNotification("admin", RecipientRole.HEAD));

        userNotifications.forEach(notificationService::save);
        roleNotifications.forEach(notificationService::save);
        otherNotifications.forEach(notificationService::save);

        var requestBuilder = get("/api/v1/notification-service/notifications")
                .with(jwt().jwt(jwt -> jwt
                                .claim("sub", "elagun")
                                .claim("realm_access", Map.of("roles", List.of("ENGINEER"))))
                        .authorities(new SimpleGrantedAuthority("ROLE_ENGINEER")))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.totalElements").value(4));
    }

    @Test
    void getNotifications_shouldReturnHeadNotifications() throws Exception {
        var roleNotifications = List.of(
                createTestNotification(null, RecipientRole.HEAD),
                createTestNotification(null, RecipientRole.HEAD));
        var userNotifications = List.of(
                createTestNotification("test", RecipientRole.HEAD),
                createTestNotification("elagun", RecipientRole.HEAD),
                createTestNotification("admin", RecipientRole.HEAD),
                createTestNotification("elagun", RecipientRole.ENGINEER));

        roleNotifications.forEach(notificationService::save);
        userNotifications.forEach(notificationService::save);

        var requestBuilder = get("/api/v1/notification-service/notifications")
                .with(jwt().jwt(jwt -> jwt
                                .claim("sub", "test")
                                .claim("realm_access", Map.of("roles", List.of("HEAD"))))
                        .authorities(new SimpleGrantedAuthority("ROLE_HEAD")))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.totalElements").value(3));
    }

    @Test
    void getNotifications_withoutJwt_shouldReturnUnauthorized() throws Exception {
        var requestBuilder = get("/api/v1/notification-service/notifications")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotifications_shouldReturnEmptyPage() throws Exception {
        var requestBuilder = get("/api/v1/notification-service/notifications")
                .with(jwt().jwt(jwt -> jwt
                                .claim("sub", "unknown.user")
                                .claim("realm_access", Map.of("roles", List.of("ENGINEER"))))
                        .authorities(new SimpleGrantedAuthority("ROLE_ENGINEER")))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content").isEmpty(),
                        jsonPath("$.totalElements").value(0));
    }

    @Test
    void getNotifications_shouldReturnAllNotificationsForHead() throws Exception {
        var notifications = List.of(
                createTestNotification("elagun", RecipientRole.ENGINEER),
                createTestNotification("test", RecipientRole.HEAD),
                createTestNotification(null, RecipientRole.HEAD),
                createTestNotification(null, RecipientRole.ENGINEER));

        notifications.forEach(notificationService::save);

        var requestBuilder = get("/api/v1/notification-service/notifications")
                .with(jwt().jwt(jwt -> jwt
                                .claim("sub", "test")
                                .claim("realm_access", Map.of("roles", List.of("HEAD"))))
                        .authorities(new SimpleGrantedAuthority("ROLE_HEAD")))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content[*].recipient").value(containsInAnyOrder(
                                "test", null)),
                        jsonPath("$.totalElements").value(2));
    }

    private Notification createTestNotification(String recipient, RecipientRole role) {
        var notification = new Notification();
        notification.setPayload("Test notification for " + recipient);
        notification.setRecipient(recipient);
        notification.setRecipientRole(role);

        return notification;
    }
}
