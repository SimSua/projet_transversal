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

    public function coordinates()
    {
        return $this->hasOne(Coordinate::class);
    }

    public function trucks()
    {
        return $this->belongsToMany(Truck::class);
    }
}
