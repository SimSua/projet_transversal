<?php

/** @var Factory $factory */

use App\Models\Fire;
use Faker\Generator as Faker;
use Illuminate\Database\Eloquent\Factory;

$factory->define(Fire::class, function (Faker $faker) {
    return [
        'intensity' => $faker->numberBetween(0, 9),
        'id_coordinate' => $faker->numberBetween(1, 9)
    ];
});
