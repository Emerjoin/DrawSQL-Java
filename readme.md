# Draw-SQL
Draw table sketches and we turn them into SQL INSERT Statements like magic.
Integration tests with relational databases will never be a pain again.

[![Build Status](https://travis-ci.org/talk-code/DrawSQL.svg?branch=master)](https://travis-ci.org/talk-code/DrawSQL)

##Not understanding?

### This sketch

```text
@person
-----------------------
id  | name | age      
-----------------------
1     Enuar  21
2     Gaby   23
3     Yman   26
-----------------------
```

### Becomes this:

```sql
INSERT INTO person(id,name,age) VALUES(1,'Enuar',21);
INSERT INTO person(id,name,age) VALUES(2,'Gaby',23);
INSERT INTO person(id,name,age) VALUES(3,'Yman',26);
```


## You want it?

### Download the jar


### User maven


#### Repository
```xml
    ...
    <repository>
       <id>talk-code</id>
       <name>maven-repo</name>
       <url>https://github.com/talk-code/maven-repo/raw/master</url>
    </repository>
    ...

```


#### Dependency
```xml
    ...
    <dependency>
        <groupId>mz.co.talkcode</groupId>
        <artifactId>DrawSQL</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependecy>
    ...

```


## The magic steps

### Using a sketch file
```java
            DrawSQL.Builder builder = new DrawSQL.Builder();
            DrawSQL drawSQL = builder.fromSketch(new File("your_sketch_file_path")).build();
            drawSQL.getSQL();

```

## Need a ready example?


## Where do you find the API documentation?



