package service;

import br.com.challenge_4.core.domain.Notification;
import br.com.challenge_4.core.domain.Status;
import br.com.challenge_4.dto.NotificationDto;
import br.com.challenge_4.repository.NotificationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository repository;
    @Autowired
    private ModelMapper mapper;

    @Async
    public CompletableFuture<Notification> sendNotificationAsync(NotificationDto dto) {
        var notification = mapper.map(dto, Notification.class);
        notification.setStatus(Status.PROCESSING);
        repository.save(notification);

        return CompletableFuture
                .runAsync(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Notification sending was interrupted", e);
                    }
                }, CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS))
                .thenApplyAsync(unused -> {
                    notification.setStatus(Status.SENT);
                    return repository.save(notification);
                })
                .exceptionally(ex -> {
                    notification.setStatus(Status.FAILED);
                    repository.save(notification);
                    throw new RuntimeException("Failed to send notification", ex);
                });
    }

    public NotificationDto saveNotification(NotificationDto dto) {
        var notification = mapper.map(dto, Notification.class);
        notification.setStatus(Status.PENDING);
        repository.save(notification);
        return mapper.map(notification, NotificationDto.class);
    }

    public Optional<NotificationDto> getNotificationStatus(Long id) {
        var notification = repository.findById(id);

        return mapper.map(notification, Optional.class);
    }
}
