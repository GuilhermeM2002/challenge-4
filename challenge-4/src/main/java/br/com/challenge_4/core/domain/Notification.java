package br.com.challenge_4.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document("notification")
public class Notification {
    @Id
    private Long id;
    private String recipient;
    private String message;
    private String type;
    private Status status;
    private LocalDateTime dateTime;

}
