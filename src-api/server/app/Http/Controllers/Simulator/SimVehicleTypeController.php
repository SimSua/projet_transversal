<?php

namespace App\Http\Controllers\Simulator;

use App\Exceptions\ExceptionResponse;
use App\Exceptions\ResponseInterface;
use App\Http\Resources\VehicleTypeCollection;
use App\Models\Simulator\VehicleType;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Resources\VehicleType as VehicleTypeResource;

class SimVehicleTypeController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return VehicleTypeCollection | ExceptionResponse
     */
    public function index(): ResponseInterface
    {
        try {
            return new VehicleTypeCollection(VehicleType::All());
        } catch (\Exception $e) {
            return new ExceptionResponse([
                'status'=>'error',
                'message'=>$e->getMessage(),
                'trace'=>$e->getTrace(),
            ], 400);
        }
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param Request $request
     * @return VehicleTypeResource
     */
    public function store(Request $request): ResponseInterface
    {
        try {
            $vehicleType = new VehicleType();
            $vehicleType->label = $request->get('label');
            $vehicleType->speed = (int)$request->get('speed');
            $vehicleType->efficiency = (int)$request->get('efficiency');
            $vehicleType->save();

            return new VehicleTypeResource($vehicleType);
        } catch (\Exception $e) {
            return new ExceptionResponse([
                'status'=>'error',
                'message'=>$e->getMessage(),
                'trace'=>$e->getTrace(),
            ], 400);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param int $id
     * @return VehicleTypeResource
     */
    public function show($id): ResponseInterface
    {
        try {
            return new VehicleTypeResource(VehicleType::FindOrFail($id));
        } catch (\Exception $e) {
            return new ExceptionResponse([
                'status'=>'error',
                'message'=>$e->getMessage(),
                'trace'=>$e->getTrace(),
            ], 400);
        }
    }

    /**
     * Update the specified resource in storage.
     *
     * @param Request $request
     * @param int $id
     * @return VehicleTypeResource
     */
    public function update(Request $request, $id): ResponseInterface
    {
        try {
            $vehicleType = VehicleType::findOrFail($id);
            $vehicleType->label = $request->get('label');
            $vehicleType->speed = (int)$request->get('speed');
            $vehicleType->efficiency = (int)$request->get('efficiency');
            $vehicleType->save();

            return new VehicleTypeResource($vehicleType);
        } catch (\Exception $e) {
            return new ExceptionResponse([
                'status'=>'error',
                'message'=>$e->getMessage(),
                'trace'=>$e->getTrace(),
            ], 400);
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param int $id
     * @return VehicleTypeResource
     */
    public function destroy($id): ResponseInterface
    {
        try {
            $vehicleType = VehicleType::findOrFail($id);
            $vehicleType->delete();

            return new VehicleTypeResource($vehicleType);
        } catch (\Exception $e) {
            return new ExceptionResponse([
                'status'=>'error',
                'message'=>$e->getMessage(),
                'trace'=>$e->getTrace(),
            ], 400);
        }
    }
}
