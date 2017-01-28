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
	public int hashCode() {
		try {
			final int prime = 31;
			int result = 1;
			result = prime * result + (getValue() == null ? 0 : getValue().hashCode());
			return result;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		try {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final LazySystemValue other = (LazySystemValue) obj;
			if (getValue() == null) {
				if (other.getValue() != null) {
					return false;
				}
			} else if (!getValue().equals(other.getValue())) {
				return false;
			}
			return true;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
