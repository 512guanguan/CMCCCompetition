package com.llb.nature.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.llb.nature.R;
import com.llb.nature.domain.WebAppItem2Bean;

public class WebApp2GridviewAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<WebAppItem2Bean> list;
	private ImageView imageView;
	private TextView title,content;
	
	public WebApp2GridviewAdapter(Context context,
			ArrayList<WebAppItem2Bean> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.webapp_item2, null);
		}
		imageView=(ImageView) convertView.findViewById(R.id.icon);
		title=(TextView) convertView.findViewById(R.id.tv_title);
		content=(TextView) convertView.findViewById(R.id.tv_content);
		
		//imageView设置图标省略
		title.setText(list.get(position).getTitle());
		content.setText(list.get(position).getContent());
		return convertView;
	}

}
