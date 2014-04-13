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

public class ActionbarNewsPopupListviewAdapter extends BaseAdapter{
	private ArrayList<String> list;//存储的是几个item需要的信息
	private Context mContext;
	private ImageView imageView;//item上面的
	private TextView textView;
	public ActionbarNewsPopupListviewAdapter( Context mContext,ArrayList<String> list){
		this.mContext=mContext;
		this.list=list;
	}
	public ActionbarNewsPopupListviewAdapter(Context mContext){
		this.mContext=mContext;
		String item=null;//=new LeftViewItemBean(R.drawable.leftview, "热门帖子");
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
			convertView=LayoutInflater.from(mContext).inflate(R.layout.actionbar_news_popup_item, null);
		}
//		View view=LayoutInflater.from(mContext).inflate(R.layout.left_listview_item, null);
//		imageView=(ImageView) convertView.findViewById(R.id.iv_item_add);
		textView=(TextView) convertView.findViewById(R.id.tv_item_name);
//		imageView.setImageResource(list.get(position).getItemImage());
		textView.setText(list.get(position));
		return convertView;
	}
}
