package cc.ethon.jprocess.common;

public class NativeStringUtil {

	public static String decodeZeroTerminatedChars(char[] chars) {
		int count;
		for (count = 0; chars[count] != 0; ++count) {
		}
		return String.copyValueOf(chars, 0, count).intern();
	}

}
