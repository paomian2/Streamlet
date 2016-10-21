package com.streamlet.appui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter2<E> extends BaseAdapter {

	public LayoutInflater inflater;
	public Context context;
	
	private List<E> mlist=new ArrayList<E>();
	
	public BaseAdapter2(Context context){
		this.context=context;
		inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void addAdapterData(List<E> mlist){
		if(mlist!=null){
			this.mlist.addAll(mlist);
		}
		notifyDataSetChanged();
	}

	/**数据从指定位置插入*/
	public void addAdapterData(int index,List<E> mlist){
		if(mlist!=null){
			this.mlist.addAll(index,mlist);
		}
		notifyDataSetChanged();
	}

	public void addAdapterData(int index, E mObject) {
		if (mlist != null) {
			this.mlist.add(index, mObject);
		}
		notifyDataSetChanged();
	}

	public void resetListAdapterData(List<?extends E> mlist){
		this.mlist.clear();
		this.mlist.addAll(mlist);
		notifyDataSetChanged();
	}

	/**与Activity中的list同步*/
	public void setMlist(List<E> mlist){
		this.mlist=mlist;
	}
	
	public void addAdapterData(E e){
		if(mlist!=null){
			mlist.add(e);
		}
		notifyDataSetChanged();
	}

	public List<E> getMlist(){
		return  mlist;
	}
	
	public void removeAdapterData(E e){
		if(mlist!=null&&mlist.contains(e)){
			mlist.remove(e);
		}
		notifyDataSetChanged();
	}
	
	public void removeAdapterData(int position){
		if(mlist!=null&&mlist.size()>=position){
			mlist.remove(position);
		}
		notifyDataSetChanged();
	}
	
	
	public void clearAdapter(){
		if(mlist!=null){
			mlist.clear();
		}
		notifyDataSetChanged();
	}
	
	public int getAdapterSize(){
		if(mlist!=null){
			return mlist.size();
		}else{
			return 0;
		}
	}

	public List<E> getAdapterData(){
		return  mlist;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public E getItem(int position) {
		// TODO Auto-generated method stub
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
}
	
