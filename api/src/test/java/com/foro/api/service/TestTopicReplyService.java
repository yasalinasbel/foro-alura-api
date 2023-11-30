package com.foro.api.service;

import com.foro.api.topic.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

@SpringBootTest
public class TestTopicReplyService extends AbstractTestNGSpringContextTests {
    @Autowired
    private TopicReplyService topicReplyService;

    @Test
    @Rollback(value = true)
    //@Rollback(false) se utiliza para especificar que no se debe realizar un rollback al final de la transacción, permitiendo que los cambios en la base de datos persistan
    @Transactional
    void testServiceCRUD() {
        String title = "What means GitHub";
        Integer idUser = 2;
        String message = "I dont understand the difference between Git and GitHub";
        Course course = Course.GITHUB;

        Topic topicInformation = Topic.builder()
                .title(title)
                .idUser(idUser)
                .message(message)
                .course(course)
                .build();

        Topic topicInitialInformation = topicReplyService.saveTopic(topicInformation);

        String title2 = "What means Git";
        Integer idUser2 = 1;
        String message2 = "I dont understand the difference between Git and GitHub";
        Course course2 = Course.GITHUB;

        Topic topicInformation2 = Topic.builder()
                .title(title2)
                .idUser(idUser2)
                .message(message2)
                .course(course2)
                .build();

        Topic topicInitialInformation2 = topicReplyService.saveTopic(topicInformation2);

        Assert.assertEquals(topicInitialInformation.getTitle(), title);
        Assert.assertEquals(topicInitialInformation.getIdUser(), idUser);
        Assert.assertEquals(topicInitialInformation.getMessage(), message);
        Assert.assertEquals(topicInitialInformation.getCourse(), course);

        Assert.assertEquals(topicInitialInformation2.getTitle(), title2);
        Assert.assertEquals(topicInitialInformation2.getIdUser(), idUser2);
        Assert.assertEquals(topicInitialInformation2.getMessage(), message2);
        Assert.assertEquals(topicInitialInformation2.getCourse(), course2);

        Topic topic = topicReplyService.topicById(topicInitialInformation.getId());

        assertEqualsTopic(topicInitialInformation, topic);

        List<Topic> topicsList = topicReplyService.topicList();
        Assert.assertEquals(topicsList.size(), 2);
    }

    public void assertEqualsTopic(Topic topicToBeCompared, Topic topicWhoComparesTo) {
        Assert.assertEquals(topicToBeCompared.getId(), topicWhoComparesTo.getId());
        Assert.assertEquals(topicToBeCompared.getMessage(), topicWhoComparesTo.getMessage());
        Assert.assertEquals(topicToBeCompared.getTopicStatus(), topicWhoComparesTo.getTopicStatus());
        Assert.assertEquals(topicToBeCompared.getCourse(), topicWhoComparesTo.getCourse());
    }

    @Test
    @Rollback(value = true)
    @Transactional
    public void testSaveReply() {

        String title = "What means GitHub";
        Integer idUser = 2;
        String message = "I dont understand the difference between Git and GitHub";
        Course course = Course.GITHUB;

        Topic topicInformation = Topic.builder()
                .title(title)
                .idUser(idUser)
                .message(message)
                .course(course)
                .build();

        Topic topicInitialInformation = topicReplyService.saveTopic(topicInformation);

        String reply = "is a web-based platform that uses the Git version control system. It provides hosting for software development and a variety of features that facilitate collaboration among developers. ";
        Integer idUserReply = topicInitialInformation.getIdUser();
        Integer idTopic = topicInitialInformation.getId();

        Reply replyInformation = Reply.builder()
                .reply(reply)
                .idUser(idUserReply)
                .idTopic(idTopic)
                .build();

        topicReplyService.saveReply(replyInformation);
    }

    @Test
    @Rollback(value = true)
    @Transactional
    public void testUpdateReply() {

        String title = "What means GitHub";
        Integer idUser = 2;
        String message = "I dont understand the difference between Git and GitHub";
        Course course = Course.GITHUB;

        Topic topicInformation = Topic.builder()
                .title(title)
                .idUser(idUser)
                .message(message)
                .course(course)
                .build();

        Topic topicInitialInformation = topicReplyService.saveTopic(topicInformation);

        String reply = "is a web-based platform that uses the Git version control system. It provides hosting for software development and a variety of features that facilitate collaboration among developers. ";
        Integer idUserReply = topicInformation.getIdUser();
        Integer idTopic = topicInitialInformation.getId();

        Reply replyInformation = Reply.builder()
                .reply(reply)
                .idUser(idUserReply)
                .idTopic(idTopic)
                .build();

        Reply replyInitialInformation = topicReplyService.saveReply(replyInformation);

        Integer idReply = replyInitialInformation.getId();
        String replyModified = " It has become a standard platform for collaborative software development due to its ease of use, powerful features, and a large community of developers.";
        Integer idUserReplyModified = 1;
        Integer idTopicModified = topicInitialInformation.getId();

        Reply replyInformationModified = Reply.builder()
                .id(idReply)
                .reply(replyModified)
                .idUser(idUserReplyModified)
                .idTopic(idTopicModified)
                .build();
        topicReplyService.updateReply(replyInformationModified);
    }

    @Test
    @Rollback(value = true)
    @Transactional
    public void testReplyList() {

        String title = "What means GitHub";
        Integer idUser = 2;
        String message = "I dont understand the difference between Git and GitHub";
        Course course = Course.GITHUB;

        Topic topicInformation = Topic.builder()
                .title(title)
                .idUser(idUser)
                .message(message)
                .course(course)
                .build();

        Topic topicInitialInformation = topicReplyService.saveTopic(topicInformation);

        String reply = "is a web-based platform that uses the Git version control system. It provides hosting for software development and a variety of features that facilitate collaboration among developers. ";
        Integer idUserReply = topicInformation.getIdUser();
        Integer idTopic = topicInitialInformation.getId();

        Reply replyInformation = Reply.builder()
                .reply(reply)
                .idUser(idUserReply)
                .idTopic(idTopic)
                .build();

        Reply replyInitialInformation1 = topicReplyService.saveReply(replyInformation);

        String reply2 = " It has become a standard platform for collaborative software development due to its ease of use, powerful features, and a large community of developers.";
        Integer idUserReply2 = topicInformation.getIdUser();
        Integer idTopic2 = topicInitialInformation.getId();

        Reply replyInformation2 = Reply.builder()
                .reply(reply2)
                .idUser(idUserReply2)
                .idTopic(idTopic2)
                .build();

        topicReplyService.saveReply(replyInformation2);

        Reply replyById = topicReplyService.replyById(replyInitialInformation1.getId());

        assertEqualsReply(replyInitialInformation1, replyById);

        List<Reply> repliesList = topicReplyService.replyList();
        Assert.assertEquals(repliesList.size(), 2);
    }

    public void assertEqualsReply(Reply replyToBeCompared, Reply replyWhoComparesTo) {
        Assert.assertEquals(replyToBeCompared.getId(), replyWhoComparesTo.getId());
        Assert.assertEquals(replyToBeCompared.getReply(), replyWhoComparesTo.getReply());
        Assert.assertEquals(replyToBeCompared.getIdUser(), replyWhoComparesTo.getIdUser());
        Assert.assertEquals(replyToBeCompared.getIdTopic(), replyWhoComparesTo.getIdTopic());
    }
    @Test
    @Rollback(value = true)
    @Transactional
    public void testDeleteReply() {

        String title = "What means GitHub";
        Integer idUser = 2;
        String message = "I dont understand the difference between Git and GitHub";
        Course course = Course.GITHUB;

        Topic topicInformation = Topic.builder()
                .title(title)
                .idUser(idUser)
                .message(message)
                .course(course)
                .build();

        Topic topicInitialInformation = topicReplyService.saveTopic(topicInformation);

        String reply = "is a web-based platform that uses the Git version control system. It provides hosting for software development and a variety of features that facilitate collaboration among developers. ";
        Integer idUserReply = topicInformation.getIdUser();
        Integer idTopic = topicInitialInformation.getId();

        Reply replyInformation = Reply.builder()
                .reply(reply)
                .idUser(idUserReply)
                .idTopic(idTopic)
                .build();

        Reply replyInitialInformation1 = topicReplyService.saveReply(replyInformation);

        boolean replyDeleted = topicReplyService.deleteReply(replyInitialInformation1);
        Assert.assertTrue(replyDeleted);

    }
}
