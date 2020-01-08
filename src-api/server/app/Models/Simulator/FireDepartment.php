<?php

namespace App\Models\Simulator;

use Illuminate\Database\Eloquent\Model;

class FireDepartment extends Model
{
    protected $connection = 'pgsql_sim';

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
