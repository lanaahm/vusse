<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Models\Restaurant as ModelsRestaurant;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Storage;

class Restaurant extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $data = ModelsRestaurant::all();
        return response()->json([
            'success' => true,
            'message' => 'Data Restoran.',
            'data' => $data
        ], 200);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'name'=> 'required',
            'foto'=> 'required|mimes:png,jpg',
            'rating'=> 'required|numeric|min:1|max:5',
            'latitude'=> 'required',
            'longitude'=> 'required',
        ]);
        
        if ($validator->fails()) {
            return response()->json([
                'success' => false,
                'message' => 'Ada kesalahan',
                'data' => $validator->errors()
            ], 401);
        }
        
        $restaurant = new ModelsRestaurant();
        $restaurant->name = $request->name;
        $restaurant->rating = $request->rating;
        $restaurant->latitude = $request->latitude;
        $restaurant->longitude = $request->longitude;
        
        $fileName = time().$request->file('foto')->getClientOriginalName();
        $path = $request->file('foto')->storeAs('uploads/restaurants', $fileName);
        $restaurant->foto = url($path);
        $restaurant->save();

        return response()->json([
            'message' => 'Data Berhasil Ditambahkan.',
            'data' => $restaurant
        ], 200);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $data = ModelsRestaurant::where('id', $id)->first();
        if ($data) {
            return response()->json($data);
        }
        return response()->json([
            'message' => 'Data Id Not Found.',
        ], 400);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        if (ModelsRestaurant::where('id', $id)->exists()) {
            $validator = Validator::make($request->all(), [
                'name'=> 'required',
                'rating'=> 'required|numeric|min:1|max:5',
                'latitude'=> 'required',
                'longitude'=> 'required',
            ]);
            
            if ($validator->fails()) {
                return response()->json([
                    'success' => false,
                    'message' => 'Ada kesalahan',
                    'data' => $validator->errors()
                ], 401);
            }
                        
            if ($request->hasFile('foto') || $request->foto) {
                $restaurant = ModelsRestaurant::find($id);
                $validator = Validator::make($request->all(), [
                    'foto'=> 'required|mimes:png,jpg'
                ]);
                if ($validator->fails()) {
                    return response()->json([
                        'success' => false,
                        'message' => 'Ada kesalahan',
                        'data' => $validator->errors()
                    ], 401);
                }
                
                $doc = ModelsRestaurant::where('id',$id)->first();
                $file_path = parse_url($doc['foto'], PHP_URL_PATH);
                
                if(Storage::exists($file_path)) {
                   Storage::delete($file_path); 
                }
                $fileName = time().$request->file('foto')->getClientOriginalName();
                $path = $request->file('foto')->storeAs('uploads/restaurants', $fileName);
                $restaurant->foto = url($path);
                $restaurant->name = $request->name;
                $restaurant->rating = $request->rating;
                $restaurant->latitude = $request->latitude;
                $restaurant->longitude = $request->longitude;
                $restaurant->update();
                return response()->json([
                    'message' => 'Data Berhasil Diubah.',
                    'data' => $restaurant
                ], 200);
            }

            if(!$request->hasFile('foto')){
                $restaurant = ModelsRestaurant::find($id);
                $restaurant->name = $request->name;
                $restaurant->rating = $request->rating;
                $restaurant->latitude = $request->latitude;
                $restaurant->longitude = $request->longitude;
                $restaurant->update();
                return response()->json([
                    'message' => 'Data Berhasil Diubah.',
                    'data' => $restaurant
                ], 200);
            }
        }else{
            return response()->json([
                'message' => 'Data Id Not Found.',
            ], 400);
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function delete($id)
    {
        $data = ModelsRestaurant::where('id', $id);
        if ($data->exists()) {
            $doc = $data->first();
            $file_path = parse_url($doc['foto'], PHP_URL_PATH);
            
            if(Storage::exists($file_path)) {
                Storage::delete($file_path);
            }
            
            $data->delete();
            return response()->json([
                'message' => 'Data Berhasil dihapus.'
            ], 200);

        }else{
            return response()->json([
                'message' => 'Data Id Not Found.',
            ], 400);
        }
    }
}
