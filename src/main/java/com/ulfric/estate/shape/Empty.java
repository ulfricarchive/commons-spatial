package com.ulfric.estate.shape;

import com.ulfric.commons.naming.Name;

@Name("Empty")
public class Empty extends Shape {

	public static final Empty INSTANCE = new Empty();

	public Empty() {
		super(Point.ZERO, Point.ZERO);
	}

	@Override
	public boolean containsPoint(int x, int y, int z) {
		return false;
	}

}
