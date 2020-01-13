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

Route::apiResource('coordinates', 'SimCoordinateController');
Route::apiResource('fires','SimFireController');
Route::apiResource('fire-departments','SimFireDepartmentController');
Route::apiResource('vehicle-types','SimVehicleTypeController');
Route::apiResource('trucks','SimTruckController');

Route::post('/fires/update-intensity/{id}', function(SimFireController $fireController, int $id) {
    return $fireController->updateIntensity(Request::capture(), $id);
})->where(['id' => '[0-9]+']);

Route::post('/fires/update-intensity/{line}/{column}', function(SimFireController $fireController, int $line, int $column) {
    return $fireController->updateIntensityFromPosition(Request::capture(), $line, $column);
})->where(['line' => '[0-9]+', 'column' => '[0-9]+']);

Route::get('/fires/position/get', function(SimFireController $fireController) {
    return $fireController->getPosition();
});

Route::get('/fires/reset/all', function(SimFireController $fireController) {
    return $fireController->resetAllFires();
});

Route::get('/fires/active/untreated', function(SimFireController $fireController) {
    return $fireController->getAllActiveUntreatedFires();
});

Route::get('/coordinates/{line}/{column}', function(SimCoordinateController $coordinateController, int $line, int $column) {
    return $coordinateController->getCoordinateFromGrid($line, $column);
})->where(['line' => '[0-9]+', 'column' => '[0-9]+']);

Route::get('/fire-departments/vehicles/{id}', function(SimFireDepartmentController $fireDepartmentController, int $id) {
    return $fireDepartmentController->getAllTrucks($id);
})->where(['id' => '[0-9]+']);

Route::post('/trucks/assign-fire/{id}', function(SimTruckController $truckController, int $id) {
    return $truckController->assignFire(Request::capture(), $id);
})->where(['id' => '[0-9]+']);

Route::post('/trucks/update-coordinate/{id}', function(SimTruckController $truckController, int $id) {
    return $truckController->updateCoordinate(Request::capture(), $id);
})->where(['id' => '[0-9]+']);

Route::get('/trucks/reset/all', function(SimTruckController $truckController) {
    return $truckController->resetAllTrucks();
});
