# Draw-SQL
Draw table sketches and we turn them into SQL INSERT Statements like magic.

[![Build Status](https://travis-ci.org/talk-code/DrawSQL.svg?branch=master)](https://travis-ci.org/talk-code/DrawSQL)

##Not understanding?

### This sketch
@person
-----------------------
id  | name | age      |
-----------------------
1     Enuar  21
2     Gaby   23
3     Yman   26
-----------------------

### Becomes this:

````sql
INSERT INTO person(id,name,age) VALUES(1,'Enuar',21);
INSERT INTO person(id,name,age) VALUES(2,'Gaby',23);
INSERT INTO person(id,name,age) VALUES(3,'Yman',26);
```


## Why is this useful?
We make it easier to create integration tests with relational databases.


## You want it?

### Download the jar


### User maven


#### Repository

#### Dependency


## The magic steps

### Using a sketch file
```java
            DrawSQL.Builder builder = new DrawSQL.Builder();
            DrawSQL drawSQL = builder.fromSketch(new File("your_sketch_file_path")).build();
            drawSQL.getSQL();

```

## Need a ready example?


## Where do you find the API documentation?



