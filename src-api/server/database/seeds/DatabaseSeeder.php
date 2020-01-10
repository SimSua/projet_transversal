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
        DB::table('trucks')->truncate();
        DB::table('vehicle_types')->truncate();
        DB::table('fires')->truncate();
        DB::table('fire_departments')->truncate();
        DB::table('coordinates')->truncate();

        /** Generates random data set with Faker */
//        factory(Coordinate::class, 10)->create()->each(function($coordinate) {
//            $fire = factory(Fire::class)->create([
//                'id_coordinate' => $coordinate
//            ]);
//
//            $department = factory(FireDepartment::class)->create([
//                'id_coordinate' => $coordinate
//            ]);
//
//            $vehicleType = factory(VehicleType::class)->create();
//
//            factory(Truck::class)->create([
//                'id_type' => $vehicleType,
//                'id_fire' => $fire,
//                'id_department' => $department,
//                'id_coordinate' => $coordinate
//            ]);
//        });

        /** Generates specific data set from json file */
        // Coordinates
        $json = File::get('database/data/coordinates.json');
        $data = json_decode($json);

        foreach ($data as $obj) {
            Coordinate::create([
                'latitude'=>$obj->latitude,
                'longitude'=>$obj->longitude,
                'line'=>$obj->line,
                'column'=>$obj->column
            ]);
        }

        // Fire Departments
        $json = File::get('database/data/fire_departments.json');
        $data = json_decode($json);

        foreach ($data as $obj) {
            FireDepartment::create([
                'label'=>$obj->label,
                'capacity'=>$obj->capacity,
                'id_coordinate'=>$obj->id_coordinate
            ]);
        }

        // Fires
        $json = File::get('database/data/fires.json');
        $data = json_decode($json);

        foreach ($data as $obj) {
            Fire::create([
                'intensity'=>$obj->intensity,
                'id_coordinate'=>$obj->id_coordinate
            ]);
        }

        // Vehicle Types
        $json = File::get('database/data/vehicle_types.json');
        $data = json_decode($json);

        foreach ($data as $obj) {
            VehicleType::create([
                'label'=>$obj->label,
                'speed'=>$obj->speed,
                'efficiency'=>$obj->efficiency
            ]);
        }

        // Trucks
        $json = File::get('database/data/trucks.json');
        $data = json_decode($json);

        foreach ($data as $obj) {
            Truck::create([
                'id_type'=>$obj->id_type,
                'id_fire'=>$obj->id_fire,
                'id_department'=>$obj->id_department,
                'id_coordinate'=>$obj->id_coordinate
            ]);
        }
    }
}
