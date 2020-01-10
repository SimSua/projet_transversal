<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateTrucksTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('trucks', function (Blueprint $table) {
            $table->bigIncrements('id')->unsigned();
            $table->bigInteger('id_type')->unsigned();
            $table->bigInteger('id_fire')->unsigned()->nullable();
            $table->bigInteger('id_department')->unsigned();
            $table->bigInteger('id_coordinate')->unsigned();
            $table->timestamps();
            $table->foreign('id_type')->references('id')->on('vehicle_types');
            $table->foreign('id_fire')->references('id')->on('fires');
            $table->foreign('id_department')->references('id')->on('fire_departments');
            $table->foreign('id_coordinate')->references('id')->on('coordinates');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('trucks');
    }
}
