<?php


namespace App\Http\Controllers;


use Illuminate\Support\Facades\DB;

class FireController extends Controller
{
    public function index()
    {
        $row = DB::connection('pgsql')->select('SELECT * FROM fires;');
        dump($row);
    }
}
