<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Truck extends Model
{
    protected $fillable = [
        'id_type',
        'id_fire',
        'id_department',
        'id_coordinate'
    ];
}
