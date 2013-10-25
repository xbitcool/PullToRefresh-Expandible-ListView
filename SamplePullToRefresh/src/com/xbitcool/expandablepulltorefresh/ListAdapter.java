package com.xbitcool.expandablepulltorefresh;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<HashMap <String,Object>>{
	private ArrayList<HashMap<String,Object>> _xb_Items;
	// Cache to Inflater to prevent multiples calls, 
	// Life cicle best practices
	private LayoutInflater mInflater;

	
	public ListAdapter(Context activity, int layoutAdapter, ArrayList<HashMap<String,Object>> _xb_Items) {
		super(activity, layoutAdapter, _xb_Items);
		this._xb_Items = _xb_Items;
		mInflater = LayoutInflater.from(activity);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			v = mInflater.inflate(R.layout.itemlist, null);
			holder = new ViewHolder();
            holder.menuName   = (TextView) v.findViewById(R.id.Name);
            holder.menuDesc   = (TextView) v.findViewById(R.id.Descrip);
            v.setTag(holder);
		}else{
			holder = (ViewHolder) v.getTag();
		}
		HashMap<String,Object> itemHash = _xb_Items.get(position);
	    v.setPadding(5,5,5,5);

		if (itemHash != null){
			//NAME
			try{
				if (holder.menuName != null) {
					String text = (String)itemHash.get("name");
					holder.menuName.setText(text);
				}
			}catch(Exception e){
				holder.menuName.setText("");
			}
			
			//DESCRIP
			try{
				if (holder.menuDesc != null) {
					String text = (String)itemHash.get("descrip");
					holder.menuDesc.setText(text);
				}
			}catch(Exception e){
				holder.menuDesc.setText("");
			}
			
		}
		return v;
	} 

	@Override
	public int getCount() {
		return this._xb_Items.size();
	}
	
	static class ViewHolder {
        TextView menuName,menuDesc;
    }
}
