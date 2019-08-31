package com.sk.demo.web;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class NameKeyDtoTest {

    @Test
    public void testJsonCreation() throws IOException {


        try {
            NameKeyDto dto = new NameKeyDto("car.engine", "ice", "car.body", "hatchback");
            JsonParser parser = new ObjectMapper().getFactory().createParser(dto.toJsonString());
        } catch (Exception e) {
            Assert.fail("Should not throw exception");
        }
    }

}