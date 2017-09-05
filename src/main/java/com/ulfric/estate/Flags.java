package com.ulfric.estate;

import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.matcher.ElementMatchers;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import com.google.gson.JsonObject;

import com.ulfric.commons.json.JsonHelper;
import com.ulfric.commons.reflect.MethodHelper;
import com.ulfric.commons.reflect.TypeHelper;
import com.ulfric.commons.value.Bean;
import com.ulfric.dragoon.reflect.Classes;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

public class Flags extends Bean {

	private static final Map<String, Class<? extends Flag>> FLAGS = new CaseInsensitiveMap<>();
	private static final String TRAILING_IDENTIFIER = "Flag";

	public static final Flags EMPTY = new Flags();

	public static void registerFlag(Class<? extends Flag> flag) {
		Objects.requireNonNull(flag, "flag");
		validateFlagStructure(flag);

		FLAGS.put(getFlagName(flag), flag);
	}

	private static void validateFlagStructure(Class<?> flag) {
		validateIsInterface(flag);

		flag = Classes.getNonDynamic(flag);
		Method[] methods = flag.getDeclaredMethods();
		validateIsSingular(flag, methods);

		Method method = methods[0];
		validateTakesNoParameters(method);
		validateNotVoid(method);
	}

	private static void validateIsInterface(Class<?> flag) {
		if (!flag.isInterface()) {
			throw new IllegalArgumentException(flag + " must be an interface");
		}
	}

	private static void validateIsSingular(Class<?> flag, Method[] methods) {
		int counter = 0;
		for (Method method : methods) {
			if (MethodHelper.isStatic(method)) {
				continue;
			}

			counter++;
		}

		if (counter != 1) {
			throw new IllegalArgumentException(flag + " declares " + counter + " non-statc methods, must be 1");
		}
	}

	private static void validateTakesNoParameters(Method method) {
		if (method.getParameterCount() > 0) {
			throw new IllegalArgumentException(method + " must not take any parameters");
		}
	}

	private static void validateNotVoid(Method method) {
		if (MethodHelper.returnsVoid(method)) {
			throw new IllegalArgumentException(method + " must not return void");
		}
	}

	public static void removeFlag(Class<? extends Flag> flag) {
		Objects.requireNonNull(flag, "flag");

		FLAGS.remove(getFlagName(flag), flag);
	}

	private static String getFlagName(Class<?> flag) {
		flag = Classes.getNonDynamic(flag);

		String name = flag.getSimpleName();

		if (hasTrailingFlagIdentifier(name)) {
			return name.substring(0, name.length() - TRAILING_IDENTIFIER.length());
		}

		return name;
	}

	private static boolean hasTrailingFlagIdentifier(String name) {
		return name.length() > TRAILING_IDENTIFIER.length() && name.endsWith(TRAILING_IDENTIFIER);
	}

	public static Flags create(JsonObject json) {
		DynamicType.Builder<Flags> builder = Classes.extend(Flags.class);

		for (String flag : json.keySet()) {
			Class<?> type = FLAGS.get(flag);

			if (!TypeHelper.isInterface(type)) {
				continue; // TODO log this
			}

			Method getter = getter(type);

			builder = builder.implement(type)
					.defineField(flag, getter.getReturnType(), Visibility.PRIVATE)
					.method(ElementMatchers.is(getter))
					.intercept(FieldAccessor.ofField(flag));
		}

		Class<? extends Flags> type = load(builder);
		return JsonHelper.read(json, type);
	}

	private static Method getter(Class<?> type) {
		type = Classes.getNonDynamic(type);

		Method nonStatic = null;
		for (Method method : type.getDeclaredMethods()) {
			if (!MethodHelper.isStatic(method)) {
				nonStatic = method;
				break;
			}
		}

		return nonStatic;
	}

	private static Class<? extends Flags> load(DynamicType.Builder<Flags> builder) {
		return builder.make()
				.load(Flags.class.getClassLoader())
				.getLoaded();
	}

}