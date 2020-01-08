<?php

namespace App\Models\Simulator;

use Illuminate\Database\Eloquent\Model;

class Coordinate extends Model
{
    protected $connection = 'pgsql_sim';

    protected $fillable = [
        'latitude',
        'longitude',
        'line',
        'column'
    ];
}
