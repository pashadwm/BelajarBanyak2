package net.pashadm.belajarbanyak2.retrofit;

import net.pashadm.belajarbanyak2.model.Response;
import net.pashadm.belajarbanyak2.model.ResponseInsert;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

//todo 15 buat package model lalu generate POJO, pilih gson
//todo 16 buat package dan interface untuk api service
public interface ApiServices {
//    todo 17 bikin base url dan sejenisnya
    @GET("show_cash_note.php")
    Call<Response> tampilkanlah();

//    todo 45 kalo @GET make @QUERY, @POST make @FIELD
//    todo 30 buat post disini. fieldnya berdasarkan data item bagian serialized name
    @FormUrlEncoded
    @POST("add_note.php")
    Call<ResponseInsert> tambahkanlah(
            @Field("keterangan") String keterangan,
            @Field("pengeluaran") String pengeluaran,
            @Field("tanggal") String tanggal
    );

//    todo 40 buat edit disini. kalo pindah gampang bisa make shift shift
    @FormUrlEncoded
    @POST("update_note.php")
    Call<ResponseInsert> ubalah(
        @Field("id") String id,
        @Field("keterangan") String keterangan,
        @Field("pengeluaran") String pengeluaran,
        @Field("tanggal") String tanggal
    );

    @FormUrlEncoded
    @POST("delete_note.php")
    Call<ResponseInsert> hapuslah(
            @Field("id") String id
    );
}