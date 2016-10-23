package com.codragon.shopping;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FoodList extends Activity {

	// String[] foods;
	ArrayList<String> foods;
	public int i = 0;
	ListView lv;
	Model[] modelItems = null;
	String food, order, price;
	Button see_order;
	int tabpos;
	public String position;
	boolean flag;
	int pos, no;
	CheckBox cb;
	TextView nf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.newlayout);
	}

	
	
	public void nof(String position, Activity context, TextView so){
		
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
		int id = entry.getId(position);
		order = entry.getOrders(id);
		price = entry.getPrice(id);
		
		int i = Integer.parseInt(order);
		i++;
		//cb.setChecked(true);
		order = Integer.toString(i);
		so.setText(order);
		//System.out.println(nof);
		System.out.println(position);
		
		/*LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View toastRoot = inflator.inflate(R.layout.toast, null);
		
		Toast toast = new Toast(context);
		TextView tt = (TextView)toastRoot.findViewById(R.id.toast);
		tt.setText("You have selected "+ order + " " + position);
		toast.setView(toastRoot);
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();*/
		/*
		Toast.makeText(context, "You have selected "+ nof + " " + position, Toast.LENGTH_SHORT).show();
		*/
		entry.updateUser(id, position, price, order);
		entry.close();
		
		
	}
	
public void nofnew(String position, Activity context, TextView so){
		
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
		int id = entry.getId(position);
		order = entry.getOrders(id);
		price = entry.getPrice(id);
		int i = Integer.parseInt(order);
		i--;
		if(i>=0){
		order = Integer.toString(i);
		so.setText(order);
		/*LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View toastRoot = inflator.inflate(R.layout.toast, null);
		
		Toast toast = new Toast(context);
		TextView tt = (TextView)toastRoot.findViewById(R.id.toast);
		tt.setText("You have selected "+ order + " " + position);
		toast.setView(toastRoot);
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();*/
		/*
		Toast.makeText(context, "You have selected "+ nof + " " + position, Toast.LENGTH_SHORT).show();
		*/
		entry.updateUser(id, position, price, order);
		}else{
			Toast.makeText(context, "Pls select a valid number", Toast.LENGTH_LONG).show();
		}
		entry.close();
		
		
	}

}
