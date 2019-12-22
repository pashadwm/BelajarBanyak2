package net.pashadm.belajarbanyak2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.pashadm.belajarbanyak2.model.DataItem;

import java.util.ArrayList;
import java.util.List;

//todo 9 buat adapter

//    3 lalu extend ke RecyclerView.Adapter supaya dapat fungsi nya otomatis
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
//    todo 39 ini jadinya kalo dijadikan konstanta. dibuat public supaya bisa diakses dari luar kelas
    public static final String DATAKETERANGAN = "dataKeterangan";
    public static final String DATAID = "id";

    //    1 buat 2 ini dah biasa atau standar
    Context context;
    List<DataItem> data = new ArrayList<>();

//    2 buat constructor, klik kanan, generate constructor

    public NoteAdapter(Context context, List<DataItem> data) {
        this.context = context;
        this.data = data;
    }

//    4 sambungkan ke layout item
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup, false);
//        ctrl alt v bisa otomatis generate variabel
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

//    7 set data
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int posisi) {
//        todo 35 buat jadi variabel dulu aja make ctrl alt v
//        myViewHolder.tvKeterangan.setText(data.get(posisi).getKeterangan());
        final String keterangan = data.get(posisi).getKeterangan();
        myViewHolder.tvKeterangan.setText(keterangan);
        final String pengeluaran = data.get(posisi).getPengeluaran();
        myViewHolder.tvPengeluaran.setText("Rp. "+ pengeluaran);
        final String tanggal = data.get(posisi).getTanggal();
        myViewHolder.tvTanggal.setText(tanggal);
        Glide.with(context).load("https://banner2.kisspng.com/20180604/tq/kisspng-cost-price-service-money-finance-uang-5b156499064111.0984023015281286650256.jpg").into(myViewHolder.ivGambar);

//        todo 36 buat fungsi pindah untuk ubah. pindah sekalian kirim data
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(context, UpdateActivity.class);
//                kirim data. bagian name nya harus sama dengan yang nanti getExtra
//                untuk memudahkan, hilangkan petik lalu jadikan konstanta saja (harus kapital semua)
                pindah.putExtra(DATAKETERANGAN, keterangan);
                pindah.putExtra("dataPengeluaran", pengeluaran);
                pindah.putExtra("dataTanggal", tanggal);
                pindah.putExtra(DATAID, data.get(posisi).getId());

                context.startActivity(pindah);
            }
        });
    }

//    6 ganti return nya buat hitung data
    @Override
    public int getItemCount() {
        return data.size();
    }

//    5 kenalin komponen dalam item. gabisa langsung find view by id
    public class MyViewHolder extends RecyclerView.ViewHolder {
//        deklarasi biasa
        ImageView ivGambar;
        TextView tvKeterangan, tvPengeluaran, tvTanggal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            inisialisasikan saja
            ivGambar = itemView.findViewById(R.id.ivItemGambar);
            tvKeterangan = itemView.findViewById(R.id.tvNama);
            tvPengeluaran = itemView.findViewById(R.id.tvPengeluaran);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
        }
    }
}
