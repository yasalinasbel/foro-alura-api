package com.foro.api.topic;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;
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