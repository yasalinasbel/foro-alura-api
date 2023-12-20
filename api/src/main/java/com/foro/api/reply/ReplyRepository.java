package com.foro.api.reply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyDTO,Integer> {

    Page<ReplyDTO> findByDeletedReplyFalse(Pageable pagination);

    //  ReplyDTO findByDeletedReplyFalse(Integer id);
}
