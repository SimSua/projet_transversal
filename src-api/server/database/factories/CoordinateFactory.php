<?php

/** @var Factory $factory */

use App\Models\Coordinate;
use Faker\Generator as Faker;
use Illuminate\Database\Eloquent\Factory;

$factory->define(Coordinate::class, function (Faker $faker) {
    return [
        'latitude' => $faker->randomFloat(5, 0, 360),
        'longitude' => $faker->randomFloat(5, 0, 360),
        'line' => $faker->numberBetween(0, 9),
        'column' => $faker->numberBetween(0, 9)
    ];
});
