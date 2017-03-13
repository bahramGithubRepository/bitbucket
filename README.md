# The Book store

The Book store

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See Running the BookStore section for notes on how to deploy the project on a live system.

### Prerequisites

There are two projects: 1- BookStore Web API and 2- MyClient console application.


### Requirement

To run BookStore WebAPI you need to install:

* [Eclipse Neon2](https://spring.io/tools/eclipse) - Eclipse IDE for Java EE Developers which includs Spring boot.
* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java Development Kit
or
* [JRE 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) - Java Runtime Environment



## Running the BookStore

For running the BookStore web API please run the Eclipse and import the BookStore project in your workspace.
Now you can run the project by right clicking on BookStore project and select Run As/Spring Boot App. Your server is ready for accepting REST GET and POST request now.
In the Eclipse console, you will see

```
....
2017-03-12 18:31:12.009  INFO 15520 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2017-03-12 18:31:12.067  INFO 15520 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2017-03-12 18:31:12.073  INFO 15520 --- [           main] com.bookStore.BookStoreApplication       : Started BookStoreApplication in 4.747 seconds (JVM running for 5.439)

```
## List of GET 

To get list of all books sends GET request to http://localhost:8080/rest/book/
```
[{"title":"Mastering åäö","author":"Average Swede","price":762.0,"quantity":0,"isbn":"1-1-1-1"},
{"title":"How To Spend Money","author":"Rich Bloke","price":1000000.0,"quantity":0,"isbn":"2-2-2-2"},
{"title":"Generic Title","author":"First Authore","price":185.5,"quantity":0,"isbn":"3-3-3-3"},
{"title":"Generic Title","author":"Second Authore","price":1748.0,"quantity":0,"isbn":"4-4-4-4"},
{"title":"Random Sales","author":"Cunning Bastard","price":999.0,"quantity":0,"isbn":"5-5-5-5"},
{"title":"Random Sales","author":"Cunning Bastard","price":499.5,"quantity":0,"isbn":"6-6-6-6"},
{"title":"How To Spend Money","author":"Rich Bloke","price":564.5,"quantity":0,"isbn":"7-7-7-7"}]

```
To Serach, sends GET request to http://localhost:8080/rest/book/{SearchItem} Ex.http://localhost:8080/rest/book/Average Swede
```
[{"title":"Mastering åäö","author":"Average Swede","price":762.0,"quantity":0,"isbn":"1-1-1-1"}]

```

To get ID,sends GET request to http://localhost:8080/rest/book/id . You should mention that you have to run one time this ULR for getting ID. you mast save ID for upon request.

```
fa3e2d1a-7520-4ea9-8366-6350a8cb02d0

```

To GET list of your basket sends GET request to http://localhost:8080/rest/book/basket/{id} Ex. http://localhost:8080/rest/book/basket/fa3e2d1a-7520-4ea9-8366-6350a8cb02d0

```
[{"title":"Mastering åäö","author":"Average Swede","price":762.0,"quantity":1,"isbn":"1-1-1-1"}]

```
## List of POST

To add a new book,  sends POST request to http://localhost:8080/rest/book/add . set Content-Type: application/json and put your the new book in body of your POST request.

```
POST http://localhost:8080/rest/book/add
Content-Type: application/json

Body of request 

{"title":"Love history","author":"Bahram","price":456.0,"quantity":50,"isbn":"14-21-923"}

Respond:

{"title":"Love history","author":"bahram","price":456.0,"quantity":10,"isbn":"14-21-923"}


```
To add a book in your basket,  sends POST request to http://localhost:8080/rest/book/buy . set Content-Type: application/json and put your the new book in body of your POST request.
The respond is 0=OK, 1=NOT_IN_STOCK and 2=DOES_NOT_EXIST 

```
POST http://localhost:8080/rest/book/buy
Content-Type: application/json

Body of request 

{"isbn":"14-21-923","id":"fa3e2d1a-7520-4ea9-8366-6350a8cb02d0"}

Respond:

0


```

To remove a book from your basket,  sends POST request to http://localhost:8080/rest/book/remove . set Content-Type: application/json and put your the new book in body of your POST request.
The respond message is a integer number 0, 1 or 2 that means 0=OK, 1=NOT_IN_STOCK and 2=DOES_NOT_EXIST 

```
POST http://localhost:8080/rest/book/remove
Content-Type: application/json

Body of request 

{"isbn":"14-21-923","id":"fa3e2d1a-7520-4ea9-8366-6350a8cb02d0"}

Respond:

0

```

## Running the MyClient console application
Plaeas mention that this console application is just for testing the BookStore web API.

Run a new The Eclipse and import the MyClient project in your workspace. To run the project, do right click on the myClient and select Run AS/ java Application


In the console you will see

```
Welcome commane Line for book story
please enter Spring server IP and port. Exm: localhost:8080 or 83.10.20.111:5070   

```
by typing your Spring Server Ip address and port number for Exm: Localhost:8080 , the myClient application will be connected to the Spring server and a menu will be appear.

```
localhost:8080
Client successfully connected to localhost:8080

Option menu
Please choose a number:
1: Get the list of books
2: Search a book
3: Add a new book
4: Add to Basket
5: Get Basket
6: Remove from Basket
7: Exit 

```



## Authors

* **Bahram Rahmatdoust ** - *Initial work* - [Download source code from here](https://github.com/bahramGithubRepository/bitbucket.git)



## License

This project is free  to use but I have used different library. please check the licence of used library for production.

