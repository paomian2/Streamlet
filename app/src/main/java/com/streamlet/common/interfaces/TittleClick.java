package com.streamlet.common.interfaces;

public class TittleClick {
	
	OnTitleClickInterface tittleClickInterface;
	
	public void setOnTitleClickInterface(OnTitleClickInterface tittleClickInterface){
		this.tittleClickInterface=tittleClickInterface;
	}
	
	public OnTitleClickInterface getInterface(){
		return tittleClickInterface;
	}
	
	public interface OnTitleClickInterface{
		public void onLeftClick();
		public void onRightClick();
	}

}
