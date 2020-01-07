<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class FireDepartment extends Model
{
    protected $fillable = [
        'label',
        'line',
        'column',
        'capacity',
        'id_coordinate'
    ];
}
