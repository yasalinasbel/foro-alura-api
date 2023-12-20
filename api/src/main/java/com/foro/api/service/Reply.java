package com.foro.api.service;

import com.foro.api.reply.ReplyDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Reply {
        @NotNull
        private Integer id;
        @NotBlank
        private String reply;
        private Integer idUser;
        private Integer idTopic;

        public static Reply fromReply(ReplyDTO replyDTO) {
                Reply reply=Reply.builder()
                        .id(replyDTO.getId())
                        .reply(replyDTO.getReply())
                        .idUser(replyDTO.getIdUser())
                        .idTopic(replyDTO.getIdTopic())
                        .build();
                return reply;
        }
}
