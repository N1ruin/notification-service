package by.niruin.notification_service.integration;

import by.niruin.notification_service.NotificationServiceApplication;
import by.niruin.notification_service.integration.config.KafkaConfig;
import by.niruin.notification_service.integration.config.PostgresConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Import({PostgresConfig.class, KafkaConfig.class})
public class BaseIntegrationTest {
}
