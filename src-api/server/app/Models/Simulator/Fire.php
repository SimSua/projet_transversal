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

    public function coordinates()
    {
        return $this->hasOne(Coordinate::class);
    }

    public function trucks()
    {
        return $this->belongsToMany(Truck::class);
    }
}
