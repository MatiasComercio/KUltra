package ar.edu.itba.kUltra.helpers;

import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;

public abstract class TypeConverter {
	private static final Map<String, Type> STRING_TYPE_MAP;
	static {
		STRING_TYPE_MAP = new HashMap<>();
		STRING_TYPE_MAP.put("void", Type.VOID_TYPE);
		STRING_TYPE_MAP.put("Integer", Type.getType(Integer.class));
		STRING_TYPE_MAP.put("String", Type.getType(String.class));
	}

	public static Type getType(final String s) {
		final Type type = STRING_TYPE_MAP.get(s);
		if (type == null) {
			throw new IllegalArgumentException("'" + s + "' is not a valid type");
		}
		return type;
	}
}
