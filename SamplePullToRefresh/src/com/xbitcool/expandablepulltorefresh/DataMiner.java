package com.xbitcool.expandablepulltorefresh;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataMiner {
	
	public static ArrayList<HashMap<String,Object>> getData(){
		JSONObject menu = new JSONObject();
		try {
			menu.put("name", "Hola");
			menu.put("Descrip", "Mundo");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return fillData(menu);
	}
	
	private static ArrayList<HashMap<String,Object>> fillData(JSONObject itemMenu) {
		ArrayList<DataStruct> menuJson = new ArrayList<DataStruct>();
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
		int menuLength=0;
		try{
			JSONArray jArray = itemMenu.getJSONArray("MenuList");
			for (int i = 0; i < jArray.length(); i++) {
					DataStruct map = new DataStruct();
	                JSONObject e = jArray.getJSONObject(i);
	                String s = e.getString("MenuItem");
	                JSONObject jObject = new JSONObject(s);
	                map.setName(jObject.getString("NAME"));
	                map.setDescrip(jObject.getString("DESCRIP"));
	                menuJson.add(map);
	        }
			menuLength = menuJson.size();
			for (int i = 0; i < menuLength; i++) {
				DataStruct p = menuJson.get(i);
				HashMap<String, Object> personMap = new HashMap<String, Object>();
                personMap.put("name", p.getName());
                personMap.put("descrip", p.getDescrip());
                data.add(personMap);
			}
			return data;
		}catch(Exception e){
			return null;
		}
	}

}
