Project that uses PanacheRepository with PostgreSQL to store, edit and delete Movie entities in Quarkus environment.

In order to run the project, first of all you need to "build" the database using docker with the following command `docker run --name my_db -e POSTGRES_USER=username -e POSTGRES_PASSWORD=password -e POSTGRES_DB=my_db -p 5432:5432 postgres:10.5`. After running the command just compile the project with `mvn compile quarkus:dev`.
