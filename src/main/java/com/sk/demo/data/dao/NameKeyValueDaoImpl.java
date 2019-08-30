package com.sk.demo.data.dao;

import com.sk.demo.data.ProofRepository;
import com.sk.demo.data.entity.NameKeyValueEntity;
import com.sk.demo.data.entity.NameKeyValueKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Implementation
 */
public class NameKeyValueDaoImpl implements NameKeyValueDao{
    Logger logger = LoggerFactory.getLogger(NameKeyValueDaoImpl.class);


    @Autowired
    private ProofRepository repository;

    @Override
    public List<NameKeyValueEntity> addAll(List<NameKeyValueEntity> entities) {
        logger.debug("Adding records to the DB");
        repository.saveAll(entities);
        return entities;
    }

    @Override
    public NameKeyValueEntity get(NameKeyValueKey key) {
        logger.debug("Getting values for {}, {}, {}, {}", key.getName(), key.getNameObject(), key.getObjectKey(), key.getTag());
        return repository.findById(key).orElse(null);
    }

    @Override
    public List<NameKeyValueEntity> getAll(NameKeyValueKey key) {
        logger.info("Fetching all values");
        Iterable<NameKeyValueKey> ids = Arrays.asList(key);
        List<NameKeyValueEntity> list = new ArrayList<>();
        repository.findAllById(ids).forEach(list::add);
        return list;
    }
}
