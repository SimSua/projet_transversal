<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateFireDepartmentsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('fire_departments', function (Blueprint $table) {
            $table->bigIncrements('id')->unsigned();
            $table->string('label');
            $table->integer('capacity')->unsigned();
            $table->bigInteger('id_coordinate')->unsigned();
            $table->timestamps();
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
        Schema::dropIfExists('fire_departments');
    }
}
