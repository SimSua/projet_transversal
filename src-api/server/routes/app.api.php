<?php

use App\Http\Controllers\Emergency\CoordinateController;
use App\Http\Controllers\Emergency\FireController;
use App\Http\Controllers\Emergency\FireDepartmentController;
use App\Http\Controllers\Emergency\TruckController;
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

Route::post('/fires/update-intensity/{line}/{column}', function(FireController $fireController, int $line, int $column) {
    return $fireController->updateIntensityFromPosition(Request::capture(), $line, $column);
})->where(['line' => '[0-9]+', 'column' => '[0-9]+']);

Route::get('/coordinates/{line}/{column}', function(CoordinateController $coordinateController, int $line, int $column) {
    return $coordinateController->getCoordinateFromGrid($line, $column);
})->where(['line' => '[0-9]+', 'column' => '[0-9]+']);

Route::get('/fire-departments/vehicles/{id}', function(FireDepartmentController $fireDepartmentController, int $id) {
    return $fireDepartmentController->getAllTrucks($id);
})->where(['id' => '[0-9]+']);

Route::post('/trucks/assign-fire/{id}', function(TruckController $truckController, int $id) {
    return $truckController->assignFire(Request::capture(), $id);
})->where(['id' => '[0-9]+']);

Route::post('/trucks/update-coordinate/{id}', function(TruckController $truckController, int $id) {
    return $truckController->updateCoordinate(Request::capture(), $id);
})->where(['id' => '[0-9]+']);
