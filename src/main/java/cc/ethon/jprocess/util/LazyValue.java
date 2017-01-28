package cc.ethon.jprocess.util;

import java.util.function.Supplier;

public class LazyValue<T> {

	private T value;
	private boolean isPresent;
	private Supplier<T> supplier;

	public LazyValue(Supplier<T> supplier) {
		value = null;
		isPresent = false;
		this.supplier = supplier;
	}

	public synchronized T getValue() {
		if (!isPresent) {
			value = supplier.get();
			isPresent = true;
			supplier = null;
		}
		return value;
	}

	public boolean isPresent() {
		return isPresent;
	}

	@Override
	public String toString() {
		return "LazyValue [value=" + value + ", isPresent=" + isPresent + "]";
	}

}
