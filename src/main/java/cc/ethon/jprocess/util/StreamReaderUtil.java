package cc.ethon.jprocess.util;

import java.io.IOException;
import java.io.InputStream;

public class StreamReaderUtil {

	public static int readDecimalInt(InputStream in) throws IOException {
		int value = 0;
		int c;
		while (Character.isDigit(c = in.read())) {
			final int digit = Character.getNumericValue(c);
			value *= 10;
			value += digit;
		}
		return value;
	}

	public static String readEnclosedString(InputStream in, char begin, char end) throws IOException {
		while (in.read() != begin) {
		}

		int c;
		final StringBuffer buffer = new StringBuffer();
		while ((c = in.read()) != end) {
			buffer.append((char) c);
		}
		return buffer.toString();
	}

	public static void skip(InputStream in, int count) throws IOException {
		for (int i = 0; i < count; ++i) {
			in.read();
		}
	}

}
