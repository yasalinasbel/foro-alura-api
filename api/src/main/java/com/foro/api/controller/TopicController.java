package com.foro.api.controller;

import com.foro.api.service.Topic;
import com.foro.api.service.TopicReplyService;
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
@RequestMapping("/topics")
public class TopicController {
    @Autowired
    private TopicReplyService topicReplyService;

    @PostMapping
    public ResponseEntity<Topic> registerTopic(@RequestBody @Valid Topic topic, UriComponentsBuilder uriComponentsBuilder){
        Topic topicSaved = topicReplyService.saveTopic(topic);
        URI url=uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(url).body(topicSaved);
    }

    @GetMapping
    public ResponseEntity<Page<Topic>> topicListController(@PageableDefault(size=2) Pageable pagination){
        Page<Topic> topicList=topicReplyService.topicList(pagination);
        return ResponseEntity.ok(topicList);
    }
    @GetMapping("/{id}")
    public ResponseEntity <Topic> returnsTopicInformation(@PathVariable @Valid Integer id) {
        Topic topicToReturn = topicReplyService.topicById(id);
        return ResponseEntity.ok(topicToReturn);
    }
}
