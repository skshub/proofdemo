package com.sk.demo.service;


import com.sk.demo.data.dao.NameKeyValueDao;
import com.sk.demo.data.entity.NameKeyValueEntity;
import com.sk.demo.data.entity.NameKeyValueKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProofService {

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

        for (String tag : tags) {
            NameKeyValueEntity nkve =new NameKeyValueEntity();
            nkve.setPrimaryKey( new NameKeyValueKey(name, nameObject, objectKey, tag));
            nkve.setValue(value);
            entities.add(nkve);
        }
        nameKeyValueDao.addAll(entities);
    }


    public void getValue(String name, String objName, String objKey, String tag) {

    }
}
