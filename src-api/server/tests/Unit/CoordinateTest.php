<?php

namespace Tests\Unit;

use Tests\TestCase;

class CoordinateTest extends TestCase
{
    public function test_post_create_ok(): void
    {
        $now = $this->getDateTimeNow();
        $data = ['data' => [
            'latitude' => 45.78,
            'longitude' => 4.8,
            'line' => 0,
            'column' => 0,
            'updated_at' => $now,
            'created_at' => $now,
            'id' => 1
        ]];

        $this->createCoordinate()
            ->assertStatus(201)
            ->assertExactJson($data);
    }

    public function test_get_all(): void
    {
        $this->createCoordinate(0, 1, 45.78, 4.8);
        $this->createCoordinate(1, 2, 50, 5);

        $now = $this->getDateTimeNow();

        $response = $this->get('/api/coordinates');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());

        $data = [
            'id' => 2,
            'latitude' => "45.78",
            'longitude' => "4.8",
            'line' => 0,
            'column' => 1,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());

        $data = [
            'id' => 3,
            'latitude' => "50",
            'longitude' => "5",
            'line' => 1,
            'column' => 2,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());
    }

    public function test_get_one_by_id(): void
    {
        $this->createCoordinate();

        $now = $this->getDateTimeNow();

        $response = $this->get('/api/coordinates/4');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());

        $data = [
            'id' => 4,
            'latitude' => "45.78",
            'longitude' => "4.8",
            'line' => 0,
            'column' => 0,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());
    }

    public function test_get_one_by_id_ko(): void
    {
        $response = $this->get('/api/coordinates/100');

        $this->assertJson($response->getContent());
        $this->assertEquals(400, $response->getStatusCode());
        $this->assertContains('"status":"error"',$response->getContent());
    }

    public function test_get_one_by_position(): void
    {
        $this->createCoordinate(5, 9, 78, 6);

        $now = $this->getDateTimeNow();

        $response = $this->get('/api/coordinates/5/9');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());

        $data = [
            'id' => 5,
            'latitude' => "78",
            'longitude' => "6",
            'line' => 5,
            'column' => 9,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());
    }

    public function test_get_one_by_position_ko(): void
    {
        $response = $this->get('/api/coordinates/100/100');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());
        $this->assertEquals('{"data":[]}', $response->getContent());
    }

    public function test_delete_ok(): void
    {
        $this->createCoordinate();
        $response = $this->delete('/api/coordinates/6');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());
        $this->assertContains('"id":6,"latitude":"45.78","longitude":"4.8"', $response->getContent());
    }

    public function test_delete_ko(): void
    {
        $this->createCoordinate();
        $response = $this->delete('/api/coordinates/1');

        $this->assertJson($response->getContent());
        $this->assertEquals(400, $response->getStatusCode());
        $this->assertContains('"status":"error"', $response->getContent());
    }
}
