<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Coordinate extends Model
{
    protected $fillable = [
        'latitude',
        'longitude',
        'line',
        'column'
    ];

    public function fires()
    {
        return $this->belongsTo(Fire::class);
    }

    public function fireDepartments()
    {
        return $this->belongsTo(FireDepartment::class);
    }

    public function trucks()
    {
        return $this->belongsToMany(Truck::class);
    }
}
