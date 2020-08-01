# City Autocomplete

## Prerequisite

- java 1.8 & maven cli

or

- docker & docker-compose

or

- IDE

## How to run

- maven
```
mvn clean package
java -jar RestfulApi/target/RestfulApi.jar
```

- docker
```
docker-compose up -d city-autocomplete
```
Then have to wait for half minute to startup

After run, shut down the container
```
docker-compose down
```

- IDE

This is developed in Intellij and java 8, but it is supposed to be run by any java IDE.
(But I enabled lombok, sometimes it's a bit difficult to configure it in a IDE. )

All the unit tests are under `RestfulApi` module.

Run `RestfulApi` module will start a http server to listen to http requests

- Test

Send requests to `localhost:8080` in any way, e.g.

```
curl GET 'http://localhost:8080/suggestions?q=londo&latitude=43.70011&longitude=-79.4163'
```

## Potential Polish

1. The current result is only containing city name and country, but I didn't spend more time
on the province extraction from geonames database.

2. The current score is only calculated by distance from caller's location, but it would be
better to take exact-match case into account. 

3. For a real product, probably we may have to handle CORS, server log on a global aspect, 
authentication etc

## Bonus Points

### More scoring parameters

- user's search history
- any kind of user's address information
- any kind of address information of the circle of his/her relatives and friends

But definitely it's going to bring the privacy problem

### Future city-suggestion-service startup

Option 1:

Add API authentication and billing logic to a global aspect like DispatcherServlet or somewhere
else of the `RestfulApi` project. For every incoming request, will check if the api key is valid,
 and charge the request.

Option 2:

Add a brandnew service ahead of city-autocomplete service to implement api key authentication 
and billing. This is a sort of microservice. So the new service won't impact city autocomplete.
Then we obtain the independent ability for both of them, like independent deployment, independent
calculating capability or scaling, independent programming languages etc. 

But microservices bring more development/devops effort to develop and maintain as well.

