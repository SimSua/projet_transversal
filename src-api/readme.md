> docker-compose up

> docker inspect postgres

> copy .env.example as .env in root dir and fill user/password

> copy .env.example as .env in src-api and fill host/user/password (emergency + simulator)

> sudo chown -R ${USER}:www-data . && sudo chmod -R 775 .

> docker-compose exec php-fpm bash

> composer install

> php artisan migrate

> php artisan db:seed

> localhost:8081/api/coordinates
