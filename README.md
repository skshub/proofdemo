# proof
Proof point demo


### Data Base Design:

**name_key_value**

|column name|Type|Constrain|
|----------|-------|-------|
|name|text|PK|
|object_name|text|PK|
|name_key|text|CC|
|tag|text|CC|
|value|text||


Retrieving the data

Query to retrieve the
```sql
SELECT name, nameobject, objectkey, tag, value 
FROM name_key_value WHERE name = ? AND nameobject = ? 
AND objectkey = ? AND tag = ?;
```

