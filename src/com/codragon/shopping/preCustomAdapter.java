package com.codragon.shopping;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class preCustomAdapter extends ArrayAdapter<premodel> {
	private final List<premodel> list;
	private final Activity context;

	// Model[] modelItems = null;

	// Context context;

	public preCustomAdapter(Activity context, List<premodel> list) {
		super(context, R.layout.order_list, list);
		this.context = context;
		this.list = list;
	}

	static class ViewHolder {
		protected TextView food;
		protected TextView nof;
		protected TextView q;

	}

	/*
	 * public CustomAdapter(Context context, Model[] resource) { super(context,
	 * R.layout.row, resource); // TODO Auto-generated constructor stub
	 * this.context = context; this.modelItems = resource; }
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.order_list, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.food = (TextView) view.findViewById(R.id.order_food);

			viewHolder.nof = (TextView) view.findViewById(R.id.order_nof);
			viewHolder.q = (TextView) view.findViewById(R.id.q);

			view.setTag(viewHolder);
		} else {
			view = convertView;
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.food.setText(list.get(position).getFood());
		holder.nof.setText(list.get(position).getNof());
		holder.q.setText(list.get(position).getQ());
		return view;
	}

}
