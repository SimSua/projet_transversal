<?php

namespace App\Models\Simulator;

use Illuminate\Database\Eloquent\Model;

class VehicleType extends Model
{
    protected $connection = 'pgsql_sim';

    protected $fillable = [
        'label',
        'speed',
        'efficiency'
    ];
}
