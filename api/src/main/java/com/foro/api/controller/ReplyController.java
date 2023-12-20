package com.foro.api.controller;

import com.foro.api.service.Reply;
import com.foro.api.service.TopicReplyService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/replies")
public class ReplyController {
    @Autowired
    private TopicReplyService topicReplyService;

    @PostMapping
    public ResponseEntity<Reply> registerReply(@RequestBody @Valid Reply reply, UriComponentsBuilder uriComponentsBuilder){
        Reply replySaved = topicReplyService.saveReply(reply);
        URI url = uriComponentsBuilder.path("/replies/{id}").buildAndExpand(reply.getId()).toUri();
        return ResponseEntity.created(url).body(replySaved);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateReplyController(@RequestBody@Valid Reply reply){
        Reply replyUpdated = topicReplyService.updateReply(reply);
        return ResponseEntity.ok(replyUpdated);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteReplyController(@PathVariable @Valid Integer id){
        topicReplyService.deleteReply(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<Page<Reply>> replyListController(@PageableDefault(size=2) Pageable pagination){
        Page<Reply> replyList=topicReplyService.replyList(pagination);
        return ResponseEntity.ok(replyList);
    }

    @GetMapping("/{id}")
    public ResponseEntity <Reply> returnsReplyInformation(@PathVariable @Valid Integer id) {
        Reply replyToReturn = topicReplyService.replyById(id);
        return ResponseEntity.ok(replyToReturn);

    }

}
