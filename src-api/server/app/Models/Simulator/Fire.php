<?php

namespace App\Models\Simulator;

use Illuminate\Database\Eloquent\Model;

class Fire extends Model
{
    protected $connection = 'pgsql_sim';

    protected $fillable = [
        'intensity',
        'id_coordinate'
    ];
}
