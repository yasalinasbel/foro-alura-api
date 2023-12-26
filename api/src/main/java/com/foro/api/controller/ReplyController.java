package com.foro.api.controller;

import com.foro.api.service.Reply;
import com.foro.api.service.TopicReplyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/replies")
public class ReplyController {

    private final TopicReplyService topicReplyService ;

    public ReplyController(TopicReplyService topicReplyService) {
        this.topicReplyService = topicReplyService;
    }


    @PostMapping
    public ResponseEntity<Reply> registerReply(@RequestBody @Valid Reply reply, UriComponentsBuilder uriComponentsBuilder){
        Reply replySaved = topicReplyService.saveReply(reply);
        URI url = uriComponentsBuilder.path("/replies/{id}").buildAndExpand(reply.getId()).toUri();
        return ResponseEntity.created(url).body(replySaved);
    }

    @PutMapping
    public ResponseEntity updateReplyController(@RequestBody@Valid Reply reply){
        Reply replyUpdated = topicReplyService.updateReply(reply);
        return ResponseEntity.ok(replyUpdated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteReplyController(@PathVariable @Valid Integer id){
        topicReplyService.deleteReply(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<Page<Reply>> replyListController(@PageableDefault(size=2) Pageable pagination){
        List<Reply> replyList=topicReplyService.replyList();
        Page<Reply> replyPage=new PageImpl<>(replyList,pagination,replyList.size());
        return ResponseEntity.ok(replyPage);
    }
    @GetMapping("/{id}")
    public ResponseEntity <Reply> returnsReplyInformation(@PathVariable @Valid Integer id) {
        Reply replyToReturn = topicReplyService.replyById(id);
        return ResponseEntity.ok(replyToReturn);
    }

}
