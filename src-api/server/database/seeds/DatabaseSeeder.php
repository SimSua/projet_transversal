<?php

use App\Models\Coordinate;
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
    }
}
