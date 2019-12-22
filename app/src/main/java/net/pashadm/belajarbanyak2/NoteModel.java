package net.pashadm.belajarbanyak2;

//todo 3 buat data model untuk mempaketkan datanya. untuk jadi arraylist
//todo 4 urusin supaya xampp, phpmyadmin, db, htdocs, dll. kalo mau gampang liatnya, download plugin jsonviewer chrome
public class NoteModel {
//    todo 5 buat model sesuai atribut dibutuhkan
    String id;
    String keterangan;
    String tanggal;
    String pengeluaran;

//    todo 6 buat getter dan setter. alt insert

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPengeluaran() {
        return pengeluaran;
    }

    public void setPengeluaran(String pengeluaran) {
        this.pengeluaran = pengeluaran;
    }
}
