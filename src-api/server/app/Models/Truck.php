<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Truck extends Model
{
    protected $fillable = [
        'line',
        'column',
        'id_type',
        'id_fire',
        'id_department'
    ];
}
