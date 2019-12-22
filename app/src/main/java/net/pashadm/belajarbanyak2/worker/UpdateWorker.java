package net.pashadm.belajarbanyak2.worker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import net.pashadm.belajarbanyak2.MainActivity;
import net.pashadm.belajarbanyak2.NoteAdapter;
import net.pashadm.belajarbanyak2.UpdateActivity;
import net.pashadm.belajarbanyak2.model.ResponseInsert;
import net.pashadm.belajarbanyak2.retrofit.ApiConfig;

import androidx.work.Worker;
import androidx.work.WorkerParameters;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//todo 79 buat package baru, extends, alt enter 2 kali
public class UpdateWorker extends Worker {

    private Context context;
    public UpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
//        todo 81 buat context
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {

//        todo 80 buat try catch. mas acil gapake Worker
        try {
//            todo 83 ambil string dari noteadapter
            String dId = getInputData().getString(NoteAdapter.DATAID);
            String dKeterangan = getInputData().getString(NoteAdapter.DATAKETERANGAN);
            String dPengeluaran = getInputData().getString("dataPengeluaran");
            String dTanggal = getInputData().getString("dataTanggal");


//            todo 82 copas dari file UpdateActivity, fungsi updateData. string nya diganti yang dideklarasikan diatas
            Call<ResponseInsert> rekues = ApiConfig.getApiServices().ubalah(
                    dId,
                    dKeterangan,
                    dPengeluaran,
                    dTanggal
            );

//        copas dari yang tambah tadi
            rekues.enqueue(new Callback<ResponseInsert>() {
                @Override
                public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        if (response.body().getResult().equals("true")){
//                            todo 87 ini ngebikin dia muncul walau sudah di exit (ketika worker berhasil)
//                            context.startActivity(new Intent(context, MainActivity.class));
                            Toast.makeText(context, "DB nya dah diupdate coy", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseInsert> call, Throwable t) {
                    Toast.makeText(context, "Kok gabisa? :(", Toast.LENGTH_SHORT).show();
                }
            });
            return Worker.Result.success();
        } catch (Exception e){
            return Worker.Result.failure();
        }
    }
}
