package com.ulfric.estate;

import com.ulfric.commons.naming.Named;
import com.ulfric.commons.value.Bean;
import com.ulfric.commons.value.Weighted;
import com.ulfric.commons.value.builder.Buildable;
import com.ulfric.estate.shape.Shape;

import java.util.Objects;

public final class Region extends Bean implements Named, Weighted, Comparable<Region>, Buildable<Region> {

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder implements com.ulfric.commons.value.builder.Builder<Region> {

		private String name;
		private int weight;
		private Shape bounds;
		private Flags flags;

		Builder() {
		}

		@Override
		public Region build() {
			Objects.requireNonNull(name, "name");

			return new Region(name, weight, bounds, flags());
		}

		private Flags flags() {
			return flags == null ? Flags.EMPTY : flags;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setWeight(int weight) {
			this.weight = weight;
			return this;
		}

		public Builder setBounds(Shape bounds) {
			this.bounds = bounds;
			return this;
		}

		public Builder setFlags(Flags flags) {
			this.flags = flags;
			return this;
		}
	}

	private final String name;
	private final int weight;
	private final Shape bounds;
	private final Flags flags;

	private Region(String name, int weight, Shape bounds, Flags flags) {
		this.name = name;
		this.weight = weight;
		this.bounds = bounds;
		this.flags = flags;
	}

	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public String getName() {
		return name;
	}

	public Shape getBounds() {
		return bounds;
	}

	public Flags getFlags() {
		return flags;
	}

	@Override
	public int compareTo(Region other) {
		int compare = Integer.compare(weight, other.weight);

		if (compare != 0) {
			return compare;
		}

		return name.compareToIgnoreCase(other.name);
	}

	@Override
	public Builder copyToBuilder() {
		return Region.builder()
			.setName(name)
			.setWeight(weight)
			.setBounds(bounds)
			.setFlags(flags);
	}

}