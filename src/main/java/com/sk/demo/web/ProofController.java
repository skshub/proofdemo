package com.sk.demo.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.demo.service.ProofService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@Path("/v1/data")
public class ProofController {
    private static final Logger log = LoggerFactory.getLogger(ProofControllerMVC.class);

    @Autowired
    private ProofService proofService;


    @PUT
    @Path("/{name}/{key}")
    @Consumes({"application/json", "text/plain", "application/octet-stream"})
    public Response store(@PathParam("name") String name, @PathParam("key") String key, @QueryParam("tag") Set<String> tags, InputStream value, @HeaderParam("Content-Type") String contentType ) {
        log.info("Inside PUT, called with name:{}, key:{}, tags:{}", name, key, tags.toArray().toString());

        log.info("Inside PUT request");
        if (Objects.isNull(value)){
            log.warn("input is null");
            return Response.status(400).build();
        }

        String inVal = null;
        if (contentType.equals(MediaType.TEXT_PLAIN)){
            try {
                inVal = readInputValue(value);
                System.out.print("input:" + inVal);
            } catch (IOException e) {
                log.error("Error reading input", e);
                return Response.serverError().build();
            }
        } else if (contentType.equals(MediaType.APPLICATION_JSON)){
            try {
                ObjectMapper mapper = new ObjectMapper();
                ValueJson valueJson = mapper.readValue(value, ValueJson.class);
                inVal = valueJson.value;
            } catch (IOException e) {
                log.error("Error parsing Json", e);
                return Response.serverError().build();
            }

        }
        proofService.addKeyValue(name, key, tags, inVal);


        return Response.ok("Success").build();
    }

    private String readInputValue(InputStream value) throws IOException{
        ByteArrayOutputStream bOutStream =  new ByteArrayOutputStream();
        byte[] buffer = new byte[1000];
        int intRead = 0;
        do{
            intRead = value.read(buffer);
            if (intRead > -1){
                bOutStream.write(buffer, 0, intRead);
            }
        } while (intRead > -1);

        return bOutStream.toString();
    }

    /**
     * A sample request will be "http://localhost:8080/v1/data/cars/car/HYBRID/PRIUS"
     * @param name : cars
     * @param key : car
     * @param tags : {HYBRID, PRIUS}
     * @return
     */
    @GET
    @Path("/{name}/{key}/{tags: .+}")
    @Produces({"application/json", "text/plain", "application/octet-stream"})
    public Response resolve(@PathParam("name") String name, @PathParam("key") String key, @PathParam("tags") List<PathSegment> tags, @HeaderParam("Accept") String acceptParam) {

        List<String> tagsList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        if (tags != null) {
            for (PathSegment seg : tags) {
                builder.append(seg.getPath()).append(",");
                tagsList.add(seg.getPath());
            }
        }
        NameKeyDto nameKeyDto = proofService.getValue(name, key, tagsList);

        if (acceptParam.equals(MediaType.APPLICATION_JSON)){
            return Response.ok(nameKeyDto.toJsonString(), MediaType.APPLICATION_JSON_TYPE).build();
        } else {
            return Response.ok(nameKeyDto.toString(), MediaType.TEXT_PLAIN).build();
        }
//        return Response.ok(nameKeyDto.toJson(), MediaType.APPLICATION_JSON_TYPE).build();

    }

    @GET
    @Path("/ping")
    @Produces({"application/json", "text/plain"})
    public Response ping() {
        return Response.ok("Pong proof-demo", MediaType.TEXT_PLAIN_TYPE).build();
    }



    public static class ValueJson {
        @JsonProperty
        String value;

        @JsonCreator
        public ValueJson(String value) {
            this.value = value;
        }

        public ValueJson() {
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}