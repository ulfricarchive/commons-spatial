package com.ulfric.spatialregions;

import java.util.List;

public interface RegionSpace {

	List<Region> getRegions(int x, int y, int z);

	void add(Region region);

	void remove(Region region);

}