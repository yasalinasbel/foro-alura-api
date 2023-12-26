package com.foro.api.service;

import com.foro.api.topic.Course;
import com.foro.api.topic.TopicDTO;
import com.foro.api.topic.TopicStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Topic {
    @NotNull
    private Integer id;
    @NotBlank
    private String title;
    private Integer idUser;
    @NotBlank
    private String message;
    @Valid
    private TopicStatus topicStatus;
    @Valid
    private Course course;

    public static Topic from(TopicDTO topicDTO){
        Topic topic= Topic.builder()
            .id(topicDTO.getId())
            .title(topicDTO.getTitle())
            .idUser(topicDTO.getIdUser())
            .message(topicDTO.getMessage())
            .topicStatus(topicDTO.getTopicStatus())
            .course(topicDTO.getCourse())
            .build();

        return topic;
    }
}

