package com.xbitcool.expandablepulltorefresh;

public class DataStruct {
	private String Name, Descrip;
	
	public DataStruct(){
		super();
	}
	
	public DataStruct(String name, String descrip) {
		super();
		this.Name = name;
		this.Name = descrip;
	}
	
	public void setName(String name){
		this.Name = name;
	}
	
	public void setDescrip(String descrip){
		this.Descrip = descrip;
	}

	public String getName() {
		return this.Name;
	}
	public String getDescrip() {
		return this.Descrip;
	}
	
}
