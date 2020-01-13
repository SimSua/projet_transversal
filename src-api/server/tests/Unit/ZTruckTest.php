<?php

namespace Tests\Unit;

use Tests\TestCase;

class ZTruckTest extends TestCase
{
    public function test_post_create_ok(): void
    {
        $this->createCoordinate();
        $this->createFireDepartment(20);
        $this->createVehicleType();
        $this->createFire(0, 20);
        $now = $this->getDateTimeNow();
        $data = ['data' => [
            'id_type' => 6,
            'id_fire' => 8,
            'id_department' => 6,
            'id_coordinate' => 20,
            'updated_at' => $now,
            'created_at' => $now,
            'id' => 1
        ]];

        $this->createTruck(6, 8, 6, 20)
            ->assertStatus(201)
            ->assertExactJson($data);
    }

    public function test_get_all(): void
    {
        $this->createCoordinate();
        $this->createFireDepartment(21);
        $this->createVehicleType();
        $this->createFire(0, 21);
        $this->createTruck(7, 9, 7, 21);

        $now = $this->getDateTimeNow();
        $response = $this->get('/api/trucks');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());

        $data = [
            'id' => 2,
            'id_type' => 7,
            'id_fire' => 9,
            'id_department' => 7,
            'id_coordinate' => 21,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());
    }

    public function test_get_one_by_id(): void
    {
        $this->createCoordinate();
        $this->createFireDepartment(22);
        $this->createVehicleType();
        $this->createFire(0, 22);
        $this->createTruck(8, 10, 8, 22);

        $now = $this->getDateTimeNow();

        $response = $this->get('/api/trucks/3');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());

        $data = [
            'id' => 3,
            'id_type' => 8,
            'id_fire' => 10,
            'id_department' => 8,
            'id_coordinate' => 22,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());
    }

    public function test_get_one_by_id_ko(): void
    {
        $response = $this->get('/api/trucks/100');

        $this->assertJson($response->getContent());
        $this->assertEquals(400, $response->getStatusCode());
        $this->assertContains('"status":"error"',$response->getContent());
    }

    public function test_delete_ok(): void
    {
        $this->createCoordinate();
        $this->createFireDepartment(23);
        $this->createVehicleType();
        $this->createFire(0, 23);
        $this->createTruck(9, 11, 9, 23);
        $response = $this->delete('/api/trucks/4');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());
        $this->assertContains('"id":4,"id_type":9,"id_fire":11,"id_department":9,"id_coordinate":23', $response->getContent());
    }

    public function test_delete_ko(): void
    {
        $response = $this->delete('/api/trucks/1');

        $this->assertJson($response->getContent());
        $this->assertEquals(400, $response->getStatusCode());
        $this->assertContains('"status":"error"', $response->getContent());
    }
}
