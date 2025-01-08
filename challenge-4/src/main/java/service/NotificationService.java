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

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository repository;
    @Autowired
    private ModelMapper mapper;

    @Async
    @Transactional
    public CompletableFuture<Void> sendNotificationAsync(NotificationDto dto) {
        var notification = mapper.map(dto, Notification.class);
        try {
            notification.setStatus(Status.PROCESSING);
            repository.save(notification);

            // Simular envio de notificação
            Thread.sleep(5000);

            notification.setStatus(Status.SENT);
            repository.save(notification);
        } catch (InterruptedException e) {
            notification.setStatus(Status.FAILED);
            repository.save(notification);
        }

        return CompletableFuture.completedFuture(null);
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
