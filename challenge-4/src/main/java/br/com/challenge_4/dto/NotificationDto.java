package br.com.challenge_4.dto;

import br.com.challenge_4.core.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private String recipient;
    private String message;
    private String type;
    private Status status;
    private LocalDateTime dateTime;
}
