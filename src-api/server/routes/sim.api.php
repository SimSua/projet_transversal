<?php

use App\Http\Controllers\Simulator\SimCoordinateController;
use App\Http\Controllers\Simulator\SimFireController;
use App\Http\Controllers\Simulator\SimFireDepartmentController;
use App\Http\Controllers\Simulator\SimTruckController;
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

Route::apiResource('coordinates', 'SimCoordinateController')->middleware('throttle:100,1');
Route::apiResource('fires','SimFireController')->middleware('throttle:100,1');
Route::apiResource('fire-departments','SimFireDepartmentController')->middleware('throttle:100,1');
Route::apiResource('vehicle-types','SimVehicleTypeController')->middleware('throttle:100,1');
Route::apiResource('trucks','SimTruckController')->middleware('throttle:100,1');

Route::post('/fires/update-intensity/{id}', function(SimFireController $fireController, int $id) {
    return $fireController->updateIntensity(Request::capture(), $id);
})->where(['id' => '[0-9]+'])->middleware('throttle:100,1');

Route::post('/fires/update-intensity/{line}/{column}', function(SimFireController $fireController, int $line, int $column) {
    return $fireController->updateIntensityFromPosition(Request::capture(), $line, $column);
})->where(['line' => '[0-9]+', 'column' => '[0-9]+'])->middleware('throttle:100,1');

Route::get('/coordinates/{line}/{column}', function(SimCoordinateController $coordinateController, int $line, int $column) {
    return $coordinateController->getCoordinateFromGrid($line, $column);
})->where(['line' => '[0-9]+', 'column' => '[0-9]+'])->middleware('throttle:100,1');

Route::get('/fire-departments/vehicles/{id}', function(SimFireDepartmentController $fireDepartmentController, int $id) {
    return $fireDepartmentController->getAllTrucks($id)->middleware('throttle:100,1');
})->where(['id' => '[0-9]+'])->middleware('throttle:100,1');

Route::post('/trucks/assign-fire/{id}', function(SimTruckController $truckController, int $id) {
    return $truckController->assignFire(Request::capture(), $id);
})->where(['id' => '[0-9]+'])->middleware('throttle:100,1');

Route::post('/trucks/update-coordinate/{id}', function(SimTruckController $truckController, int $id) {
    return $truckController->updateCoordinate(Request::capture(), $id);
})->where(['id' => '[0-9]+'])->middleware('throttle:100,1');
