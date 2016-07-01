package ar.edu.itba.kUltra.helpers;

import ar.edu.itba.kUltra.symbols.MethodSymbol;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DefinedMethods {
	private final String className;
	private final Map<String, MethodSymbol> definedMethods;

	public DefinedMethods(final String className) {
		this.className = className;
		this.definedMethods = new HashMap<>();
	}

	public String getClassName() {
		return className;
	}

	/* forwarded methods from Map */
	public int size() {
		return definedMethods.size();
	}

	public Collection<MethodSymbol> values() {
		return definedMethods.values();
	}

	public MethodSymbol compute(final String key, final BiFunction<? super String, ? super MethodSymbol, ? extends MethodSymbol> remappingFunction) {
		return definedMethods.compute(key, remappingFunction);
	}

	public MethodSymbol putIfAbsent(final String key, final MethodSymbol value) {
		return definedMethods.putIfAbsent(key, value);
	}

	public boolean isEmpty() {
		return definedMethods.isEmpty();
	}

	public MethodSymbol computeIfPresent(final String key, final BiFunction<? super String, ? super MethodSymbol, ? extends MethodSymbol> remappingFunction) {
		return definedMethods.computeIfPresent(key, remappingFunction);
	}

	public boolean containsKey(final Object key) {
		return definedMethods.containsKey(key);
	}

	public MethodSymbol put(final String key, final MethodSymbol value) {
		return definedMethods.put(key, value);
	}

	public void clear() {
		definedMethods.clear();
	}

	public void forEach(final BiConsumer<? super String, ? super MethodSymbol> action) {
		definedMethods.forEach(action);
	}

	public MethodSymbol replace(final String key, final MethodSymbol value) {
		return definedMethods.replace(key, value);
	}

	public MethodSymbol getOrDefault(final Object key, final MethodSymbol defaultValue) {
		return definedMethods.getOrDefault(key, defaultValue);
	}

	public MethodSymbol computeIfAbsent(final String key, final Function<? super String, ? extends MethodSymbol> mappingFunction) {
		return definedMethods.computeIfAbsent(key, mappingFunction);
	}

	public MethodSymbol merge(final String key, final MethodSymbol value, final BiFunction<? super MethodSymbol, ? super MethodSymbol, ? extends MethodSymbol> remappingFunction) {
		return definedMethods.merge(key, value, remappingFunction);
	}

	public MethodSymbol remove(final Object key) {
		return definedMethods.remove(key);
	}

	public boolean containsValue(final Object value) {
		return definedMethods.containsValue(value);
	}

	public MethodSymbol get(final Object key) {
		return definedMethods.get(key);
	}

	public boolean remove(final Object key, final Object value) {
		return definedMethods.remove(key, value);
	}

	public void putAll(final Map<? extends String, ? extends MethodSymbol> m) {
		definedMethods.putAll(m);
	}

	public boolean replace(final String key, final MethodSymbol oldValue, final MethodSymbol newValue) {
		return definedMethods.replace(key, oldValue, newValue);
	}

	public Set<String> keySet() {
		return definedMethods.keySet();
	}

	public void replaceAll(final BiFunction<? super String, ? super MethodSymbol, ? extends MethodSymbol> function) {
		definedMethods.replaceAll(function);
	}

	public Set<Map.Entry<String, MethodSymbol>> entrySet() {
		return definedMethods.entrySet();
	}
}
