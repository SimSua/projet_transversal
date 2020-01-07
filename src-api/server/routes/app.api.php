<?php

use App\Http\Controllers\Emergency\FireController;
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

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::apiResource('coordinates','CoordinateController');
Route::apiResource('fires','FireController');
Route::apiResource('fire-departments','FireDepartmentController');
Route::apiResource('vehicle-types','VehicleTypeController');
Route::apiResource('trucks','TruckController');

Route::post('/fires/update-intensity/{id}', function(FireController $fireController, int $id) {
    return $fireController->updateIntensity(Request::capture(), $id);
})->where(['id' => '[0-9]+']);
