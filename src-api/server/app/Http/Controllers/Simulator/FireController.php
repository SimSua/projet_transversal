<?php


namespace App\Http\Controllers\Simulator;

use Illuminate\Support\Facades\DB;
use App\Http\Controllers\Controller;

class FireController extends Controller
{
    public function index()
    {
        $row = DB::connection('pgsql_sim')->select('SELECT * FROM fires;');
        dump($row);
    }
}
