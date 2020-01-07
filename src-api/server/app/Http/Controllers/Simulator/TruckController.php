<?php

namespace App\Http\Controllers\Simulator;

use App\Http\Resources\TruckCollection;
use App\Models\Truck;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Resources\Truck as TruckResource;

class TruckController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     */
    public function index()
    {
        return new TruckCollection(Truck::All());
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param Request $request
     * @return TruckResource
     */
    public function store(Request $request)
    {
        $truck = new Truck();
        $truck->latitude = $request->get('latitude');
        $truck->longitude = $request->get('longitude');
        $truck->save();

        return new TruckResource($truck);
    }

    /**
     * Display the specified resource.
     *
     * @param int $id
     * @return TruckResource
     */
    public function show($id)
    {
        return new TruckResource(Truck::FindOrFail($id));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param Request $request
     * @param int $id
     * @return TruckResource
     */
    public function update(Request $request, $id)
    {
        $truck = Truck::findOrFail($id);
        $truck->latitude = $request->get('latitude');
        $truck->longitude = $request->get('longitude');
        $truck->save();

        return new TruckResource($truck);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param int $id
     * @return TruckResource
     */
    public function destroy($id)
    {
        $truck = Truck::findOrFail($id);
        $truck->delete();

        return new TruckResource($truck);
    }
}
