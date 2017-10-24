package com.ulfric.commons.spatial;

import java.util.List;

public interface RegionSpace {

	List<Region> getRegions(int x, int z);

	void add(Region region);

	void remove(Region region);

}