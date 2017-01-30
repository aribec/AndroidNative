package edu.uoc.TDDM.practica2;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter{
	Context ctx;
	LayoutInflater lInflater;
	List<String> data;

	ListAdapter(Context context, List<String> data) {
		ctx = context;
		this.data = data;
		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = lInflater.inflate(R.layout.fila, parent, false);
		}

		if (position % 2 == 0) {
			view.setBackgroundColor(Color.LTGRAY);
		} else {
			view.setBackgroundColor(Color.WHITE);
		}

		((TextView) view.findViewById(R.id.text)).setText(data.get(position));
		
		return view;
	}
}