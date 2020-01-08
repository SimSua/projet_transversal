<?php

/** @var Factory $factory */

use App\Models\Coordinate;
use Faker\Generator as Faker;
use Illuminate\Database\Eloquent\Factory;

$factory->define(Coordinate::class, function (Faker $faker) {
    return [
        'latitude' => $faker->randomFloat(6, 45.70, 45.79),
        'longitude' => $faker->randomFloat(6, 4.80, 4.90),
        'line' => $faker->numberBetween(0, 9),
        'column' => $faker->numberBetween(0, 9)
    ];
});
