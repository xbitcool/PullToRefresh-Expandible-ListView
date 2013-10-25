/*
 * This code was writen on the labs of Universidad Privada de Santa Cruz de la Sierra UPSA
 * Autor : Christian Helmut Wellz Sanchez
 * 
 * This example contains the handle to PullToRefresh Listview on threads to avoid
 * overloading main thread.
 * 
 * Best practices on app life cicle
 * 
 */
package com.xbitcool.expandablepulltorefresh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.xbitcool.library.PullToRefreshListView;
import com.xbitcool.library.PullToRefreshListView.OnRefreshListener;

public class MainActivity extends Activity implements OnRefreshListener, OnItemClickListener {
	private static String TAG = "com.xbitcool.expandablepulltorefresh";
	private static int TimeOutRequestThread = 10;
	private PullToRefreshListView _xb_List;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_xb_List = (PullToRefreshListView) findViewById(R.id.ExpandablePullToRefresh);
		_xb_List.setTextPullToRefresh("Pull to refresh");
      	_xb_List.setTextReleaseToRefresh("Release to refresh");
      	_xb_List.setTextRefreshing("Refreshing...");
		_xb_List.setOnRefreshListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		View itemView = arg1;
		String Name   = ((TextView)itemView.findViewById(R.id.Name)).getText().toString();
		Toast.makeText(MainActivity.this,Name, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRefresh() {
		//TimeOut to show Refreshing dialog on pulled...
		_xb_List.postDelayed(new Runnable() {
			@Override
			public void run() {
				getDataThreadFuture();
				_xb_List.onRefreshComplete();
			}
		}, 2000);
	}
	
	private void getDataThreadFuture(){
		Thread _dataLoader = new Thread( new Runnable() {	
			@Override
			public void run() {
				try{
					ArrayList<HashMap<String,Object>> menu = new ArrayList<HashMap<String,Object>>();
					ExecutorService executor = Executors.newSingleThreadExecutor();
			        Future<ArrayList<HashMap<String,Object>>> future = executor.submit(new Task());
			        try {
 			            menu = future.get(TimeOutRequestThread, TimeUnit.SECONDS);
 			            final ListAdapter adapter = new ListAdapter(MainActivity.this, R.layout.itemlist, menu);
 			           _xb_List.post(new Runnable() {
 	 				        public void run() {
 	 				        	_xb_List.setAdapter(adapter);
 	 				        	_xb_List.setTextPullToRefresh("Pull to refresh");
 	 				        	_xb_List.setTextReleaseToRefresh("Release to refresh");
 	 				        	_xb_List.setTextRefreshing("Refreshing...");
 	 				        	_xb_List.setOnItemClickListener(MainActivity.this);
 	 				          }
 	 				    });
 			        } catch (TimeoutException e) {
 			        	Log.e(TAG, "Timeout operation...");
 			        }
 			        executor.shutdownNow();
				}catch(Exception e){
					Log.e(TAG, "Error on fetching data...");
				}
			}
		});
		_dataLoader.start();
	}
	
	class Task implements Callable<ArrayList<HashMap<String,Object>>> {
        @Override
        public ArrayList<HashMap<String,Object>> call() throws Exception {
        	return DataMiner.getData();
        }
    }

}
