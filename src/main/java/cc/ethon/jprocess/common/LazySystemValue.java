package cc.ethon.jprocess.common;

public class LazySystemValue<T> {

	private T value;
	private boolean isPresent;
	private SystemSupplier<T> supplier;

	public LazySystemValue(T value) {
		this.value = value;
		this.isPresent = true;
		this.supplier = null;
	}

	public LazySystemValue(SystemSupplier<T> supplier) {
		value = null;
		isPresent = false;
		this.supplier = supplier;
	}

	public synchronized T getValue() throws SystemException {
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
		return "LazySystemValue [value=" + value + ", isPresent=" + isPresent + "]";
	}

}
