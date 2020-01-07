<?php

namespace App\Http\Controllers\Simulator;

use App\Http\Resources\FireCollection;
use App\Models\Simulator\Fire;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Resources\Fire as FireResource;

class SimFireController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     */
    public function index()
    {
        return new FireCollection(Fire::All());
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param Request $request
     * @return FireResource
     */
    public function store(Request $request)
    {
        $fire = new Fire();
        $fire->latitude = $request->get('latitude');
        $fire->longitude = $request->get('longitude');
        $fire->save();

        return new FireResource($fire);
    }

    /**
     * Display the specified resource.
     *
     * @param int $id
     * @return FireResource
     */
    public function show($id)
    {
        return new FireResource(Fire::FindOrFail($id));
    }

    /**
     * Update the specified resource in storage.
     *
     * @param Request $request
     * @param int $id
     * @return FireResource
     */
    public function update(Request $request, $id)
    {
        $fire = Fire::findOrFail($id);
        $fire->latitude = $request->get('latitude');
        $fire->longitude = $request->get('longitude');
        $fire->save();

        return new FireResource($fire);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param int $id
     * @return FireResource
     */
    public function destroy($id)
    {
        $fire = Fire::findOrFail($id);
        $fire->delete();

        return new FireResource($fire);
    }
}
