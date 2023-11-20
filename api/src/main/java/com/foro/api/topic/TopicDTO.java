package com.foro.api.topic;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name="topic")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
public class TopicDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idUser;
    private String title;
    private String message;
    private LocalDateTime creationDate;
    @Enumerated(EnumType.STRING)
    private TopicStatus topicStatus;
    @Enumerated(EnumType.STRING)
    private Course course;
    private Boolean deleted;
}