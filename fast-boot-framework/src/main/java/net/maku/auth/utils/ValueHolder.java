package net.maku.auth.utils;

public class ValueHolder<T> {
	private T value;

	private ValueHolder(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public T get() {
		return value;
	}

	public boolean isPresent() {
		return value != null;
	}

	public static <T> ValueHolder<T> of(T value) {
		return new ValueHolder<>(value);
	}
	public static <T> ValueHolder<T> nil() { return new ValueHolder<T>(null); }

}
