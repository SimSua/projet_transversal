<?php

namespace App\Http\Resources;

use App\Exceptions\ResponseInterface;
use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class Coordinate extends JsonResource implements ResponseInterface
{
    /**
     * Transform the resource into an array.
     *
     * @param  Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return parent::toArray($request);
    }
}
