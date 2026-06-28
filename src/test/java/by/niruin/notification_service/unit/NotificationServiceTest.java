package by.niruin.notification_service.unit;

import by.niruin.notification_service.domain.Notification;
import by.niruin.notification_service.domain.RecipientRole;
import by.niruin.notification_service.repository.NotificationRepository;
import by.niruin.notification_service.model.notification.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;
    @InjectMocks
    private NotificationService notificationService;

    @Test
    void findAllForUser_shouldReturnUserAndRoleNotifications() {
        var username = "elagun";
        var role = RecipientRole.ENGINEER;
        var pageable = PageRequest.of(0, 10);

        var userNotification = new Notification();
        userNotification.setRecipient(username);
        userNotification.setRecipientRole(role);
        userNotification.setPayload("Test for user");

        var roleNotification = new Notification();
        roleNotification.setRecipient(null);
        roleNotification.setRecipientRole(role);
        roleNotification.setPayload("Test for role");

        var expectedPage = new PageImpl<>(List.of(userNotification, roleNotification));

        when(notificationRepository.findAllForUser(eq(username), eq(role), any(Pageable.class)))
                .thenReturn(expectedPage);

        var result = notificationService.findAllForUser(username, role, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent())
                .extracting("recipient")
                .containsExactlyInAnyOrder(username, null);
        assertThat(result.getContent())
                .extracting("recipientRole")
                .containsExactly(RecipientRole.ENGINEER, RecipientRole.ENGINEER);

        verify(notificationRepository).findAllForUser(username, role, pageable);
    }

    @Test
    void findAllForUser_shouldReturnOnlyUserNotifications_whenNoRoleNotifications() {
        var username = "elagun";
        var role = RecipientRole.HEAD;
        var pageable = PageRequest.of(0, 10);

        var userNotification = new Notification();
        userNotification.setRecipient(username);
        userNotification.setRecipientRole(role);
        userNotification.setPayload("Test for user as HEAD");

        var expectedPage = new PageImpl<>(List.of(userNotification));

        when(notificationRepository.findAllForUser(eq(username), eq(role), any(Pageable.class)))
                .thenReturn(expectedPage);

        var result = notificationService.findAllForUser(username, role, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst().getRecipient()).isEqualTo(username);
        assertThat(result.getContent().getFirst().getRecipientRole()).isEqualTo(role);

        verify(notificationRepository).findAllForUser(username, role, pageable);
    }

    @Test
    void findAllForUser_shouldReturnEmptyPage_whenNoNotifications() {
        var username = "unknown";
        var role = RecipientRole.ENGINEER;
        var pageable = PageRequest.of(0, 10);

        when(notificationRepository.findAllForUser(eq(username), eq(role), any(Pageable.class)))
                .thenReturn(Page.empty());

        var result = notificationService.findAllForUser(username, role, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getContent()).isEmpty();

        verify(notificationRepository).findAllForUser(username, role, pageable);
    }

    @Test
    void save_shouldCallRepositorySave() {
        var notification = new Notification();
        notification.setPayload("Test payload");
        notification.setRecipient("elagun");
        notification.setRecipientRole(RecipientRole.ENGINEER);

        notificationService.save(notification);

        verify(notificationRepository).save(notification);
    }

    @Test
    void save_shouldReturnSavedNotification() {
        var notification = new Notification();
        notification.setPayload("Test payload");
        notification.setRecipient("elagun");
        notification.setRecipientRole(RecipientRole.ENGINEER);

        var savedNotification = new Notification();
        savedNotification.setPayload("Test payload");
        savedNotification.setRecipient("elagun");
        savedNotification.setRecipientRole(RecipientRole.ENGINEER);

        when(notificationRepository.save(notification)).thenReturn(savedNotification);

        var result = notificationService.save(notification);

        assertThat(result).isNotNull();
        assertThat(result.getRecipient()).isEqualTo("elagun");
        assertThat(result.getRecipientRole()).isEqualTo(RecipientRole.ENGINEER);
        assertThat(result.getPayload()).isEqualTo("Test payload");

        verify(notificationRepository).save(notification);
    }
}