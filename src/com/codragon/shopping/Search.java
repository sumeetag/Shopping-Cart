package com.codragon.shopping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Search extends ActionBarActivity {

	// List view
	private ListView lv;

	// Listview Adapter
	ArrayAdapter<Model> adapter;

	// Search EditText
	EditText inputSearch;
	ArrayList<String> products;
	ArrayList<String> product;
	private ActionBar actionBar;
	// ArrayList for Listview
	ArrayList<HashMap<String, String>> productList;

	Model[] modelItems = null;
	String food, order, price;
	Button see_order;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_list);
		actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(false);

		ListView lv = (ListView) findViewById(android.R.id.list);
		// Adding Tabs
		ArrayAdapter<Model> adapter = new CustomAdapter(this, getModel());
		lv.setAdapter(adapter);
		/**
		 * on swiping the viewpager make respective tab selected
		 * */

	}

	private List<Model> getModel() {
		List<Model> list = new ArrayList<Model>();
		DatabaseOpenHelper entry = new DatabaseOpenHelper(this);

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
		for (int i = 1; i <= entry.getUserDataCount(); i++) {
			food = entry.getFood(i);
			order = entry.getOrders(i);
			price = entry.getPrice(i);

			list.add(get(food, order, price));
		}
		entry.close();
		// Initially select one of the items
		// list.get(0).setSelected(true);
		return list;
	}

	private Model get(String food, String orders, String price) {
		return new Model(food, orders, price);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.See_Order:
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			Intent in = new Intent();
			in.setClass(this, Order_List.class);
			startActivity(in);
			return true;
			
		case R.id.See_PreOrder:
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			Intent inn = new Intent();
			inn.setClass(this, pre_orderlist.class);
			startActivity(inn);
			return true;

		}
		return false;
	}
}

