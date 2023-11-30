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
    private final TopicRepository topicRepository;

    @Autowired
    private final ReplyRepository replyRepository;
    public TopicReplyService(TopicRepository topicRepository, ReplyRepository replyRepository) {
        this.topicRepository = topicRepository;
        this.replyRepository = replyRepository;
    }

    @Transactional(readOnly = true)
    public Topic topicById(Integer id) {
        TopicDTO topicDTO = topicRepository.findById(id).orElse(null);
        if(topicDTO!=null){
            Topic topicById = Topic.from(topicDTO);
            return topicById;
        }else{
            throw new IllegalArgumentException("Ingrese un id válido");
        }
    }

    @Transactional(readOnly = true)
    public List<Topic> topicList() {
        List<TopicDTO> topicDTOList = topicRepository.findAll();
        List<Topic> topicList = topicDTOList.stream().map(topicDTO -> Topic.from(topicDTO)).collect(Collectors.toList());
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
    public List<Reply> replyList() {
        List<ReplyDTO> replyDTOList=replyRepository.findAll();
        List<Reply> replyList=replyDTOList.stream().map(replyDTO -> Reply.fromReply(replyDTO)).collect(Collectors.toList());
        return replyList;
    }

    @Transactional(readOnly = true)
    public Reply replyById(Integer id) {
        ReplyDTO replyDTO = replyRepository.findById(id).orElse(null);
        if(replyDTO!=null){
            Reply replyById = Reply.fromReply(replyDTO);
            return replyById;
        }else{
            throw new IllegalArgumentException("Ingrese un id válido");
        }
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
        ReplyDTO replyToDeleteById = replyRepository.getReferenceById(id);
        if(replyToDeleteById!=null) {
            replyToDeleteById.setDeletedReply(true);
            return replyToDeleteById.getDeletedReply();
        }else{
            throw new IllegalArgumentException("Ingrese un id válido");
        }
    }

    @Transactional
    public Reply updateReply(Reply replyService) {
        validateReply(replyService);
        String reply = replyService.getReply();
        Integer idReply = replyService.getId();

        if (idReply!=null) {
            ReplyDTO replyById = replyRepository.getReferenceById(idReply);
            replyById.setReply(reply);
            Reply replyUpdated = Reply.fromReply(replyById);
            return replyUpdated;
        }else{
            throw new IllegalArgumentException("The id "+idReply+ "must be a valid number");
        }
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


