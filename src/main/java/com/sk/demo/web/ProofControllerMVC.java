package com.sk.demo.web;

import com.sk.demo.service.ProofService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@RestController
public class ProofControllerMVC {

    private final Logger logger = LoggerFactory.getLogger(ProofControllerMVC.class);

    @Autowired
    private ProofService proofService;

    @RequestMapping(path = "/v2/data/{name}/{key}", method = RequestMethod.PUT, consumes = {"application/json", "text/plain", "application/octet-stream"})
    public ResponseEntity store(@PathVariable("name") final String name, @PathVariable("key") final String key,
                                @RequestParam(value = "tag", required = false)List<String> tags, InputStream value, @RequestHeader("Content-type") String contentType) {

        logger.info("Inside PUT request");
        if (Objects.isNull(value)){
            logger.warn("input is null");
            return ResponseEntity.status(400).build();
        }

        try {
            String input = readInputValue(value);
            if (contentType.equals(MediaType.TEXT_PLAIN_VALUE) || contentType.equals(MediaType.APPLICATION_JSON_VALUE)
                    || contentType.equals(MediaType.APPLICATION_OCTET_STREAM_VALUE)) {
                proofService.addKeyValue(name, key, new HashSet<>(tags) , input);
                return ResponseEntity.accepted().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(400).body("Unable to process input");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();


    }

    private String readInputValue(InputStream value) throws IOException {
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



//    @RequestMapping(path = "/v1/data/{name}/{key}/{tags:.+}", method = RequestMethod.GET,
//            produces = {"application/json", "text/plain", "application/octet-stream"})
//    public ResponseEntity resolve(@PathVariable("name") final String name, @PathVariable("key") final String key,
//                                  @PathVariable(value = "tags", required = false) List<PathContainer.PathSegment> tags){
//
//        List<String> tagsList = null;
//        if (tags != null) {
//            tagsList = new ArrayList<>();
//            for(PathContainer.PathSegment seg : tags) {
//                tagsList.add(seg.value());
//            }
//        }
//        if (tagsList != null) {
//            logger.info("The tags captured:{}", tagsList.toArray().toString());
//        }
//        return ResponseEntity.ok("Processing request");
//    }
}
