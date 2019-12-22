package net.pashadm.belajarbanyak2;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import net.pashadm.belajarbanyak2.database.RealmClass;
import net.pashadm.belajarbanyak2.model.DataItem;
import net.pashadm.belajarbanyak2.model.Response;
import net.pashadm.belajarbanyak2.retrofit.ApiConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

//    todo 21 ganti arraylist jadi list, lalu <> dibuat dataitem
    //    todo 7 buat arraylist buat nampung data
    List<DataItem> listData = new ArrayList<>();
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private FloatingActionButton fab;

//    todo 53 deklarasi realm kelas tadi
    RealmClass realmClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        todo 1 buat recycler view. isinya membutuhkan layout item, model data, adapter, layout manager
//        todo 8 1 model data
//        NoteModel note1 = new NoteModel();
//        note1.setId("1");
//        note1.setKeterangan("Jajan");
//        note1.setPengeluaran("30.000");
//        note1.setTanggal("12-02-2018");

//        for (int i = 0; i < 10; i++) {
//            listData.add(note1);
//        }

//        jangan lupa init view
//        todo 54 inisialisasi realm kelas nya
        realmClass = new RealmClass(MainActivity.this);

        initView();

//        todo 63 cek koneksi untuk menentukan ambil data atau tidak
        if (isOnline()){
            getDataOnline();
        } else {
            getDataOffline();
        }

//        todo 33 buat intent
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahDataActivity.class));
            }
        });

//        todo 19 ganti data dummy ke online
        getDataOnline();

//        todo 21 ntar ada merah merah alt enter aja
//        todo 12 2 adapter
        NoteAdapter adapter = new NoteAdapter(MainActivity.this, listData);
        recyclerView.setAdapter(adapter);

//        todo 13 3 layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

//    todo 65 ambil data kalo offline
    private void getDataOffline() {
        listData = realmClass.daftarTampil();

//        adapter nya
        NoteAdapter adapter = new NoteAdapter(MainActivity.this, listData);
        recyclerView.setAdapter(adapter);
    }

    //    todo 64 cek online nya
    private boolean isOnline() {
        ConnectivityManager manajer = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manajer.getActiveNetworkInfo();
        if (info != null && info.isConnected()){
            return true;
        } else {
            return false;
        }
    }

    //    todo 20 buat variabel untuk manggil apiconfig. bisa execute (proses depan) bisa enqueu (proses belakang)
    private void getDataOnline() {
        Call<Response> req = ApiConfig.getApiServices().tampilkanlah();
        req.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()){
                    if (response.body().getResult().equals("true")){
//                        kalo berhasil baca dan memang keterangan datanya benar
                        listData = response.body().getData();

//                        todo 62 hapus data supaya tidak terjadi penumpukan
                        realmClass.deleteData();

//                        todo 55 insert ke db
                        for (int i = 0; i < listData.size(); i++) {
                            DataItem datam = listData.get(i);
                            realmClass.insertData(datam);
                        }

//                        todo 24 ini jangan dilupakan, gunanya untuk refresh recyclerview nya
                        NoteAdapter adapter = new NoteAdapter(MainActivity.this, listData);
                        recyclerView.setAdapter(adapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal nih", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    todo 34 buat onresume supaya melanjutkan setelah finish tambah data

    @Override
    protected void onResume() {
        super.onResume();
//        getDataOnline();
        if (isOnline()){
            getDataOnline();
        } else {
            getDataOffline();
        }
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
    }
}
