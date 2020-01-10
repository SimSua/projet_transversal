<?php

namespace Tests\Unit;

use Tests\TestCase;

class CoordinateTest extends TestCase
{
    public function test_post_create_ok()
    {
        $data = [
            'latitude' => $this->faker->randomFloat(6, 45.70, 45.79),
            'longitude' => $this->faker->randomFloat(6, 4.80, 4.90),
            'line' => $this->faker->numberBetween(0, 9),
            'column' => $this->faker->numberBetween(0, 9)
        ];

        $now = $this->getDateTimeNow();
        $formattedData = ["data"=>array_merge($data, ["updated_at"=>$now,"created_at"=>$now,"id"=>1])];

        $this->post('/api/coordinates', $data)
            ->assertStatus(201)
            ->assertExactJson($formattedData);
    }
}
