<?php

namespace App\Http\Controllers\Emergency;

use App\Exceptions\ExceptionResponse;
use App\Exceptions\ResponseInterface;
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
     * @return TruckCollection | ExceptionResponse
     */
    public function index(): ResponseInterface
    {
        try {
            return new TruckCollection(Truck::All());
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
     * @return TruckResource
     */
    public function store(Request $request): ResponseInterface
    {
        try {
            $truck = new Truck();
            $truck->id_type = (int)$request->get('id_type');
            $truck->id_fire = ((int)$request->get('id_fire')!=0)?(int)$request->get('id_fire'):NULL;
            $truck->id_department = (int)$request->get('id_department');
            $truck->id_coordinate = (int)$request->get('id_coordinate');
            $truck->save();

            return new TruckResource($truck);
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
     * @return TruckResource
     */
    public function show($id): ResponseInterface
    {
        try {
            return new TruckResource(Truck::FindOrFail($id));
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
     * @return TruckResource
     */
    public function update(Request $request, $id): ResponseInterface
    {
        try {
            $truck = Truck::findOrFail($id);
            $truck->id_type = (int)$request->get('id_type');
            $truck->id_fire = ((int)$request->get('id_fire')!=0)?(int)$request->get('id_fire'):NULL;
            $truck->id_department = (int)$request->get('id_department');
            $truck->id_coordinate = (int)$request->get('id_coordinate');
            $truck->save();

            return new TruckResource($truck);
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
     * @return TruckResource
     */
    public function destroy($id): ResponseInterface
    {
        try {
            $truck = Truck::findOrFail($id);
            $truck->delete();

            return new TruckResource($truck);
        } catch (\Exception $e) {
            return new ExceptionResponse([
                'status'=>'error',
                'message'=>$e->getMessage(),
                'trace'=>$e->getTrace(),
            ], 400);
        }
    }
}
