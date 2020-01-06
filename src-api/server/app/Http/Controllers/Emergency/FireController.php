<?php


namespace App\Http\Controllers\Emergency;

use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\Http\Controllers\Controller;

class FireController extends Controller
{
    /**
     * @return JsonResponse
     */
    public function getFires(): JsonResponse
    {
        $row = DB::connection('pgsql')->select('SELECT * FROM fires;');

        return new JsonResponse($row);
    }

    /**
     * @param int $id
     * @return JsonResponse
     */
    public function getFire(int $id): JsonResponse
    {
        $row = DB::connection('pgsql')->selectOne('SELECT * FROM fires WHERE id=:id;', [':id'=>$id]);

        return new JsonResponse($row);
    }

    /**
     * @param Request $request
     * @return JsonResponse
     */
    public function create(Request $request): JsonResponse
    {
        $data = [
            'line' => $request->get('line'),
            'column' => $request->get('column'),
            'intensity' => $request->get('intensity'),
            'coordinate' => $request->get('id_coordinate')
        ];

        $sql = '
            INSERT INTO fires ("line", "column", "intensity", "id_coordinate")
            VALUES ('.$data['line'].', '.$data['column'].', '.$data['intensity'].', '.$data['coordinate'].')
        ';
        DB::connection('pgsql')->insert($sql);

        return new JsonResponse(['status' => 'success']);
    }
}
