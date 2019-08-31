package com.sk.demo.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
public class ProofControllerJersyTest {

    private final Logger logger = LoggerFactory.getLogger(ProofControllerJersyTest.class);

    static class InJson {
        @JsonProperty
        String value;

        @JsonCreator
        public InJson(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void pingTest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/data/ping", String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertTrue(response.getBody().equals("Pong proof-demo"));
    }

    @Test
    public void storeTest() {
        String url = "/v1/data/cars/car.engine?tag=EV&tag=HYBRID";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> req = new HttpEntity<>("electric motor", headers);
        restTemplate.put(url, req, String.class);
    }


    @Test
    public void retrieveTest() {
//        setting engine
        String url = "/v1/data/cars/car.engine?tag=GAS&tag=DIESEL";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> req = new HttpEntity<>("i c e", headers);
        restTemplate.put(url, req, String.class);
        //setting body
         url = "/v1/data/cars/car.body";
        req = new HttpEntity<>("sedan", headers);
        restTemplate.put(url, req, String.class);

        //making the get request
         url = "/v1/data/cars/car/GAS/CIVIC";
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_PLAIN));
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        logger.info("Output:{}", response.getBody());
        Assert.assertTrue(response.getBody().contains("i c e"));
        Assert.assertTrue(response.getBody().contains("sedan"));

    }

    @Test
    public void testInJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String out =  mapper.writeValueAsString(new InJson("sedan"));
        Assert.assertNotNull(out);
    }

    @Test
    public void storeTestApplicationJson() throws Exception{
        String url = "/v1/data/cars/car.engine?tag=EV&tag=HYBRID";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        String out =  mapper.writeValueAsString(new InJson("sedan"));
        HttpEntity<String> req = new HttpEntity<>(out, headers);
        ResponseEntity response = restTemplate.exchange(url, HttpMethod.PUT, req, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
