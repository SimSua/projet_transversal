<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateFiresTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('fires', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->integer('line')->unsigned();
            $table->integer('column')->unsigned();
            $table->integer('intensity')->unsigned();
            $table->bigInteger('id_point')->unsigned();
            $table->timestamps();
//            $table->foreign('id_point')->references('id')->on('points');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('fires');
    }
}
