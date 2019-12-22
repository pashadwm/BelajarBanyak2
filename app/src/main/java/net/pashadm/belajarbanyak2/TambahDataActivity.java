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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahDataActivity extends AppCompatActivity {

    private TextInputEditText edPengeluaran;
    private TextInputEditText edKeterangan;
    private TextInputEditText edTanggal;
    private Button btnTambah;

    //    todo 25 buat activity baru untuk tambah data
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        todo 27 biasalah initview
        setContentView(R.layout.activity_tambah_data);
        initView();

        edTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                todo 28 datepicker lagi
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

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()){
                    tambahData();
                } else {
                    tambahDataOffline();
                }
            }
        });
    }

    RealmClass realmClass = new RealmClass(TambahDataActivity.this);
    private void tambahDataOffline() {
        DataItem dataItem = new DataItem();
        dataItem.setKeterangan(edKeterangan.getText().toString());
        dataItem.setPengeluaran(edPengeluaran.getText().toString());
        dataItem.setTanggal(edTanggal.getText().toString());

        realmClass.tambahSatuData(dataItem);
        finish();
    }

    //    todo 77 kasih fungsi isOnline()
    private boolean isOnline() {
        ConnectivityManager manajer = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manajer.getActiveNetworkInfo();
        if (info != null && info.isConnected()){
            return true;
        } else {
            return false;
        }
    }

//    todo 31 ini bikin fungsi yang manggil api services
    private void tambahData() {
        Call<ResponseInsert> minta = ApiConfig.getApiServices().tambahkanlah(
                edKeterangan.getText().toString(),
                edPengeluaran.getText().toString(),
                edTanggal.getText().toString()
        );

        minta.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TambahDataActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    if (response.body().getResult().equals("true")){
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(TambahDataActivity.this, "Kok gabisa? :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        edPengeluaran = (TextInputEditText) findViewById(R.id.edPengeluaran);
        edKeterangan = (TextInputEditText) findViewById(R.id.edKeterangan);
        edTanggal = (TextInputEditText) findViewById(R.id.edTanggal);
        btnTambah = (Button) findViewById(R.id.btnTambah);
    }
}
