<?php

namespace Tests\Unit;

use Tests\TestCase;

class FireTest extends TestCase
{
    public function test_post_create_ok(): void
    {
        $this->createCoordinate();
        $now = $this->getDateTimeNow();
        $data = ['data' => [
            'intensity' => 0,
            'id_coordinate' => 8,
            'updated_at' => $now,
            'created_at' => $now,
            'id' => 1
        ]];

        $this->createFire(0, 8)
            ->assertStatus(201)
            ->assertExactJson($data);
    }

    public function test_get_all(): void
    {
        $this->createCoordinate(0, 1, 45.78, 4.8);
        $this->createCoordinate(1, 2, 50, 5);
        $this->createFire(1, 9);
        $this->createFire(5, 10);

        $now = $this->getDateTimeNow();

        $response = $this->get('/api/fires');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());

        $data = [
            'id' => 2,
            'intensity' => 1,
            'id_coordinate' => 9,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());

        $data = [
            'id' => 3,
            'intensity' => 5,
            'id_coordinate' => 10,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());
    }

    public function test_get_one_by_id(): void
    {
        $this->createCoordinate();
        $this->createFire(5, 11);

        $now = $this->getDateTimeNow();

        $response = $this->get('/api/fires/4');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());

        $data = [
            'id' => 4,
            'intensity' => 5,
            'id_coordinate' => 11,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());
    }

    public function test_get_one_by_id_ko(): void
    {
        $response = $this->get('/api/fires/100');

        $this->assertJson($response->getContent());
        $this->assertEquals(400, $response->getStatusCode());
        $this->assertContains('"status":"error"',$response->getContent());
    }

    public function test_get_position(): void
    {
        $this->createCoordinate(5, 9);
        $this->createCoordinate(1, 3);
        $this->createFire(0, 12);
        $this->createFire(9, 13);

        $response = $this->get('/api/fires/position/get');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());

        $data = [
            'id_fire' => 5,
            'intensity' => 0,
            'line' => 5,
            'column' => 9
        ];
        $this->assertContains(json_encode($data), $response->getContent());

        $data = [
            'id_fire' => 6,
            'intensity' => 9,
            'line' => 1,
            'column' => 3
        ];
        $this->assertContains(json_encode($data), $response->getContent());
    }

    public function test_delete_ok(): void
    {
        $this->createCoordinate();
        $this->createFire(0, 14);
        $response = $this->delete('/api/fires/7');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());
        $this->assertContains('"id":7,"intensity":0,"id_coordinate":14', $response->getContent());
    }

    public function test_delete_ko(): void
    {
        $response = $this->delete('/api/fires/1');

        $this->assertJson($response->getContent());
        $this->assertEquals(400, $response->getStatusCode());
        $this->assertContains('"status":"error"', $response->getContent());
    }
}
