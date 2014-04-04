package com.llb.nature.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.llb.nature.R;
import com.llb.nature.domain.GridViewItemBean;


public class DragGridViewAdapter extends BaseAdapter {

	private Context context;
	private List<GridViewItemBean> itemBeans;
	private GridViewItemBean firstItem,secondItem,lastItem;//用来存在移出来的item
//	private List<String> itemNames;
	private TextView textView;
	private ImageView deleteImageView,itemImageView;
	private View view;//=(View) getItem(getCount()-1);//最后一个
	private int holdPosition;//当前所在的位置，即item索引
	private boolean isChanged = false;
	private boolean ShowItem = false;
	private boolean showDelItem=false;//是否显示删除按钮

	public DragGridViewAdapter(Context mContext, List<GridViewItemBean> itemBeans) {
		this.context = mContext;
		this.itemBeans = itemBeans;
	}

	@Override
	public int getCount() {
		return itemBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return itemBeans.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	//长按item开始浮动交换的过程中，每移动一个位置就调用一次
	public void exchange(int startPosition, int endPosition) {
		holdPosition = endPosition;
		Object startObject = getItem(startPosition);
		Log.i("ON","startPostion ==== " + startPosition );
		Log.i("ON","endPosition ==== " + endPosition );
		if(startPosition < endPosition){
			itemBeans.add(endPosition + 1, (GridViewItemBean) startObject);
			itemBeans.remove(startPosition);
		}else{
			itemBeans.add(endPosition,(GridViewItemBean)startObject);
			itemBeans.remove(startPosition + 1);
		}
		isChanged = true;
		notifyDataSetChanged();
	}
	//移动位置放下的onDrop函数里面调用，让它显示
	public void showDropItem(boolean showItem){
		this.ShowItem = showItem;		
	}
	//决定是否显示删除标记 
	public void setToShowDelItem(boolean showDelItem){
		this.showDelItem=showDelItem;
//		view=getView(getCount()-1, null, null);//(View) getgetItem(getCount()-1);
		if(showDelItem){//true表示正在拖动状态，需要显示其他删除角标 
			lastItem=itemBeans.remove(itemBeans.size()-1);//移除添加按钮
			secondItem=itemBeans.remove(1);//隐藏MM商城
			firstItem=itemBeans.remove(0);//隐藏移动梦网资讯
//			view.setVisibility(View.GONE);
		}else {
			itemBeans.add(0,firstItem);
			itemBeans.add(1,secondItem);
			itemBeans.add(itemBeans.size(),lastItem);//最后添加一个按钮
//			view.setVisibility(View.VISIBLE);
		}
		
		notifyDataSetChanged();
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
//		if (convertView==null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.gridviewitem, null);
//		}
		
		//处理最后一个添加按钮
		if(itemBeans.get(position).getImageUrl()==""){
			convertView=LayoutInflater.from(context).inflate(R.layout.add_item, null);
			return convertView;
			//表示最后一个
		}else {
			//图片再议
			textView = (TextView) convertView.findViewById(R.id.itemtext);
			itemImageView=(ImageView) convertView.findViewById(R.id.itemimage);
			deleteImageView=(ImageView) convertView.findViewById(R.id.deleteitem);
			
			textView.setText( itemBeans.get(position).getName());
			//设置图标，其他的暂时用默认图标
			if(itemBeans.get(position).getImageUrl().equals("0")){//第一个资讯
				itemImageView.setImageResource(R.drawable.mmlogo);
			}else if (itemBeans.get(position).getImageUrl().equals("1")) {//第二个MM商城
				itemImageView.setImageResource(R.drawable.mmlogo);
			}
//			//演示如何监听事件
//			itemImageView.setOnLongClickListener(new OnLongClickListener() {
//				@Override
//				public boolean onLongClick(View v) {
//					Log.i("llb","事件传递到了itemImageView上面！");
//					return true;
//				}
//			});
			
			if (isChanged){//拖动改变
			    if (position == holdPosition){
			    	if(!ShowItem){
				        convertView.setVisibility(View.INVISIBLE);
			    	}
			    }
			}
			//尝试删除,前提是已经显示了删除角标
			if(showDelItem){
				deleteImageView.setVisibility(View.VISIBLE);
				deleteImageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("llb","点击有反应了");
					itemBeans.remove(position);
					notifyDataSetChanged();
				}
				});
			}
		}
		
		return convertView;
	}

}
