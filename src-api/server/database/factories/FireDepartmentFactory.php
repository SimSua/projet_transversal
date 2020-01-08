<?php

/** @var Factory $factory */

use App\Models\FireDepartment;
use Faker\Generator as Faker;
use Illuminate\Database\Eloquent\Factory;

$factory->define(FireDepartment::class, function (Faker $faker) {
    return [
        'label' => $faker->words($nb = 4, $asText = true),
        'capacity' => $faker->numberBetween(0, 9),
        'id_coordinate' => $faker->numberBetween(1, 9)
    ];
});
