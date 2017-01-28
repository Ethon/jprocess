package cc.ethon.jprocess.common;

@FunctionalInterface
public interface SystemPredicate<T> {

	public boolean test(T value) throws SystemException;

}
