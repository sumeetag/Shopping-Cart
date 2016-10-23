package com.codragon.shopping;

import java.util.ArrayList;
import java.util.List;

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

public class pre_orderfragment extends ListFragment {
	ArrayList<String> listItems = new ArrayList<String>();
	Button confirm, add;
	ListView lv;
	String table_no, t;
	String price, food;
	String[] splited;
	int net, q;
	TextView tv;
	private String order;
	
	
	  	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		// inflater.getContext(), android.R.layout.simple_list_item_1,
		// numbers_text);
		View v = inflater.inflate(R.layout.pre_order, container, false);
		
		

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
		LoginDatabase entry = new LoginDatabase(getActivity());

		boolean diditwork = true;
		try {

			entry.open();
			net = 0;
			q = 0;
			for (int i = 1; i <=entry.getUserDataCount(); i++) {
				
				order = entry.getOrders(i);
				price = entry.getPrice(i);
				food = entry.getName(i);
				/*int total = Integer.parseInt(order) * Integer.parseInt(price);
				net += total;
				q += Integer.parseInt(order);*/
				
				//if (Integer.parseInt(nof) >= 1) {
					list.add(get(food, price, order));
			
			}
			entry.close();
			// Initially select one of the items
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			diditwork = false;
			String error = e.toString();

		

		} finally {
			if (diditwork) {

			}
		}
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
		
		return false;

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getActivity().finish();
	}
}
