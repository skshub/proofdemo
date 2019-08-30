package com.sk.demo.data.entity;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Table(value = NameKeyValueEntity.TABLE_NAME)
public class NameKeyValueEntity implements Serializable {

    public static final String TABLE_NAME = "name_key_value";
    /*
    Column name
     */
    public static final String VALUE = "value";

    @PrimaryKey
    private NameKeyValueKey primaryKey;

    @Column(value = "value")
    private String value;

    public void setPrimaryKey(NameKeyValueKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public NameKeyValueKey getPrimaryKey() {
        return primaryKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
