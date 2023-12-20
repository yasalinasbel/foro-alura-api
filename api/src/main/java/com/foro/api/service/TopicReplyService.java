package com.foro.api.service;

import com.foro.api.reply.ReplyDTO;
import com.foro.api.reply.ReplyRepository;
import com.foro.api.topic.TopicDTO;
import com.foro.api.topic.TopicRepository;
import com.foro.api.topic.TopicStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class TopicReplyService {
    private final TopicRepository topicRepository;
    private final ReplyRepository replyRepository;

    public TopicReplyService(TopicRepository topicRepository, ReplyRepository replyRepository) {
        this.topicRepository = topicRepository;
        this.replyRepository = replyRepository;
    }

    @Transactional(readOnly = true)
    public Topic topicById(Integer id) {
        TopicDTO topicDTO = topicRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Please enter a valid id"));
        Topic topicById = Topic.from(topicDTO);
        return topicById;
    }

    @Transactional(readOnly = true)
    public Page<Topic> topicList(Pageable pagination) {
        Page<TopicDTO> topicDTOList = topicRepository.findAll(pagination);
        Page<Topic> topicList = topicDTOList.map(topicDTO -> Topic.from(topicDTO));
        return topicList;
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
    public Page<Reply> replyList(Pageable pagination) {
        Page<ReplyDTO> replyDTOList=replyRepository.findByDeletedReplyFalse(pagination);
        Page<Reply> replyList=replyDTOList.map(replyDTO -> Reply.fromReply(replyDTO));
        return replyList;
    }

    @Transactional(readOnly = true)
    public Reply replyById(Integer id) {
        ReplyDTO replyDTO = replyRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Please enter a valid Id"));
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
    public boolean deleteReply(Integer id) {
        ReplyDTO replyToDeleteById = replyRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Please enter a valid Id"));
        replyToDeleteById.setDeletedReply(true);
        return replyToDeleteById.getDeletedReply();
    }

    @Transactional
    public Reply updateReply(Reply replyService) {
        validateReply(replyService);
        String reply = replyService.getReply();
        Integer idReply = replyService.getId();

        ReplyDTO replyById = replyRepository.findById(idReply).orElseThrow(() ->new IllegalArgumentException("The id "+idReply+ "must be a valid number"));
        replyById.setReply(reply);
        Reply replyUpdated = Reply.fromReply(replyById);
        return replyUpdated;
    }

    private void validateTopic(Topic topic){

        String title=topic.getTitle();
        String message= topic.getMessage();

        if(title==null || title.isBlank()){
            throw new IllegalArgumentException("Please add a valid title");
        }
        if(message==null || message.isBlank()){
            throw new IllegalArgumentException("Please add a valid message");
        }
    }

    private void validateReply(Reply replyToValidate){
        String reply=replyToValidate.getReply();

        if(reply==null|| reply.isBlank()){
            throw new IllegalArgumentException("Please add a valid reply");
        }
    }


}


