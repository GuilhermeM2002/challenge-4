package br.com.challenge_4.controller;

import br.com.challenge_4.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.NotificationService;

@RestController
@RequestMapping("notification")
public class NotificationController {
    @Autowired
    private NotificationService service;

    @PostMapping
    public ResponseEntity<Long> createNotification(@RequestBody NotificationDto dto) {
        var saved = service.saveNotification(dto);
        service.sendNotificationAsync(saved);
        return ResponseEntity.ok(saved.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotificationStatus(@PathVariable Long id) {
        return service.getNotificationStatus(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
