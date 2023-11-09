package com.foro.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class TestTopicRepositoryIT extends AbstractTestNGSpringContextTests {

    @Autowired
    private TopicRepository topicRepository;

    @Test
    @Transactional
    void testCreateTopic() {

        String title = "What means Spring";
        String message = "I dont understand the difference between Spring and SpringBoot";
        LocalDateTime creationDate = LocalDateTime.parse("2021-05-04T11:30:00.859762975");
        TopicStatus topicStatus = TopicStatus.CLOSED;
        Integer idUser = 3;
        Course course = Course.SPRINGBOOT;
        Boolean deleted = false;

        TopicDTO topicInformation = TopicDTO.builder()
                .title(title)
                .message(message)
                .creationDate(creationDate)
                .topicStatus(topicStatus)
                .idUser(idUser)
                .course(course)
                .deleted(deleted)
                .build();

        TopicDTO topic = topicRepository.save(topicInformation);
        TopicDTO topicInformationInitial=new TopicDTO(topicInformation.getId(), idUser,title,message,creationDate,topicStatus,course,deleted);


        String titleModified = "What means SpringBoot";
        String messageModified = "I dont understand the difference between Spring and SpringBoot";
        LocalDateTime creationDateModified =  LocalDateTime.parse("2021-05-04T11:30:00.859762975");
        TopicStatus topicStatusModified = TopicStatus.CLOSED;
        Integer idUserModified = 3;
        Course courseModified = Course.SPRINGBOOT;
        Boolean deletedModified = false;

        TopicDTO topicToBeModified = topicRepository.getReferenceById(topicInformation.getId());

        topicToBeModified.setTitle(titleModified);
        topicToBeModified.setMessage(messageModified);
        topicToBeModified.setCreationDate(creationDateModified);
        topicToBeModified.setTopicStatus(topicStatusModified);
        topicToBeModified.setIdUser(idUserModified);
        topicToBeModified.setCourse(courseModified);
        topicToBeModified.setDeleted(deletedModified);

        topicRepository.save(topicToBeModified);
        Assert.assertEquals(topicToBeModified.getTitle(),"What means SpringBoot");
        assertEqualsTopic(topicInformationInitial, topicToBeModified);

        topicRepository.deleteById(topicToBeModified.getId());
        boolean topicDeleted=topicRepository.findById(topicToBeModified.getId()).isEmpty();
        Assert.assertTrue(topicDeleted);
    }
    public void assertEqualsTopic(TopicDTO topicToBeCompared,TopicDTO topicWhoComparesTo){
        Assert.assertEquals(topicToBeCompared.getId(), topicWhoComparesTo.getId());
        Assert.assertEquals(topicToBeCompared.getMessage(), topicWhoComparesTo.getMessage());
        Assert.assertEquals(topicToBeCompared.getCreationDate(), topicWhoComparesTo.getCreationDate());
        Assert.assertEquals(topicToBeCompared.getTopicStatus(), topicWhoComparesTo.getTopicStatus());
        Assert.assertEquals(topicToBeCompared.getIdUser(), topicWhoComparesTo.getIdUser());
        Assert.assertEquals(topicToBeCompared.getCourse(), topicWhoComparesTo.getCourse());
        }
    @Test
    public void testListTopics(){

        String title = "What means Java";
        String message = "I dont understand the difference between Java and Python";
        LocalDateTime creationDate =  LocalDateTime.parse("2022-05-04T12:30:00.859762975");
        TopicStatus topicStatus = TopicStatus.CLOSED;
        Integer idUser = 4;
        Course course = Course.JAVA;
        Boolean deleted = false;

        TopicDTO topicInformation1 = TopicDTO.builder()
                .title(title)
                .message(message)
                .creationDate(creationDate)
                .topicStatus(topicStatus)
                .idUser(idUser)
                .course(course)
                .deleted(deleted)
                .build();

        TopicDTO topic = topicRepository.save(topicInformation1);

        String title2 = "Where could I comment in the foro page";
        String message2 = "I dont know how to reply the questions in the page";
        LocalDateTime creationDate2 =  LocalDateTime.parse("2023-04-04T03:30:00.859762975");
        TopicStatus topicStatus2 = TopicStatus.CLOSED;
        Integer idUser2 = 7;
        Course course2 = Course.OTHER;
        Boolean deleted2 = false;

        TopicDTO topicInformation2 = TopicDTO.builder()
                .title(title2)
                .message(message2)
                .creationDate(creationDate2)
                .topicStatus(topicStatus2)
                .idUser(idUser2)
                .course(course2)
                .deleted(deleted2)
                .build();

        TopicDTO topic2 = topicRepository.save(topicInformation2);

        List<TopicDTO> topics=topicRepository.findAll();
        Assert.assertEquals(topics.size(),2);

        topicRepository.deleteById(topic.getId());
        topicRepository.deleteById(topic2.getId());

        boolean idTopicDeleted=topicRepository.findById(topic.getId()).isEmpty();
        boolean idTopic2Deleted=topicRepository.findById(topic2.getId()).isEmpty();

        Assert.assertTrue(idTopicDeleted);
        Assert.assertTrue(idTopic2Deleted);
    }
}

