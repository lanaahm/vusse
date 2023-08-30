<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Transaction_details extends Model
{
    use HasFactory;
    protected $table = 'transaction_details';
    protected $guarded = [];

    public function menus()
    {
        return $this->belongsTo(Menu::class, 'menu_id', 'id');
    }
    public function transactions()
    {
        return $this->belongsTo(Transactions::class, 'transaction_id', 'id');
    }
}
