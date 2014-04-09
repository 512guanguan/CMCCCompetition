package com.llb.nature.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.llb.nature.R;
import com.llb.nature.domain.SoftwareItem1Bean;

public class Software1ListViewAdapter extends BaseAdapter{
	private ArrayList<SoftwareItem1Bean> list;//存储的是几个item需要的信息
	private Context mContext;
	private ImageView imageView;
	private TextView title,content,downloadTime,size;
	private RatingBar starRating;
	private boolean ifDownload;
	public Software1ListViewAdapter( Context mContext,ArrayList<SoftwareItem1Bean> list){
		this.mContext=mContext;
		this.list=list;
	}
	public Software1ListViewAdapter(Context mContext){
		this.mContext=mContext;
		SoftwareItem1Bean item=null;//=new LeftViewItemBean(R.drawable.leftview, "热门帖子");
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
			convertView=LayoutInflater.from(mContext).inflate(R.layout.software_item1, null);
		}
//		View view=LayoutInflater.from(mContext).inflate(R.layout.left_listview_item, null);
		imageView=(ImageView) convertView.findViewById(R.id.icon);
		title=(TextView) convertView.findViewById(R.id.tv_title);
		content=(TextView) convertView.findViewById(R.id.tv_content);
		starRating=(RatingBar) convertView.findViewById(R.id.ratingbar);
		downloadTime=(TextView) convertView.findViewById(R.id.tv_downloadtimes);
		size=(TextView) convertView.findViewById(R.id.tv_size);
		
		imageView.setImageResource(R.drawable.appicon1);
		title.setText(list.get(position).getTitle());
		content.setText(list.get(position).getContent());
		starRating.setRating(list.get(position).getStarRating());
		downloadTime.setText(list.get(position).getDownloadTime()+"次下载");
		size.setText(list.get(position).getSize());
		if(list.get(position).isIfDownload()){
			//设置下载的图标和文字
		}else {
			//设置打开的图标文字
		}
		return convertView;
	}
}
