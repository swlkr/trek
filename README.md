# trek

Opinionated database migrations for clojure

## Usage

Migrations look like this

```sql
-- up
create table posts (
  id serial primary key,
  title text not null,
  body text not null,
  created_at timestamp
)

-- down
drop table posts
```

Migrations live in resources/migrations
There is no lein plugin but you could use aliases with
the functions below:

```clojure
(migrate db-spec) ; applies all pending migrations in order
(rollback db-spec) ; rolls one migration back
(create "create-users" "email:text" "password:text") ; creates a migration file in resources/migrations
```
