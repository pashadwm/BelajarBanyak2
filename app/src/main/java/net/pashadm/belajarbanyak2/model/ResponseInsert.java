package net.pashadm.belajarbanyak2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//todo 29 buatnya copas dari response biasa, hilangkan data
public class ResponseInsert {
    @SerializedName("msg")
    private String msg;

    @SerializedName("result")
    private String result;

    public void setMsg(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }

    public void setResult(String result){
        this.result = result;
    }

    public String getResult(){
        return result;
    }

    @Override
    public String toString(){
        return
                "Response{" +
                        "msg = '" + msg + '\'' +
                        ",result = '" + result + '\'' +
                        "}";
    }
}
