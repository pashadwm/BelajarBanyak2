package net.pashadm.belajarbanyak2.database;

import android.content.Context;
import android.widget.Toast;

import net.pashadm.belajarbanyak2.model.DataItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

//todo 48 buat package baru untuk database nya
public class RealmClass {

//    todo 49 atur atur realmnya begini
    private Context context;
    private Realm realm;
    private RealmResults<DataItem> model;

    public RealmClass(Context context) {
        this.context = context;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

//    todo 51 sekarang mengawali dengan insert
    public void insertData(DataItem data){
        realm.beginTransaction();
        realm.copyToRealm(data);
        realm.commitTransaction();
    }

//    todo 60 fungsi delete data
    public void deleteData(){
        realm.beginTransaction();
        RealmResults<DataItem> data = realm.where(DataItem.class).findAll();
        data.deleteAllFromRealm();
        realm.commitTransaction();
    }

//    todo 61 fungsi update data
    public List<DataItem> daftarTampil(){
        model = realm.where(DataItem.class).findAll();
        List<DataItem> data = new ArrayList<>();
        data.addAll(realm.copyFromRealm(model));
        return data;
    }

//    todo 66 buat update data. ikuti sampe todo 65 aja insyaallah dah aman app nya
    public void updateData(DataItem data){
        realm.beginTransaction();
//        todo 72 ini cuma kepake kalo bukan primary key
//        DataItem dataaaa = realm.where(DataItem.class).equalTo("id", data.getId()).findFirst(); //karna datanya cuma 1
        realm.copyToRealmOrUpdate(data);

//        todo 73 ini untuk menguji perubahan
        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                Toast.makeText(context, "Dah Apdet", Toast.LENGTH_SHORT).show();
            }
        });

        realm.commitTransaction();
    }

//    todo 67 kalo delete, lebih baik make string id aja. ke updateActivity
    public void deleteSatuData(String id){
        realm.beginTransaction();
        DataItem dataaaa = realm.where(DataItem.class).equalTo("id", id).findFirst(); //karna datanya cuma 1
        dataaaa.deleteFromRealm();

//        todo 74 ini untuk menguji perubahan
        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                Toast.makeText(context, "Dah Hapus", Toast.LENGTH_SHORT).show();
            }
        });
        realm.commitTransaction();
    }

    public void tambahSatuData(DataItem data) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(data);
        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                Toast.makeText(context, "Dah Tambah", Toast.LENGTH_SHORT).show();
            }
        });

        realm.commitTransaction();
    }
}
