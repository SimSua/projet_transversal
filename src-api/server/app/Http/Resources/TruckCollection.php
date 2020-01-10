<?php

namespace App\Http\Resources;

use App\Exceptions\ResponseInterface;
use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\ResourceCollection;

class TruckCollection extends ResourceCollection implements ResponseInterface
{
    /**
     * Transform the resource collection into an array.
     *
     * @param  Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return parent::toArray($request);
    }
}
