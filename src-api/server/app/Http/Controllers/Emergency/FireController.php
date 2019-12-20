<?php


namespace App\Http\Controllers\Emergency;

use Illuminate\Support\Facades\DB;
use App\Http\Controllers\Controller;

class FireController extends Controller
{
    public function index()
    {
        $row = DB::connection('pgsql')->select('SELECT * FROM fires;');
        dump($row);
    }
}
