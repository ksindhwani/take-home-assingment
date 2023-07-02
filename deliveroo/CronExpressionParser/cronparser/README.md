## Setup 

### Pre-Requistiees
This project used Java 11 and Maven, hence to build the project , Java 11 and Maven 1.4 is needed.

1. Unzip the project / clone the project into local.
2. Go to project directory `cd <path to project directory>`
3. run this command to build the project `mvn clean package`. This will create a directory `target/` and a jar named `cronparser-1.0-SNAPSHOT.jar`.
4. Run the command `java -jar target/cronparser-1.0-SNAPSHOT.jar <Command Line arguments for the application in " ">`
Example 

```
java -jar target/cronparser-1.0-SNAPSHOT.jar "5 4 3 2 1 /usr/find"
```