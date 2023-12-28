package com.foro.test.functional.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest

public class TopicControllerTest extends AbstractTestNGSpringContextTests {
    @Autowired
    TopicRepository topicRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void topicControllerTest() throws JsonProcessingException {
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

        String jsonString = response.asString();
        Map<String, Object> mapTopic = objectMapper.readValue(jsonString, Map.class);

        Object topicId = mapTopic.get("id");

        given()
            .pathParam("id", topicId)
        .when()
            .get("http://localhost:8080/topics/{id}")
        .then()
            .statusCode(200)
            .log().body()
            .body("title", equalTo(topicData.get("title")))
            .body("idUser", equalTo(topicData.get("idUser")))
            .body("message", equalTo(topicData.get("message")))
            .body("topicStatus", equalTo(topicData.get("topicStatus")))
            .body("course", equalTo(topicData.get("course")));

        topicRepository.deleteById((Integer) topicId);

        boolean topicDeleted = topicRepository.findById((Integer)mapTopic.get("id")).isEmpty();
        Assert.assertTrue(topicDeleted);
    }

    @Test
    void topicListControllerTest() throws JsonProcessingException {
        Map<String, Object> topicData = new HashMap();
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

        String jsonString = response.asString();
        Map<String, Object> mapTopic1 = objectMapper.readValue(jsonString, Map.class);

        Map<String, Object> topicData2 = new HashMap();
        topicData2.put("title", "What means JPA");
        topicData2.put("idUser", 2);
        topicData2.put("message", "I dont understand the difference between Java and JPA");
        topicData2.put("topicStatus", "OPEN");
        topicData2.put("course", "JAVA");

        Response response2 =
        given()
            .contentType("application/json")
            .body(topicData2)
        .when()
            .post("http://localhost:8080/topics")
        .then()
            .statusCode(201)
            .log().body()
            .extract().response();

        String jsonString2 = response2.asString();
        Map<String, Object> mapTopic2 = objectMapper.readValue(jsonString2, Map.class);

        Map<String, Object> topicData3 = new HashMap();
        topicData3.put("title", "What means JPA");
        topicData3.put("idUser", 2);
        topicData3.put("message", "I dont understand the difference between Java and JPA");
        topicData3.put("topicStatus", "OPEN");
        topicData3.put("course", "JAVA");

        Response response3 =
        given()
            .contentType("application/json")
            .body(topicData3)
        .when()
            .post("http://localhost:8080/topics")
        .then()
            .statusCode(201)
            .log().body()
            .extract().response();

        String jsonString3 = response3.asString();
        Map<String, Object> mapTopic3 = objectMapper.readValue(jsonString3, Map.class);

        given()
        .when()
            .get("http://localhost:8080/topics")
        .then()
            .statusCode(200)
            .log().body()
            .body("content[0].title", equalTo(topicData.get("title")))
            .body("content[0].idUser", equalTo(topicData.get("idUser")))
            .body("content[0].message", equalTo(topicData.get("message")))
            .body("content[0].topicStatus", equalTo(topicData.get("topicStatus")))
            .body("content[0].course", equalTo(topicData.get("course")))
            .body("content[1].title", equalTo(topicData2.get("title")))
            .body("content[1].idUser", equalTo(topicData2.get("idUser")))
            .body("content[1].message", equalTo(topicData2.get("message")))
            .body("content[1].topicStatus", equalTo(topicData2.get("topicStatus")))
            .body("content[1].course", equalTo(topicData2.get("course")));

        topicRepository.deleteById((Integer)mapTopic1.get("id"));
        topicRepository.deleteById((Integer)mapTopic2.get("id"));
        topicRepository.deleteById((Integer)mapTopic3.get("id"));

        boolean topicDeleted1 = topicRepository.findById((Integer)mapTopic1.get("id")).isEmpty();
        Assert.assertTrue(topicDeleted1);
        boolean topicDeleted2 = topicRepository.findById((Integer)mapTopic2.get("id")).isEmpty();
        Assert.assertTrue(topicDeleted2);
        boolean topicDeleted3 = topicRepository.findById((Integer)mapTopic3.get("id")).isEmpty();
        Assert.assertTrue(topicDeleted3);
    }
}
