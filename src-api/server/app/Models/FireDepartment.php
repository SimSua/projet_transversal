<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class FireDepartment extends Model
{
    protected $fillable = [
        'label',
        'capacity',
        'id_coordinate'
    ];
}
