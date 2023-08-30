<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Hash;

class AuthController extends Controller
{
    public function register(Request $request)
    {
        
        $validator = Validator::make($request->all(), [
            'name'      => 'required',
            'email'     => 'required|unique:users,email',
            'password'  => 'required',
            'phone'     => 'required',
            'confirm_password' => 'required|same:password'
        ]);

        if ($validator->fails()) {
            return response()->json([
                'success' => false,
                'message' => 'Ada kesalahan',
                'data' => $validator->errors()
            ], 401);
        }

        if ($request->has('roles')) {
            $request['roles'] = 'admin';
        }
        
        $request['roles'] = 'user';
        $request['password'] = Hash::make($request['password']);

        $user = new User();
        $user->name = $request->name;
        $user->email = $request->email;
        $user->password = $request->password;
        $user->phone = $request->phone;
        $user->roles = $request->roles;
        $user->save();


        return response()->json([
            'success'   => true,
            'message'   => 'Sukses register',
            'data'      => $user
        ], 200);
    }

    public function login(Request $request)
    {
        
        $user = User::where('email', $request->email)->first();
        if (!$user || !Hash::check($request->password, $user->password)) {
            return response()->json([
                'success' => false,
                'message' => 'Unauthorized'
            ], 401);
        }
        
        
        $user['token'] = $user->createToken('token')->plainTextToken;
        return response()->json([
            'success'   => true,
            'message'   => 'Success Login',
            'user'      => $user
        ], 200);
    }

    public function logout(Request $request)
    {
        $user = $request->user();
        $user->currentAccessToken()->delete();
        return response()->json([
            'success'   => true,
            'message'   => 'Berhasil Logout'
        ], 200);
    }
}
