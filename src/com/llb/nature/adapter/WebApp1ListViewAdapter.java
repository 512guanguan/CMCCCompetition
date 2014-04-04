package com.llb.nature.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.llb.nature.R;
import com.llb.nature.domain.WebAppItem1Bean;

public class WebApp1ListViewAdapter extends BaseAdapter{
	private ArrayList<WebAppItem1Bean> list;//存储的是几个item需要的信息
	private Context mContext;
	private ImageView imageView;
	private TextView title,content;
	private Button bt_add;
	public WebApp1ListViewAdapter( Context mContext,ArrayList<WebAppItem1Bean> list){
		this.mContext=mContext;
		this.list=list;
	}
	public WebApp1ListViewAdapter(Context mContext){
		this.mContext=mContext;
		WebAppItem1Bean item=null;//=new LeftViewItemBean(R.drawable.leftview, "热门帖子");
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
			convertView=LayoutInflater.from(mContext).inflate(R.layout.webapp_item1, null);
		}
//		View view=LayoutInflater.from(mContext).inflate(R.layout.left_listview_item, null);
		imageView=(ImageView) convertView.findViewById(R.id.icon);
		title=(TextView) convertView.findViewById(R.id.tv_title);
		content=(TextView) convertView.findViewById(R.id.tv_content);
		bt_add=(Button) convertView.findViewById(R.id.bt_add);
		imageView.setImageResource(R.drawable.mmlogo);
		title.setText(list.get(position).getTitle());
		content.setText(list.get(position).getContent());
		if(list.get(position).isIfAdd()){
			bt_add.setText("打开");
		}else {
			bt_add.setText("添加");
		}
		return convertView;
	}
}
