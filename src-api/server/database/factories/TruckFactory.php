<?php

/** @var Factory $factory */

use App\Models\Truck;
use Faker\Generator as Faker;
use Illuminate\Database\Eloquent\Factory;

$factory->define(Truck::class, function (Faker $faker) {
    return [
        'id_type' => $faker->numberBetween(1, 9),
        'id_fire' => $faker->numberBetween(1, 9),
        'id_department' => $faker->numberBetween(1, 9),
        'id_coordinate' => $faker->numberBetween(1, 9)
    ];
});
