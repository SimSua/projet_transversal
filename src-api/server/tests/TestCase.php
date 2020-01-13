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

    protected function createFire(int $intensity=0, int $idCoordinate=1)
    {
        $data = [
            'intensity' => $intensity,
            'id_coordinate' => $idCoordinate
        ];

        return $this->post('/api/fires', $data);
    }

    protected function createFireDepartment(int $idCoordinate=1, string $label="Default", int $capacity=1)
    {
        $data = [
            'label' => $label,
            'capacity' => $capacity,
            'id_coordinate' => $idCoordinate
        ];

        return $this->post('/api/fire-departments', $data);
    }

    protected function createVehicleType(string $label="Default", int $speed=1, int $efficiency=1)
    {
        $data = [
            'label' => $label,
            'speed' => $speed,
            'efficiency' => $efficiency
        ];

        return $this->post('/api/vehicle-types', $data);
    }

    protected function createTruck(int $idType=1, int $idFire=1, int $idDepartment=1, int $idCoordinate=1)
    {
        $data = [
            'id_type' => $idType,
            'id_fire' => $idFire,
            'id_department' => $idDepartment,
            'id_coordinate' => $idCoordinate
        ];

        return $this->post('/api/trucks', $data);
    }
}
