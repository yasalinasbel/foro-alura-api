package com.foro.api.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyDTO,Integer> {

    List<ReplyDTO> findByDeletedReplyFalse();

}
