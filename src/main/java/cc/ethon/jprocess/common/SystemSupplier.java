package cc.ethon.jprocess.common;

@FunctionalInterface
public interface SystemSupplier<T> {

	public T get() throws SystemException;

}
