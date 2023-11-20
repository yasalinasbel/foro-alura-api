package com.foro.api.topic;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
    @Table(name="reply")
    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
   @AllArgsConstructor
    @EqualsAndHashCode(of="id")
    @Builder(toBuilder = true)
    public class ReplyDTO{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private Integer idUser;
        private Integer idTopic;
        private String reply;
        private LocalDateTime creationDateReply;

    }


