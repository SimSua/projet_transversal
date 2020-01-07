<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/
//Route::get('/fires', 'FireController@index');

Route::apiResource('coordinates','CoordinateController');
Route::apiResource('fires','FireController');
Route::apiResource('fire-departments','FireDepartmentController');
Route::apiResource('vehicle-types','VehicleTypeController');
Route::apiResource('trucks','TruckController');
