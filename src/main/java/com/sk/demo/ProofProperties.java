package com.sk.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

public class ProofProperties {

    private Map<String, String[]> priority = new HashMap<>();


    @Autowired
    private Environment env;

    String DEFAULT_KEY = "notFount:notfound";
    public String[] getPriority(String propertyKey){
        if(priority.containsKey(propertyKey)) {
            priority.get(propertyKey);
        } else {

        }
        String defaultVal = env.getProperty(propertyKey, DEFAULT_KEY);
        return defaultVal.split("[:]");
    }
}
