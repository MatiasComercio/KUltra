package ar.edu.itba.kUltra.helpers;

import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;

public abstract class TypeConverter {
	private static final Map<String, Type> STRING_TYPE_MAP;
	static {
		STRING_TYPE_MAP = new HashMap<>();
		STRING_TYPE_MAP.put("void", Type.VOID_TYPE);
		STRING_TYPE_MAP.put("Integer", Type.INT_TYPE/*Type.getType(Integer.class)*/);
		STRING_TYPE_MAP.put("String", Type.getType(String.class));
	}

	public static Type getType(final String s) {
		final Type type = STRING_TYPE_MAP.get(s);
		if (type == null) {
			throw new IllegalArgumentException("'" + s + "' is not a valid type");
		}
		return type;
	}


	private static final Map<String, String> BOX_TO_UNBOX_MAP;
	static {
		BOX_TO_UNBOX_MAP = new HashMap<>();
		BOX_TO_UNBOX_MAP.put("Void", "void");
		BOX_TO_UNBOX_MAP.put("Integer", "int");
	}
	public static String getUnboxedTypeString(final String s) {
		final String type = BOX_TO_UNBOX_MAP.get(s);
		if (type == null) {
			return s; // could not unwrap
		}
		return type;
	}
}
