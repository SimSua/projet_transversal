<?php

namespace App\Http\Controllers\Simulator;

use App\Http\Resources\CoordinateCollection;
use App\Models\Coordinate;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Resources\Coordinate as CoordinateResource;

class CoordinateController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     */
    public function index()
    {
        return new CoordinateCollection(Coordinate::All());
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param Request $request
     * @return CoordinateResource
     */
    public function store(Request $request)
    {
        $coordinate = new Coordinate();
        $coordinate->latitude = $request->get('latitude');
        $coordinate->longitude = $request->get('longitude');
        $coordinate->save();

        return new CoordinateResource($coordinate);
    }

    /**
     * Display the specified resource.
     *
     * @param int $id
     * @return CoordinateResource
     */
    public function show($id)
    {
        return new CoordinateResource(Coordinate::FindOrFail($id));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param Request $request
     * @param int $id
     * @return CoordinateResource
     */
    public function update(Request $request, $id)
    {
        $coordinate = Coordinate::findOrFail($id);
        $coordinate->latitude = $request->get('latitude');
        $coordinate->longitude = $request->get('longitude');
        $coordinate->save();

        return new CoordinateResource($coordinate);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param int $id
     * @return CoordinateResource
     */
    public function destroy($id)
    {
        $coordinate = Coordinate::findOrFail($id);
        $coordinate->delete();

        return new CoordinateResource($coordinate);
    }
}
