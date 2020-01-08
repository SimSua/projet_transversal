<?php

use App\Models\Coordinate;
use App\Models\Fire;
use App\Models\FireDepartment;
use App\Models\VehicleType;
use App\Models\Truck;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        DB::table('trucks')->delete();
        DB::table('vehicle_types')->delete();
        DB::table('fires')->delete();
        DB::table('fire_departments')->delete();
        DB::table('coordinates')->delete();

        factory(Coordinate::class, 10)->create()->each(function($coordinate) {
            $fire = factory(Fire::class)->create([
                'id_coordinate' => $coordinate
            ]);

            $department = factory(FireDepartment::class)->create([
                'id_coordinate' => $coordinate
            ]);

            $vehicleType = factory(VehicleType::class)->create();

            factory(Truck::class)->create([
                'id_type' => $vehicleType,
                'id_fire' => $fire,
                'id_department' => $department,
                'id_coordinate' => $coordinate
            ]);
        });
    }
}
