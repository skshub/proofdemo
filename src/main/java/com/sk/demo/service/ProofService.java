package com.sk.demo.service;


import com.sk.demo.data.dao.NameKeyValueDao;
import com.sk.demo.data.entity.NameKeyValueEntity;
import com.sk.demo.data.entity.NameKeyValueKey;
import com.sk.demo.web.NameKeyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProofService {
    private final Logger logger = LoggerFactory.getLogger(ProofService.class);

    private final String DEFAULT = "DEFAULT";

    @Autowired
    NameKeyValueDao nameKeyValueDao;


    /**
     * The value is
     * @param name
     * @param key
     * @param tags
     * @param value
     */
    public void addKeyValue(String name, String key, Set<String> tags, String value) throws AssertionError{
        //split key into name object and object key
        // eg: car.engine nameObject = car and objectKey = engine

        String []  splits = key.split("[.]");
        assert splits.length == 2 : "The key provided is not valid";
        String nameObject = key.split("[.]")[0];
        String objectKey = key.split("[.]")[1];


        List<NameKeyValueEntity> entities = new ArrayList<>();

        if (tags == null || tags.isEmpty()) {
            tags = Stream.of(DEFAULT).collect(Collectors.toSet());
        }
        for (String tag : tags) {
            NameKeyValueEntity nkve =new NameKeyValueEntity();
            nkve.setPrimaryKey( new NameKeyValueKey(name, nameObject, objectKey, tag));
            nkve.setValue(value);
            entities.add(nkve);
        }
        if (!entities.isEmpty())
            nameKeyValueDao.addAll(entities);
        else
            logger.warn("No entities added.");
    }


    /**
     *
     * @param name
     * @param key
     * @param tagsList
     */
    public NameKeyDto getValue(String name, String key, List<String> tagsList) {
        //currently only 2 tags to consider
        NameKeyDto nameKeyDto = new NameKeyDto();

        String engTag=null, bodTag=null;
        if(tagsList.size() > 0){
            engTag = tagsList.get(0); // Engine tag
            if (tagsList.size() > 1){
                bodTag = tagsList.get(1); //body tag
            }
        }
        if (Objects.nonNull(engTag)){
            NameKeyValueKey pkey = new NameKeyValueKey(name, key, "engine", engTag);
            NameKeyValueEntity entity = nameKeyValueDao.get(pkey);
            if (entity == null){
                //value not found look for defaut
                pkey = new NameKeyValueKey(name, key, "engine", DEFAULT);
                entity = nameKeyValueDao.get(pkey);
            }
            //if entity is still null
            nameKeyDto.setObjKey1(key + '.' + "engine");
            nameKeyDto.setObjValue1(entity != null ? entity.getValue() : "NO-DATA");
        }

        if (Objects.nonNull(bodTag)){
            NameKeyValueEntity entity = getEntity(name, key, "body", bodTag);
            nameKeyDto.setObjKey2(key + '.' + "body");
            nameKeyDto.setObjValue2(entity != null ? entity.getValue() : "NO-DATA");
        }

        return nameKeyDto;
    }




    private NameKeyValueEntity getEntity(String name, String key, String keyType, String tag) {
        NameKeyValueKey pkey = new NameKeyValueKey(name, key, keyType, tag);
        NameKeyValueEntity entity = nameKeyValueDao.get(pkey);
        if (entity == null){
            //value not found look for defaut
            pkey = new NameKeyValueKey(name, key, keyType, DEFAULT);
            entity = nameKeyValueDao.get(pkey);
        }
        return entity;
    }
}
