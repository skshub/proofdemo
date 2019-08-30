package com.sk.demo.data.dao;

import com.sk.demo.data.entity.NameKeyValueEntity;
import com.sk.demo.data.entity.NameKeyValueKey;

import java.util.List;

public interface NameKeyValueDao {

    /**
     * Add the list of tags to the DB
     * @param entities
     * @return
     */
    List<NameKeyValueEntity> addAll(List<NameKeyValueEntity> entities);

    /**
     * Fetch a NameKeyValueEntity based on the NameKeyValueKey
     * @param key
     * @return
     */
    NameKeyValueEntity get(NameKeyValueKey key);

    /**
     * Fetch all the NameKeyValueEntity based on the NameKeyValueKey
     * @param key
     * @return
     */
    List<NameKeyValueEntity> getAll(NameKeyValueKey key);
}
