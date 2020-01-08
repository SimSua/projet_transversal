> docker-compose up

> docker inspect postgres

> copy .env.example as .env in root dir and fill user/password

> copy .env.example as .env in src-api and fill host/user/password (emergency + simulator)

> sudo chown -R ${USER}:www-data . && sudo chmod -R 775 .

> docker-compose exec php-fpm bash

> composer install

> php artisan migrate --database=pgsql

> php artisan migrate --database=pgsql_sim

> php artisan db:seed --database=pgsql

> php artisan db:seed --database=pgsql_sim

> localhost:8081/api/coordinates
