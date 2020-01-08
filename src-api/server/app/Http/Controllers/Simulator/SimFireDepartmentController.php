<?php

namespace App\Http\Controllers\Simulator;

use App\Exceptions\ExceptionResponse;
use App\Exceptions\ResponseInterface;
use App\Http\Resources\FireDepartmentCollection;
use App\Models\Simulator\FireDepartment;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Resources\FireDepartment as FireDepartmentResource;

class SimFireDepartmentController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return FireDepartmentCollection | ExceptionResponse
     */
    public function index(): ResponseInterface
    {
        try {
            return new FireDepartmentCollection(FireDepartment::All());
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
     * @return FireDepartmentResource
     */
    public function store(Request $request): ResponseInterface
    {
        try {
            $fireDepartment = new FireDepartment();
            $fireDepartment->label = $request->get('label');
            $fireDepartment->capacity = (int)$request->get('capacity');
            $fireDepartment->id_coordinate = (int)$request->get('id_coordinate');
            $fireDepartment->save();

            return new FireDepartmentResource($fireDepartment);
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
     * @return FireDepartmentResource
     */
    public function show($id): ResponseInterface
    {
        try {
            return new FireDepartmentResource(FireDepartment::FindOrFail($id));
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
     * @return FireDepartmentResource
     */
    public function update(Request $request, $id): ResponseInterface
    {
        try {
            $fireDepartment = FireDepartment::findOrFail($id);
            $fireDepartment->label = $request->get('label');
            $fireDepartment->capacity = (int)$request->get('capacity');
            $fireDepartment->id_coordinate = (int)$request->get('id_coordinate');
            $fireDepartment->save();

            return new FireDepartmentResource($fireDepartment);
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
     * @return FireDepartmentResource
     */
    public function destroy($id): ResponseInterface
    {
        try {
            $fireDepartment = FireDepartment::findOrFail($id);
            $fireDepartment->delete();

            return new FireDepartmentResource($fireDepartment);
        } catch (\Exception $e) {
            return new ExceptionResponse([
                'status'=>'error',
                'message'=>$e->getMessage(),
                'trace'=>$e->getTrace(),
            ], 400);
        }
    }
}
