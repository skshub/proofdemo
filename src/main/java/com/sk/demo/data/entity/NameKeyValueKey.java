package com.sk.demo.data.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.Objects;

@PrimaryKeyClass
public class NameKeyValueKey implements Serializable{

    /*
  Column names
   */
    public static final String NAME = "name";
    public static final String NAME_OBJECT = "object";
    public static final String OBJECT_KEY = "nameObject";
    public static final String TAG = "tag";


    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED, name = "name")
    private String name;

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.PARTITIONED, name = "nameObject")
    private String nameObject;

    @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED, name = "objectKey")
    private String objectKey;

    @PrimaryKeyColumn(ordinal = 3, type = PrimaryKeyType.CLUSTERED, name = "tag")
    private String tag;

    public NameKeyValueKey(String name, String nameObject, String objectKey, String tag) {
        this.name = name;
        this.nameObject = nameObject;
        this.objectKey = objectKey;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public String getNameObject() {
        return nameObject;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NameKeyValueKey)) return false;
        NameKeyValueKey that = (NameKeyValueKey) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(nameObject, that.nameObject) &&
                Objects.equals(objectKey, that.objectKey) &&
                Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nameObject, objectKey, tag);
    }
}
