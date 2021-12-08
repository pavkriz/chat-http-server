# Chat HTTP server for PRO2 project

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

## Running Locally

Make sure you have Java and Maven installed.  Also, install the [Heroku CLI](https://cli.heroku.com/).

```sh
$ mvn install
$ heroku local:start
```

Your app should now be running on [localhost:5000](http://localhost:5000/).

If you're going to use a database, ensure you have a local `.env` file that reads something like this:

```
JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/chatdb
```

and run PostgreSQL like this:
```
docker run --name chat-postgres -p 5432:5432 -e POSTGRES_HOST_AUTH_METHOD=trust -e POSTGRES_DB=chatdb -d postgres:13
```

## Deploying to Heroku

```sh
$ heroku create
$ git push heroku main
$ heroku open
```
