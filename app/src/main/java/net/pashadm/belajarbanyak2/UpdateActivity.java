package net.pashadm.belajarbanyak2;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shagi.materialdatepicker.date.DatePickerFragmentDialog;

import net.pashadm.belajarbanyak2.database.RealmClass;
import net.pashadm.belajarbanyak2.model.DataItem;
import net.pashadm.belajarbanyak2.model.ResponseInsert;
import net.pashadm.belajarbanyak2.retrofit.ApiConfig;
import net.pashadm.belajarbanyak2.worker.UpdateWorker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    private TextInputEditText edPengeluaran;
    private TextInputEditText edKeterangan;
    private TextInputEditText edTanggal;
    private Button btnUpdate;
    private Button btnDelete;
    private String dId;

    //        todo 69 kenalkan Realm Class
    RealmClass realmClass = new RealmClass(UpdateActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

//        todo 37 menerima data yang tadinya dikirim
        String dKeterangan = getIntent().getStringExtra(NoteAdapter.DATAKETERANGAN);
        String dPengeluaran = getIntent().getStringExtra("dataPengeluaran");
        String dTanggal = getIntent().getStringExtra("dataTanggal");
        dId = getIntent().getStringExtra(NoteAdapter.DATAID);

        initView();

//        todo 44 supaya bisa edit tanggal
        edTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragmentDialog dialog = DatePickerFragmentDialog.newInstance(new DatePickerFragmentDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerFragmentDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar kalender = Calendar.getInstance();
                        kalender.set(year,monthOfYear,dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        edTanggal.setText(format.format(kalender.getTime()));
                    }
                });
                dialog.show(getSupportFragmentManager(), "tag");
            }
        });

//        todo 38 masukkan tulisan ke edittext. lalu pastikan dulu berhasil atau enggak
        edKeterangan.setText(dKeterangan);
        edPengeluaran.setText(dPengeluaran);
        edTanggal.setText(dTanggal);

//        todo 41 bikin fungsi update kalo tombol dipencet
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                todo 70 bikin opsi offline
                if (isOnline()){
                    updateData();
                } else {
                    updateDataOffline();
//                    todo 84 supaya memerintahkan si worker
                    updateDataWorker();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                todo 75 belajar delete
                if (isOnline()){
                    deleteDataHuhu();
                } else {
                    deleteDataOffline();
                }
            }
        });
    }

    private void updateDataWorker() {
        WorkManager manager = WorkManager.getInstance();

//        todo 85 buat kirim data nya
        Data.Builder data = new Data.Builder();
        data.putString(NoteAdapter.DATAID, dId);
        data.putString(NoteAdapter.DATAKETERANGAN, edKeterangan.getText().toString());
        data.putString("dataPengeluaran", edPengeluaran.getText().toString());
        data.putString("dataTanggal", edTanggal.getText().toString());
        Data newData = data.build();

//        todo 86 untuk refresh (jalankan worker ketika konek)
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UpdateWorker.class)
                .setInputData(newData)
                .setConstraints(constraints)
                .build();

        manager.enqueue(request);
    }

    //    todo 76 delete ketika offline
    private void deleteDataOffline() {
        realmClass.deleteSatuData(dId);
        finish();
    }

    //    todo 71 buat fungsi update kalo offline
    private void updateDataOffline() {
        DataItem dataItem = new DataItem();
        dataItem.setId(dId);
        dataItem.setKeterangan(edKeterangan.getText().toString());
        dataItem.setPengeluaran(edPengeluaran.getText().toString());
        dataItem.setTanggal(edTanggal.getText().toString());

        realmClass.updateData(dataItem);
        finish();
    }

    //    todo 43 coba kalo delete. ganti fungsi dan parameter. enqueu nya biarkan saja
    private void deleteDataHuhu() {
        Call<ResponseInsert> rekun = ApiConfig.getApiServices().hapuslah(
                dId
        );

//        sama nih copas doang bawah sini
        rekun.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    if (response.body().getResult().equals("true")){
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Kok gabisa? :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //    todo 42 panggil dId tapi gabisa, jadikan global dulu. alt enter, pilih create field. fungsi setelah getApiService sesuai nama fungsi di ApiService
    private void updateData() {
        Call<ResponseInsert> rekues = ApiConfig.getApiServices().ubalah(
                dId,
                edKeterangan.getText().toString(),
                edPengeluaran.getText().toString(),
                edTanggal.getText().toString()
        );

//        copas dari yang tambah tadi
        rekues.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    if (response.body().getResult().equals("true")){
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Kok gabisa? :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    todo 68 supaya cek online dan offline
    private boolean isOnline() {
        ConnectivityManager manajer = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manajer.getActiveNetworkInfo();
        if (info != null && info.isConnected()){
            return true;
        } else {
            return false;
        }
    }

    private void initView() {
        edPengeluaran = (TextInputEditText) findViewById(R.id.edPengeluaran);
        edKeterangan = (TextInputEditText) findViewById(R.id.edKeterangan);
        edTanggal = (TextInputEditText) findViewById(R.id.edTanggal);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
    }
}
