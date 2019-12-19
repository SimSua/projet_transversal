<?php

use Illuminate\Database\Seeder;
use App\Fire;

class FiresTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::connection('pgsql_sim');
        DB::table('fires')->delete();

        $json = File::get("database/data-sample/fires.json");
        $data = json_decode($json);
        foreach ($data as $obj) {
            Fire::create(
                [
                    'intensity' => $obj->intensity
                ]
            );
        }
    }
}
