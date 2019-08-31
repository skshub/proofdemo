## Proof point demo



#### Rest Endpoints

|Method|url|Comments
|-----|-----|-----|
|PUT|`/v1/data/{name}/{key}?tag*=<val>`|multiple tags can be passed|
|GET|`/v1/data/{name}/{key}/{tags: .+}`|Only first 2 tags path params will be considered|



### Data Base Design:

**name_key_value**

|column name|Type|Constrain|
|----------|-------|-------|
|name|text|PK|
|object_name|text|PK|
|name_key|text|PK|
|tag|text|CC|
|value|text||

#### Scheam script
```
CREATE TABLE proof.name_key_value (
    name text,
    objectname text,
    objectkey text,
    tag text,
    value text,
    PRIMARY KEY ((name, objectname, objectkey), tag)
) WITH CLUSTERING ORDER BY (tag ASC);
```

#### Keyspace creation script
```
 String script = "CREATE KEYSPACE IF NOT EXISTS " + getKeyspaceName()
                + " WITH durable_writes = 'true' "
                + "AND replication = { 'replication_factor' : 1, 'class' : 'SimpleStrategy' };";
```

#### Table structure



#### Query to retrieve the
```sql
SELECT name, nameobject, objectkey, tag, value 
FROM name_key_value WHERE name = ? AND nameobject = ? 
AND objectkey = ? AND tag = ?;
```

##### Service implemented using
- Spring boot
- Jersey
- spring-data-cassandra
##### datastore
- cassandra


#### Know issues:
- The curl XPUT is getting filtered out by the spings internal filter. 
Tested with POSTMAN client
