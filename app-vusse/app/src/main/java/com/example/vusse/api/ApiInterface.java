package com.example.vusse.api;

import com.example.vusse.model.detailpesananadmin.DetailPesananAdminModel;
import com.example.vusse.model.keranjang.KeranjangModel;
import com.example.vusse.model.listorderadmin.ListOrderAdminModel;
import com.example.vusse.model.login.LoginModel;
import com.example.vusse.model.login.Logout;
import com.example.vusse.model.menu.MenuModel;
import com.example.vusse.model.pesan.PesanModel;
import com.example.vusse.model.register.RegisterModel;
import com.example.vusse.model.restoran.RestoranModel;
import com.example.vusse.model.riwayat.RiwayatModel;
import com.example.vusse.model.updatestatus.UpdateStatusModel;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> loginResponse(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register")
    Call<RegisterModel> registerResponse(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password
    );

    @GET("restaurants")
    Call<RestoranModel> restaurantsResponse(
            @Header("Authorization") String authHeader
    );

    @GET("restaurant/menu/{id}")
    Call<MenuModel> menusResponse(
            @Path("id") Integer id,
            @Header("Authorization") String authHeader
    );

    @GET("transaksi_admin")
    Call<ListOrderAdminModel> listOrderAdminResponse(
            @Header("Authorization") String authHeader
    );

    @GET("transaksi_admin/detail_user/{user_id}/{restaurant_id}")
    Call<DetailPesananAdminModel> detailPesananAdminResponse(
            @Path("user_id") String user_id,
            @Path("restaurant_id") String restaurant_id,
            @Header("Authorization") String authHeader
    );

    @FormUrlEncoded
    @POST("transaksi")
    Call<PesanModel> pesanResponse(
            @Header("Authorization") String authHeader,
            @Field("user_id") String user_id,
            @Field("restaurant_id") String restaurant_id,
            @Field("menu_id") String menu_id
    );

    @GET("transaksi/chart/{id}")
    Call<KeranjangModel> keranjangResponse(
            @Path("id") String id,
            @Header("Authorization") String authHeader
    );

    @GET("transaksi/{id}")
    Call<RiwayatModel> riwayatResponse(
            @Path("id") String id,
            @Header("Authorization") String authHeader
    );

    @FormUrlEncoded
    @POST("transaksi/updateStatus/")
    Call<UpdateStatusModel> updateStatusResponse(
            @Field("user_id") String user_id,
            @Field("transactions_id") String transactions_id,
            @Header("Authorization") String authHeader
    );
    @DELETE("transaksi/{transaction_id}/{user_id}/{menu_id}")
    Call<KeranjangModel> keranjangHapus(
            @Path("transaction_id") Integer transaction_id,
            @Path("user_id") Integer user_id,
            @Path("menu_id") Integer menu_id,
            @Header("Authorization") String authHeader
    );

    @GET("logout")
    Call<Logout> logoutResponse(
            @Header("Authorization") String authHeader
    );
}
