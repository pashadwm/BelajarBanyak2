package net.pashadm.belajarbanyak2.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//todo 18 buat java class
public class ApiConfig {
    public static ApiServices getApiServices(){
//        todo 23 buat okhttp untuk mantau logcat
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.97.59/CashNoteServer/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        ApiServices service = retrofit.create(ApiServices.class);
        return service;
    }
}
