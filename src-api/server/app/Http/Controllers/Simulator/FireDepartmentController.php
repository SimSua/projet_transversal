<?php

namespace App\Http\Controllers\Simulator;

use App\Http\Resources\FireDepartmentCollection;
use App\Models\FireDepartment;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Resources\FireDepartment as FireDepartmentResource;

class FireDepartmentController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     */
    public function index()
    {
        return new FireDepartmentCollection(FireDepartment::All());
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param Request $request
     * @return FireDepartmentResource
     */
    public function store(Request $request)
    {
        $fireDepartment = new FireDepartment();
        $fireDepartment->latitude = $request->get('latitude');
        $fireDepartment->longitude = $request->get('longitude');
        $fireDepartment->save();

        return new FireDepartmentResource($fireDepartment);
    }

    /**
     * Display the specified resource.
     *
     * @param int $id
     * @return FireDepartmentResource
     */
    public function show($id)
    {
        return new FireDepartmentResource(FireDepartment::FindOrFail($id));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param Request $request
     * @param int $id
     * @return FireDepartmentResource
     */
    public function update(Request $request, $id)
    {
        $fireDepartment = FireDepartment::findOrFail($id);
        $fireDepartment->latitude = $request->get('latitude');
        $fireDepartment->longitude = $request->get('longitude');
        $fireDepartment->save();

        return new FireDepartmentResource($fireDepartment);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param int $id
     * @return FireDepartmentResource
     */
    public function destroy($id)
    {
        $fireDepartment = FireDepartment::findOrFail($id);
        $fireDepartment->delete();

        return new FireDepartmentResource($fireDepartment);
    }
}
