<?php

/** @var Factory $factory */

use App\Models\VehicleType;
use Faker\Generator as Faker;
use Illuminate\Database\Eloquent\Factory;

$factory->define(VehicleType::class, function (Faker $faker) {
    return [
        'label' => $faker->words($nb = 2, $asText = true),
        'speed' => $faker->numberBetween(1, 10),
        'efficiency' => $faker->numberBetween(1, 10)
    ];
});
