<?php

namespace App\Models\Simulator;

use Illuminate\Database\Eloquent\Model;

class Truck extends Model
{
    protected $connection = 'pgsql_sim';

    protected $fillable = [
        'line',
        'column',
        'id_type',
        'id_fire',
        'id_department'
    ];
}
