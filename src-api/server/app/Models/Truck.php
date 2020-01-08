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

    public function coordinates()
    {
        return $this->hasOne(Coordinate::class);
    }

    public function fires()
    {
        return $this->hasOne(Fire::class);
    }

    public function fireDepartments()
    {
        return $this->hasOne(FireDepartment::class);
    }
}
