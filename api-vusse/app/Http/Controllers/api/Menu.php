<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Models\Menu as ModelsMenu;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Facades\Validator;

class Menu extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $data = ModelsMenu::all();
        return response()->json([
            'success' => true,
            'message' => 'Data Menu.',
            'data' => $data
        ], 200);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function create(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'name'=> 'required',
            'price'=> 'required|numeric',
            'foto'=> 'required|mimes:png,jpg',
            'description'=> 'required',
            'restaurant_id'=> 'required',
        ]);
        
        if ($validator->fails()) {
            return response()->json([
                'success' => false,
                'message' => 'Ada kesalahan',
                'data' => $validator->errors()
            ], 401);
        }
        
        $menu = new ModelsMenu();
        $menu->name = $request->name;
        $menu->price = $request->price;
        $menu->description = $request->description;
        $menu->restaurant_id = $request->restaurant_id;
        
        $fileName = $request->file('foto')->getClientOriginalName();
        $path = $request->file('foto')->storeAs('uploads/menus', time().$fileName);
        $menu->foto = url($path);
        $menu->save();

        return response()->json([
            'success' => true,
            'message' => 'Data Berhasil Ditambahkan.',
            'data' => $menu
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
        $data = ModelsMenu::where('id', $id)->first();
        if ($data) {
            return response()->json($data);
        }
        return response()->json([
            'success' => false,
            'message' => 'Data Id Not Found.',
        ], 400);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function showByRestaurant($id)
    {
        $data = ModelsMenu::where('restaurant_id', $id)->get();
        if ($data) {
            return response()->json([
                'success' => true,
                'message' => 'Data Menu.',
                'data' => $data
            ], 200);
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
        if (ModelsMenu::where('id', $id)->exists()) {
            $validator = Validator::make($request->all(), [
                'name'=> 'required',
                'price'=> 'required|numeric',
                'description'=> 'required',
                'restaurant_id'=> 'required',
            ]);
            
            if ($validator->fails()) {
                return response()->json([
                    'success' => false,
                    'message' => 'Ada kesalahan',
                    'data' => $validator->errors()
                ], 401);
            }
                        
            if ($request->hasFile('foto') || $request->foto) {
                $menu = ModelsMenu::find($id);
                $validator = Validator::make($request->all(), [
                    'foto'=> 'required|mimes:png,jpg',
                ]);
                if ($validator->fails()) {
                    return response()->json([
                        'success' => false,
                        'message' => 'Ada kesalahan',
                        'data' => $validator->errors()
                    ], 401);
                }
                
                $doc = ModelsMenu::where('id',$id)->first();
                $file_path = parse_url($doc['foto'], PHP_URL_PATH);
                
                if(Storage::exists($file_path)) {
                   Storage::delete($file_path); 
                }
                $fileName = $request->file('foto')->getClientOriginalName();
                $path = $request->file('foto')->storeAs('uploads/menus', time().$fileName);
                $menu->foto = url($path);
                $menu->name = $request->name;
                $menu->price = $request->price;
                $menu->description = $request->description;
                $menu->restaurant_id = $request->restaurant_id;
                $menu->update();
                return response()->json([
                    'message' => 'Data Berhasil Diubah.',
                    'data' => $menu
                ], 200);
            }
            if(!$request->foto){
                $menu = ModelsMenu::find($id);
                $menu->name = $request->name;
                $menu->price = $request->price;
                $menu->description = $request->description;
                $menu->restaurant_id = $request->restaurant_id;
                $menu->update();
                return response()->json([
                    'success' => true,
                    'message' => 'Data Berhasil Diubah.',
                    'data' => $menu
                ], 200);
            }
        }else{
            return response()->json([
                'success' => false,
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
        $data = ModelsMenu::where('id', $id);
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
