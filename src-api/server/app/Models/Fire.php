<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Fire extends Model
{
    protected $fillable = [
        'intensity',
        'id_coordinate'
    ];
}
