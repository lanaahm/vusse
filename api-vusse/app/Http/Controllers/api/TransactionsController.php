<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Models\Restaurant;
use App\Models\Transaction_details;
use App\Models\Transactions;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;

class TransactionsController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $data = Transactions::all();
        return response()->json([
            'success' => true,
            'message' => 'Data Menu.',
            'data' => $data
        ], 200);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  @user_id
     * @param  \Illuminate\Http\Request  @restaurant_id
     * @param  \Illuminate\Http\Request  @menu_id
     * @return \Illuminate\Http\Response
     */
    public function create(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'user_id'=> 'required',
            'restaurant_id'=> 'required',
            'menu_id'=> 'required',
        ]);
        
        if ($validator->fails()) {
            return response()->json([
                'success' => false,
                'message' => 'Ada kesalahan',
                'data' => $validator->errors()
            ], 401);
        }
        
        $cek_pesanan = Transactions::with("restaurants")->where([
            ['user_id', '=', $request->user_id],
            ['status', '=', 'waiting']
        ])->first();

        if ($cek_pesanan && $cek_pesanan->restaurant_id != $request->restaurant_id) {
            return response()->json([
                'success' => false,
                'message' => 'Anda memiliki pesanan yang belum terselesaikan pada '. $cek_pesanan->restaurants->name,
            ], 405);
        } else if ($cek_pesanan && $cek_pesanan->restaurant_id == $request->restaurant_id) {
            $transaksi_details = new Transaction_details();
            $transaksi_details->menu_id = $request->menu_id;
            $transaksi_details->transaction_id = $cek_pesanan->id;
            $transaksi_details->save();
            return response()->json([
                'success' => true,
                'message' => 'Pesanan ditambahkan pada '. $cek_pesanan->restaurants->name,
            ], 200);
        } else {
            $transaksi = DB::table('transactions')->insertGetId([
                'code'=> Hash::make(time().$request->user_id),
                'status'=> 'waiting',
                'restaurant_id'=> $request->restaurant_id,
                'user_id'=> $request->user_id,
                'created_at'=> date('Y-m-d H:i:s'),
                'updated_at'=> date('Y-m-d H:i:s')
            ]); 
    
            $transaksi_details = new Transaction_details();
            $transaksi_details->menu_id = $request->menu_id;
            $transaksi_details->transaction_id = $transaksi;
            $transaksi_details->save();
    
            return response()->json([
                'success' => true,
                'message' => 'Transaksi Berhasil Ditambahkan.',
                'data' => $transaksi
            ], 200);
        }
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function updateStatus(Request $request)
    {
        $restaurant = Transactions::where([
            ['transactions.id', '=', $request->transactions_id],
            ['transactions.user_id', '=', $request->user_id]
        ])->first();

        $restaurant["status"] = "done";
        $restaurant->update();
        return response()->json([
            'message' => 'Transaksi Berhasil.',
            'data' => $restaurant
        ], 200);

    }
    /**
     * Display the specified resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function showChart(Request $request)
    {
        if ($request->payment) {
            $data = DB::table('transactions')
            ->select('transactions.id', 'restaurants.name', 'restaurants.foto', DB::raw('SUM(menus.price) AS amount'))
            ->join('transaction_details', 'transactions.id', '=', 'transaction_details.transaction_id')
            ->join('restaurants', 'transactions.restaurant_id', '=', 'restaurants.id')
            ->join('menus', 'transaction_details.menu_id', '=', 'menus.id')
            ->where([
                ['transactions.user_id', '=', $request->user_id],
                ['transactions.status', '=', 'waiting'],
            ])->groupBy('transactions.restaurant_id')->get();

            return response()->json([
                'success' => true,
                'message' => 'Data Chart anda.',
                'data' => $data
            ], 200);
        }

        $data = DB::table('transactions')
        ->select('transactions.id as transactions_id', 'transactions.user_id', 'menus.id as menu_id', 'menus.name', 'restaurants.name as nama_resto', 'menus.foto', 'menus.price')
        ->join('transaction_details', 'transactions.id', '=', 'transaction_details.transaction_id')
        ->join('restaurants', 'transactions.restaurant_id', '=', 'restaurants.id')
        ->join('menus', 'transaction_details.menu_id', '=', 'menus.id')
        ->where([
            ['transactions.user_id', '=', $request->user_id],
            ['transactions.status', '=', 'waiting'],
        ])->get();

        return response()->json([
            'success' => true,
            'message' => 'Data Chart anda.',
            'data' => $data], 200);
    }
    /**
     * Display the specified resource.
     *
     */
    public function showHistoryAdmin(Request $request)
    {
        if ($request->detail_user) {
            $data = DB::table('transactions')
            ->select('menus.name', 'menus.description', 'menus.price', 'menus.foto')
            ->join('transaction_details', 'transactions.id', '=', 'transaction_details.transaction_id')
            ->join('restaurants', 'transactions.restaurant_id', '=', 'restaurants.id')
            ->join('menus', 'transaction_details.menu_id', '=', 'menus.id')
            ->where([
                ['transactions.user_id', '=', $request->user_id],
                ['transactions.restaurant_id', '=', $request->restaurant_id],
                ['transactions.status', '=', 'waiting'],
            ])->get();
        }else{
            $data = DB::table('transactions')
            ->select('transactions.id', 'restaurants.id as restauran_id', 'restaurants.name as restaurants_name', 'users.id as user_id', 'users.name as user_name')
            ->join('users', 'transactions.user_id', '=', 'users.id')
            ->join('restaurants', 'transactions.restaurant_id', '=', 'restaurants.id')
            ->where(
                'transactions.status', '=', 'waiting'
            )->get();
        }
        if ($data) {
            return response()->json([
                'success' => true,
                'message' => 'Pesanan Admin.',
                'data' => $data
            ], 200);
        }
    }
    /**
     * Display the specified resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function showHistory(Request $request)
    {

        if ($request->restaurant_id) {
            $data = DB::table('transactions')
            ->select('menus.name', 'menus.description', 'menus.price')
            ->join('transaction_details', 'transactions.id', '=', 'transaction_details.transaction_id')
            ->join('restaurants', 'transactions.restaurant_id', '=', 'restaurants.id')
            ->join('menus', 'transaction_details.menu_id', '=', 'menus.id')
            ->where([
                ['transactions.user_id', '=', $request->user_id],
                ['transactions.restaurant_id', '=', $request->restaurant_id],
                ['transactions.status', '=', 'done'],
            ])->get();
        }
        else{
            $data = DB::table('transactions')
            ->select('transactions.id', 'restaurants.name as restaurants_name', 'menus.name as menus_name', 'menus.foto', 'menus.price')
            ->join('transaction_details', 'transactions.id', '=', 'transaction_details.transaction_id')
            ->join('restaurants', 'transactions.restaurant_id', '=', 'restaurants.id')
            ->join('menus', 'transaction_details.menu_id', '=', 'menus.id')
            ->where([
                ['transactions.user_id', '=', $request->user_id],
                ['transactions.status', '=', 'done'],
            ])->get();
        }
        
        if ($data) {
            return response()->json([
                'success' => true,
                'message' => 'Riwayat pesanan anda.',
                'data' => $data
            ], 200);
        }
        return response()->json([
            'message' => 'Data Transaction Not Found.',
        ], 400);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @return \Illuminate\Http\Response
     */
    public function delete(Request $request)
    {
        $data = Transaction_details::where([
            ['transaction_id', $request->transaction_id]
        ]);
        if ($data->exists() && $data->count() > 1) {
            $createdt = $data->first();
            $data = $data->where([
                ['menu_id', $request->menu_id],
                ['created_at', $createdt->created_at]
            ]);
            $data->delete();
            return response()->json([
                'success' => true,
                'message' => 'Pesanan Berhasil dihapus.'
            ], 200);
        }else{
            $chartClean = Transactions::where([
                ['user_id', '=', $request->user_id],
                ['status', '=', 'waiting']
            ]);
            $chartClean->delete();
            return response()->json([
                'success' => true,
                'message' => 'Transaksi Berhasil dihapus.'
            ], 200);
        }
        return response()->json([
            'success' => false,
            'message' => 'Transaksi Gagal dihapus.'
        ], 401);
    }
}
