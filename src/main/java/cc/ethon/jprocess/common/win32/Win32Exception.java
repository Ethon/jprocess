package cc.ethon.jprocess.common.win32;

import cc.ethon.jprocess.common.SystemException;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;

public class Win32Exception extends SystemException {

	private static final long serialVersionUID = 3611402016611569736L;

	public Win32Exception() {
	}

	public Win32Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public Win32Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public Win32Exception(String message) {
		super(message);
	}

	public Win32Exception(Throwable cause) {
		super(cause);
	}

	public static Win32Exception fromLastError(String message, int lastError) {
		final String msg = String.format("%s : %s (error code %d)", message, Kernel32Util.formatMessage(lastError), lastError);
		return new Win32Exception(msg);
	}

	public static Win32Exception fromLastError(String message) {
		return fromLastError(message, Kernel32.INSTANCE.GetLastError());
	}

}
