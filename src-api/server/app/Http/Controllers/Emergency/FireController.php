<?php

namespace App\Http\Controllers\Emergency;

use App\Exceptions\ExceptionResponse;
use App\Exceptions\ResponseInterface;
use App\Http\Resources\FireCollection;
use App\Models\Fire;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Resources\Fire as FireResource;

class FireController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return FireCollection | ExceptionResponse
     */
    public function index(): ResponseInterface
    {
        try {
            return new FireCollection(Fire::All());
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
     * @return FireResource
     */
    public function store(Request $request): ResponseInterface
    {
        try {
            $fire = new Fire();
            $fire->intensity = (int)$request->get('intensity');
            $fire->id_coordinate = (int)$request->get('id_coordinate');
            $fire->save();

            return new FireResource($fire);
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
     * @return FireResource
     */
    public function show($id): ResponseInterface
    {
        try {
            return new FireResource(Fire::FindOrFail($id));
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
     * @return FireResource
     */
    public function update(Request $request, $id): ResponseInterface
    {
        try {
            $fire = Fire::findOrFail($id);
            $fire->intensity = (int)$request->get('intensity');
            $fire->id_coordinate = (int)$request->get('id_coordinate');
            $fire->save();

            return new FireResource($fire);
        } catch (\Exception $e) {
            return new ExceptionResponse([
                'status' => 'error',
                'message' => $e->getMessage(),
                'trace' => $e->getTrace(),
            ], 400);
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param int $id
     * @return FireResource
     */
    public function destroy($id): ResponseInterface
    {
        try {
            $fire = Fire::findOrFail($id);
            $fire->delete();

            return new FireResource($fire);
        } catch (\Exception $e) {
            return new ExceptionResponse([
                'status'=>'error',
                'message'=>$e->getMessage(),
                'trace'=>$e->getTrace(),
            ], 400);
        }
    }

    /**
     * Update the specified resource from storage.
     *
     * @param Request $request
     * @param $id
     * @return FireResource
     */
    public function updateIntensity(Request $request , $id): ResponseInterface
    {
        try {
            $fire = Fire::findOrFail($id);
            $fire->intensity = (int)$request->intensity;
            $fire->save();

            return new FireResource($fire);
        } catch (\Exception $e) {
            return new ExceptionResponse([
                'status'=>'error',
                'message'=>$e->getMessage(),
                'trace'=>$e->getTrace(),
            ], 400);
        }
    }
}
