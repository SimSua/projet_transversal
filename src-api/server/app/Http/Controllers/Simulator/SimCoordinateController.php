<?php

namespace App\Http\Controllers\Simulator;

use App\Exceptions\ExceptionResponse;
use App\Exceptions\ResponseInterface;
use App\Http\Resources\CoordinateCollection;
use App\Models\Simulator\Coordinate;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Resources\Coordinate as CoordinateResource;

class SimCoordinateController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return CoordinateCollection | ExceptionResponse
     */
    public function index(): ResponseInterface
    {
        try {
            return new CoordinateCollection(Coordinate::All());
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
     * @return CoordinateResource | ExceptionResponse
     */
    public function store(Request $request): ResponseInterface
    {
        try {
            $coordinate = new Coordinate();
            $coordinate->latitude = (float)$request->get('latitude');
            $coordinate->longitude = (float)$request->get('longitude');
            $coordinate->line = (int)$request->get('line');
            $coordinate->column = (int)$request->get('column');
            $coordinate->save();

            return new CoordinateResource($coordinate);
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
     * @return CoordinateResource | ExceptionResponse
     */
    public function show($id): ResponseInterface
    {
        try {
            return new CoordinateResource(Coordinate::FindOrFail($id));
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
     * @return CoordinateResource | ExceptionResponse
     */
    public function update(Request $request, $id): ResponseInterface
    {
        try {
            $coordinate = Coordinate::findOrFail($id);
            $coordinate->latitude = (float)$request->get('latitude');
            $coordinate->longitude = (float)$request->get('longitude');
            $coordinate->line = (int)$request->get('line');
            $coordinate->column = (int)$request->get('column');
            $coordinate->save();

            return new CoordinateResource($coordinate);
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
     * @return CoordinateResource | ExceptionResponse
     */
    public function destroy($id): ResponseInterface
    {
        try {
            $coordinate = Coordinate::findOrFail($id);
            $coordinate->delete();

            return new CoordinateResource($coordinate);
        } catch (\Exception $e) {
            return new ExceptionResponse([
                'status'=>'error',
                'message'=>$e->getMessage(),
                'trace'=>$e->getTrace(),
            ], 400);
        }
    }
}
