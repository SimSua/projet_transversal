<?php

namespace App\Http\Controllers\Emergency;

use App\Http\Resources\VehicleTypeCollection;
use App\Models\VehicleType;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Resources\VehicleType as VehicleTypeResource;

class VehicleTypeController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     */
    public function index()
    {
        return new VehicleTypeCollection(VehicleType::All());
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param Request $request
     * @return VehicleTypeResource
     */
    public function store(Request $request)
    {
        $vehicleType = new VehicleType();
        $vehicleType->latitude = $request->get('latitude');
        $vehicleType->longitude = $request->get('longitude');
        $vehicleType->save();

        return new VehicleTypeResource($vehicleType);
    }

    /**
     * Display the specified resource.
     *
     * @param int $id
     * @return VehicleTypeResource
     */
    public function show($id)
    {
        return new VehicleTypeResource(VehicleType::FindOrFail($id));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param Request $request
     * @param int $id
     * @return VehicleTypeResource
     */
    public function update(Request $request, $id)
    {
        $vehicleType = VehicleType::findOrFail($id);
        $vehicleType->latitude = $request->get('latitude');
        $vehicleType->longitude = $request->get('longitude');
        $vehicleType->save();

        return new VehicleTypeResource($vehicleType);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param int $id
     * @return VehicleTypeResource
     */
    public function destroy($id)
    {
        $vehicleType = VehicleType::findOrFail($id);
        $vehicleType->delete();

        return new VehicleTypeResource($vehicleType);
    }
}
