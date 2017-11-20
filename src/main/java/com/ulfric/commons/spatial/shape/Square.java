package com.ulfric.commons.spatial.shape;

import com.ulfric.commons.naming.Name;

@Name("square")
public final class Square extends Shape {

	private final int minX;
	private final int minZ;
	private final int maxX;
	private final int maxZ;

	public Square(Point2d x, Point2d z) {
		super(x, z);

		this.minX = min.getX();
		this.minZ = min.getZ();
		this.maxX = max.getX();
		this.maxZ = max.getZ();
	}

	@Override
	public boolean containsPoint(int x, int z) {
		return x >= this.minX && x <= this.maxX
				&& z >= this.minZ && z <= this.maxZ;
	}

}
