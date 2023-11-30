package com.foro.api.service;

import com.foro.api.reply.ReplyDTO;
import com.foro.api.reply.ReplyRepository;
import com.foro.api.topic.TopicDTO;
import com.foro.api.topic.TopicRepository;
import com.foro.api.topic.TopicStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class TopicReplyService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @Transactional(readOnly = true)
    public List<Topic> topicList() {
        List<TopicDTO> topicDTOList = topicRepository.findAll();
        List<Topic> topicList = topicDTOList.stream().map(topicDTO -> Topic.from(topicDTO)).collect(Collectors.toList());
        return topicList;
    }

    @Transactional(readOnly = true)
    public Topic topicById(Integer id) {
        TopicDTO topicDTO = topicRepository.findById(id).orElse(null);
        Topic topicById = Topic.from(topicDTO);
        return topicById;
    }

    @Transactional
    public Topic saveTopic(Topic topic) {
        validateTopic(topic);
        TopicDTO topicToSave=new TopicDTO();
        topicToSave.setTitle(topic.getTitle());
        topicToSave.setIdUser(topic.getIdUser());
        topicToSave.setMessage(topic.getMessage());
        topicToSave.setCourse(topic.getCourse());
        topicToSave.setCreationDate(LocalDateTime.now());
        topicToSave.setDeleted(false);
        topicToSave.setTopicStatus(TopicStatus.OPEN);
        TopicDTO topicSaved = topicRepository.save(topicToSave);
        return Topic.from(topicSaved);
    }

    @Transactional(readOnly = true)
    public List<Reply> replyList() {
        List<ReplyDTO> replyDTOList=replyRepository.findAll();
        List<Reply> replyList=replyDTOList.stream().map(replyDTO -> Reply.fromReply(replyDTO)).collect(Collectors.toList());
        return replyList;
    }

    @Transactional(readOnly = true)
    public Reply replyById(Integer id) {
        ReplyDTO replyDTO = replyRepository.findById(id).orElse(null);
        Reply replyById = Reply.fromReply(replyDTO);
        return replyById;
    }

    @Transactional
    public Reply saveReply(Reply reply) {
        validateReply(reply);
        ReplyDTO replyDTO=new ReplyDTO();
        replyDTO.setReply(reply.getReply());
        replyDTO.setIdUser(reply.getIdUser());
        replyDTO.setIdTopic(reply.getIdTopic());
        replyDTO.setCreationDateReply(LocalDateTime.now());
        replyDTO.setDeletedReply(false);
        ReplyDTO replySaved = replyRepository.save(replyDTO);
        return Reply.fromReply(replySaved);
    }

    @Transactional
    public boolean deleteReply(Reply reply) {
        validateReply(reply);
        ReplyDTO replyToDeleteById = replyRepository.getReferenceById(reply.getId());
        replyToDeleteById.setDeletedReply(true);

        return replyToDeleteById.getDeletedReply();
    }

    @Transactional
    public Reply updateReply(Reply replyService) {
        validateReply(replyService);
        String reply = replyService.getReply();

        ReplyDTO replyById = replyRepository.getReferenceById(replyService.getId());
        replyById.setReply(reply);
        replyById.setCreationDateReply(LocalDateTime.now());
        replyById.setDeletedReply(false);

        Reply replyUpdated = Reply.fromReply(replyById);

        return replyUpdated;
    }

    public void validateTopic(Topic topic){
        String title=topic.getTitle();
        String message= topic.getMessage();

        if(title==null){
            throw new IllegalArgumentException("Title was null");
        }
        if(message==null){
            throw new IllegalArgumentException("Message was null");
        }
    }

    public void validateReply(Reply replyToValidate){
        String reply=replyToValidate.getReply();

        if(reply==null){
            throw new IllegalArgumentException("Title was null");
        }
    }


}


