package ar.edu.itba.kUltra.helpers;

import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;

public abstract class TypeConverter {
	private static final Map<String, Type> STRING_TYPE_MAP;
	static {
		STRING_TYPE_MAP = new HashMap<>();
		STRING_TYPE_MAP.put("void", Type.VOID_TYPE);
		STRING_TYPE_MAP.put("int", Type.getType(Integer.class));
		STRING_TYPE_MAP.put("str", Type.getType(String.class));
	}

	private static final Map<String, String> STRING_TO_STRING_JAVA_TYPE_MAP;
	static {
		STRING_TO_STRING_JAVA_TYPE_MAP = new HashMap<>();
		STRING_TO_STRING_JAVA_TYPE_MAP.put("void", "void");
		STRING_TO_STRING_JAVA_TYPE_MAP.put("int", "int");
		STRING_TO_STRING_JAVA_TYPE_MAP.put("str", "String");
	}

	public static Type getType(final String s) {
		final Type type = STRING_TYPE_MAP.get(s);
		if (type == null) {
			throw new IllegalArgumentException("'" + s + "' is not a valid type");
		}
		return type;
	}

	public static String getJavaTypeString (final String s) {
		final String type = STRING_TO_STRING_JAVA_TYPE_MAP.get(s);
		if (type == null) {
			throw new IllegalArgumentException("'" + s + "' is not a valid java type");
		}
		return type;
	}
}
