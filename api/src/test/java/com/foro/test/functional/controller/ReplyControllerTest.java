package com.foro.test.functional.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foro.api.reply.ReplyRepository;
import com.foro.api.topic.TopicRepository;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
@SpringBootTest
public class ReplyControllerTest extends AbstractTestNGSpringContextTests {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private  ReplyRepository replyRepository;
    @Autowired
    private TopicRepository topicRepository;

    @Test
    void replyControllerTest() throws JsonProcessingException {

        Map<String, Object> topicData = new HashMap();
        topicData.put("id", "232");
        topicData.put("title", "What means Java");
        topicData.put("idUser", 3);
        topicData.put("message", "I dont understand the difference between Java and Python");
        topicData.put("topicStatus", "OPEN");
        topicData.put("course", "JAVA");

        Response response =
            given()
                .contentType("application/json")
                .body(topicData)
            .when()
                .post("http://localhost:8080/topics")
            .then()
                .statusCode(201)
                .log().body()
                .extract().response();

        String responseBodyTopic = response.asString();
        Map<String, Object> mapTopic = objectMapper.readValue(responseBodyTopic, Map.class);

        Map<String, Object> replyData = new HashMap();
        replyData.put("reply", "Hello Earth");
        replyData.put("idUser", 3);
        replyData.put("idTopic", mapTopic.get("id"));

        Response response2 =
            given()
                .contentType("application/json")
                .body(replyData)
            .when()
                .post("http://localhost:8080/replies")
            .then()
                .statusCode(201)
                .log().body()
                .extract().response();

        String responseBodyReply = response2.getBody().asString();
        Map<String, Object> mapReply = objectMapper.readValue(responseBodyReply, Map.class);

        Map<String, Object> replyDataToModify = new HashMap();
        replyDataToModify.put("id", mapReply.get("id"));
        replyDataToModify.put("reply", "Hello World");

        given()
            .contentType("application/json")
            .body(replyDataToModify)
        .when()
            .put("http://localhost:8080/replies")
        .then()
            .statusCode(200)
            .log()
            .body()
            .body("reply", equalTo(replyDataToModify.get("reply")));

        Object replyId = mapReply.get("id");

        given()
            .pathParam("id", replyId)
        .when()
            .get("http://localhost:8080/replies/{id}")
        .then()
            .statusCode(200)
            .log().body()
            .body("reply", equalTo(replyDataToModify.get("reply")))
            .body("idUser", equalTo(replyData.get("idUser")))
            .body("idTopic", equalTo(replyData.get("idTopic")));

        Object idToDelete = mapReply.get("id");
        given()
            .pathParam("id", idToDelete)
        .when()
            .delete("http://localhost:8080/replies/{id}")
        .then()
            .statusCode(204)
            .log().body()
            .extract().response();

        replyRepository.deleteById((Integer)idToDelete);
        topicRepository.deleteById((Integer)mapTopic.get("id"));

        boolean topicDeleted = topicRepository.findById((Integer)mapTopic.get("id")).isEmpty();
        Assert.assertTrue(topicDeleted);

        boolean replyDeleted = replyRepository.findById((Integer)idToDelete).isEmpty();
        Assert.assertTrue(replyDeleted);
    }
    @Test
    void replyListControllerTest() throws JsonProcessingException {

        Map<String, Object> topicData = new HashMap();
        topicData.put("id", "232");
        topicData.put("title", "What means Java");
        topicData.put("idUser", 3);
        topicData.put("message", "I dont understand the difference between Java and Python");
        topicData.put("topicStatus", "OPEN");
        topicData.put("course", "JAVA");

        Response response =
        given()
            .contentType("application/json")
            .body(topicData)
        .when()
            .post("http://localhost:8080/topics")
        .then()
            .statusCode(201)
            .log().body()
            .extract().response();

        String responseBodyTopic = response.asString();
        Map<String, Object> mapTopic = objectMapper.readValue(responseBodyTopic, Map.class);

        Map<String, Object> replyData1 = new HashMap();
        replyData1.put("reply", "Hello Earth");
        replyData1.put("idUser", 3);
        replyData1.put("idTopic", mapTopic.get("id"));

        Response responseReply1 =
        given()
            .contentType("application/json")
            .body(replyData1)
        .when()
            .post("http://localhost:8080/replies")
        .then()
            .statusCode(201)
            .log().body()
            .extract().response();

        String jsonStringResponse1 = responseReply1.asString();
        Map<String, Object> mapReply1 = objectMapper.readValue(jsonStringResponse1, Map.class);

        Map<String, Object> replyData2 = new HashMap();
        replyData2.put("reply", "Hello Mars");
        replyData2.put("idUser", 3);
        replyData2.put("idTopic", mapTopic.get("id"));

        Response responseReply2 =
        given()
            .contentType("application/json")
            .body(replyData2)
        .when()
            .post("http://localhost:8080/replies")
        .then()
            .statusCode(201)
            .log().body()
            .extract().response();

        String jsonStringResponse2 = responseReply2.asString();
        Map<String, Object> mapReply2 = objectMapper.readValue(jsonStringResponse2, Map.class);

        Map<String, Object> replyData3 = new HashMap();
        replyData3.put("reply", "Hello Jupiter");
        replyData3.put("idUser", 3);
        replyData3.put("idTopic", mapTopic.get("id"));

        Response responseReply3 =
        given()
            .contentType("application/json")
            .body(replyData3)
        .when()
            .post("http://localhost:8080/replies")
        .then()
            .statusCode(201)
            .log().body()
            .extract().response();

        String jsonStringResponse3 = responseReply3.asString();
        Map<String, Object> mapReply3 = objectMapper.readValue(jsonStringResponse3, Map.class);

        given()
        .when()
            .get("http://localhost:8080/replies")
        .then()
            .statusCode(200)
            .log().body()
            .body("content[0].reply",equalTo(replyData1.get("reply")))
            .body("content[0].idUser", equalTo(replyData1.get("idUser")))
            .body("content[0].idTopic",equalTo(replyData1.get("idTopic")))
            .body("content[1].reply",equalTo(replyData2.get("reply")))
            .body("content[1].idUser", equalTo(replyData2.get("idUser")))
            .body("content[1].idTopic",equalTo(replyData2.get("idTopic")));

        replyRepository.deleteById((Integer)mapReply1.get("id"));
        replyRepository.deleteById((Integer)mapReply2.get("id"));
        replyRepository.deleteById((Integer)mapReply3.get("id"));
        topicRepository.deleteById((Integer)mapTopic.get("id"));

        boolean topicDeleted = topicRepository.findById((Integer)mapTopic.get("id")).isEmpty();
        Assert.assertTrue(topicDeleted);

        boolean replyDeleted1 = replyRepository.findById((Integer)mapReply1.get("id")).isEmpty();
        Assert.assertTrue(replyDeleted1);
        boolean replyDeleted2 = replyRepository.findById((Integer)mapReply2.get("id")).isEmpty();
        Assert.assertTrue(replyDeleted2);
        boolean replyDeleted3 = replyRepository.findById((Integer)mapReply3.get("id")).isEmpty();
        Assert.assertTrue(replyDeleted3);
    }
}
