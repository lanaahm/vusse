<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Transactions extends Model
{
    use HasFactory;
    protected $table = 'transactions';
    protected $guarded = [];
    protected $hidden = ['created_at', 'updated_at'];

    public function transaction_details()
    {
        return $this->hasMany(Transaction_details::class, 'transaction_id', 'id');
    }
    public function users()
    {
        return $this->hasMany(User::class, 'id', 'user_id');
    }
    public function restaurants()
    {
        return $this->belongsTo(Restaurant::class, 'restaurant_id', 'id');
    }
}
