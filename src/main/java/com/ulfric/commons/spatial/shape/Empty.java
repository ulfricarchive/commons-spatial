package com.ulfric.commons.spatial.shape;

import com.ulfric.commons.naming.Name;

@Name("empty")
public class Empty extends Shape {

	public static final Empty INSTANCE = new Empty();

	public Empty() {
		super(Point2d.ZERO, Point2d.ZERO);
	}

	@Override
	public boolean containsPoint(int x, int z) {
		return false;
	}

}
