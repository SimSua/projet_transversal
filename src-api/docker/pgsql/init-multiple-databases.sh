#!/bin/bash

set -e
set -u

function create_user_and_database() {
	local database=$1
	echo "  Creating user and database '$database'"
	psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL	
		DROP DATABASE $database;    
	    CREATE DATABASE $database;
	    GRANT ALL PRIVILEGES ON DATABASE $database TO $POSTGRES_USER;
EOSQL
}

psql -v ON_ERROR_TOP=1 -U "postgres" <<-EOSQL
	DROP ROLE $POSTGRES_USER;
	CREATE ROLE $POSTGRES_USER WITH SUPERUSER CREATEDB LOGIN PASSWORD '$POSTGRES_PASSWORD';
	CREATE DATABASE $POSTGRES_USER;
EOSQL

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
	echo "Multiple database creation requested: $POSTGRES_MULTIPLE_DATABASES"
	for db in $(echo $POSTGRES_MULTIPLE_DATABASES | tr ',' ' '); do
		create_user_and_database $db
	done
	echo "Multiple databases created"
fi