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
import com.llb.nature.domain.NewsItem1Bean;

public class News1ListViewAdapter extends BaseAdapter{
	private ArrayList<NewsItem1Bean> list;//存储的是几个item需要的信息
	private Context mContext;
	private ImageView imageView;
	private TextView title,content,time;
	public News1ListViewAdapter( Context mContext,ArrayList<NewsItem1Bean> list){
		this.mContext=mContext;
		this.list=list;
	}
	public News1ListViewAdapter(Context mContext){
		this.mContext=mContext;
		NewsItem1Bean item=null;//=new LeftViewItemBean(R.drawable.leftview, "热门帖子");
		list.add(item);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return list.get(index);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(R.layout.news_item1, null);
		}
//		View view=LayoutInflater.from(mContext).inflate(R.layout.left_listview_item, null);
		imageView=(ImageView) convertView.findViewById(R.id.icon);
		title=(TextView) convertView.findViewById(R.id.tv_title);
		content=(TextView) convertView.findViewById(R.id.tv_content);
		time=(TextView) convertView.findViewById(R.id.tv_time);
		
		imageView.setImageResource(R.drawable.aa);
		title.setText(list.get(position).getTitle());
		content.setText(list.get(position).getContent());
		time.setText(list.get(position).getTime());
		return convertView;
	}
}
