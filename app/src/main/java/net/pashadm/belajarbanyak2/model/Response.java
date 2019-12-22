package net.pashadm.belajarbanyak2.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Response{

	@SerializedName("msg")
	private String msg;

	@SerializedName("result")
	private String result;

	@SerializedName("data")
	private List<DataItem> data;

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

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"msg = '" + msg + '\'' + 
			",result = '" + result + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}