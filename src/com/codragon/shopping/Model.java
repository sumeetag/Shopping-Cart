package com.codragon.shopping;

public class Model {
	String food, order, rating, price, nof, serves;
	boolean selected;

	// int value; /* 0 -> checkbox disable, 1 -> checkbox enable */

	Model(String food, String order, String price) {
		this.food = food;
		this.order = order;
		this.price = price;
		selected = false;
	}

	public String getFood() {
		return this.food;
	}

	public String getOrder() {
		return this.order;
	}
	
	public String getPrice() {
		return this.price;
	}
	
	
	public void setFood(String food) {
	    this.food = food;
	  }
	
	public void setPrice(String price) {
	    this.price = price;
	  }
	
	public void setOrder(String order) {
	    this.order = order;
	  }
	
	public boolean isSelected() {
	    return selected;
	  }

	  public void setSelected(boolean selected) {
	    this.selected = selected;
	  }

}
