<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\API\AuthController;
use App\Http\Controllers\API\Menu;
use App\Http\Controllers\API\Restaurant;
use App\Http\Controllers\API\TransactionsController;
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

Route::group(['middleware' => 'auth:sanctum'], function(){
    // restaurants
    Route::get('restaurants/', [Restaurant::class, 'index']);
    Route::get('restaurants/{id}', [Restaurant::class, 'show']);
    Route::post('restaurants/create', [Restaurant::class, 'create']);
    Route::post('restaurants/update/{id}', [Restaurant::class, 'update']);
    Route::delete('restaurants/delete/{id}', [Restaurant::class, 'delete']);
    Route::get('restaurant/menu/{id}', [Menu::class, 'showByRestaurant']);
    
    // menu
    Route::get('menu/', [Menu::class, 'index']);
    Route::get('menu/{id}', [Menu::class, 'show']);
    Route::post('menu/create', [Menu::class, 'create']);
    Route::post('menu/update/{id}', [Menu::class, 'update']);
    Route::delete('menu/delete/{id}', [Menu::class, 'delete']);
    
    // transaksi
    Route::get('transaksi/', [TransactionsController::class, 'index']);
    Route::Post('transaksi/', [TransactionsController::class, 'create']);
    Route::get('transaksi/chart/{user_id}', [TransactionsController::class, 'showChart']);
    Route::get('transaksi/chart/{payment?}/{user_id}', [TransactionsController::class, 'showChart']);
    Route::get('transaksi/{user_id?}/{restaurant_id?}', [TransactionsController::class, 'showHistory']);
    Route::post('transaksi/updateStatus/', [TransactionsController::class, 'updateStatus']);
    Route::delete('transaksi/{transaction_id}/{user_id}/{menu_id}', [TransactionsController::class, 'delete']);
        
    Route::get('transaksi_admin/{detail_user?}/{user_id?}/{restaurant_id?}', [TransactionsController::class, 'showHistoryAdmin']);

    // Transaksi
    Route::get('/logout', [AuthController::class, 'logout']);
});

Route::post('register', [AuthController::class, 'register']);
Route::post('login', [AuthController::class, 'login']);
