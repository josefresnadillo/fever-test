# Fever code challenge

Hello! Glad you are on this step of the process. We would like to see how you are doing while coding and this exercise
tries to be a simplified example of something we do on our daily basis.

At Fever we work to bring experiences to people. We have a marketplace of events from different providers that are
curated and then consumed by multiple applications. We work hard to expand the range of experiences we offer to our customers.
Consequently, we are continuosly looking for new providers with great events to integrate in our platforms. 
In this challenge, you will have to set up a simple integration with one of those providers to offer new events to our users.

Even if this is just a disposable test, imagine when coding that somebody will pick up this code an maintain it on
the future. It will be evolved, adding new features, adapting existent ones, or even removing unnecessary functionalities.
So this should be conceived as a long term project, not just one-off code.

## Evaluation
We will value the solution as a whole, but some points that we must special attention are:
- How the proposed solution matches the given problem.
- Code style.
- Consistency across the codebase.
- Software architecture proposed to solve the problem.
- Documentation about decisions you made.

## Tooling
- Use Python 3 unless something different has been told.
- You can use any library, framework or tool that you think are the best for the job.
- To provide your code, use the master branch of this repository.

## Description
We have an external provider that gives us some events from their company, and we want to integrate them on the Fever
marketplace, in order to do that, we are developing this microservice.

##### External provider service
The provider will have one endpoint:

https://provider.code-challenge.feverup.com/api/events

Where they will give us their list of events on XML. Every time we fetch the events,
the endpoint will give us the current events available on their side. Here we provide some examples of three different
calls to that endpoint on three different consecutive moments.

Response 1
https://gist.githubusercontent.com/sergio-nespral/82879974d30ddbdc47989c34c8b2b5ed/raw/44785ca73a62694583eb3efa0757db3c1e5292b1/response_1.xml

Response 2
https://gist.githubusercontent.com/sergio-nespral/82879974d30ddbdc47989c34c8b2b5ed/raw/44785ca73a62694583eb3efa0757db3c1e5292b1/response_2.xml

Response 3
https://gist.githubusercontent.com/sergio-nespral/82879974d30ddbdc47989c34c8b2b5ed/raw/44785ca73a62694583eb3efa0757db3c1e5292b1/response_3.xml

As you can see, the events that aren't available anymore aren't shown on their API anymore.

##### What we need to develop
Our mission is to develop and expose just one endpoint, and should respect the following Open API spec, with
the formatted and normalized data from the external provider:
https://app.swaggerhub.com/apis-docs/luis-pintado-feverup/backend-test/1.0.0

This endpoint should accept a "starts_at" and "ends_at" param, and return only the events within this time range.
- It should only return the events that were available at some point in the provider's endpoint(the sell mode was online, the rest should be ignored)
- We should be able to request this endpoint and get events from the past (events that came in previous API calls to the provider service since we have the app running) and the future.
- The endpoint should be fast in hundred of ms magnitude order, regardless of the state of other external services. For instance, if the external provider service is down, our search endpoint should still work as usual.

Example: If we deploy our application on 2021-02-01, and we request the events from 2021-02-01 to 2022-07-03, we should
see in our endpoint the events 291, 322 and 1591 with their latest known values. 

## Requirements
- The service should be as resource and time efficient as possible.
- The Open API specification should be respected.
- Use PEP8 guidelines for the formatting
- Add a README file that includes any considerations or important decision you made.
- If able, add a Makefile with a target named `run` that will do everything that is needed to run the application.

## The extra mile
With the mentioned above we can have a pretty solid application. Still we would like to know your opinion, either 
directly coded (if you want to invest the time) or explained on a README file about how to scale this application
to focus on performance. The examples are small for the sake of the test, but imagine that those files contains
thousands of events with hundreds of zones each. Also consider, that this endpoint developed by us, will have peaks
of traffic between 5k/10k request per second.

## Feedback
If you have any questions about the test you can contact us, we will try to reply as soon as possible.

In Fever, we really appreciate your interest and time. We are constantly looking for ways to improve our selection processes,
our code challenges and how we evaluate them. Hence, we would like to ask you to fill the following (very short) form:

https://forms.gle/6NdDApby6p3hHsWp8

Thank you very much for participating!

# The Solution

## How to run it
In order to be able to run this application you need:

- Docker or Colima (Mac)
- Gradle version 8+
- Make

From the project directory just do:

```
make
```

This Makefile has the following steps:
1) Install a Redis docker image
2) Install a MongoDB docker image 
3) Generate API classes from the swagger definition
4) Build the project
5) Create a fat jar (just in case it is needed)
6) Execute the app

To test it just execute:

```
curl --location 'http://localhost:8080/search' 
```

![Fever](resources/itisalive.jpg)

## Endpoints

As mentioned before, to retrieve the events just make:

```
curl --location 'http://localhost:8080/search' 
```
This is the main endpoint of the service. Because of that I have implemented a throttling mechanism
in this endpoint so it is protected against some DDoS attacks

### Throthling

You can see the throttling configuration in the src/main/resources/config.yaml file:

```
throttling:
  enabled: true
  capacity: 10
  intervalInSeconds: 30
```
As you can see it is specified a pace of 10 request every 30 seconds.
If you want to disable it just specify false in the enabled attribute:

```
throttling:
  enabled: false <---
  capacity: 10
  intervalInSeconds: 30
```

## Other endpoints

There are some other endpoints that help us to check the current state of the application:

- Health

```
curl --location 'http://localhost:8080/health' 
```

It can help us to know if the application is alive, specially useful when we deploy it in a kubernetes

- Swagger

```
curl --location 'http://localhost:8080/swagger' 
```
With this endpoint it is possible to retrive the current swagger of the service

- Metrics

```
curl --location 'http://localhost:9091/metrics' 
```

This endpoint give us some inside metrics about the service. Specially useful in combination with 
Prometheus and Grafana

## Code architecture

- Hexagonal
- Domain Driven Design
- Layers: api (primary), domain and repository (secondary)

## Application architecture

I have chosen the following technologies:

### Redis

It helps me cache the results from the event provider. This is very useful and it is related to 
improve the performance.

It also help us to be more resilience against downfalls of the event provider.

You can modify the redis configuration in the config.yaml file:

```
cache:
  host: "localhost"
  port: 6379
  db: 1
  enabled: true
  ttlInSeconds: 60
```

In order to disable it just specify enable to false:

```
cache:
  host: "localhost"
  port: 6379
  db: 1
  enabled: false  <-----
  ttlInSeconds: 60 
```

I would configure the TTL depending on the event provider characteristics

### MongoDB

In my opinion, there is nothing more suitable to be store in a noSQL database than events :)
That is why I have chosen Mongo

## Redis and MongoDB in the same application? How come?

To be honest, I would chose only Redis as cache solution and database solution. 
Redis is an amazing tool with some incredible capabilities as message broker, for instance. It also
has noSQL capabilities. 

I would choose Redis in order to reduce the number of infrastructures so the solution to be cheaper and easier 
to maintain

Why I didn't?

Because I have never implemented this Redis noSQL capabilities with Vert.x and I was afraid not to have 
enough time to learn.

With some more time, I would investigate how to store and query json in Redis

[What is Redis NoSQL](https://redis.com/nosql/what-is-nosql/)

## Java usage

- Functional over imperative
- Immutable classes if possible
- Builder pattern when the number of attributes is greater than four
- Variables and parameters contains the unit in their names, ex. bandwidthInBytes, callsInMinutes
- Null Object pattern instead of null or optional
- Lombok is NOT recommended
- Reflection is NOT recommended
- Introspection is NOT recommended

## The Performance Extra Mile

I have implemented some actions in order to improve the performance :

1) **A cache between the application and the event provider**
Depending on the nature of the event provider it helps us to reduce calls and network traffic. In most occasions, if
events don't change in hours, we could have much better response retrieving this events from Redis instead of calling
to some rest endpoint

2) **Save only new events to MongoDB** 
Instead of storing all the events that comes from the event provider, I have implemented a way to know if the 
event should be store or not. Reducing the inserts in MongoDB definitely will help us to increase performance (See EventsAggregate consolidateEvents method)

3) **Use a noSQL database**
Faster than a relational database. Events seems to suit perfectly to this kind of database. It allows us to search
by specific dates, so it is not necessary to retrieve the whole database every time

4) **Vert.x and rxJava**
Thanks to Vert.x and rxJava we can query MongoDB and call to the event provider in parallel (Single.zip method). Also, 
Vert.x one thread event bus strategy eliminates the risk of getting out of threads, which is something that
usually happen in a Spring Boot application, for example. So it can attends more consumers than a thread base application

5) **Dagger as dependency injection**
With Dagger we achieve a much faster starting time because it resolves dependencies in compilation time. This is
very useful when scaling horizontally

If performance is still an issue, I would do the following:

1) **Implement an asynchronous endpoint**
Something like the following:

```
POST /search-orders
```
This endpoint creates a search order resource. The consumer will received a response like this:

```
{
  "id": "1", <---- ID
  "status": "PENDING"
  "events": []
}
```

After receiving this call, the backend will star looling for the events in a background process. It could queue an event
to some topic so another process could start calling to the event provider and to process the response

Meanwhile the consumer will start a polling calling to:

```
GET /search-orders/1
```

It would receive a response like the one before while the backend is retrieving the response from the event provider

After some time, the backend finish to retrieve the events from the events provider. The consumer 
will finally receive the results (or an error)

```
{
  "id": "1",
  "status": "DONE"
  "events": [
    {
      "id": "event1",
      .
      .
    },
    {
      "id": "event2",
      .
      .
      .
    }  
  ]
}
```

I have updated the initial swagger definition with this new endpoints (resources/fever-extramile.yaml)

2) **Retrieve the events in the background periodically**
This could be another solutions. A process in the background that retrieve the events from the events provider
periodically and store them in the mongoDB. So there will be no need to call to the event provider when our frontend
call us

3) **Ask to our events provider to paginate their endpoints** :)
I guess this is impossible. But at least we could paginate our endpoints, so our query could go faster and retrieve
less data from the database (offset and limit). Also it helps to reduce the network transfer time. 

Compressing the response is also a good way to reduce network transfer time

4) Cache our own endpoints
So a repetead call does not result in queries to the database or calls to the events provider
   
## Tech Radar

### Domain Driven Design

In my opinion the best way to transform business needs into code. It also helps to follow good practices
as SOLID, CUPID or GRASP

I also helps to create an Ubiquitous Language through the whole organization

(I have a class called EventsAggregate that doesn't sound very ubiquitous. I did it just for the shake of this test)

Some documentation:
- [Domain-Driven Design in Practice by Vladimir Khorikov](https://www.pluralsight.com/courses/domain-driven-design-in-practice?clickid=RDvwixxUqxyLWV4wUx0Mo3EHUkExSWQs2UvC0I0&irgwc=1&mpid=1970485&aid=7010a000001xAKZAA2&utm_medium=digital_affiliate&utm_campaign=1970485&utm_source=impactradius)
- [Domain-Driven Design Fundamentals by Steve Smith and Julie Lerman](https://app.pluralsight.com/library/courses/fundamentals-domain-driven-design/table-of-contents)
- [Effective aggregate design 1/3 by Vaughn Vernon](https://www.dddcommunity.org/wp-content/uploads/files/pdf_articles/Vernon_2011_1.pdf)
- [Effective aggregate design 2/3 by Vaughn Vernon](https://www.dddcommunity.org/wp-content/uploads/files/pdf_articles/Vernon_2011_2.pdf)
- [Effective aggregate design 3/3 by Vaughn Vernon](https://www.dddcommunity.org/wp-content/uploads/files/pdf_articles/Vernon_2011_3.pdf)

### Hexagonal Architecture
A nice way to implement clean architecture

Some documentation:
- [Hexagonal Architecture](https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/?utm_content=buffer01772&utm_medium=social&utm_source=twitter.com&utm_campaign=buffer)

### Vertx

And asynchronous library based on the one thread model (like Node.js)
Unfortunately it is very "low level" so it is needed a lot of code to implement something reusable 
to run endpoints

That is why I have implemented a kind of "framework" to do so (rest-handler library)

Some documentation:
- [Vertx homepage](https://vertx.io/)
- [Vertx Examples](https://github.com/vert-x3/vertx-examples)
- [Vertx Blueprint microservice](http://www.sczyh30.com/vertx-blueprint-microservice/)
- [Vertx asynchronous programming guide for java developers](http://www.julienviet.com/vertx-3.5.0/docs/guide-for-java-devs/)
- [You might not need Dependency Injection in a Vertx application](https://fdk.codes/you-might-not-need-dependency-injection-in-a-vertx-application/)

### rxJava
Very nice but REALLY HARD asynchronous library. I am so looking forward to use the Loom project ;)

[Loom Project in Java](https://www.baeldung.com/openjdk-project-loom)

Some documentation:
- [RxJava github page](https://github.com/ReactiveX/RxJava)

### Dagger
A really nice library for the dependency injection.
This dependency injection is resolved in compilation time, so it allows applications to start 
very quickly (hello Spring boot)

Some documentation:
- [Dagger2 homepage](https://dagger.dev/)
- [Dagger2 tutorial](https://www.vogella.com/tutorials/Dagger/article.html)

### Bucket4j (Throttling)
This is the best throttling library ever. It implements the Token Bucket algorithm and it uses
Redis to store the buckets

Some documentation:
- [Bucket4j Home](https://bucket4j.com)

### Open Api Generate
A gradle plugin or an standalone commando to generate code from a open api definition. Is is perfect to generate
both the server and the client

Some documentation:
- [Open Api Generate](https://openapi-generator.tech)

### Gradle

Some documentation:
- [Gradle home](https://www.baeldung.com/groovy-spock)

### Swagger

Some documentation:
- [swagger home page](https://swagger.io)

### Docker

Some documentation:
- [Docker Home](https://www.docker.com/)
- [Docker Tutorial](https://docker-curriculum.com)

### Redis

Some documentation:
- [Redis Home](https://redis.io)

### MongoDB

Some documentation:
- [MongoDB Home](https://www.mongodb.com)
- [MongoDB and Vert.x](https://vertx.io/docs/vertx-mongo-client/java/)

### Git

- [GIT Hello World](https://guides.github.com/activities/hello-world/)

### Other

- [Reactive Manifesto](https://www.reactivemanifesto.org)
- [ReactiveX](http://reactivex.io/intro.html)

Enjoy!!!


