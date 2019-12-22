package net.pashadm.belajarbanyak2.m;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResultItem{

	@SerializedName("jenis_truk")
	private String jenisTruk;

	@SerializedName("harga")
	private String harga;

	@SerializedName("berat")
	private String berat;

	@SerializedName("ukuranks")
	private String ukuranks;

	@SerializedName("ukuranmbl")
	private String ukuranmbl;

	@SerializedName("id")
	private String id;

	public void setJenisTruk(String jenisTruk){
		this.jenisTruk = jenisTruk;
	}

	public String getJenisTruk(){
		return jenisTruk;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setBerat(String berat){
		this.berat = berat;
	}

	public String getBerat(){
		return berat;
	}

	public void setUkuranks(String ukuranks){
		this.ukuranks = ukuranks;
	}

	public String getUkuranks(){
		return ukuranks;
	}

	public void setUkuranmbl(String ukuranmbl){
		this.ukuranmbl = ukuranmbl;
	}

	public String getUkuranmbl(){
		return ukuranmbl;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"jenis_truk = '" + jenisTruk + '\'' + 
			",harga = '" + harga + '\'' + 
			",berat = '" + berat + '\'' + 
			",ukuranks = '" + ukuranks + '\'' + 
			",ukuranmbl = '" + ukuranmbl + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}