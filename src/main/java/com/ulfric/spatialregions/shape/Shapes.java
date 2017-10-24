package com.ulfric.spatialregions.shape;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import com.ulfric.commons.value.Bean;
import com.ulfric.dragoon.reflect.Classes;

import java.util.Map;
import java.util.Objects;

public class Shapes extends Bean {

	private static final Map<String, Class<? extends Shape>> SHAPES = new CaseInsensitiveMap<>();

	static {
		registerShape(Empty.class);
		registerShape(Square.class);
	}

	public static void registerShape(Class<? extends Shape> shape) {
		Objects.requireNonNull(shape, "shape");

		SHAPES.put(getShapeName(shape), shape);
	}

	public static void unregisterShape(Class<? extends Shape> shape) {
		Objects.requireNonNull(shape, "shape");

		SHAPES.remove(getShapeName(shape), shape);
	}

	private static String getShapeName(Class<? extends Shape> shape) {
		return Classes.getNonDynamic(shape).getSimpleName();
	}

	public static Class<? extends Shape> getShape(String name) {
		return SHAPES.get(name);
	}

	private Shapes() {
	}

}