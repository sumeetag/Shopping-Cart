package com.codragon.shopping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Order_ListFragment extends ListFragment {
	ArrayList<String> listItems = new ArrayList<String>();
	Button confirm, add;
	ListView lv;
	String table_no, t;
	String price;
	String[] splited;
	int net, q;
	ArrayList<String> food = new ArrayList<String>();
	int table, pos;
	TextView tv;
	private String order;
	
	public static final String MyPREFERENCES = "MyPrefs" ;
	   public static final String Name = "1"; 
	   
	   SharedPreferences sharedpreferences;
	private String no;

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		// inflater.getContext(), android.R.layout.simple_list_item_1,
		// numbers_text);
		View v = inflater.inflate(R.layout.order_listview, container, false);
		
		sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		
		if (sharedpreferences.contains(Name))
	      {
	         no = (sharedpreferences.getString(Name, no));

	      }
		
		System.out.println("ASDASDasD"+no);
		
		if(no == null){
			Editor editor = sharedpreferences.edit();
		      editor.putString(Name, "0");
		      editor.commit(); 
		      no = "1";
		}
		
		System.out.println("wetewtt"+no);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle b) {
		setHasOptionsMenu(true);
		ArrayAdapter<OrderModel> adapter = new OrderCustomAdapter(
				getActivity(), getModel());
		setListAdapter(adapter);
		super.onActivityCreated(b);
	}

	private List<OrderModel> getModel() {
		List<OrderModel> list = new ArrayList<OrderModel>();
		DatabaseOpenHelper entry = new DatabaseOpenHelper(getActivity());

		try {

			entry.create();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}

		try {

			entry.open();

		} catch (SQLException sqle) {

			throw sqle;

		}

		entry.open();
		food = entry.getFood1("0");
		net = 0;
		q = 0;
		System.out.println(food.size());
		for (int i = 0; i < food.size(); i++) {
			System.out.println(food.get(i));
			int id = entry.getId(food.get(i));
			order = entry.getOrders(id);
			price = entry.getPrice(id);
			int total = Integer.parseInt(order) * Integer.parseInt(price);
			net += total;
			q += Integer.parseInt(order);
			
			//if (Integer.parseInt(nof) >= 1) {
				list.add(get(food.get(i), Integer.toString(total), order));
		
		}
		entry.close();
		list.add(get("Grand Total: ", Integer.toString(net),
				Integer.toString(q)));
		// Initially select one of the items
		return list;
	}

	private OrderModel get(String food, String nof, String q) {
		return new OrderModel(food, nof, q);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		// TODO Add your menu entries here
		inflater.inflate(R.menu.menu, menu);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.confirm:
			if(food.size()>0){
			DatabaseOpenHelper entry = new DatabaseOpenHelper(getActivity());
			LoginDatabase lg = new LoginDatabase(getActivity());
			try {

				entry.create();

			} catch (IOException ioe) {

				throw new Error("Unable to create database");

			}

			try {

				entry.open();
				lg.open();

			} catch (SQLException sqle) {

				throw sqle;

			}
			entry.open();
			lg.open();
			int c1=0,c2=0;
			lg.createEntry("Order "+ no +" :", " ", " ");
			for (int i = 0; i < food.size(); i++) {
				int id = entry.getId(food.get(i));
				order = entry.getOrders(id);
				c2+=Integer.parseInt(order);
				price = entry.getPrice(id);
				int total = Integer.parseInt(order) * Integer.parseInt(price);
				c1+=total;
				System.out.println("dfdsfdassdada"+food.get(i));
				lg.createEntry(food.get(i), Integer.toString(total), order);
			}
			
			lg.createEntry("Total - ", Integer.toString(c1), Integer.toString(c2));
			lg.createEntry("", "", "");
			
			
			lg.close();
			//food = entry.getFood1(FoodList.table);
			
			int n = Integer.parseInt(no);
			n = n+1;
			System.out.println("rrrrrrr"+n);
			
			Editor editor = sharedpreferences.edit();
		      editor.putString(Name, Integer.toString(n));
		      editor.commit(); 
			
			for (int i = 1; i <= entry.getUserDataCount(); i++) {
			
				entry.updateOrders(i);
			}
			entry.close();
			
			}
			
			Toast.makeText(getActivity(), "Your Order has been  placed...", Toast.LENGTH_LONG).show();
			Thread thread = new Thread()
			{
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					try {
						sleep(2000);
						//sleep(5000);
					} catch (InterruptedException e) {
						// TODO: handle exception
						e.printStackTrace();
			        	
					}finally{
						Intent intent = new Intent(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_HOME);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						//Kills all activities of app
						
						System.exit(0);
					
					}
			    }
			};
			
			thread.start();
			
			return true;

		case R.id.add_order:
			getActivity().finish();
			return true;
		}
		return false;

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getActivity().finish();
	}
}
