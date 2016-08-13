# Draw-SQL
Draw table sketches and we turn them into SQL INSERT Statements like magic.
Integration tests with relational databases will never be a pain again.

[![Build Status](https://travis-ci.org/talk-code/DrawSQL.svg?branch=master)](https://travis-ci.org/talk-code/DrawSQL)

##Not understanding?

### This sketch

```text
@person
-----------------------
id  | name | age      |
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

# Download

## JAR
[Get from here](https://talk-code.github.io/releases/get/DrawSQL/drawSQL-1.0.jar)

## Maven

### Repository
```xml
    ...
    <repository>
       <id>talk-code</id>
       <name>maven-repo</name>
       <url>https://github.com/talk-code/maven-repo/raw/master</url>
    </repository>
    ...

```

### Dependency
```xml
    ...
    <dependency>
        <groupId>mz.co.talkcode</groupId>
        <artifactId>DrawSQL</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    ...

```


# The basics

## Getting SQL from sketch file
```java
      DrawSQL.Builder builder = new DrawSQL.Builder();
      DrawSQL drawSQL = builder.fromSketch(new File("your_sketch_file_path")).build();
      String generatedSql = drawSQL.getSQL(); //This will return the generated SQL.

```


## Inserting from a sketch file directly to a JDBC connection
```java

      DrawSQL.Builder builder = new DrawSQL.Builder();
      DrawSQL drawSQL = builder.fromSketch(new File("your_sketch_file_path")).build();
      drawSQL.apply(jdbcConnection);//This will execute the sql against a JDBC Connection object.

```

# What are the sketching rules?
### Align the column name and the values both to left.
The first character of a column name should be aligned with its values

#### wrong
```text
@person
-----------------------
id  | name   | age    | 
-----------------------
 1     Enuar  21
 2     Gaby   23
 3     Yman   26
-----------------------
```
#### correct
```text
@person
-----------------------
id  | name | age      |
-----------------------
1     Enuar  21
2     Gaby   23
3     Yman   26
-----------------------
```

### Align the column separator (|) to the right of the largest column value
#### wrong
```text
@person
------------------------
id  | name |       age |      
------------------------
 1     Enuar Ben   21
 2     Gaby        23
 3     Yman        26
------------------------
```
#### correct
```text
@person
------------------------
id  | name      |  age |      
------------------------
 1     Enuar Ben   21
 2     Gaby        23
 3     Yman        26
------------------------
```


## Text values with empty space on the right - how to keep the space
### The problem
### The solution


## Id Comments - refresh your mind
### The problem
### The solution


## Where do you find the API documentation?