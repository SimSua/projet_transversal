<?php

use App\Models\Fire;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class FiresTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('fires')->delete();

        $json = File::get("database/data-sample/fires.json");
        $data = json_decode($json);
        foreach ($data as $obj) {
            Fire::create(
                [
                    'line' => $obj->line,
                    'column' => $obj->column,
                    'intensity' => $obj->intensity,
                    'id_point' => $obj->id_point
                ]
            );
        }
    }
}
