<?php

use App\Models\Coordinate;
use App\Models\Fire;
use App\Models\FireDepartment;
use App\Models\VehicleType;
use App\Models\Truck;
use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        factory(Coordinate::class, 10)->create();
        factory(Fire::class, 10)->create();
        factory(FireDepartment::class, 10)->create();
        factory(VehicleType::class, 5)->create();
        factory(Truck::class, 5)->create();
    }
}
