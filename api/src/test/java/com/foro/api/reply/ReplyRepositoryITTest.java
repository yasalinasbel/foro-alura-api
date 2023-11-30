package com.foro.api.reply;

import com.foro.api.topic.Course;
import com.foro.api.topic.TopicDTO;
import com.foro.api.topic.TopicRepository;
import com.foro.api.topic.TopicStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ReplyRepositoryITTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private TopicRepository topicRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void testReplyCRUD(){
        String title = "What means Spring";
        String message = "I dont understand the difference between Spring and SpringBoot";
        LocalDateTime creationDateTopic = LocalDateTime.parse("2021-05-04T11:30:00.859762975");
        TopicStatus topicStatus = TopicStatus.CLOSED;
        Integer idUser = 3;
        Course course = Course.SPRINGBOOT;
        Boolean deleted = false;

        TopicDTO topicInformation = TopicDTO.builder()
                .title(title)
                .message(message)
                .creationDate(creationDateTopic)
                .topicStatus(topicStatus)
                .idUser(idUser)
                .course(course)
                .deleted(deleted)
                .build();
        TopicDTO topic = topicRepository.save(topicInformation);

        String reply = "This is a comprehensive framework that provides a wide range of tools and libraries to simplify Python development";
        Integer idTopic = topic.getId();
        LocalDateTime creationDateReply=LocalDateTime.parse("2022-05-04T02:30:00.859762975");
        Boolean deletedReply=false;

        ReplyDTO replyInformation = ReplyDTO.builder()
                .idUser(idUser)
                .idTopic(idTopic)
                .reply(reply)
                .creationDateReply(creationDateReply)
                .deletedReply(deletedReply)
                .build();

        replyRepository.save(replyInformation);
        ReplyDTO replyInitialInformation = new ReplyDTO(replyInformation.getId(), replyInformation.getIdUser(), replyInformation.getIdTopic(), replyInformation.getReply(),replyInformation.getCreationDateReply(),deletedReply);

        String replyModified="This is a comprehensive framework that provides a wide range of tools and libraries to simplify Java development";
        LocalDateTime creationDateReplyModified=LocalDateTime.parse("2023-05-04T04:30:00.859762975");

        ReplyDTO replyToBeModified=replyRepository.getReferenceById((replyInformation.getId()));

        replyToBeModified.setReply(replyModified);
        replyToBeModified.setCreationDateReply(creationDateReplyModified);

        replyRepository.save(replyToBeModified);
        Assert.assertEquals(replyToBeModified.getReply(),"This is a comprehensive framework that provides a wide range of tools and libraries to simplify Java development");
        Assert.assertEquals(replyToBeModified.getCreationDateReply(),(LocalDateTime.parse("2023-05-04T04:30:00.859762975")));
        assertEqualsTopic(replyInitialInformation, replyToBeModified);

        replyRepository.deleteById(replyToBeModified.getId());
        boolean replyDeleted = replyRepository.findById(replyToBeModified.getId()).isEmpty();
        Assert.assertTrue(replyDeleted);
    }
    private void assertEqualsTopic(ReplyDTO replyToBeCompared, ReplyDTO replyWhoComparesTo) {
        Assert.assertEquals(replyToBeCompared.getIdTopic(), replyWhoComparesTo.getIdTopic());
        Assert.assertEquals(replyToBeCompared.getId(), replyWhoComparesTo.getId());
        Assert.assertEquals(replyToBeCompared.getIdUser(), replyWhoComparesTo.getIdUser());
    }
    @Test
    public void testListReplies() {

        String title = "What means Spring";
        String message = "I dont understand the difference between Spring and SpringBoot";
        LocalDateTime creationDateTopic = LocalDateTime.parse("2021-05-04T11:30:00.859762975");
        TopicStatus topicStatus = TopicStatus.CLOSED;
        Integer idUser = 3;
        Course course = Course.SPRINGBOOT;
        Boolean deleted = false;

        TopicDTO topicInformation = TopicDTO.builder()
                .title(title)
                .message(message)
                .creationDate(creationDateTopic)
                .topicStatus(topicStatus)
                .idUser(idUser)
                .course(course)
                .deleted(deleted)
                .build();
        TopicDTO topic = topicRepository.save(topicInformation);
        TopicDTO topicInitialInformation = new TopicDTO(topicInformation.getId(), idUser, title, message, creationDateTopic, topicStatus, course, deleted);

        String reply = "This is a comprehensive framework that provides a wide range of tools and libraries to simplify Python development";
        Integer idTopic =topicInitialInformation.getId();
        LocalDateTime creationDateReply=LocalDateTime.parse("2022-05-04T02:30:00.859762975");
        Boolean deletedReply=false;

        ReplyDTO replyInformation1 = ReplyDTO.builder()
                .idUser(idUser)
                .idTopic(idTopic)
                .reply(reply)
                .creationDateReply(creationDateReply)
                .deletedReply(deletedReply)
                .build();

        ReplyDTO replyDTO = replyRepository.save(replyInformation1);

        String reply2 = "This is a comprehensive framework that provides a wide range of tools and libraries to simplify Python development";
        Integer idTopic2 = topicInitialInformation.getId();
        LocalDateTime creationDateReply2=LocalDateTime.parse("2022-05-04T06:30:00.859762975");
        Boolean deletedReply2=false;

        ReplyDTO replyInformation2 = ReplyDTO.builder()
                .idUser(idUser)
                .idTopic(idTopic2)
                .reply(reply2)
                .creationDateReply(creationDateReply2)
                .deletedReply(deletedReply2)
                .build();

        ReplyDTO replyDTO2 = replyRepository.save(replyInformation2);

        List<ReplyDTO> replies = replyRepository.findAll();
        Assert.assertEquals(replies.size(), 2);

        replyRepository.deleteById(replyDTO.getId());
        replyRepository.deleteById(replyDTO2.getId());

        boolean idReplyDeleted = replyRepository.findById(replyDTO.getId()).isEmpty();
        boolean idReply2Deleted = replyRepository.findById(replyDTO2.getId()).isEmpty();

        Assert.assertTrue(idReplyDeleted);
        Assert.assertTrue(idReply2Deleted);

        topicRepository.deleteById(topicInitialInformation.getId());
        boolean topicDeleted = topicRepository.findById(topicInitialInformation.getId()).isEmpty();
        Assert.assertTrue(topicDeleted);
    }
}