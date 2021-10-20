# Installation
1. Clone repository: https://git.scc.kit.edu/cm-tm/cm-team/serviceenvironment/1.domain/api

## Configuration
For configuration the microservice requires the following environment variables:

| Variable                     | Description                                          | Default                    |
| ---------------------------- | ---------------------------------------------------- | -------------------------- |
| POSTGRES_SERVER              | The IP address or hostname of the postgresql server. | ${RELEASE_SLUG}-postgresql |
| POSTGRES_PORT                | The port the postgresql is reachable under.          | 5432                       |
| POSTGRES_DB                  | The database name on the postgresql server.          | postgresDB                 |
| POSTGRES_USER                | The database user.                                   | postgres                   |
| POSTGRES_PASSWORD            | The database password for the database user.         | postgres                   |
| DOMAIN_MICROSERVICE_API_PORT | The port the domain microservice API should run on.  | 80                         |


An example for local development follows. The postgres sql server runs locally on IP 127.0.0.1 and port 5432.
The default username is postgres and a password isn't required for local access.

```sh
export POSTGRES_SERVER=127.0.0.1
export POSTGRES_PORT=5432
export POSTGRES_DB=apidb
export POSTGRES_USER=postgres
export POSTGRES_PASSWORD=
export DOMAIN_MICROSERVICE_API_PORT=8080
```

Then create the database. This may be changed later so the domain microservice API automatically creates its postgres database.

```sh
psql 'postgresql://postgres:@127.0.0.1:5432' -c "CREATE DATABASE apidb;"
```


## Running

1. Execute the app with `mvn spring-boot:run`.


## In-Cluster Database

The domain microservice API starts its own postgres database, which is defined in the [Helm Chart](../helm/Chart.yaml).
