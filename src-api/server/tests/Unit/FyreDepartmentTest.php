<?php

namespace Tests\Unit;

use Tests\TestCase;

class FyreDepartmentTest extends TestCase
{
    public function test_post_create_ok(): void
    {
        $this->createCoordinate();
        $now = $this->getDateTimeNow();
        $data = ['data' => [
            'label' => 'Default',
            'capacity' => 1,
            'id_coordinate' => 15,
            'updated_at' => $now,
            'created_at' => $now,
            'id' => 1
        ]];

        $this->createFireDepartment(15)
            ->assertStatus(201)
            ->assertExactJson($data);
    }

    public function test_get_all(): void
    {
        $this->createCoordinate(0, 1, 45.78, 4.8);
        $this->createCoordinate(1, 2, 50, 5);
        $this->createFireDepartment(16);
        $this->createFireDepartment(17);

        $now = $this->getDateTimeNow();

        $response = $this->get('/api/fire-departments');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());

        $data = [
            'id' => 2,
            'label' => 'Default',
            'capacity' => 1,
            'id_coordinate' => 16,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());

        $data = [
            'id' => 3,
            'label' => 'Default',
            'capacity' => 1,
            'id_coordinate' => 17,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());
    }

    public function test_get_one_by_id(): void
    {
        $this->createCoordinate();
        $this->createFireDepartment(18);

        $now = $this->getDateTimeNow();

        $response = $this->get('/api/fire-departments/4');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());

        $data = [
            'id' => 4,
            'label' => 'Default',
            'capacity' => 1,
            'id_coordinate' => 18,
            'created_at' => $now,
            'updated_at' => $now
        ];
        $this->assertContains(json_encode($data), $response->getContent());
    }

    public function test_get_one_by_id_ko(): void
    {
        $response = $this->get('/api/fire-departments/100');

        $this->assertJson($response->getContent());
        $this->assertEquals(400, $response->getStatusCode());
        $this->assertContains('"status":"error"',$response->getContent());
    }

    public function test_delete_ok(): void
    {
        $this->createCoordinate();
        $this->createFireDepartment(19);
        $response = $this->delete('/api/fire-departments/5');

        $this->assertJson($response->getContent());
        $this->assertEquals(200, $response->getStatusCode());
        $this->assertContains('"id":5,"label":"Default","capacity":1,"id_coordinate":19', $response->getContent());
    }

    public function test_delete_ko(): void
    {
        $response = $this->delete('/api/fire-departments/1');

        $this->assertJson($response->getContent());
        $this->assertEquals(400, $response->getStatusCode());
        $this->assertContains('"status":"error"', $response->getContent());
    }
}
