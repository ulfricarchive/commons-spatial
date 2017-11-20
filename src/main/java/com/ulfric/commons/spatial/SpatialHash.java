package com.ulfric.commons.spatial;

import org.apache.commons.collections4.CollectionUtils;

import com.ulfric.commons.collection.Computations;
import com.ulfric.commons.spatial.shape.Point2d;
import com.ulfric.commons.spatial.shape.Shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public final class SpatialHash<V> {

	private final int sectionSize;
	private final Int2ObjectMap<List<Entry>> data = new Int2ObjectOpenHashMap<>();

	public SpatialHash(int sectionSize) {
		this.sectionSize = sectionSize;
		validateSectionSize();
	}

	private void validateSectionSize() {
		if (sectionSize < 1) {
			throw new IllegalArgumentException("Section size must be at least 1, was " + sectionSize);
		}
	}

	public void put(Shape shape, V value) {
		Objects.requireNonNull(shape, "shape");
		Objects.requireNonNull(value, "value");

		Point2d min = shape.getMin();
		Point2d max = shape.getMax();

		int size = this.sectionSize;
		int minX = min.getX() / size;
		int maxX = max.getX() / size;
		int minZ = min.getZ() / size;
		int maxZ = max.getZ() / size;

		Int2ObjectMap<List<Entry>> data = this.data;
		for (int x = minX; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				int packed = this.pack(x, z);
				List<Entry> entries = data.computeIfAbsent(packed, Computations::newArrayListIgnoring);
				entries.add(new Entry(shape, value));
			}
		}
	}

	public void remove(Predicate<V> value) { // TODO stop leaking empty lists if all entries are removed
		for (List<Entry> entries : data.values()) {
			Iterator<Entry> entriesIterator = entries.iterator();

			while (entriesIterator.hasNext()) {
				if (value.test(entriesIterator.next().value)) {
					entriesIterator.remove();
				}
			}
		}
	}

	public List<V> get(int x, int z) {
		List<Entry> entries = getEntries(x, z);

		int size = entries.size();
		List<V> values = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			Entry entry = entries.get(i);
			if (entry.shape.containsPoint(x, z)) {
				values.add(entry.value);
			}
		}
		return values;
	}

	public V getAny(int x, int z) {
		List<Entry> entries = getEntries(x, z);

		for (int i = 0, size = entries.size(); i < size; i++) {
			Entry entry = entries.get(i);
			if (entry.shape.containsPoint(x, z)) {
				return entry.value;
			}
		}

		return null;
	}

	private List<Entry> getEntries(int x , int z) {
		int packed = pack(x / this.sectionSize, z / this.sectionSize);

		List<Entry> entries = data.get(packed);
		if (CollectionUtils.isEmpty(entries)) {
			return Collections.emptyList();
		}

		return entries;
	}

	private int pack(int x, int z) {
		return (x << 16) | (z & 0xFFFF);
	}

	private final class Entry {
		final Shape shape;
		final V value;

		Entry(Shape shape, V value) {
			this.shape = shape;
			this.value = value;
		}
	}

}
