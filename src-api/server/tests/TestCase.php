<?php

namespace Tests;

use DateTime;
use Faker\Factory;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Foundation\Testing\TestCase as BaseTestCase;

abstract class TestCase extends BaseTestCase
{
    use CreatesApplication;
    use RefreshDatabase;

    protected $faker;

    public function setUp(): void
    {
        parent::setUp();
        $this->faker = Factory::create();
    }

    public function tearDown(): void
    {
        parent::tearDown();
        try {
            $this->refreshDatabase();
        } catch(\Throwable $t) {
        }
    }

    protected function getDateTimeNow(): ?string
    {
        try {
            return (new DateTime(now()))->format('Y-m-d H:i:s');
        } catch(\Throwable $t) {
            return null;
        }
    }

    protected function createCoordinate(int $line=0, int $column=0, float $latitude=45.78, float $longitude=4.8)
    {
        $data = [
            'latitude' => $latitude,
            'longitude' => $longitude,
            'line' => $line,
            'column' => $column
        ];

        return $this->post('/api/coordinates', $data);
    }
}
