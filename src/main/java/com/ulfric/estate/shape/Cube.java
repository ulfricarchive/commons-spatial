package com.ulfric.estate.shape;

import com.ulfric.commons.naming.Name;

@Name("Cube")
public final class Cube extends Shape {

	private final int minX;
	private final int minY;
	private final int minZ;
	private final int maxX;
	private final int maxY;
	private final int maxZ;

	public Cube(Point x, Point z) {
		super(x, z);

		this.minX = min.getX();
		this.minY = min.getY();
		this.minZ = min.getZ();
		this.maxX = max.getX();
		this.maxY = max.getY();
		this.maxZ = max.getZ();
	}

	@Override
	public boolean containsPoint(int x, int y, int z) {
		return x >= this.minX && x <= this.maxX
				&& y >= this.minY && y <= this.maxY
				&& z >= this.minZ && z <= this.maxZ;
	}

}
