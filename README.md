# followermaze-server

The challenge proposed here is to build a system which acts as a socket server, reading events from an <code>*event source*</code> and forwarding them when appropriate to <code>*user clients*</code>. Read <code>instructions.md</code>

# Getting started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

followermaze is based on Java 1.8 and uses the Maven 3 build system. 

### Installing

To build followermaze from source, use the following command in this directory:
```
mvn clean install
```

### Running 

An executable jar <code> followermaze-server.jar</code> attached in the repository and use the command 
```
java -jar followermaze-server.jar
```
The application can run from maven using:
```
 mvn exec:exec 
```
