<?php

namespace App\Http\Controllers\Simulator;

use App\Exceptions\ExceptionResponse;
use App\Exceptions\ResponseInterface;
use App\Http\Resources\FireCollection;
use App\Models\Simulator\Coordinate;
use App\Models\Simulator\Fire;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Resources\Fire as FireResource;

class SimFireController extends Controller
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

    /**
     * Update the specified resource from storage from its position on the grid.
     *
     * @param Request $request
     * @param $id
     * @return FireResource
     */
    public function updateIntensityFromPosition(Request $request , int $line, int $column): ResponseInterface
    {
        try {
            $coordinate = Coordinate::where(['line'=> $line, 'column' => $column])->first();
            $fire = Fire::where('id_coordinate', $coordinate->id)->first();
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

    /**
     * Update the specified resource from storage from its position on the grid.
     *
     * @return JsonResponse
     */
    public function getPosition()
    {
        try {
            $response = [];
            $fires = Fire::All();
            foreach ($fires as $fire) {
                $coordinate = Coordinate::where('id', $fire->id_coordinate)->first();
                $response[] = [
                    'id_fire' => $fire->id,
                    'intensity' => $fire->intensity,
                    'line' => $coordinate->line,
                    'column' => $coordinate->column
                ];
            }

            return new JsonResponse($response);
        } catch (\Exception $e) {
            return new ExceptionResponse([
                'status'=>'error',
                'message'=>$e->getMessage(),
                'trace'=>$e->getTrace(),
            ], 400);
        }
    }
}
