<?php

namespace Tests\Unit;

use Tests\TestCase;

class VehicleTypeTest extends TestCase
{
    public function test_post_create_ok(): void
    {
        $now = $this->getDateTimeNow();
        $data = ['data' => [
            'label' => 'Default',
            'speed' => 1,
            'efficiency' => 1,
            'updated_at' => $now,
            'created_at' => $now,
            'id' => 1
        ]];

        $this->createVehicleType()
            ->assertStatus(201)
            ->assertExactJson($data);
    }

    public function test_get_all(): void
    {
        $this->createVehicleType("First", 2, 2);
        $this->createVehicleType("Second", 3, 3);

        $now = $this->getDateTimeNow();

        $response = $this->get('/api/vehicle-types');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());

        $data = [
            'id' => 2,
            'label' => 'First',
            'speed' => 2,
            'efficiency' => 2,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());

        $data = [
            'id' => 3,
            'label' => 'Second',
            'speed' => 3,
            'efficiency' => 3,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());
    }

    public function test_get_one_by_id(): void
    {
        $this->createVehicleType();

        $now = $this->getDateTimeNow();

        $response = $this->get('/api/vehicle-types/4');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());

        $data = [
            'id' => 4,
            'label' => 'Default',
            'speed' => 1,
            'efficiency' => 1,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());
    }

    public function test_get_one_by_id_ko(): void
    {
        $response = $this->get('/api/vehicle-types/100');

        $this->assertJson($response->getContent());
        $this->assertEquals(400, $response->getStatusCode());
        $this->assertContains('"status":"error"',$response->getContent());
    }

    public function test_delete_ok(): void
    {
        $this->createVehicleType();
        $response = $this->delete('/api/vehicle-types/5');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());
        $this->assertContains('"id":5,"label":"Default","speed":1,"efficiency":1', $response->getContent());
    }

    public function test_delete_ko(): void
    {
        $response = $this->delete('/api/vehicle-types/1');

        $this->assertJson($response->getContent());
        $this->assertEquals(400, $response->getStatusCode());
        $this->assertContains('"status":"error"', $response->getContent());
    }
}
