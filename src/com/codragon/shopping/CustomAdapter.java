package com.codragon.shopping;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class CustomAdapter extends ArrayAdapter<Model> implements Filterable{

	private List<Model> list;
	private List<Model> li;
	private final Activity context;
	public static int no;
	String fontPath = "fonts/Face Your Fears.ttf";

	// Model[] modelItems = null;

	// Context context;
	
	public CustomAdapter(Activity context, List<Model> list) {
	    super(context, R.layout.row, list);
	    this.context = context;
	    this.list = list;
	    this.li = list;
	  }
	
	public int getCount () {
	    return list.size ();
	}
	

	static class ViewHolder {
	    protected TextView food, price, orders, showorder;
	    protected CheckBox checkbox;
	    protected Button add, sub;
	    
	    
	  }
	/*
	public CustomAdapter(Context context, Model[] resource) {
		super(context, R.layout.row, resource);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.modelItems = resource;
	}
	*/
	
	 @Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
	   // View view = null;
	   // if (convertView == null) {
	      LayoutInflater inflator = context.getLayoutInflater();
	      convertView = inflator.inflate(R.layout.row, null);
	      
	      final ViewHolder viewHolder = new ViewHolder();
	      viewHolder.food = (TextView) convertView.findViewById(R.id.textView1);
	      viewHolder.price = (TextView) convertView.findViewById(R.id.price);
	      viewHolder.showorder = (TextView) convertView.findViewById(R.id.tvshoworder);
	      viewHolder.add = (Button) convertView.findViewById(R.id.addfood);
	      viewHolder.sub = (Button) convertView.findViewById(R.id.subfood);
	      viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkBox1);
	      viewHolder.add.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stuboas
				//Toast.makeText(context, "You have selected", Toast.LENGTH_SHORT).show();
				//list.get(position).setSelected(true);
				FoodList no_of_orders = new FoodList();
				no_of_orders.nof(list.get(position).getFood().replace("null", ""), context, viewHolder.showorder);
				
			}
		});
	      viewHolder.sub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FoodList no_of_orders = new FoodList();
				no_of_orders.nofnew(list.get(position).getFood().replace("null", ""), context, viewHolder.showorder);
			}
		});
	      viewHolder.checkbox
	          .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
 
	            @Override
	            public void onCheckedChanged(CompoundButton buttonView,
	                boolean isChecked) {
	            	if(isChecked){
	            		Toast.makeText(context, "Pls select the Quantity", Toast.LENGTH_SHORT).show();
	            		//FoodList fl = new FoodList();
	            		//fl.name(list.get(position).getFood().replace("null", ""));
	            		System.out.println("dfdsfaddaf"+position);
	            		System.out.println(list.get(position).getFood().trim().replaceAll(" ", "_"));
	            		//System.out.println(FoodList.table);
	            	}else if(!isChecked){
	            		//FoodList f2 = new FoodList();
	            		//f2.notname(list.get(position).getFood().replace("null", ""));
	            		System.out.println(list.get(position).getFood());
	            	}
	           
	              Model element = (Model) viewHolder.checkbox
	                  .getTag();
	              element.setSelected(buttonView.isChecked());
	            }
	            
	          });
	      convertView.setTag(viewHolder);
	      viewHolder.checkbox.setTag(list.get(position));
	      //System.out.println("drqqqqqqqqqqqqqqqq"+(viewHolder.checkbox.setTag(list.get(position).toString()));
	  //  } else {
	      //view = convertView;
	      ((ViewHolder) convertView.getTag()).checkbox.setTag(list.get(position));
	      
	  //  }
	    ViewHolder holder = (ViewHolder) convertView.getTag();
	    holder.food.setText(list.get(position).getFood().replace("null", ""));
	    holder.price.setText(list.get(position).getPrice());
	    holder.checkbox.setChecked(list.get(position).isSelected());
	    
	    return convertView;
	  }

}