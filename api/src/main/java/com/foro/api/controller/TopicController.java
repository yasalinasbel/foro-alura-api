package com.foro.api.controller;

import com.foro.api.service.Topic;
import com.foro.api.service.TopicReplyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicReplyService topicReplyService;

    public TopicController(TopicReplyService topicReplyService) {
        this.topicReplyService = topicReplyService;
    }

    @PostMapping
    public ResponseEntity<Topic> registerTopic(@RequestBody @Valid Topic topic, UriComponentsBuilder uriComponentsBuilder){
        Topic topicSaved = topicReplyService.saveTopic(topic);
        URI url=uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(url).body(topicSaved);
    }

    @GetMapping
    public ResponseEntity<Page<Topic>> topicListController(@PageableDefault(size=2) Pageable pagination){
        List<Topic> topicList=topicReplyService.topicList();
        Page<Topic> topicPage=new PageImpl<>(topicList, pagination,topicList.size());
        return ResponseEntity.ok(topicPage);
    }
    @GetMapping("/{id}")
    public ResponseEntity <Topic> returnsTopicInformation(@PathVariable @Valid Integer id) {
        Topic topicToReturn = topicReplyService.topicById(id);
        return ResponseEntity.ok(topicToReturn);
    }
}
